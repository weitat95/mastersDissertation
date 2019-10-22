/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.field.PreciseDurationDateTimeField;

final class BasicDayOfYearDateTimeField
extends PreciseDurationDateTimeField {
    private final BasicChronology iChronology;

    BasicDayOfYearDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfYear(), durationField);
        this.iChronology = basicChronology;
    }

    @Override
    public int get(long l) {
        return this.iChronology.getDayOfYear(l);
    }

    @Override
    public int getMaximumValue() {
        return this.iChronology.getDaysInYearMax();
    }

    @Override
    public int getMaximumValue(long l) {
        int n = this.iChronology.getYear(l);
        return this.iChronology.getDaysInYear(n);
    }

    @Override
    protected int getMaximumValueForSet(long l, int n) {
        int n2 = this.iChronology.getDaysInYearMax() - 1;
        if (n > n2 || n < 1) {
            n2 = this.getMaximumValue(l);
        }
        return n2;
    }

    @Override
    public int getMinimumValue() {
        return 1;
    }

    @Override
    public DurationField getRangeDurationField() {
        return this.iChronology.years();
    }

    @Override
    public boolean isLeap(long l) {
        return this.iChronology.isLeapDay(l);
    }
}

