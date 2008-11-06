package com.acme.stripes;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/logout")
public class LogoutActionBean extends BaseActionBean {
    @DefaultHandler
    public Resolution logout() {
        getContext().logout();

        addMessage("loggedOut");

        return new RedirectResolution(WelcomeActionBean.class);
    }
}
