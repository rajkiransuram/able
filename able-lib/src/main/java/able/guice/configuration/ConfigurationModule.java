package able.guice.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;
import able.util.Log;

import java.util.Properties;
import java.util.TimeZone;
import java.lang.annotation.Annotation;

public abstract class ConfigurationModule implements Module {
    private static final Log LOG = new Log();
    private Properties props;
    private Binder binder;

    public void configure(Binder binder) {
        this.binder = binder;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        props = buildProperties(binder);

        bindString(BlowfishKey.class, "blowfishKey");
        bindConfiguration();
    }

    protected abstract void bindConfiguration();

    protected void bindString(Class<? extends Annotation> annotation, String property) {
        binder.bindConstant()
                    .annotatedWith(annotation)
                    .to(props.getProperty(property));
    }

    protected void bindInteger(Binder binder, Properties props, Class<? extends Annotation> annotation, String property) {
        binder.bindConstant()
                .annotatedWith(annotation)
                .to(Integer.parseInt(props.getProperty(property)));
    }

    protected void bindBoolean(Binder binder, Properties props, Class<? extends Annotation> annotation, String property) {
        binder.bindConstant()
                .annotatedWith(annotation)
                .to(Boolean.parseBoolean(props.getProperty(property)));
    }

    public Properties getProperties() {
        return props;
    }

    private Properties buildProperties(Binder binder) {
        Properties props = new Properties();
        String path = "/" + System.getenv("USER") + ".properties";

        try {
            props.load(ConfigurationModule.class.getResourceAsStream(path));
        } catch (Exception e) {
            binder.addError("Could not load configuration properties at %s", path);
            LOG.severe("Problem loading properties", e);
        }

        return props;
    }
}
