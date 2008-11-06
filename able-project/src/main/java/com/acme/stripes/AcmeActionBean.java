package com.acme.stripes;

import able.stripes.AbleActionBean;
import com.acme.stripes.config.AcmeActionBeanContext;

public class AcmeActionBean extends AbleActionBean {
    public AcmeActionBeanContext getContext() {
        return (AcmeActionBeanContext) super.getContext();
    }
}
