package able.stripes;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import java.util.Locale;
import java.util.Collection;

import org.hibernate.Session;
import com.google.inject.Provider;
import com.google.inject.Inject;

public class GenericHibernateTypeConverter implements TypeConverter {
    private Provider<Session> sessionProvider;
    private Class type;

    @Inject
    public GenericHibernateTypeConverter(Provider<Session> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public void setLocale(Locale locale) {
    }

    public Object convert(String input, Class targetType, Collection errors) {
        try {
            long id = Long.parseLong(input);
            Session session = sessionProvider.get();
            //noinspection unchecked
            return session.get(type, id);
        } catch (NumberFormatException e) {
            errors.add(new BetterScopedLocalizableError(type.getName(), "invalidId"));

            return null;
        }
    }
}
