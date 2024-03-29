package com.acme.stripes;

import able.stripes.HibernateValidate;
import able.stripes.HibernateValidateNestedProperties;
import com.acme.account.AccountManager;
import com.acme.user.User;
import com.acme.user.UserManager;
import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationMethod;

@UrlBinding("/signup")
@StrictBinding
public class SignupActionBean extends BaseActionBean {
    private AccountManager accountManager;
    private UserManager userManager;

    @HibernateValidateNestedProperties({
        @HibernateValidate(field = "firstName"),
        @HibernateValidate(field = "lastName"),
        @HibernateValidate(field = "email"),
        @HibernateValidate(field = "password", minlength = 3, maxlength = 32)
            })
    private User user;

    @Validate(required = true)
    private String passwordAgain;

    @Inject
    public SignupActionBean(AccountManager accountManager, UserManager userManager) {
        this.accountManager = accountManager;
        this.userManager = userManager;
    }

    @DefaultHandler
    @DontValidate
    public Resolution showForm() {
        return new ForwardResolution("/WEB-INF/jsp/signup.jsp");
    }

    @ValidationMethod
    @Transactional
    public void validate_signup() {
        if (!passwordAgain.equals(user.getPassword())) {
            addError("passwordAgain", "user", "passwordMismatch");
        }

        if (userManager.findByEmail(user.getEmail()) != null) {
            addError("user.email", "user", "emailTaken");
        }
    }

    @Transactional
    public Resolution signup() {
        // create the new account
        accountManager.createAccount(user);

        // log the user in
        getContext().setCurrentUser(user);

        return new RedirectResolution(HomeActionBean.class);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
}
