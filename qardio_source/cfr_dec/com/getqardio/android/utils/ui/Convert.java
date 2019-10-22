/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 */
package com.getqardio.android.utils.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.Utils;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class Convert {
    public static String abbreviateMonthIfNecessary(String string2, Locale locale) {
        String string3 = string2;
        if ("fi".equalsIgnoreCase(locale.getLanguage())) {
            string3 = string2.substring(0, 3);
        }
        return string3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Integer booleanToInteger(Boolean bl) {
        int n;
        if (bl == null) {
            return null;
        }
        if (!bl.booleanValue()) {
            n = 0;
            do {
                return n;
                break;
            } while (true);
        }
        n = 1;
        return n;
    }

    public static String changeLettersCaseIfNecessary(String string2, Locale locale) {
        String string3 = string2;
        if ("pt".equalsIgnoreCase(locale.getLanguage())) {
            string3 = string2.toLowerCase();
        }
        return string3;
    }

    public static float convertDpToPixel(float f, Context context) {
        return f * ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }

    public static String floatToString(Float f, int n) {
        if (f == null) {
            return new String();
        }
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(n);
        numberFormat.setMinimumFractionDigits(n);
        return numberFormat.format(f);
    }

    public static String getFullDayName(int n) {
        return Convert.getFullDayName(n, null);
    }

    public static String getFullDayName(int n, Locale locale) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(7, 2);
        calendar.add(5, n);
        Locale locale2 = locale;
        if (locale == null) {
            locale2 = Utils.getLocale();
        }
        return Convert.changeLettersCaseIfNecessary(String.format(locale2, "%tA", calendar), locale2);
    }

    public static String getStringForLocale(Context context, int n, Locale locale) {
        Object object = locale;
        if (locale == null) {
            object = Utils.getLocale();
        }
        context = context.getResources();
        locale = context.getConfiguration();
        Locale locale2 = ((Configuration)locale).locale;
        ((Configuration)locale).locale = object;
        context.updateConfiguration((Configuration)locale, null);
        object = context.getString(n);
        ((Configuration)locale).locale = locale2;
        context.updateConfiguration((Configuration)locale, null);
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean integerToBoolean(Integer n) {
        boolean bl;
        if (n == null) {
            return null;
        }
        if (n == 0) {
            bl = false;
            do {
                return bl;
                break;
            } while (true);
        }
        bl = true;
        return bl;
    }

    public static abstract class Reminder {
        public static String daysToString(Context context, int n) {
            return Reminder.daysToString(context, null, n);
        }

        /*
         * Enabled aggressive block sorting
         */
        public static String daysToString(Context context, Locale locale, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            if ((n & 0x7F) == 127) {
                stringBuilder.append(Convert.getStringForLocale(context, 2131361920, locale));
                return stringBuilder.toString();
            }
            if ((n & 1) == 1) {
                stringBuilder.append(Convert.getFullDayName(0, locale));
            }
            if ((n & 2) == 2) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(Convert.getFullDayName(1, locale));
            }
            if ((n & 4) == 4) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(Convert.getFullDayName(2, locale));
            }
            if ((n & 8) == 8) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(Convert.getFullDayName(3, locale));
            }
            if ((n & 0x10) == 16) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(Convert.getFullDayName(4, locale));
            }
            if ((n & 0x20) == 32) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(Convert.getFullDayName(5, locale));
            }
            if ((n & 0x40) != 64) return stringBuilder.toString();
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(Convert.getFullDayName(6, locale));
            return stringBuilder.toString();
        }

        public static long getTimeAfterMidnight(int n, int n2) {
            return TimeUnit.HOURS.toMillis(n) + TimeUnit.MINUTES.toMillis(n2);
        }

        public static long localTimeToTimeAfterMidnight(long l) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(l);
            return Reminder.getTimeAfterMidnight(calendar.get(11), calendar.get(12));
        }

        public static String localTimeToTimeString(long l) {
            return DateUtils.getCurrentTimeFormat().format(new Date(l));
        }

        public static int timeAfterMidnightToLocalHours(long l) {
            return (int)TimeUnit.MILLISECONDS.toHours(l);
        }

        public static int timeAfterMidnightToLocalMinutes(long l) {
            int n = (int)TimeUnit.MILLISECONDS.toHours(l);
            return (int)TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(n));
        }

        public static long timeAfterMidnightToLocalTime(long l) {
            long l2 = System.currentTimeMillis();
            int n = (int)TimeUnit.MILLISECONDS.toHours(l);
            int n2 = (int)TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(n));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(l2);
            calendar.set(11, n);
            calendar.set(12, n2);
            calendar.set(13, 0);
            calendar.set(14, 0);
            return calendar.getTimeInMillis();
        }
    }

}

