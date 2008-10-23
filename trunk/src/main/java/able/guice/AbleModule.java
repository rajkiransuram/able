package able.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import able.account.AccountManager;
import able.dwr.DwrModule;
import able.guice.configuration.ConfigurationModule;
import able.hibernate.HibernateModule;
import able.stripes.StripesModule;
import able.user.Blowfish;
import able.user.UserManager;
import able.util.Log;
import able.util.StandardFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbleModule implements Module {
    private static final Log LOG = new Log();

    public void configure(Binder binder) {
        configureLogging();

        binder.install(new ServletModule());
        binder.install(new HibernateModule());

        // bind your dependencies here
        //binder.bind(ReportManager.class).asEagerSingleton();

        binder.bind(Blowfish.class);
        binder.bind(UserManager.class);
        binder.bind(AccountManager.class);

        binder.install(new ConfigurationModule());

        binder.install(new StripesModule());
        binder.install(new DwrModule());
    }

    private void configureLogging() {
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
