package able.stripes;

import able.guice.AbleContextListener;
import net.sourceforge.stripes.validation.DefaultTypeConverterFactory;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.BooleanTypeConverter;
import net.sourceforge.stripes.config.Configuration;

import java.util.Locale;
import java.util.List;

/**
 * Uses Guice to create new TypeConverters, which means they can get handles to other Guice objects.
 */
public class AbleTypeConverterFactory extends DefaultTypeConverterFactory {
    private List<Class> hibernateEntities;

    public void init(Configuration configuration) {
        super.init(configuration);

        hibernateEntities = AbleContextListener.getHibernateEntities();
    }

    public TypeConverter getInstance(Class<? extends TypeConverter> clazz, Locale locale) throws Exception {
        TypeConverter converter = AbleContextListener.newInstance(clazz);
        converter.setLocale(locale);

        return converter;
    }

    @SuppressWarnings("unchecked")
    public TypeConverter getTypeConverter(Class forType, Locale locale) throws Exception {
        TypeConverter converter = super.getTypeConverter(forType, locale);

        if (converter == null && hibernateEntities.contains(forType)) {
            GenericHibernateTypeConverter hibernateTypeConverter = AbleContextListener.newInstance(GenericHibernateTypeConverter.class);
            hibernateTypeConverter.setType(forType);
            converter = hibernateTypeConverter;
        }

        return converter;
    }
}
