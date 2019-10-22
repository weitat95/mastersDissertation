/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.chrono.BasicMonthOfYearDateTimeField;
import org.joda.time.chrono.GJLocaleSymbols;

final class GJMonthOfYearDateTimeField
extends BasicMonthOfYearDateTimeField {
    GJMonthOfYearDateTimeField(BasicChronology basicChronology) {
        super(basicChronology, 2);
    }

    @Override
    protected int convertText(String string2, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).monthOfYearTextToValue(string2);
    }

    @Override
    public String getAsShortText(int n, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).monthOfYearValueToShortText(n);
    }

    @Override
    public String getAsText(int n, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).monthOfYearValueToText(n);
    }

    @Override
    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.forLocale(locale).getMonthMaxTextLength();
    }
}

