/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.chrono.BasicChronology;

abstract class BasicGJChronology
extends BasicChronology {
    private static final int[] MAX_DAYS_PER_MONTH_ARRAY;
    private static final long[] MAX_TOTAL_MILLIS_BY_MONTH_ARRAY;
    private static final int[] MIN_DAYS_PER_MONTH_ARRAY;
    private static final long[] MIN_TOTAL_MILLIS_BY_MONTH_ARRAY;

    static {
        long l = 0L;
        MIN_DAYS_PER_MONTH_ARRAY = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        MAX_DAYS_PER_MONTH_ARRAY = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        MIN_TOTAL_MILLIS_BY_MONTH_ARRAY = new long[12];
        MAX_TOTAL_MILLIS_BY_MONTH_ARRAY = new long[12];
        long l2 = 0L;
        for (int i = 0; i < 11; ++i) {
            BasicGJChronology.MIN_TOTAL_MILLIS_BY_MONTH_ARRAY[i + 1] = l2 += (long)MIN_DAYS_PER_MONTH_ARRAY[i] * 86400000L;
            BasicGJChronology.MAX_TOTAL_MILLIS_BY_MONTH_ARRAY[i + 1] = l += (long)MAX_DAYS_PER_MONTH_ARRAY[i] * 86400000L;
        }
    }

    BasicGJChronology(Chronology chronology, Object object, int n) {
        super(chronology, object, n);
    }

    @Override
    int getDaysInMonthMaxForSet(long l, int n) {
        int n2 = 28;
        if (n > 28 || n < 1) {
            n2 = this.getDaysInMonthMax(l);
        }
        return n2;
    }

    @Override
    int getDaysInYearMonth(int n, int n2) {
        if (this.isLeapYear(n)) {
            return MAX_DAYS_PER_MONTH_ARRAY[n2 - 1];
        }
        return MIN_DAYS_PER_MONTH_ARRAY[n2 - 1];
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    int getMonthOfYear(long l, int n) {
        int n2 = (int)(l - this.getYearMillis(n) >> 10);
        if (this.isLeapYear(n)) {
            if (n2 < 15356250) {
                if (n2 < 7678125) {
                    if (n2 < 2615625) return 1;
                    {
                        if (n2 >= 5062500) return 3;
                        return 2;
                    }
                }
                if (n2 < 10209375) {
                    return 4;
                }
                if (n2 >= 12825000) return 6;
                return 5;
            }
            if (n2 < 23118750) {
                if (n2 < 17971875) {
                    return 7;
                }
                if (n2 >= 20587500) return 9;
                return 8;
            }
            if (n2 < 25734375) {
                return 10;
            }
            if (n2 >= 28265625) return 12;
            return 11;
        }
        if (n2 < 15271875) {
            if (n2 < 7593750) {
                if (n2 < 2615625) {
                    return 1;
                }
                if (n2 >= 4978125) return 3;
                return 2;
            }
            if (n2 < 10125000) {
                return 4;
            }
            if (n2 >= 12740625) return 6;
            return 5;
        }
        if (n2 < 23034375) {
            if (n2 < 17887500) {
                return 7;
            }
            if (n2 >= 20503125) return 9;
            return 8;
        }
        if (n2 < 25650000) {
            return 10;
        }
        if (n2 >= 28181250) return 12;
        return 11;
    }

    @Override
    long getTotalMillisByYearMonth(int n, int n2) {
        if (this.isLeapYear(n)) {
            return MAX_TOTAL_MILLIS_BY_MONTH_ARRAY[n2 - 1];
        }
        return MIN_TOTAL_MILLIS_BY_MONTH_ARRAY[n2 - 1];
    }

    @Override
    boolean isLeapDay(long l) {
        return this.dayOfMonth().get(l) == 29 && this.monthOfYear().isLeap(l);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    long setYear(long l, int n) {
        int n2 = this.getYear(l);
        int n3 = this.getDayOfYear(l, n2);
        int n4 = this.getMillisOfDay(l);
        int n5 = n3;
        if (n3 <= 59) return this.getYearMonthDayMillis(n, 1, n5) + (long)n4;
        if (this.isLeapYear(n2)) {
            n5 = n3;
            if (this.isLeapYear(n)) return this.getYearMonthDayMillis(n, 1, n5) + (long)n4;
            n5 = n3 - 1;
            return this.getYearMonthDayMillis(n, 1, n5) + (long)n4;
        }
        n5 = n3;
        if (!this.isLeapYear(n)) return this.getYearMonthDayMillis(n, 1, n5) + (long)n4;
        n5 = n3 + 1;
        return this.getYearMonthDayMillis(n, 1, n5) + (long)n4;
    }
}

