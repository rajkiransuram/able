package able.stripes.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface HibernateValidateNestedProperties {
    HibernateValidate[] value();
}
