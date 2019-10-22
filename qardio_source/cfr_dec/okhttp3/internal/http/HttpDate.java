/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.internal.Util;

public final class HttpDate {
    private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT;

    static {
        STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>(){

            @Override
            protected DateFormat initialValue() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                simpleDateFormat.setLenient(false);
                simpleDateFormat.setTimeZone(Util.UTC);
                return simpleDateFormat;
            }
        };
        BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length];
    }

    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.get().format(date);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Date parse(String string2) {
        if (string2.length() == 0) {
            return null;
        }
        ParsePosition parsePosition = new ParsePosition(0);
        Cloneable cloneable = STANDARD_DATE_FORMAT.get().parse(string2, parsePosition);
        if (parsePosition.getIndex() == string2.length()) return cloneable;
        String[] arrstring = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
        synchronized (arrstring) {
            int n = 0;
            int n2 = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
            while (n < n2) {
                DateFormat dateFormat = BROWSER_COMPATIBLE_DATE_FORMATS[n];
                cloneable = dateFormat;
                if (dateFormat == null) {
                    cloneable = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[n], Locale.US);
                    ((DateFormat)cloneable).setTimeZone(Util.UTC);
                    HttpDate.BROWSER_COMPATIBLE_DATE_FORMATS[n] = cloneable;
                }
                parsePosition.setIndex(0);
                cloneable = ((DateFormat)cloneable).parse(string2, parsePosition);
                if (parsePosition.getIndex() != 0) {
                    return cloneable;
                }
                ++n;
            }
            return null;
        }
    }

}

