package able.stripes;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.Validate;

import java.lang.annotation.Annotation;

@SuppressWarnings({"ClassExplicitlyAnnotation"})
public class StubValidate implements Validate {
    String field = "";
    boolean encrypted;
    boolean required;
    boolean trim = true;
    String[] on = new String[0];
    boolean ignore;
    int minlength = -1;
    int maxlength = -1;
    double minvalue = Double.MIN_VALUE;
    double maxvalue = Double.MAX_VALUE;
    String mask = "";
    String expression = "";
    Class<? extends TypeConverter> converter = TypeConverter.class;
    String label = "";

    public StubValidate(HibernateValidate validate) {
        field = validate.field();
        encrypted = validate.encrypted();
        trim = validate.trim();
        on = validate.on();
        ignore = validate.ignore();
        minlength = validate.minlength();
        maxlength = validate.maxlength();
        minvalue = validate.minvalue();
        maxvalue = validate.maxvalue();
        mask = validate.mask();
        expression = validate.expression();
        converter = validate.converter();
        label = validate.label();
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    public void setOn(String[] on) {
        this.on = on;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public void setMinlength(int minlength) {
        this.minlength = minlength;
    }

    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public void setMinvalue(double minvalue) {
        this.minvalue = minvalue;
    }

    public void setMaxvalue(double maxvalue) {
        this.maxvalue = maxvalue;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setConverter(Class<? extends TypeConverter> converter) {
        this.converter = converter;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String field() {
        return field;
    }

    public boolean encrypted() {
        return encrypted;
    }

    public boolean required() {
        return required;
    }

    public boolean trim() {
        return trim;
    }

    public String[] on() {
        return on;
    }

    public boolean ignore() {
        return ignore;
    }

    public int minlength() {
        return minlength;
    }

    public int maxlength() {
        return maxlength;
    }

    public double minvalue() {
        return minvalue;
    }

    public double maxvalue() {
        return maxvalue;
    }

    public String mask() {
        return mask;
    }

    public String expression() {
        return expression;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends TypeConverter> converter() {
        return converter;
    }

    public String label() {
        return label;
    }

    public Class<? extends Annotation> annotationType() {
        return Validate.class;
    }
}
