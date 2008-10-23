package able.guice.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;
import able.util.Log;

import java.util.Properties;
import java.util.TimeZone;

public class ConfigurationModule implements Module {
    private static final Log LOG = new Log();

    public void configure(Binder binder) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Properties props = getProperties(binder);

        // do interesting stuff with properties here

        binder.bindConstant()
                .annotatedWith(BlowfishKey.class)
                .to(props.getProperty("blowfishKey"));
    }

    public static Properties getProperties(Binder binder) {
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
