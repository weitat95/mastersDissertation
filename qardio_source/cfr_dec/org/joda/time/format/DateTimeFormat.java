/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public class DateTimeFormat {
    private static final ConcurrentHashMap<String, DateTimeFormatter> cPatternCache = new ConcurrentHashMap();
    private static final AtomicReferenceArray<DateTimeFormatter> cStyleCache = new AtomicReferenceArray(25);

    private static DateTimeFormatter createFormatterForPattern(String string2) {
        DateTimeFormatter dateTimeFormatter;
        block5: {
            Object object;
            block4: {
                if (string2 == null || string2.length() == 0) {
                    throw new IllegalArgumentException("Invalid pattern specification");
                }
                dateTimeFormatter = cPatternCache.get(string2);
                object = dateTimeFormatter;
                if (dateTimeFormatter != null) break block4;
                object = new DateTimeFormatterBuilder();
                DateTimeFormat.parsePatternTo((DateTimeFormatterBuilder)object, string2);
                dateTimeFormatter = ((DateTimeFormatterBuilder)object).toFormatter();
                if (cPatternCache.size() >= 500 || (object = cPatternCache.putIfAbsent(string2, dateTimeFormatter)) == null) break block5;
            }
            return object;
        }
        return dateTimeFormatter;
    }

    public static DateTimeFormatter forPattern(String string2) {
        return DateTimeFormat.createFormatterForPattern(string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean isNumericToken(String var0) {
        var2_1 = true;
        var1_2 = var0.length();
        if (var1_2 <= 0) ** GOTO lbl10
        switch (var0.charAt(0)) {
            default: {
                ** GOTO lbl10
            }
            case 'M': {
                if (var1_2 <= 2) {
                    return true;
                }
lbl10:
                // 4 sources
                var2_1 = false;
            }
            case 'C': 
            case 'D': 
            case 'F': 
            case 'H': 
            case 'K': 
            case 'S': 
            case 'W': 
            case 'Y': 
            case 'c': 
            case 'd': 
            case 'e': 
            case 'h': 
            case 'k': 
            case 'm': 
            case 's': 
            case 'w': 
            case 'x': 
            case 'y': 
        }
        return var2_1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void parsePatternTo(DateTimeFormatterBuilder dateTimeFormatterBuilder, String string2) {
        int n = string2.length();
        int[] arrn = new int[1];
        int n2 = 0;
        do {
            int n3;
            String string3;
            int n4;
            block47: {
                block46: {
                    if (n2 >= n) break block46;
                    arrn[0] = n2;
                    string3 = DateTimeFormat.parseToken(string2, arrn);
                    n4 = arrn[0];
                    n3 = string3.length();
                    if (n3 != 0) break block47;
                }
                return;
            }
            char c = string3.charAt(0);
            block0 : switch (c) {
                default: {
                    throw new IllegalArgumentException("Illegal pattern component: " + string3);
                }
                case 'G': {
                    dateTimeFormatterBuilder.appendEraText();
                    break;
                }
                case 'C': {
                    dateTimeFormatterBuilder.appendCenturyOfEra(n3, n3);
                    break;
                }
                case 'Y': 
                case 'x': 
                case 'y': {
                    if (n3 == 2) {
                        boolean bl = true;
                        boolean bl2 = true;
                        if (n4 + 1 < n) {
                            arrn[0] = arrn[0] + 1;
                            bl = bl2;
                            if (DateTimeFormat.isNumericToken(DateTimeFormat.parseToken(string2, arrn))) {
                                bl = false;
                            }
                            arrn[0] = arrn[0] - 1;
                        }
                        switch (c) {
                            default: {
                                dateTimeFormatterBuilder.appendTwoDigitYear(new DateTime().getYear() - 30, bl);
                                break block0;
                            }
                            case 'x': 
                        }
                        dateTimeFormatterBuilder.appendTwoDigitWeekyear(new DateTime().getWeekyear() - 30, bl);
                        break;
                    }
                    int n5 = n2 = 9;
                    if (n4 + 1 < n) {
                        arrn[0] = arrn[0] + 1;
                        if (DateTimeFormat.isNumericToken(DateTimeFormat.parseToken(string2, arrn))) {
                            n2 = n3;
                        }
                        arrn[0] = arrn[0] - 1;
                        n5 = n2;
                    }
                    switch (c) {
                        default: {
                            break block0;
                        }
                        case 'Y': {
                            dateTimeFormatterBuilder.appendYearOfEra(n3, n5);
                            break block0;
                        }
                        case 'x': {
                            dateTimeFormatterBuilder.appendWeekyear(n3, n5);
                            break block0;
                        }
                        case 'y': 
                    }
                    dateTimeFormatterBuilder.appendYear(n3, n5);
                    break;
                }
                case 'M': {
                    if (n3 >= 3) {
                        if (n3 >= 4) {
                            dateTimeFormatterBuilder.appendMonthOfYearText();
                            break;
                        }
                        dateTimeFormatterBuilder.appendMonthOfYearShortText();
                        break;
                    }
                    dateTimeFormatterBuilder.appendMonthOfYear(n3);
                    break;
                }
                case 'd': {
                    dateTimeFormatterBuilder.appendDayOfMonth(n3);
                    break;
                }
                case 'a': {
                    dateTimeFormatterBuilder.appendHalfdayOfDayText();
                    break;
                }
                case 'h': {
                    dateTimeFormatterBuilder.appendClockhourOfHalfday(n3);
                    break;
                }
                case 'H': {
                    dateTimeFormatterBuilder.appendHourOfDay(n3);
                    break;
                }
                case 'k': {
                    dateTimeFormatterBuilder.appendClockhourOfDay(n3);
                    break;
                }
                case 'K': {
                    dateTimeFormatterBuilder.appendHourOfHalfday(n3);
                    break;
                }
                case 'm': {
                    dateTimeFormatterBuilder.appendMinuteOfHour(n3);
                    break;
                }
                case 's': {
                    dateTimeFormatterBuilder.appendSecondOfMinute(n3);
                    break;
                }
                case 'S': {
                    dateTimeFormatterBuilder.appendFractionOfSecond(n3, n3);
                    break;
                }
                case 'e': {
                    dateTimeFormatterBuilder.appendDayOfWeek(n3);
                    break;
                }
                case 'E': {
                    if (n3 >= 4) {
                        dateTimeFormatterBuilder.appendDayOfWeekText();
                        break;
                    }
                    dateTimeFormatterBuilder.appendDayOfWeekShortText();
                    break;
                }
                case 'D': {
                    dateTimeFormatterBuilder.appendDayOfYear(n3);
                    break;
                }
                case 'w': {
                    dateTimeFormatterBuilder.appendWeekOfWeekyear(n3);
                    break;
                }
                case 'z': {
                    if (n3 >= 4) {
                        dateTimeFormatterBuilder.appendTimeZoneName();
                        break;
                    }
                    dateTimeFormatterBuilder.appendTimeZoneShortName(null);
                    break;
                }
                case 'Z': {
                    if (n3 == 1) {
                        dateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", false, 2, 2);
                        break;
                    }
                    if (n3 == 2) {
                        dateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", true, 2, 2);
                        break;
                    }
                    dateTimeFormatterBuilder.appendTimeZoneId();
                    break;
                }
                case '\'': {
                    string3 = string3.substring(1);
                    if (string3.length() == 1) {
                        dateTimeFormatterBuilder.appendLiteral(string3.charAt(0));
                        break;
                    }
                    dateTimeFormatterBuilder.appendLiteral(new String(string3));
                }
            }
            n2 = n4 + 1;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String parseToken(String string2, int[] arrn) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = arrn[0];
        int n3 = string2.length();
        char c = string2.charAt(n2);
        if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') {
            stringBuilder.append(c);
            do {
                n = n2;
                if (n2 + 1 < n3) {
                    n = n2;
                    if (string2.charAt(n2 + 1) == c) {
                        stringBuilder.append(c);
                        ++n2;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            stringBuilder.append('\'');
            int n4 = 0;
            do {
                n = n2;
                if (n2 >= n3) break;
                c = string2.charAt(n2);
                if (c == '\'') {
                    if (n2 + 1 < n3 && string2.charAt(n2 + 1) == '\'') {
                        ++n2;
                        stringBuilder.append(c);
                        n = n4;
                    } else {
                        n = n4 == 0 ? 1 : 0;
                    }
                } else {
                    if (n4 == 0 && (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')) {
                        n = n2 - 1;
                        break;
                    }
                    stringBuilder.append(c);
                    n = n4;
                }
                ++n2;
                n4 = n;
            } while (true);
        }
        arrn[0] = n;
        return stringBuilder.toString();
    }
}

