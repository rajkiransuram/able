package able.stripes.util;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.TimeZone;

@Intercepts({
        LifecycleStage.RequestInit,
        LifecycleStage.RequestComplete
        })
public class TimeZoneInterceptor implements Interceptor {
    private static final ThreadLocal<TimeZone> threadLocal = new ThreadLocal<TimeZone>();

    public Resolution intercept(ExecutionContext context) throws Exception {
        switch (context.getLifecycleStage()) {
            case RequestInit:
                HttpServletRequest req = context.getActionBeanContext().getRequest();
                HttpSession session = req.getSession(false);
                if (session != null) {
                    TimeZone timeZone = (TimeZone) Config.get(session, Config.FMT_TIME_ZONE);
                    threadLocal.set(timeZone);
                }
                break;
            case RequestComplete:
                threadLocal.set(null);
                break;
            default:
                throw new RuntimeException("This can never happen!");
        }

        return context.proceed();
    }

    public static TimeZone getTimeZone() {
        TimeZone timeZone = threadLocal.get();
        if (timeZone != null) {
            return timeZone;
        } else {
            return TimeZone.getDefault();
        }
    }
}
