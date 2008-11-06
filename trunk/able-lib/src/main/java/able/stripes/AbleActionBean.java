package able.stripes;

import able.stripes.BetterScopedLocalizableError;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.LocalizableMessage;

public abstract class AbleActionBean implements ActionBean {
    protected ActionBeanContext context;

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public ActionBeanContext getContext() {
        return context;
    }

    protected void addMessage(String key, Object... params) {
        getContext().getMessages().add(new LocalizableMessage(key, params));
    }

    protected void addError(String field, String defaultScope, String key, Object... params) {
        getContext().getValidationErrors().add(field, new BetterScopedLocalizableError(defaultScope, key, params));
    }

    protected void addGlobalError(String defaultScope, String key, Object... params) {
        getContext().getValidationErrors().addGlobalError(new BetterScopedLocalizableError(defaultScope, key, params));
    }
}
