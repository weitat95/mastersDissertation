/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.chrono.GJLocaleSymbols;
import org.joda.time.field.PreciseDurationDateTimeField;

final class GJDayOfWeekDateTimeField
extends PreciseDurationDateTimeField {
    private final BasicChronology iChronology;

    GJDayOfWeekDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfWeek(), durationField);
        this.iChronology = basicChronology;
    }

    @Override
    protected int convertText(String string2, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).dayOfWeekTextToValue(string2);
    }

    @Override
    public int get(long l) {
        return this.iChronology.getDayOfWeek(l);
    }

    @Override
    public String getAsShortText(int n, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).dayOfWeekValueToShortText(n);
    }

    @Override
    public String getAsText(int n, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).dayOfWeekValueToText(n);
    }

    @Override
    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.forLocale(locale).getDayOfWeekMaxTextLength();
    }

    @Override
    public int getMaximumValue() {
        return 7;
    }

    @Override
    public int getMinimumValue() {
        return 1;
    }

    @Override
    public DurationField getRangeDurationField() {
        return this.iChronology.weeks();
    }
}

