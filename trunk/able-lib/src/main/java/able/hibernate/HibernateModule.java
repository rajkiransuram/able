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
import org.hibernate.EntityMode;
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
        };
        c.setInterceptor(new GuiceInterceptor());
        c.getEntityTuplizerFactory().registerDefaultTuplizerClass(EntityMode.POJO, GuiceEntityTuplizer.class);

        c.configure();
        binder.bind(Configuration.class).toInstance(c);
    }
}
