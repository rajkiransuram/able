package able.hibernate;

import com.google.inject.Binder;
import com.google.inject.Module;
import able.guice.configuration.ConfigurationModule;
import able.util.Log;
import com.wideplay.warp.persist.PersistenceService;
import com.wideplay.warp.persist.UnitOfWork;
import com.wideplay.warp.persist.TransactionStrategyBuilder;
import org.hibernate.EntityMode;
import org.hibernate.MappingException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

public class HibernateModule implements Module {
    private static final Log LOG = new Log();

    private ConfigurationModule configurationModule;
    private List<Class> entities = new ArrayList<Class>();

    public HibernateModule(ConfigurationModule configurationModule) {
        this.configurationModule = configurationModule;
    }

    public void configure(final Binder binder) {
        Module persistenceModule = PersistenceService.usingHibernate()
                .across(UnitOfWork.REQUEST)
                .buildModule();
        binder.install(persistenceModule);

        // configure Hibernate
        AnnotationConfiguration c = new AnnotationConfiguration() {
            {
                Properties props = configurationModule.getProperties();
                for (Map.Entry<Object, Object> entry : props.entrySet()) {
                    String key = entry.getKey().toString();
                    if (key.startsWith("hibernate.")) {
                        setProperty(key, entry.getValue().toString());
                    }
                }
            }

            public AnnotationConfiguration addAnnotatedClass(Class aClass) throws MappingException {
                entities.add(aClass);

                return super.addAnnotatedClass(aClass);
            }
        };
        c.setInterceptor(new GuiceInterceptor());
        c.getEntityTuplizerFactory().registerDefaultTuplizerClass(EntityMode.POJO, GuiceEntityTuplizer.class);

        c.configure();
        binder.bind(Configuration.class).toInstance(c);
    }

    public List<Class> getEntities() {
        return entities;
    }
}
