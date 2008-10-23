package able.dwr;

import org.directwebremoting.guice.AbstractDwrModule;
import org.directwebremoting.guice.DwrScopes;
import org.directwebremoting.guice.ParamName;

public class DwrModule extends AbstractDwrModule {
    protected void configure() {
        bindRemoted(SampleDwrController.class).to(SampleDwrController.class).in(DwrScopes.APPLICATION);
        bindAnnotatedClasses(Answer.class);

        bindParameter(ParamName.DEBUG).to(true);
        bindParameter(ParamName.ACTIVE_REVERSE_AJAX_ENABLED).to(true);
        bindParameter(ParamName.MAX_WAIT_AFTER_WRITE).to(10000L);

        bindDwrScopes(false);
    }
}
