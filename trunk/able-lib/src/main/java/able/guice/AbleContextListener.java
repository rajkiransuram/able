package able.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.hibernate.proxy.HibernateProxy;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sourceforge.stripes.action.ActionBeanContext;

import java.util.List;

public abstract class AbleContextListener implements ServletContextListener {
    private static Injector injector;
    private static Class<? extends ActionBeanContext> actionBeanContextClass;
    private static List<Class> hibernateEntities;

    public void contextInitialized(ServletContextEvent sce) {
        synchronized (AbleContextListener.class) {
            if (injector == null) {
                AbleModule module = getAbleModule();
                injector = Guice.createInjector(module.getGuiceStage(), module);
                hibernateEntities = module.getHibernateEntities();
                // and start up Centcom!
                injector.getInstance(AbleInitialization.class).init();

                afterGuiceInit(injector);

                actionBeanContextClass = getActionBeanContextType();
            }
        }
    }

    protected abstract void afterGuiceInit(Injector injector);

    protected abstract AbleModule getAbleModule();

    protected abstract Class<? extends ActionBeanContext> getActionBeanContextType();

    public void contextDestroyed(ServletContextEvent sce) {
    }

    public static Class<? extends ActionBeanContext> getActionBeanContextClass() {
        return actionBeanContextClass;
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

    public static ActionBeanContext getActionBeanContextInstance() {
        return newInstance(actionBeanContextClass);
    }

    public static List<Class> getHibernateEntities() {
        return hibernateEntities;
    }
}
