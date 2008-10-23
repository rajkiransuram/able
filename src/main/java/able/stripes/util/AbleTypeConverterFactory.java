package able.stripes.util;

import able.guice.GuiceServletContextListner;
import net.sourceforge.stripes.validation.DefaultTypeConverterFactory;
import net.sourceforge.stripes.validation.TypeConverter;

import java.util.Locale;

/**
 * Uses Guice to create new TypeConverters, which means they can get handles to other Guice objects.
 */
public class AbleTypeConverterFactory extends DefaultTypeConverterFactory {
    public TypeConverter getInstance(Class<? extends TypeConverter> clazz, Locale locale) throws Exception {
        TypeConverter converter = GuiceServletContextListner.newInstance(clazz);
        converter.setLocale(locale);

        return converter;
    }

}
