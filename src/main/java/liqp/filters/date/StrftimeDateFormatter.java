package liqp.filters.date;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Reformat a date
 *
 * %a - The abbreviated weekday name (``Sun'')
 * %A - The  full  weekday  name (``Sunday'')
 * %b - The abbreviated month name (``Jan'')
 * %B - The  full  month  name (``January'')
 * %c - The preferred local date and time representation
 * %d - Day of the month (01..31)
 * %e - Day of the month (1..31)
 * %H - Hour of the day, 24-hour clock (00..23)
 * %I - Hour of the day, 12-hour clock (01..12)
 * %j - Day of the year (001..366)
 * %k - Hour of the day, 24-hour clock (0..23)
 * %l - Hour of the day, 12-hour clock (0..12)
 * %m - Month of the year (01..12)
 * %M - Minute of the hour (00..59)
 * %p - Meridian indicator (``AM''  or  ``PM'')
 * %S - Second of the minute (00..60)
 * %U - Week  number  of the current year,
 *      starting with the first Sunday as the first
 *      day of the first week (00..53)
 * %W - Week  number  of the current year,
 *      starting with the first Monday as the first
 *      day of the first week (00..53)
 * %w - Day of the week (Sunday is 0, 0..6)
 * %x - Preferred representation for the date alone, no time
 * %X - Preferred representation for the time alone, no date
 * %y - Year without a century (00..99)
 * %Y - Year with century
 * %Z - Time zone name
 * %% - Literal ``%'' character
 */
public class StrftimeDateFormatter {


    private static Locale locale = null;


    private final static Map<Character, StrftimeFormat> LIQUID_TO_JAVA_FORMAT =
            new HashMap<>();

    public StrftimeDateFormatter(Locale locale) {
        if (StrftimeDateFormatter.locale != locale) {
            StrftimeDateFormatter.locale = locale;
            init();
        }
    }

    private static void init() {

        // %% - Literal ``%'' character
        LIQUID_TO_JAVA_FORMAT.put('%', new StrftimeFormat(new SimpleDateFormat("%", locale)));

        // %a - The abbreviated weekday name (``Sun'')
        LIQUID_TO_JAVA_FORMAT.put('a', new StrftimeFormat(new SimpleDateFormat("EEE", locale)));

        // %A - The  full  weekday  name (``Sunday'')
        LIQUID_TO_JAVA_FORMAT.put('A', new StrftimeFormat(new SimpleDateFormat("EEEE", locale)));

        // %b - The abbreviated month name (``Jan'')
        LIQUID_TO_JAVA_FORMAT.put('b', new StrftimeFormat(new SimpleDateFormat("MMM", locale)));
        LIQUID_TO_JAVA_FORMAT.put('h', new StrftimeFormat(new SimpleDateFormat("MMM", locale)));

        // %B - The  full  month  name (``January'')
        LIQUID_TO_JAVA_FORMAT.put('B', new StrftimeFormat(new SimpleDateFormat("MMMM", locale)));

        // %c - The preferred local date and time representation
        LIQUID_TO_JAVA_FORMAT.put('c', new StrftimeFormat(new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", locale)));

        // %d - Day of the month (01..31)
        LIQUID_TO_JAVA_FORMAT.put('d', new StrftimeFormat(new SimpleDateFormat("dd", locale)));

        // %e - Day of the month (1..31)
        LIQUID_TO_JAVA_FORMAT.put('e', new StrftimeFormat(new SimpleDateFormat("d", locale)));

        // %H - Hour of the day, 24-hour clock (00..23)
        LIQUID_TO_JAVA_FORMAT.put('H', new StrftimeFormat(new SimpleDateFormat("HH", locale)));

        // %I - Hour of the day, 12-hour clock (01..12)
        LIQUID_TO_JAVA_FORMAT.put('I', new StrftimeFormat(new SimpleDateFormat("hh", locale)));

        // %j - Day of the year (001..366)
        LIQUID_TO_JAVA_FORMAT.put('j', new StrftimeFormat(new SimpleDateFormat("DDD", locale)));

        // %k - Hour of the day, 24-hour clock (0..23)
        LIQUID_TO_JAVA_FORMAT.put('k', new StrftimeFormat(new SimpleDateFormat("H", locale)));

        // %l - Hour of the day, 12-hour clock (1..12)
        LIQUID_TO_JAVA_FORMAT.put('l', new StrftimeFormat(new SimpleDateFormat("h", locale)));

        // %m - Month of the year (01..12)
        LIQUID_TO_JAVA_FORMAT.put('m', new StrftimeFormat(new SimpleDateFormat("MM", locale)));

        // %M - Minute of the hour (00..59)
        LIQUID_TO_JAVA_FORMAT.put('M', new StrftimeFormat(new SimpleDateFormat("mm", locale)));

        // %p - Meridian indicator (``AM''  or  ``PM'')
        LIQUID_TO_JAVA_FORMAT.put('p', new StrftimeFormat(new SimpleDateFormat("a", locale)));

        // %S - Second of the minute (00..60)
        LIQUID_TO_JAVA_FORMAT.put('S', new StrftimeFormat(new SimpleDateFormat("ss", locale)));

        // %U - Week  number  of the current year,
        //      starting with the first Sunday as the first
        //      day of the first week (00..53)
        LIQUID_TO_JAVA_FORMAT.put('U', new StrftimeFormat(new SimpleDateFormat("ww", locale)));

        // %W - Week  number  of the current year,
        //      starting with the first Monday as the first
        //      day of the first week (00..53)
        LIQUID_TO_JAVA_FORMAT.put('W', new StrftimeFormat(new SimpleDateFormat("ww", locale)));

        // %w - Day of the week (Sunday is 0, 0..6)
        LIQUID_TO_JAVA_FORMAT.put('w', new StrftimeFormat(new SimpleDateFormat("F", locale)));

        // %x - Preferred representation for the date alone, no time
        LIQUID_TO_JAVA_FORMAT.put('x', new StrftimeFormat(new SimpleDateFormat("MM/dd/yy", locale)));

        // %X - Preferred representation for the time alone, no date
        LIQUID_TO_JAVA_FORMAT.put('X', new StrftimeFormat(new SimpleDateFormat("HH:mm:ss", locale)));

        // %y - Year without a century (00..99)
        LIQUID_TO_JAVA_FORMAT.put('y', new StrftimeFormat(new SimpleDateFormat("yy", locale)));

        // %Y - Year with century
        LIQUID_TO_JAVA_FORMAT.put('Y', new StrftimeFormat(new SimpleDateFormat("yyyy", locale)));

        // %Z - Time zone name
        LIQUID_TO_JAVA_FORMAT.put('Z', new StrftimeFormat(new SimpleDateFormat("z", locale)));
    }

    public String format(String format, StrftimeCompatibleDate date) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < format.length(); i++) {

            char ch = format.charAt(i);

            if (ch == '%') {

                i++;

                if (i == format.length()) {
                    // a trailing (single) '%' sign: just append it
                    builder.append("%");
                    break;
                }

                char next = format.charAt(i);

                Format javaFormat = LIQUID_TO_JAVA_FORMAT.get(next);

                if (javaFormat == null) {
                    // no valid date-format: append the '%' and the 'next'-char
                    builder.append("%").append(next);
                } else {
                    builder.append(javaFormat.format(date));
                }
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }
}