package able.stripes;

import net.sourceforge.stripes.validation.TypeConverter;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface HibernateValidate {
    String field();

    /**
     * If true, then a parameter value to be bound to this field must be an encrypted string. It also implies that when
     * the value of this field is rendered by certain tags (e.g., {@link net.sourceforge.stripes.tag.InputHiddenTag})
     * that it is to be rendered as an encrypted string. This prevents clients from injecting random values.
     */
    boolean encrypted() default false;

    /**
     * If set to true, requires that a non-null, non-empty value must be submitted for the field.
     */
    boolean required() default false;

    /**
     * Trim white space from the beginning and end of request parameter values before attempting validation, type
     * conversion or binding.
     *
     * @see String#trim()
     */
    boolean trim() default true;

    /**
     * <p>If required=true, restricts the set of events to which the required check is applied. If required=false (or
     * omitted) this setting has <i>no effect</i>. This setting is entirely optional and if omitted then the field will
     * simply be required for all events.</p>
     * <p/>
     * <p>Can be specified as either a positive list (e.g. on={"save, "update"}) in which case the required check will
     * be performed only on the listed events.  Can also be specified as an inverted list (e.g. on="!new") in which case
     * the check will be performed on all events that are not listed.  Cannot contain a mix of "!event" and "event"
     * since it does not make sense! </p>
     */
    String[] on() default {};

    /**
     * If set to true will cause the property to be ignore by binding and validation even if it was somehow submitted in
     * the request.
     *
     * @since Stripes 1.1
     */
    boolean ignore() default false;

    /**
     * Specifies a minimum length of characters that must be submitted. This validation is performed on the String value
     * before any other validations or conversions are made.
     */
    int minlength() default -1;

    /**
     * Specifies a maximum length of characters that must be submitted. This validation is performed on the String value
     * before any other validations or conversions are made.
     */
    int maxlength() default -1;

    /**
     * Specifies the minimum numeric value acceptable for a numeric field. This validation is performed after the field
     * has been converted to it's java type. This validation is only valid on numeric types (including BigInteger and
     * BigDecimal).
     */
    double minvalue() default Double.MIN_VALUE;

    /**
     * Specifies the maximum numeric value acceptable for a numeric field. This validation is performed after the field
     * has been converted to it's java type. This validation is only valid on numeric types (including BigInteger and
     * BigDecimal).
     */
    double maxvalue() default Double.MAX_VALUE;

    /**
     * Specifies a regular expression mask to be used to check the format of the String value submitted. The mask will
     * be compiled into a java.util.regex.Pattern for use.
     */
    String mask() default "";

    /**
     * <p>Specifies an expression in the JSP expression language that should be evaluated to check the validity of this
     * field.  In the case of lists, arrays and maps the expression is evaluated once for each value supplied.  The
     * expression is evaluated <i>only if a value is supplied</i> - it will not be evaluated if the user did not supply
     * a value for the field.</p>
     * <p/>
     * <p>The value being validated is available in the EL variable 'this'.  Properties of the ActionBean (including the
     * context) can be referenced directly, as can values in request and session scope if necessary.</p>
     * <p/>
     * <p>Note: it is not necessary to encapsulate the expression in ${} as on a JSP page.</p>
     */
    String expression() default "";

    /**
     * A type converter to use to convert this field from String to it's rich object type. If none is specified (which
     * should be very common) then the default converter for the target type of object will be used.
     */
    @SuppressWarnings("unchecked") Class<? extends TypeConverter> converter() default TypeConverter.class;

    /**
     * The natural language name to use for the field when reporting validation errors, generating form input labels,
     * etc. This will only be used if a localized field name cannot be found in the resource bundle.
     *
     * @see net.sourceforge.stripes.localization.LocalizationUtility#getLocalizedFieldName(String, String, Class,
     *      java.util.Locale)
     */
    String label() default "";
}
