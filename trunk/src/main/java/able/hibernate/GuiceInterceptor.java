package able.hibernate;

import org.hibernate.EmptyInterceptor;

public class GuiceInterceptor extends EmptyInterceptor {
    public String getEntityName(Object object) {
        String name = object.getClass().getName();
        if (name.contains("$$")) {
            name = name.substring(0, name.indexOf("$$"));
        }

        return name;
    }
}
