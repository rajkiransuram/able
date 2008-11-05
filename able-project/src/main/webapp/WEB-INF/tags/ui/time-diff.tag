<%@ tag import="java.util.concurrent.TimeUnit" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<%@ attribute name="time" required="true" type="java.util.Date" %>
<%
    long now = System.currentTimeMillis();
    long ms = time.getTime() - now;

    if (ms < 0) {
        out.print("PAST");
    } else {
        long days;
        long hours;
        long minutes;
        long seconds;

        days = TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS);
        ms = (ms - TimeUnit.DAYS.toMillis(days));
        hours = TimeUnit.HOURS.convert(ms, TimeUnit.MILLISECONDS);
        ms = (ms - TimeUnit.HOURS.toMillis(hours));
        minutes = TimeUnit.MINUTES.convert(ms, TimeUnit.MILLISECONDS);
        ms = (ms - TimeUnit.MINUTES.toMillis(minutes));
        seconds = TimeUnit.SECONDS.convert(ms, TimeUnit.MILLISECONDS);

        if (days > 0) {
            out.print(days + " days, ");
        }

        if (hours > 0) {
            out.print(hours + " hours, ");
        }

        out.print(minutes + " minutes, ");
        out.print(seconds + " seconds");
    }
%>