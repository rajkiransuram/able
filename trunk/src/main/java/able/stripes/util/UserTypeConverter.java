package able.stripes.util;

import com.google.inject.Inject;
import able.user.User;
import able.user.UserManager;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import java.util.Collection;
import java.util.Locale;

public class UserTypeConverter implements TypeConverter<User> {
    private UserManager userManager;

    @Inject
    public UserTypeConverter(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setLocale(Locale locale) {
    }

    public User convert(String input, Class<? extends User> targetType, Collection<ValidationError> errors) {
        try {
            long id = Long.parseLong(input);
            return userManager.findById(id);
        } catch (NumberFormatException e) {
            errors.add(new BetterScopedLocalizableError("user", "invalidId"));

            return null;
        }
    }
}
