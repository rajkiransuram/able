package able.stripes;

import com.google.inject.Inject;
import able.stripes.util.HibernateValidate;
import able.stripes.util.HibernateValidateNestedProperties;
import able.stripes.util.JSP;
import able.user.User;
import able.user.UserManager;
import com.wideplay.warp.persist.Transactional;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.ValidationMethod;

@UrlBinding("/login")
public class LoginActionBean extends AbleActionBean {
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
