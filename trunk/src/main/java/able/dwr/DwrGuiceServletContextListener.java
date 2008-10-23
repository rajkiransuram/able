package able.dwr;

import com.google.inject.Injector;
import able.guice.GuiceServletContextListner;
import org.directwebremoting.guice.CustomInjectorServletContextListener;

public class DwrGuiceServletContextListener extends CustomInjectorServletContextListener {
    protected Injector createInjector() {
        return GuiceServletContextListner.getInjector();
    }
}
