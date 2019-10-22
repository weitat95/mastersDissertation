/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

class BasicYearDateTimeField
extends ImpreciseDateTimeField {
    protected final BasicChronology iChronology;

    BasicYearDateTimeField(BasicChronology basicChronology) {
        super(DateTimeFieldType.year(), basicChronology.getAverageMillisPerYear());
        this.iChronology = basicChronology;
    }

    @Override
    public long add(long l, int n) {
        if (n == 0) {
            return l;
        }
        return this.set(l, FieldUtils.safeAdd(this.get(l), n));
    }

    @Override
    public long add(long l, long l2) {
        return this.add(l, FieldUtils.safeToInt(l2));
    }

    @Override
    public int get(long l) {
        return this.iChronology.getYear(l);
    }

    @Override
    public DurationField getLeapDurationField() {
        return this.iChronology.days();
    }

    @Override
    public int getMaximumValue() {
        return this.iChronology.getMaxYear();
    }

    @Override
    public int getMinimumValue() {
        return this.iChronology.getMinYear();
    }

    @Override
    public DurationField getRangeDurationField() {
        return null;
    }

    @Override
    public boolean isLeap(long l) {
        return this.iChronology.isLeapYear(this.get(l));
    }

    @Override
    public long remainder(long l) {
        return l - this.roundFloor(l);
    }

    @Override
    public long roundCeiling(long l) {
        int n = this.get(l);
        long l2 = l;
        if (l != this.iChronology.getYearMillis(n)) {
            l2 = this.iChronology.getYearMillis(n + 1);
        }
        return l2;
    }

    @Override
    public long roundFloor(long l) {
        return this.iChronology.getYearMillis(this.get(l));
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, this.iChronology.getMinYear(), this.iChronology.getMaxYear());
        return this.iChronology.setYear(l, n);
    }

    @Override
    public long setExtended(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, this.iChronology.getMinYear() - 1, this.iChronology.getMaxYear() + 1);
        return this.iChronology.setYear(l, n);
    }
}

