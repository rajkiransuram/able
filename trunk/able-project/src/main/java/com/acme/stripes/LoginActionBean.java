package com.acme.stripes;

import able.stripes.HibernateValidate;
import able.stripes.HibernateValidateNestedProperties;
import able.stripes.JSP;
import com.acme.user.User;
import com.acme.user.UserManager;
import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.ValidationMethod;

@UrlBinding("/login")
public class LoginActionBean extends BaseActionBean {
    private UserManager userManager;

    @Inject
    public LoginActionBean(UserManager userManager) {
        this.userManager = userManager;
    }

    @HibernateValidateNestedProperties({
        @HibernateValidate(field = "email"),
        @HibernateValidate(field = "password")
            })
    private User user;

    @DefaultHandler
    @DontValidate
    public Resolution showForm() {
        return new JSP("login.jsp");
    }

    @ValidationMethod
    @Transactional
    public void validate_signup() {
        if (!userManager.authenticate(user.getEmail(), user.getPassword())) {
            addError("user.email", "user", "badLogin");
        }
    }

    @Transactional
    public Resolution login() {
        getContext().setCurrentUser(userManager.findByEmail(user.getEmail()));

        return new RedirectResolution(HomeActionBean.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
