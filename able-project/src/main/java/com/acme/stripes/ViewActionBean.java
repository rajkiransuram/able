package com.acme.stripes;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import com.acme.user.User;

@UrlBinding("/user/{user}")
public class ViewActionBean extends AcmeActionBean {
    private User user;

    @DefaultHandler
    public Resolution dislay() {
        return new StreamingResolution("text/html", "foo");
    }

    public void setUser(User user) {
        this.user = user;
    }
}
