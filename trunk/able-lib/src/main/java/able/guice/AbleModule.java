package able.guice;

import able.dwr.DwrModule;
import able.guice.configuration.ConfigurationModule;
import able.hibernate.HibernateModule;
import able.stripes.StripesModule;
import able.user.Blowfish;
import able.util.Log;
import able.util.StandardFormatter;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.servlet.ServletModule;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class AbleModule implements Module {
    private static final Log LOG = new Log();

    private ConfigurationModule configurationModule;
    private StripesModule stripesModule;
    private HibernateModule hibernateModule;

    public AbleModule(ConfigurationModule configurationModule) {
        this(configurationModule, new StripesModule());
    }

    public AbleModule(ConfigurationModule configurationModule, StripesModule stripesModule) {
        this.configurationModule = configurationModule;
        this.stripesModule = stripesModule;
    }

    public Stage getGuiceStage() {
        return Stage.DEVELOPMENT;
    }

    public void configure(Binder binder) {
        configureLogging();

        binder.install(configurationModule);
        binder.install(new ServletModule());
        hibernateModule = new HibernateModule(configurationModule);
        binder.install(hibernateModule);
        binder.install(stripesModule);
        binder.install(new DwrModule());

        // bind utility stuff
        binder.bind(Blowfish.class);
    }

    public List<Class> getHibernateEntities() {
        return hibernateModule.getEntities();
    }

    protected void configureLogging() {
        Logger root = Logger.getLogger("");
        Handler[] existing = root.getHandlers();
        for (Handler handler : existing) {
            root.removeHandler(handler);
        }

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        handler.setFormatter(new StandardFormatter());

        root.addHandler(handler);
    }
}
