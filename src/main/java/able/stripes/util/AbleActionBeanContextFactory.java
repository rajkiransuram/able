package able.stripes.util;

import able.guice.GuiceServletContextListner;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.ActionBeanContextFactory;
import net.sourceforge.stripes.exception.StripesServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbleActionBeanContextFactory implements ActionBeanContextFactory {
    public ActionBeanContext getContextInstance(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            AbleActionBeanContext context = GuiceServletContextListner.newInstance(AbleActionBeanContext.class);
            context.setRequest(request);
            context.setResponse(response);
            return context;
        }
        catch (Exception e) {
            throw new StripesServletException("Could not instantiate AbleActionBeanContext", e);
        }

    }

    public void init(Configuration configuration) throws Exception {
    }
}
