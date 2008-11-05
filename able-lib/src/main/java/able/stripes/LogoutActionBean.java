package able.stripes;

import net.sourceforge.stripes.action.*;

@UrlBinding("/logout")
public class LogoutActionBean extends AbleActionBean {
    @DefaultHandler
    public Resolution logout() {
        getContext().logout();

        addMessage("loggedOut");

        return new RedirectResolution(WelcomeActionBean.class);
    }
}
