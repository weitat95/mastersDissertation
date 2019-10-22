/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.field.PreciseDurationDateTimeField;

final class BasicDayOfMonthDateTimeField
extends PreciseDurationDateTimeField {
    private final BasicChronology iChronology;

    BasicDayOfMonthDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.dayOfMonth(), durationField);
        this.iChronology = basicChronology;
    }

    @Override
    public int get(long l) {
        return this.iChronology.getDayOfMonth(l);
    }

    @Override
    public int getMaximumValue() {
        return this.iChronology.getDaysInMonthMax();
    }

    @Override
    public int getMaximumValue(long l) {
        return this.iChronology.getDaysInMonthMax(l);
    }

    @Override
    protected int getMaximumValueForSet(long l, int n) {
        return this.iChronology.getDaysInMonthMaxForSet(l, n);
    }

    @Override
    public int getMinimumValue() {
        return 1;
    }

    @Override
    public DurationField getRangeDurationField() {
        return this.iChronology.months();
    }

    @Override
    public boolean isLeap(long l) {
        return this.iChronology.isLeapDay(l);
    }
}

