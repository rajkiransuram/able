package able.stripes.util;

import net.sourceforge.stripes.action.ForwardResolution;

public class JSP extends ForwardResolution {
    public JSP(String path) {
        super("/WEB-INF/jsp/" + path);
    }
}
