package able.stripes;

import com.google.inject.Inject;
import able.stripes.util.JSP;
import able.user.UserManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/welcome")
public class WelcomeActionBean extends AbleActionBean {
    private UserManager userManager;

    @Inject
    public WelcomeActionBean(UserManager userManager) {
        this.userManager = userManager;
    }

    @DefaultHandler
    public Resolution welcome() {
        if (getContext().isLoggedIn()) {
            return new RedirectResolution(HomeActionBean.class);
        }

        return new JSP("welcome.jsp");
    }

}
