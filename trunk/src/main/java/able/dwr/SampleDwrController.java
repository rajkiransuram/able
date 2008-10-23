package able.dwr;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
public class SampleDwrController {
    private Provider<WebContext> webContextProvider;

    @Inject
    public SampleDwrController(Provider<WebContext> webContextProvider) {
        this.webContextProvider = webContextProvider;
    }

    @RemoteMethod
    public Answer add(int x, int y) {
        return new Answer(x + y);
    }
}
