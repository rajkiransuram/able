package able.stripes;

import able.guice.AbleContextListener;
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
            ActionBeanContext context = AbleContextListener.getActionBeanContextInstance();
            context.setRequest(request);
            context.setResponse(response);
            return context;
        }
        catch (Exception e) {
            throw new StripesServletException("Could not instantiate ActionBeanContext", e);
        }

    }

    public void init(Configuration configuration) throws Exception {
    }
}
