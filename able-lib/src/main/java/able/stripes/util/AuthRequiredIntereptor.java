package able.stripes.util;

import able.stripes.AbleActionBean;
import able.stripes.WelcomeActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AuthRequiredIntereptor implements MethodInterceptor {
    public Object invoke(MethodInvocation invocation) throws Throwable {
        AbleActionBean bean = (AbleActionBean) invocation.getThis();
        if (!bean.getContext().isLoggedIn()) {
            return new RedirectResolution(WelcomeActionBean.class);
        } else {
            return invocation.proceed();
        }
    }
}
