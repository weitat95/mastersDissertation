/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

final class BasicWeekyearDateTimeField
extends ImpreciseDateTimeField {
    private final BasicChronology iChronology;

    BasicWeekyearDateTimeField(BasicChronology basicChronology) {
        super(DateTimeFieldType.weekyear(), basicChronology.getAverageMillisPerYear());
        this.iChronology = basicChronology;
    }

    @Override
    public long add(long l, int n) {
        if (n == 0) {
            return l;
        }
        return this.set(l, this.get(l) + n);
    }

    @Override
    public long add(long l, long l2) {
        return this.add(l, FieldUtils.safeToInt(l2));
    }

    @Override
    public int get(long l) {
        return this.iChronology.getWeekyear(l);
    }

    @Override
    public DurationField getLeapDurationField() {
        return this.iChronology.weeks();
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
        return this.iChronology.getWeeksInYear(this.iChronology.getWeekyear(l)) > 52;
    }

    @Override
    public long remainder(long l) {
        return l - this.roundFloor(l);
    }

    @Override
    public long roundFloor(long l) {
        long l2 = this.iChronology.weekOfWeekyear().roundFloor(l);
        int n = this.iChronology.getWeekOfWeekyear(l2);
        l = l2;
        if (n > 1) {
            l = l2 - (long)(n - 1) * 604800000L;
        }
        return l;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long set(long l, int n) {
        long l2;
        FieldUtils.verifyValueBounds(this, Math.abs(n), this.iChronology.getMinYear(), this.iChronology.getMaxYear());
        int n2 = this.get(l);
        if (n2 == n) {
            return l;
        }
        int n3 = this.iChronology.getDayOfWeek(l);
        n2 = this.iChronology.getWeeksInYear(n2);
        int n4 = this.iChronology.getWeeksInYear(n);
        if (n4 < n2) {
            n2 = n4;
        }
        if ((n4 = this.iChronology.getWeekOfWeekyear(l)) <= n2) {
            n2 = n4;
        }
        if ((n4 = this.get(l2 = this.iChronology.setYear(l, n))) < n) {
            l = l2 + 604800000L;
        } else {
            l = l2;
            if (n4 > n) {
                l = l2 - 604800000L;
            }
        }
        l2 = n2 - this.iChronology.getWeekOfWeekyear(l);
        return this.iChronology.dayOfWeek().set(l2 * 604800000L + l, n3);
    }
}

