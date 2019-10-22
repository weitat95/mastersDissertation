/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.chrono.GJLocaleSymbols;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.UnsupportedDurationField;

final class GJEraDateTimeField
extends BaseDateTimeField {
    private final BasicChronology iChronology;

    GJEraDateTimeField(BasicChronology basicChronology) {
        super(DateTimeFieldType.era());
        this.iChronology = basicChronology;
    }

    @Override
    public int get(long l) {
        if (this.iChronology.getYear(l) <= 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public String getAsText(int n, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).eraValueToText(n);
    }

    @Override
    public DurationField getDurationField() {
        return UnsupportedDurationField.getInstance(DurationFieldType.eras());
    }

    @Override
    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.forLocale(locale).getEraMaxTextLength();
    }

    @Override
    public int getMaximumValue() {
        return 1;
    }

    @Override
    public int getMinimumValue() {
        return 0;
    }

    @Override
    public DurationField getRangeDurationField() {
        return null;
    }

    @Override
    public long roundCeiling(long l) {
        if (this.get(l) == 0) {
            return this.iChronology.setYear(0L, 1);
        }
        return Long.MAX_VALUE;
    }

    @Override
    public long roundFloor(long l) {
        if (this.get(l) == 1) {
            return this.iChronology.setYear(0L, 1);
        }
        return Long.MIN_VALUE;
    }

    @Override
    public long roundHalfCeiling(long l) {
        return this.roundFloor(l);
    }

    @Override
    public long roundHalfEven(long l) {
        return this.roundFloor(l);
    }

    @Override
    public long roundHalfFloor(long l) {
        return this.roundFloor(l);
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, 0, 1);
        long l2 = l;
        if (this.get(l) != n) {
            n = this.iChronology.getYear(l);
            l2 = this.iChronology.setYear(l, -n);
        }
        return l2;
    }

    @Override
    public long set(long l, String string2, Locale locale) {
        return this.set(l, GJLocaleSymbols.forLocale(locale).eraTextToValue(string2));
    }
}

