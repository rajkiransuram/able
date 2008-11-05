package able.stripes;

import able.account.Account;
import able.stripes.util.JSP;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/home")
public class HomeActionBean extends AbleActionBean {
    @DefaultHandler
    public Resolution execute() {
        Account account = getContext().getCurrentUser().getAccount();

        return new JSP("home.jsp");
    }

}
