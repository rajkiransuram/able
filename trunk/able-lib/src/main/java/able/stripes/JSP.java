package able.stripes;

import net.sourceforge.stripes.action.ForwardResolution;

public class JSP extends ForwardResolution {
    public JSP(String path) {
        super("/WEB-INF/jsp/" + path);
    }
}
