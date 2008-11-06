package able.dwr;

import com.google.inject.Injector;
import able.guice.AbleContextListener;
import org.directwebremoting.guice.CustomInjectorServletContextListener;

public class DwrGuiceServletContextListener extends CustomInjectorServletContextListener {
    protected Injector createInjector() {
        return AbleContextListener.getInjector();
    }
}
