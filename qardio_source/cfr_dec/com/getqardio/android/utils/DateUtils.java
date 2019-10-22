/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.format.DateFormat
 */
package com.getqardio.android.utils;

import android.content.Context;
import android.text.format.DateFormat;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static final java.text.DateFormat LOCALE_DATE_FORMAT = new SimpleDateFormat(DateFormat.getBestDateTimePattern((Locale)Locale.getDefault(), (String)"dd MMM yy"), Locale.getDefault());

    private static void appendNumber(StringBuilder stringBuilder, int n, int n2) {
        String string2 = Integer.toString(n2);
        for (n2 = 0; n2 < n - string2.length(); ++n2) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string2);
    }

    public static int calculateDiffYears(Date comparable, Date comparable2) {
        int n;
        block3: {
            int n2;
            block2: {
                comparable = DateUtils.getCalendar((Date)comparable);
                comparable2 = DateUtils.getCalendar((Date)comparable2);
                n2 = ((Calendar)comparable2).get(1) - ((Calendar)comparable).get(1);
                if (((Calendar)comparable).get(2) > ((Calendar)comparable2).get(2)) break block2;
                n = n2;
                if (((Calendar)comparable).get(2) != ((Calendar)comparable2).get(2)) break block3;
                n = n2;
                if (((Calendar)comparable).get(5) <= ((Calendar)comparable2).get(5)) break block3;
            }
            n = n2 - 1;
        }
        return n;
    }

    public static boolean containsDate(List<Calendar> object, Calendar calendar) {
        object = object.iterator();
        while (object.hasNext()) {
            if (!DateUtils.sameDate(calendar, (Calendar)object.next())) continue;
            return true;
        }
        return false;
    }

    public static String createUtcOffsetString(boolean bl, boolean bl2, int n) {
        int n2 = n / 60000;
        char c = '+';
        n = n2;
        if (n2 < 0) {
            c = '-';
            n = -n2;
        }
        StringBuilder stringBuilder = new StringBuilder(9);
        if (bl) {
            stringBuilder.append("UTC");
        }
        stringBuilder.append(c);
        DateUtils.appendNumber(stringBuilder, 2, n / 60);
        if (bl2) {
            stringBuilder.append(':');
        }
        DateUtils.appendNumber(stringBuilder, 2, Math.abs(n % 60));
        return stringBuilder.toString();
    }

    public static String formatDateInLocaleAndWithTodayAndYesterday(Context context, Date date) {
        if (date.after(DateUtils.getStartOfTheDay(new Date()))) {
            return context.getString(2131362185);
        }
        if (date.after(DateUtils.getStartOfTheDay(DateUtils.getDateDiffByNumberOfDays(new Date(), -1)))) {
            return context.getString(2131362186);
        }
        return LOCALE_DATE_FORMAT.format(date);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTime(date);
        return calendar;
    }

    public static SimpleDateFormat getCurrentDateFormat() {
        return (SimpleDateFormat)DateFormat.getDateFormat((Context)CustomApplication.getApplication());
    }

    public static SimpleDateFormat getCurrentDateTimeFormat(String string2) {
        Locale locale = Locale.getDefault();
        return new SimpleDateFormat(DateFormat.getBestDateTimePattern((Locale)locale, (String)string2), locale);
    }

    public static SimpleDateFormat getCurrentTimeFormat() {
        return (SimpleDateFormat)DateFormat.getTimeFormat((Context)CustomApplication.getApplication());
    }

    public static Date getDateDiffByNumberOfDays(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, n);
        return calendar.getTime();
    }

    public static int getDayNumber(int n, int n2, int n3) {
        int n4 = (14 - n2) / 12;
        n = n + 4800 - n4;
        return ((n4 * 12 + n2 - 3) * 153 + 2) / 5 + n3 + n * 365 + n / 4 - n / 100 + n / 400 - 32045;
    }

    public static Date getEndOfTheDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTime();
    }

    public static Date getMinDate(Date date, List<Calendar> object) {
        Comparable<Calendar> comparable = Calendar.getInstance();
        comparable.add(2, -1);
        Date date2 = comparable.getTime();
        comparable = date2;
        if (object != null) {
            comparable = date2;
            if (object.size() > 0) {
                object = object.get(0);
                comparable = date2;
                if (object != null) {
                    comparable = date2;
                    if (!DateUtils.isSameMonth(date, ((Calendar)object).getTime())) {
                        comparable = ((Calendar)object).getTime();
                    }
                }
            }
        }
        return comparable;
    }

    public static SimpleDateFormat getProfileDateFormat(String string2) {
        return DateUtils.getCurrentDateTimeFormat(string2);
    }

    public static Date getStartOfTheDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getStringTimeZoneOffset(long l) {
        String string2;
        int n = DateUtils.getTimeZoneOffset(l);
        int n2 = n / 60;
        n = Math.abs(n % 60);
        if (n2 < 0) {
            string2 = String.valueOf(n2);
            do {
                return string2 + ":" + n;
                break;
            } while (true);
        }
        string2 = "+" + n2;
        return string2 + ":" + n;
    }

    public static long getThisWeekStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.clear(12);
        calendar.clear(13);
        calendar.clear(14);
        calendar.set(7, calendar.getFirstDayOfWeek());
        return calendar.getTimeInMillis();
    }

    public static long getTimeOffset(long l) {
        return TimeZone.getDefault().getOffset(l);
    }

    public static int getTimeZoneOffset(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        return (calendar.get(15) + calendar.get(16)) / 60000;
    }

    public static String getTimeZoneOffset(Date date) {
        return DateUtils.getStringTimeZoneOffset(date.getTime());
    }

    public static boolean isSameMonth(Date date, Date date2) {
        return Utils.getMonthNumber(date.getTime()) == Utils.getMonthNumber(date2.getTime());
    }

    public static boolean sameDate(Calendar calendar, Calendar calendar2) {
        return calendar.get(2) == calendar2.get(2) && calendar.get(1) == calendar2.get(1) && calendar.get(5) == calendar2.get(5);
    }

    public static boolean sameDate(Date comparable, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date)comparable);
        comparable = Calendar.getInstance();
        ((Calendar)comparable).setTime(date);
        return DateUtils.sameDate(calendar, comparable);
    }
}

