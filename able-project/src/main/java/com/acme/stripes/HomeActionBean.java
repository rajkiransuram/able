package com.acme.stripes;

import able.stripes.JSP;
import com.acme.account.Account;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/home")
public class HomeActionBean extends BaseActionBean {
    @DefaultHandler
    public Resolution execute() {
        Account account = getContext().getCurrentUser().getAccount();

        return new JSP("home.jsp");
    }

}
