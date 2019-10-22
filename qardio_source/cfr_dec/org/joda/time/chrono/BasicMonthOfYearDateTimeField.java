/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.BasicChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

class BasicMonthOfYearDateTimeField
extends ImpreciseDateTimeField {
    private final BasicChronology iChronology;
    private final int iLeapMonth;
    private final int iMax;

    BasicMonthOfYearDateTimeField(BasicChronology basicChronology, int n) {
        super(DateTimeFieldType.monthOfYear(), basicChronology.getAverageMillisPerMonth());
        this.iChronology = basicChronology;
        this.iMax = this.iChronology.getMaxMonth();
        this.iLeapMonth = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long add(long l, int n) {
        int n2;
        if (n == 0) {
            return l;
        }
        long l2 = this.iChronology.getMillisOfDay(l);
        int n3 = this.iChronology.getYear(l);
        int n4 = this.iChronology.getMonthOfYear(l, n3);
        int n5 = n4 - 1 + n;
        if (n4 > 0 && n5 < 0) {
            if (Math.signum(this.iMax + n) == Math.signum(n)) {
                n5 = n3 - 1;
                n2 = this.iMax + n;
                n = n5;
            } else {
                n2 = n3 + 1;
                n5 = n - this.iMax;
                n = n2;
                n2 = n5;
            }
            n5 = n2 + (n4 - 1);
            n2 = n;
            n = n5;
        } else {
            n2 = n3;
            n = n5;
        }
        if (n >= 0) {
            n2 += n / this.iMax;
            n5 = n % this.iMax + 1;
            n = n2;
            n2 = n5;
        } else {
            int n6;
            n5 = n2 + n / this.iMax - 1;
            n = n2 = Math.abs(n) % this.iMax;
            if (n2 == 0) {
                n = this.iMax;
            }
            n2 = n6 = this.iMax - n + 1;
            n = n5;
            if (n6 == 1) {
                n = n5 + 1;
                n2 = n6;
            }
        }
        if ((n3 = this.iChronology.getDayOfMonth(l, n3, n4)) > (n5 = this.iChronology.getDaysInYearMonth(n, n2))) {
            n3 = n5;
        }
        return this.iChronology.getYearMonthDayMillis(n, n2, n3) + l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long add(long l, long l2) {
        int n;
        long l3;
        int n2 = (int)l2;
        if ((long)n2 == l2) {
            return this.add(l, n2);
        }
        long l4 = this.iChronology.getMillisOfDay(l);
        int n3 = this.iChronology.getYear(l);
        int n4 = this.iChronology.getMonthOfYear(l, n3);
        long l5 = (long)(n4 - 1) + l2;
        if (l5 >= 0L) {
            l3 = (long)n3 + l5 / (long)this.iMax;
            l5 = l5 % (long)this.iMax + 1L;
        } else {
            long l6;
            long l7 = (long)n3 + l5 / (long)this.iMax - 1L;
            n2 = n = (int)(Math.abs(l5) % (long)this.iMax);
            if (n == 0) {
                n2 = this.iMax;
            }
            l5 = l6 = (long)(this.iMax - n2 + 1);
            l3 = l7;
            if (l6 == 1L) {
                l3 = l7 + 1L;
                l5 = l6;
            }
        }
        if (l3 < (long)this.iChronology.getMinYear() || l3 > (long)this.iChronology.getMaxYear()) {
            throw new IllegalArgumentException("Magnitude of add amount is too large: " + l2);
        }
        int n5 = (int)l3;
        int n6 = (int)l5;
        n2 = this.iChronology.getDayOfMonth(l, n3, n4);
        if (n2 > (n = this.iChronology.getDaysInYearMonth(n5, n6))) {
            n2 = n;
        }
        return this.iChronology.getYearMonthDayMillis(n5, n6, n2) + l4;
    }

    @Override
    public int get(long l) {
        return this.iChronology.getMonthOfYear(l);
    }

    @Override
    public DurationField getLeapDurationField() {
        return this.iChronology.days();
    }

    @Override
    public int getMaximumValue() {
        return this.iMax;
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
        boolean bl = false;
        int n = this.iChronology.getYear(l);
        boolean bl2 = bl;
        if (this.iChronology.isLeapYear(n)) {
            bl2 = bl;
            if (this.iChronology.getMonthOfYear(l, n) == this.iLeapMonth) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @Override
    public long remainder(long l) {
        return l - this.roundFloor(l);
    }

    @Override
    public long roundFloor(long l) {
        int n = this.iChronology.getYear(l);
        int n2 = this.iChronology.getMonthOfYear(l, n);
        return this.iChronology.getYearMonthMillis(n, n2);
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, 1, this.iMax);
        int n2 = this.iChronology.getYear(l);
        int n3 = this.iChronology.getDayOfMonth(l, n2);
        int n4 = this.iChronology.getDaysInYearMonth(n2, n);
        if (n3 > n4) {
            n3 = n4;
        }
        return this.iChronology.getYearMonthDayMillis(n2, n, n3) + (long)this.iChronology.getMillisOfDay(l);
    }
}

