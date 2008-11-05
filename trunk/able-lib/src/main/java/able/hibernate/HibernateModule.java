package able.hibernate;

import com.google.inject.Binder;
import com.google.inject.Module;
import able.account.AccountAccess;
import able.guice.configuration.ConfigurationModule;
import able.user.UserAccess;
import able.util.Log;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;
import org.hibernate.MappingException;
import org.hibernate.annotations.Tuplizer;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Map;
import java.util.Properties;

public class HibernateModule implements Module {
    private static final Log LOG = new Log();

    public void configure(final Binder binder) {
        Module persistenceModule = PersistenceService.usingHibernate()
                .across(UnitOfWork.REQUEST)
                .addAccessor(AccountAccess.class)
                .addAccessor(UserAccess.class)
                .buildModule();
        binder.install(persistenceModule);

        // configure Hibernate
        AnnotationConfiguration c = new AnnotationConfiguration() {
            {
                Properties props = ConfigurationModule.getProperties(binder);
                for (Map.Entry<Object, Object> entry : props.entrySet()) {
                    String key = entry.getKey().toString();
                    if (key.startsWith("hibernate.")) {
                        setProperty(key, entry.getValue().toString());
                    }
                }
            }

            public AnnotationConfiguration addAnnotatedClass(Class persistentClass) throws MappingException {
                //noinspection unchecked
                Tuplizer tuplizer = (Tuplizer) persistentClass.getAnnotation(Tuplizer.class);
                //noinspection unchecked
                Entity entity = (Entity) persistentClass.getAnnotation(Entity.class);
                //noinspection unchecked
                Embeddable embeddable = (Embeddable) persistentClass.getAnnotation(Embeddable.class);

                if (entity == null && embeddable == null) {
                    error(persistentClass, "@Entity or @Embeddable required on");
                }

                if (entity != null) {
                    if (tuplizer == null || tuplizer.impl() != GuiceEntityTuplizer.class) {
                        error(persistentClass, "@Tuplizer(impl = GuiceEntityTuplizer.class) not added to");
                    }
                } else {
                    if (tuplizer == null || tuplizer.impl() != GuiceComponentTuplizer.class) {
                        error(persistentClass, "@Tuplizer(impl = GuiceComponentTuplizer.class) not added to");
                    }
                }

                //noinspection unchecked
                binder.bind(persistentClass);

                return super.addAnnotatedClass(persistentClass);
            }

            private void error(Class persistentClass, String error) {
                System.err.println("");
                System.err.println("");
                System.err.println("***************************************************");
                System.err.println("");
                System.err.println("");
                System.err.println("" + error);
                System.err.println("");
                System.err.println("\t" + persistentClass.getName());
                System.err.println("");
                System.err.println(" Hibernate can NOT be started.");
                System.err.println("");
                System.err.println("");
                System.err.println("***************************************************");
                System.err.println("");
                System.err.println("");

                throw new RuntimeException(error + " " + persistentClass.getName());
            }
        };
        c.setInterceptor(new GuiceInterceptor());

        c.configure();
        binder.bind(Configuration.class).toInstance(c);
    }
}
