package able.hibernate;

import able.guice.GuiceServletContextListner;
import org.hibernate.tuple.Instantiator;

import java.io.Serializable;

public class GuiceInstantiator implements Instantiator {
    private String entityName;

    public GuiceInstantiator(String entityName) {
        this.entityName = entityName;
    }

    public Object instantiate(Serializable id) {
        try {
            return GuiceServletContextListner.getInjector().getInstance(Class.forName(entityName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object instantiate() {
        return instantiate(null);
    }

    public boolean isInstance(Object object) {
        return object.getClass().getName().startsWith(entityName);
    }
}
