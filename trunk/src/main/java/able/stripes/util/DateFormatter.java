package able.stripes.util;

import net.sourceforge.stripes.exception.StripesRuntimeException;
import net.sourceforge.stripes.format.Formatter;
import net.sourceforge.stripes.localization.LocalizationUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateFormatter implements Formatter<Date> {
    /**
     * Maintains a map of named formats that can be used instead of patterns.
     */
    protected static final Map<String, Integer> namedPatterns = new HashMap<String, Integer>();

    static {
        namedPatterns.put("short", DateFormat.SHORT);
        namedPatterns.put("medium", DateFormat.MEDIUM);
        namedPatterns.put("long", DateFormat.LONG);
        namedPatterns.put("full", DateFormat.FULL);
    }

    private String formatType;
    private String formatPattern;
    private Locale locale;
    private DateFormat format;

    /**
     * Sets the format type to be used to render dates as Strings.
     */
    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    /**
     * Gets the format type to be used to render dates as Strings.
     */
    public String getFormatType() {
        return formatType;
    }

    /**
     * Sets the named format string or date pattern to use to format the date.
     */
    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    /**
     * Gets the named format string or date pattern to use to format the date.
     */
    public String getFormatPattern() {
        return formatPattern;
    }

    /**
     * Sets the locale that output String should be in.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets the locale that output String should be in.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Constructs the DateFormat used for formatting, based on the values passed to the various setter methods on the
     * class.  If the formatString is one of the named formats then a DateFormat instance is created of the specified
     * type and format, otherwise a SimpleDateFormat is constructed using the pattern provided and the formatType is
     * ignored.
     *
     * @throws net.sourceforge.stripes.exception.StripesRuntimeException
     *          if the formatType is not one of 'date', 'time' or 'datetime'.
     */
    public void init() {
        // Default these values if they were not supplied
        if (formatPattern == null) {
            formatPattern = "short";
        }

        if (formatType == null) {
            formatType = "date";
        }

        String lcFormatString = formatPattern.toLowerCase();
        String lcFormatType = formatType.toLowerCase();

        if (namedPatterns.containsKey(lcFormatString)) {
            // Now figure out how to construct our date format for our locale
            if (lcFormatType.equals("date")) {
                format = DateFormat.getDateInstance(namedPatterns.get(lcFormatString), locale);
            } else if (lcFormatType.equals("datetime")) {
                format = DateFormat.getDateTimeInstance(namedPatterns.get(lcFormatString),
                        namedPatterns.get(lcFormatString),
                        locale);
            } else if (lcFormatType.equals("time")) {
                format = DateFormat.getTimeInstance(namedPatterns.get(lcFormatString), locale);
            } else {
                throw new StripesRuntimeException("Invalid formatType for Date: " + formatType +
                        ". Allowed types are 'date', 'time' and 'datetime'.");
            }
        } else {
            format = new SimpleDateFormat(formatPattern, locale);
        }

        format.setTimeZone(TimeZoneInterceptor.getTimeZone());
    }

    /**
     * Formats a Date as a String using the rules supplied when the formatter was built.
     */
    public String format(Date input) {
        return this.format.format(input);
    }
}
