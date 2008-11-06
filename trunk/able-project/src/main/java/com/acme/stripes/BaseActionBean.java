package com.acme.stripes;

import able.stripes.AbleActionBean;
import com.acme.stripes.config.AcmeActionBeanContext;

public abstract class BaseActionBean extends AbleActionBean {
    public AcmeActionBeanContext getContext() {
        return (AcmeActionBeanContext) super.getContext();
    }
}
