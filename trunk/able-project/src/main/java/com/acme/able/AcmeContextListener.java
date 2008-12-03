package com.acme.able;

import able.guice.AbleContextListener;
import able.guice.AbleModule;
import com.google.inject.Injector;
import com.acme.stripes.config.AcmeActionBeanContext;
import com.acme.stripes.AcmeStripesModule;
import net.sourceforge.stripes.action.ActionBeanContext;

public class AcmeContextListener extends AbleContextListener {
    protected void afterGuiceInit(Injector injector) {
    }

    protected AbleModule getAbleModule() {
        return new AcmeModule();
    }

    protected Class<? extends ActionBeanContext> getActionBeanContextType() {
        return AcmeActionBeanContext.class;
    }
}
