package able.util;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.TimeZone;

/**
 * Configures JSTL to use the timezone offset that was set in a cookie by the browser. This can be done by adding
 * something like this to your SiteMesh decorator. Then every page except for the _first_ page will have the right
 * timezones applied.
 * <p/>
 * document.cookie = encodeURIComponent("timezoneOffset") + "=" + (-(new Date()).getTimezoneOffset())
 */
public class SetTimezoneOffsetFilter implements Filter {

    public static final String PROPERTY_TIMEZONE_OFFEST = "able.timezoneOffset";

    public static final String COOKIE_NAME = "timezoneOffset";

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute(PROPERTY_TIMEZONE_OFFEST) == null) {
            if (req.getCookies() != null) {
                for (Cookie cookie : req.getCookies()) {
                    if (COOKIE_NAME.equals(cookie.getName())) {
                        int timezoneOffsetMinutes = Integer.parseInt(cookie.getValue());
                        TimeZone timeZone = TimeZone.getTimeZone("UTC");
                        timeZone.setRawOffset(timezoneOffsetMinutes * 1000 * 60);
                        Config.set(req.getSession(), Config.FMT_TIME_ZONE, timeZone);
                        req.getSession().setAttribute(PROPERTY_TIMEZONE_OFFEST, timezoneOffsetMinutes);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
