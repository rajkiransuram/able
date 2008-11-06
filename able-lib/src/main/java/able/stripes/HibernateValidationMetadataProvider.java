package able.stripes;

import net.sourceforge.stripes.exception.StripesRuntimeException;
import net.sourceforge.stripes.util.Log;
import net.sourceforge.stripes.validation.DefaultValidationMetadataProvider;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationMetadata;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class HibernateValidationMetadataProvider extends DefaultValidationMetadataProvider {
    private static final Log log = Log.getInstance(HibernateValidationMetadataProvider.class);

    protected Map<String, ValidationMetadata> loadForClass(Class<?> beanType) {
        Map<String, ValidationMetadata> meta = new HashMap<String, ValidationMetadata>(super.loadForClass(beanType));
        Set<String> seen = new HashSet<String>();
        try {
            for (Class<?> clazz = beanType; clazz != null; clazz = clazz.getSuperclass()) {
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    String propertyName = pd.getName();
                    Method accessor = pd.getReadMethod();
                    Method mutator = pd.getWriteMethod();
                    Field field = null;
                    try {
                        field = clazz.getDeclaredField(propertyName);
                    }
                    catch (NoSuchFieldException e) {
                    }

                    boolean onAccessor = accessor != null
                            && Modifier.isPublic(accessor.getModifiers())
                            && accessor.getDeclaringClass().equals(clazz)
                            && accessor.isAnnotationPresent(HibernateValidateNestedProperties.class);
                    boolean onMutator = mutator != null
                            && Modifier.isPublic(mutator.getModifiers())
                            && mutator.getDeclaringClass().equals(clazz)
                            && mutator.isAnnotationPresent(HibernateValidateNestedProperties.class);
                    boolean onField = field != null
                            && !Modifier.isStatic(field.getModifiers())
                            && field.getDeclaringClass().equals(clazz)
                            && field.isAnnotationPresent(HibernateValidateNestedProperties.class);

                    // I don't think George Boole would like this ...
                    int count = 0;
                    if (onAccessor) ++count;
                    if (onMutator) ++count;
                    if (onField) ++count;

                    // must be 0 or 1
                    if (count > 1) {
                        StringBuilder buf = new StringBuilder(
                                "There are conflicting @HibernateValidate and/or @HibernateValidateNestedProperties annotations in ")
                                .append(clazz)
                                .append(". The following elements are improperly annotated for the '")
                                .append(propertyName)
                                .append("' property:\n");
                        if (onAccessor) {
                            boolean hasNested = accessor
                                    .isAnnotationPresent(HibernateValidateNestedProperties.class);
                            buf.append("--> Getter method ").append(accessor.getName()).append(
                                    " is annotated with ");
                            if (hasNested)
                                buf.append("@HibernateValidateNestedProperties");
                            buf.append('\n');
                        }
                        if (onMutator) {
                            boolean hasNested = mutator
                                    .isAnnotationPresent(HibernateValidateNestedProperties.class);
                            buf.append("--> Setter method ").append(mutator.getName()).append(
                                    " is annotated with ");
                            if (hasNested)
                                buf.append("@HibernateValidateNestedProperties");
                            buf.append('\n');
                        }
                        if (onField) {
                            boolean hasNested = field
                                    .isAnnotationPresent(HibernateValidateNestedProperties.class);
                            buf.append("--> Field ").append(field.getName()).append(
                                    " is annotated with ");
                            if (hasNested)
                                buf.append("@HibernateValidateNestedProperties");
                            buf.append('\n');
                        }
                        throw new StripesRuntimeException(buf.toString());
                    }

                    // after the conflict check, stop processing fields we've already seen
                    if (seen.contains(propertyName))
                        continue;

                    // get the @Validate and/or @ValidateNestedProperties
                    HibernateValidateNestedProperties nested;
                    Class fieldType;
                    if (onAccessor) {
                        nested = accessor.getAnnotation(HibernateValidateNestedProperties.class);
                        fieldType = accessor.getReturnType();
                        seen.add(propertyName);
                    } else if (onMutator) {
                        nested = mutator.getAnnotation(HibernateValidateNestedProperties.class);
                        fieldType = mutator.getParameterTypes()[0];
                        seen.add(propertyName);
                    } else if (onField) {
                        nested = field.getAnnotation(HibernateValidateNestedProperties.class);
                        fieldType = field.getType();
                        seen.add(propertyName);
                    } else {
                        nested = null;
                        fieldType = null;
                    }

                    //noinspection unchecked
                    if (nested != null && fieldType != null
                            && (fieldType.getAnnotation(Entity.class) != null
                            || fieldType.getAnnotation(Embeddable.class) != null)) {
                        HibernateValidate[] validates = nested.value();
                        if (validates != null) {
                            for (HibernateValidate validate : validates) {
                                String fieldName = validate.field();
                                String fullName = propertyName + '.' + fieldName;
                                Class parent = fieldType;

                                while (fieldName.contains(".")) {
                                    int dot = fieldName.indexOf(".");
                                    String subFieldName = fieldName.substring(0, dot);
                                    fieldName = fieldName.substring(dot + 1);
                                    Field subField = parent.getDeclaredField(subFieldName);
                                    Type type = subField.getGenericType();
                                    if (type instanceof ParameterizedType) {
                                        ParameterizedType pt = (ParameterizedType) type;

                                        // if the type is a collection, what we really want is the underlying
                                        // type that the collection represents
                                        if (Collection.class.isAssignableFrom((Class<?>) pt.getRawType())) {
                                            parent = (Class) pt.getActualTypeArguments()[0];
                                        } else {
                                            parent = (Class) pt.getRawType();
                                        }
                                    } else {
                                        parent = (Class) type;
                                    }
                                }

                                mapValidate(meta, parent, validate, fullName, fieldName);
                            }
                        }
                    }
                }
            }
        }
        catch (RuntimeException e) {
            log.error(e, "Failure checking @Validate annotations ", getClass().getName());
            throw e;
        }
        catch (Exception e) {
            log.error(e, "Failure checking @Validate annotations ", getClass().getName());
            StripesRuntimeException sre = new StripesRuntimeException(e.getMessage(), e);
            sre.setStackTrace(e.getStackTrace());
            throw sre;
        }

        // Print out a pretty debug message showing what validations got configured
        StringBuilder builder = new StringBuilder(128);
        for (Map.Entry<String, ValidationMetadata> entry : meta.entrySet()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(entry.getKey());
            builder.append("->");
            builder.append(entry.getValue());
        }
        log.debug("Loaded validations for ActionBean ", beanType.getSimpleName(), ": ", builder
                .length() > 0 ? builder : "<none>");

        return Collections.unmodifiableMap(meta);
    }

    private void mapValidate(Map<String, ValidationMetadata> meta, Class fieldType, HibernateValidate validate, String fullName, String fieldName) throws NoSuchFieldException {
        Field entityField;
        try {
            entityField = fieldType.getDeclaredField(fieldName);
        } catch (Exception e) {
            log.warn("No such field found at ", fullName, " - perhaps you have a typo? Skipping validation binding for now.");
            return;
        }

        StubValidate stub = new StubValidate(validate);

        // check for validation rules applied to the classType's field
        Length length = findAnnotation(entityField, Length.class);
        if (length != null) {
            if (stub.minlength() == -1 && length.min() != 0) {
                stub.setMinlength(length.min());
            }
            if (stub.maxlength() == -1 && length.max() != 2147483647) {
                stub.setMaxlength(length.max());
            }
        }

        Range range = findAnnotation(entityField, Range.class);
        if (range != null) {
            if (stub.minvalue() == Double.MIN_VALUE && range.min() != -9223372036854775808L) {
                stub.setMinvalue(range.min());
            }
            if (stub.maxvalue() == Double.MAX_VALUE && range.max() != 9223372036854775807L) {
                stub.setMaxvalue(range.max());
            }
        }

        NotNull notNull = findAnnotation(entityField, NotNull.class);
        if (notNull != null) {
            stub.setRequired(true);
        }

        Email email = findAnnotation(entityField, Email.class);
        if (email != null) {
            if (stub.converter().equals(TypeConverter.class)) {
                stub.setConverter(EmailTypeConverter.class);
            }
        }

        if (meta.containsKey(fullName)) {
            log.warn("More than one nested @HibernateValidate with same mapping: " + fullName);
        }

        meta.put(fullName, new ValidationMetadata(fullName, stub));
    }

    private <T extends Annotation> T findAnnotation(Field field, Class<T> ann) {
        return findAnnotation(field, null, null, ann);
    }

    private <T extends Annotation> T findAnnotation(Field field, Method getter, Method setter, Class<T> ann) {
        T result;

        if (getter != null) {
            result = getter.getAnnotation(ann);

            if (result != null) {
                return result;
            }
        }

        if (setter != null) {
            result = setter.getAnnotation(ann);

            if (result != null) {
                return result;
            }
        }

        return field.getAnnotation(ann);
    }
}
