/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.Comparator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.IllegalFieldValueException;

class GJLocaleSymbols {
    private static ConcurrentMap<Locale, GJLocaleSymbols> cCache = new ConcurrentHashMap<Locale, GJLocaleSymbols>();
    private final String[] iDaysOfWeek;
    private final String[] iEras;
    private final String[] iHalfday;
    private final int iMaxDayOfWeekLength;
    private final int iMaxEraLength;
    private final int iMaxHalfdayLength;
    private final int iMaxMonthLength;
    private final int iMaxShortDayOfWeekLength;
    private final int iMaxShortMonthLength;
    private final String[] iMonths;
    private final TreeMap<String, Integer> iParseDaysOfWeek;
    private final TreeMap<String, Integer> iParseEras;
    private final TreeMap<String, Integer> iParseMonths;
    private final String[] iShortDaysOfWeek;
    private final String[] iShortMonths;

    private GJLocaleSymbols(Locale locale) {
        Integer[] arrinteger = DateTimeUtils.getDateFormatSymbols(locale);
        this.iEras = arrinteger.getEras();
        this.iDaysOfWeek = GJLocaleSymbols.realignDaysOfWeek(arrinteger.getWeekdays());
        this.iShortDaysOfWeek = GJLocaleSymbols.realignDaysOfWeek(arrinteger.getShortWeekdays());
        this.iMonths = GJLocaleSymbols.realignMonths(arrinteger.getMonths());
        this.iShortMonths = GJLocaleSymbols.realignMonths(arrinteger.getShortMonths());
        this.iHalfday = arrinteger.getAmPmStrings();
        arrinteger = new Integer[13];
        for (int i = 0; i < 13; ++i) {
            arrinteger[i] = i;
        }
        this.iParseEras = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        GJLocaleSymbols.addSymbols(this.iParseEras, this.iEras, arrinteger);
        if ("en".equals(locale.getLanguage())) {
            this.iParseEras.put("BCE", arrinteger[0]);
            this.iParseEras.put("CE", arrinteger[1]);
        }
        this.iParseDaysOfWeek = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        GJLocaleSymbols.addSymbols(this.iParseDaysOfWeek, this.iDaysOfWeek, arrinteger);
        GJLocaleSymbols.addSymbols(this.iParseDaysOfWeek, this.iShortDaysOfWeek, arrinteger);
        GJLocaleSymbols.addNumerals(this.iParseDaysOfWeek, 1, 7, arrinteger);
        this.iParseMonths = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        GJLocaleSymbols.addSymbols(this.iParseMonths, this.iMonths, arrinteger);
        GJLocaleSymbols.addSymbols(this.iParseMonths, this.iShortMonths, arrinteger);
        GJLocaleSymbols.addNumerals(this.iParseMonths, 1, 12, arrinteger);
        this.iMaxEraLength = GJLocaleSymbols.maxLength(this.iEras);
        this.iMaxDayOfWeekLength = GJLocaleSymbols.maxLength(this.iDaysOfWeek);
        this.iMaxShortDayOfWeekLength = GJLocaleSymbols.maxLength(this.iShortDaysOfWeek);
        this.iMaxMonthLength = GJLocaleSymbols.maxLength(this.iMonths);
        this.iMaxShortMonthLength = GJLocaleSymbols.maxLength(this.iShortMonths);
        this.iMaxHalfdayLength = GJLocaleSymbols.maxLength(this.iHalfday);
    }

    private static void addNumerals(TreeMap<String, Integer> treeMap, int n, int n2, Integer[] arrinteger) {
        while (n <= n2) {
            treeMap.put(String.valueOf(n).intern(), arrinteger[n]);
            ++n;
        }
    }

    private static void addSymbols(TreeMap<String, Integer> treeMap, String[] arrstring, Integer[] arrinteger) {
        int n;
        int n2 = arrstring.length;
        while ((n = n2 - 1) >= 0) {
            String string2 = arrstring[n];
            n2 = n;
            if (string2 == null) continue;
            treeMap.put(string2, arrinteger[n]);
            n2 = n;
        }
    }

    static GJLocaleSymbols forLocale(Locale object) {
        Locale locale = object;
        if (object == null) {
            locale = Locale.getDefault();
        }
        GJLocaleSymbols gJLocaleSymbols = (GJLocaleSymbols)cCache.get(locale);
        object = gJLocaleSymbols;
        if (gJLocaleSymbols != null || (object = cCache.putIfAbsent(locale, gJLocaleSymbols = new GJLocaleSymbols(locale))) != null) {
            return object;
        }
        return gJLocaleSymbols;
    }

    private static int maxLength(String[] arrstring) {
        int n = 0;
        int n2 = arrstring.length;
        while (--n2 >= 0) {
            int n3;
            String string2 = arrstring[n2];
            if (string2 == null || (n3 = string2.length()) <= n) continue;
            n = n3;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String[] realignDaysOfWeek(String[] arrstring) {
        String[] arrstring2 = new String[8];
        int n = 1;
        while (n < 8) {
            int n2 = n < 7 ? n + 1 : 1;
            arrstring2[n] = arrstring[n2];
            ++n;
        }
        return arrstring2;
    }

    private static String[] realignMonths(String[] arrstring) {
        String[] arrstring2 = new String[13];
        for (int i = 1; i < 13; ++i) {
            arrstring2[i] = arrstring[i - 1];
        }
        return arrstring2;
    }

    public int dayOfWeekTextToValue(String string2) {
        Integer n = this.iParseDaysOfWeek.get(string2);
        if (n != null) {
            return n;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.dayOfWeek(), string2);
    }

    public String dayOfWeekValueToShortText(int n) {
        return this.iShortDaysOfWeek[n];
    }

    public String dayOfWeekValueToText(int n) {
        return this.iDaysOfWeek[n];
    }

    public int eraTextToValue(String string2) {
        Integer n = this.iParseEras.get(string2);
        if (n != null) {
            return n;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.era(), string2);
    }

    public String eraValueToText(int n) {
        return this.iEras[n];
    }

    public int getDayOfWeekMaxTextLength() {
        return this.iMaxDayOfWeekLength;
    }

    public int getEraMaxTextLength() {
        return this.iMaxEraLength;
    }

    public int getHalfdayMaxTextLength() {
        return this.iMaxHalfdayLength;
    }

    public int getMonthMaxTextLength() {
        return this.iMaxMonthLength;
    }

    public int halfdayTextToValue(String string2) {
        int n;
        String[] arrstring = this.iHalfday;
        int n2 = arrstring.length;
        while ((n = n2 - 1) >= 0) {
            n2 = n;
            if (!arrstring[n].equalsIgnoreCase(string2)) continue;
            return n;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.halfdayOfDay(), string2);
    }

    public String halfdayValueToText(int n) {
        return this.iHalfday[n];
    }

    public int monthOfYearTextToValue(String string2) {
        Integer n = this.iParseMonths.get(string2);
        if (n != null) {
            return n;
        }
        throw new IllegalFieldValueException(DateTimeFieldType.monthOfYear(), string2);
    }

    public String monthOfYearValueToShortText(int n) {
        return this.iShortMonths[n];
    }

    public String monthOfYearValueToText(int n) {
        return this.iMonths[n];
    }
}

