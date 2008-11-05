package able.stripes.util;

import com.google.inject.Inject;
import able.account.Account;
import able.user.User;
import able.user.UserManager;
import com.wideplay.warp.persist.Transactional;
import net.sourceforge.stripes.action.ActionBeanContext;

import javax.servlet.http.HttpSession;

public class AbleActionBeanContext extends ActionBeanContext {
    private static final String USER_ID_KEY = "__logged_in_user";

    private UserManager userManager;
    private User user;

    @Inject
    public AbleActionBeanContext(UserManager userManager) {
        this.userManager = userManager;
    }

    @Transactional
    public User getCurrentUser() {
        if (user == null) {
            HttpSession session = getRequest().getSession();
            if (session != null) {
                Long userId = (Long) session.getAttribute(USER_ID_KEY);
                if (userId != null) {
                    user = userManager.findById(userId);
                }
            }
        }

        return user;
    }

    public Account getCurrentAccount() {
        User user = getCurrentUser();
        if (user != null) {
            return user.getAccount();
        }

        return null;
    }

    public void setCurrentUser(User user) {
        HttpSession session = getRequest().getSession(true);
        session.setAttribute(USER_ID_KEY, user.getId());
        this.user = user;
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public void logout() {
        this.user = null;
        HttpSession session = getRequest().getSession();
        if (session != null) {
            session.setAttribute(USER_ID_KEY, null);
        }
    }
}
