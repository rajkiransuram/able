package able.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import org.hibernate.proxy.HibernateProxy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GuiceServletContextListner implements ServletContextListener {
    private static Injector injector;

    public void contextInitialized(ServletContextEvent sce) {
        synchronized (GuiceServletContextListner.class) {
            if (injector == null) {
                injector = Guice.createInjector(Stage.DEVELOPMENT, new AbleModule());

                // and start up Centcom!
                injector.getInstance(AbleInitialization.class).init();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public static Injector getInjector() {
        return injector;
    }

    public static <T> T newInstance(Class<T> clazz) {
        if (injector != null) {
            return injector.getInstance(clazz);
        } else {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void wireUp(HibernateProxy proxy) {
        injector.injectMembers(proxy);
    }
}
