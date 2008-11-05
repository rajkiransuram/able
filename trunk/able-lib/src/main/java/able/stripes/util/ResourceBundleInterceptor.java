package able.stripes.util;

import able.guice.GuiceServletContextListner;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts(LifecycleStage.ResolutionExecution)
public class ResourceBundleInterceptor implements Interceptor {
    private ResourceBundleReset reset;

    public ResourceBundleInterceptor() {
        reset = GuiceServletContextListner.getInjector().getInstance(ResourceBundleReset.class);
    }

    public Resolution intercept(ExecutionContext context) throws Exception {
        reset.reset();

        return context.proceed();
    }
}
