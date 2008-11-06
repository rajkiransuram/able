package able.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.validation.ScopedLocalizableError;

public class BetterScopedLocalizableError extends ScopedLocalizableError {
    public BetterScopedLocalizableError(String defaultScope, String key, Object... parameters) {
        super(defaultScope, key, parameters);
    }

    public void setBeanclass(Class<? extends ActionBean> beanclass) {
        if (beanclass.getName().contains("$$")) {
            //noinspection unchecked
            beanclass = (Class<? extends ActionBean>) beanclass.getSuperclass();
        }

        super.setBeanclass(beanclass);
    }
}
