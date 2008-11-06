package com.acme.stripes.config;

import com.acme.stripes.BaseActionBean;
import com.acme.stripes.WelcomeActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AuthRequiredIntereptor implements MethodInterceptor {
    public Object invoke(MethodInvocation invocation) throws Throwable {
        BaseActionBean bean = (BaseActionBean) invocation.getThis();
        if (!bean.getContext().isLoggedIn()) {
            return new RedirectResolution(WelcomeActionBean.class);
        } else {
            return invocation.proceed();
        }
    }
}
