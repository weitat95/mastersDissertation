/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.field.PreciseDurationDateTimeField;

final class BasicWeekOfWeekyearDateTimeField
extends PreciseDurationDateTimeField {
    private final BasicChronology iChronology;

    BasicWeekOfWeekyearDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.weekOfWeekyear(), durationField);
        this.iChronology = basicChronology;
    }

    @Override
    public int get(long l) {
        return this.iChronology.getWeekOfWeekyear(l);
    }

    @Override
    public int getMaximumValue() {
        return 53;
    }

    @Override
    public int getMaximumValue(long l) {
        int n = this.iChronology.getWeekyear(l);
        return this.iChronology.getWeeksInYear(n);
    }

    @Override
    protected int getMaximumValueForSet(long l, int n) {
        int n2 = 52;
        if (n > 52) {
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
        return this.iChronology.weekyears();
    }

    @Override
    public long remainder(long l) {
        return super.remainder(259200000L + l);
    }

    @Override
    public long roundCeiling(long l) {
        return super.roundCeiling(l + 259200000L) - 259200000L;
    }

    @Override
    public long roundFloor(long l) {
        return super.roundFloor(l + 259200000L) - 259200000L;
    }
}

