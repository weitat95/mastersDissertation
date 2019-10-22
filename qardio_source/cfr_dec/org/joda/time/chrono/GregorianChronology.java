/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.chrono.BasicGJChronology;
import org.joda.time.chrono.ZonedChronology;

public final class GregorianChronology
extends BasicGJChronology {
    private static final GregorianChronology INSTANCE_UTC;
    private static final ConcurrentHashMap<DateTimeZone, GregorianChronology[]> cCache;

    static {
        cCache = new ConcurrentHashMap();
        INSTANCE_UTC = GregorianChronology.getInstance(DateTimeZone.UTC);
    }

    private GregorianChronology(Chronology chronology, Object object, int n) {
        super(chronology, object, n);
    }

    public static GregorianChronology getInstance(DateTimeZone dateTimeZone) {
        return GregorianChronology.getInstance(dateTimeZone, 4);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GregorianChronology getInstance(DateTimeZone arrgregorianChronology, int n) {
        Object object;
        GregorianChronology[] arrgregorianChronology2 = arrgregorianChronology;
        if (arrgregorianChronology == null) {
            arrgregorianChronology2 = DateTimeZone.getDefault();
        }
        if ((arrgregorianChronology = cCache.get(arrgregorianChronology2)) == null && (object = cCache.putIfAbsent((DateTimeZone)arrgregorianChronology2, arrgregorianChronology = new GregorianChronology[7])) != null) {
            arrgregorianChronology = object;
        }
        GregorianChronology gregorianChronology = arrgregorianChronology[n - 1];
        object = gregorianChronology;
        if (gregorianChronology == null) {
            synchronized (arrgregorianChronology) {
                gregorianChronology = arrgregorianChronology[n - 1];
                object = gregorianChronology;
                if (gregorianChronology == null) {
                    object = arrgregorianChronology2 == DateTimeZone.UTC ? new GregorianChronology(null, null, n) : new GregorianChronology(ZonedChronology.getInstance(GregorianChronology.getInstance(DateTimeZone.UTC, n), (DateTimeZone)arrgregorianChronology2), null, n);
                    arrgregorianChronology[n - 1] = object;
                }
            }
        }
        return object;
    }

    public static GregorianChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    @Override
    protected void assemble(AssembledChronology.Fields fields) {
        if (this.getBase() == null) {
            super.assemble(fields);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    long calculateFirstDayOfYearMillis(int n) {
        int n2 = n / 100;
        if (n < 0) {
            n2 = (n2 + 3 >> 2) + ((n + 3 >> 2) - n2) - 1;
        } else {
            int n3;
            n2 = n3 = (n2 >> 2) + ((n >> 2) - n2);
            if (this.isLeapYear(n)) {
                n2 = n3 - 1;
            }
        }
        long l = n;
        return ((long)(n2 - 719527) + l * 365L) * 86400000L;
    }

    @Override
    long getApproxMillisAtEpochDividedByTwo() {
        return 31083597720000L;
    }

    @Override
    long getAverageMillisPerMonth() {
        return 2629746000L;
    }

    @Override
    long getAverageMillisPerYear() {
        return 31556952000L;
    }

    @Override
    long getAverageMillisPerYearDividedByTwo() {
        return 15778476000L;
    }

    @Override
    int getMaxYear() {
        return 292278993;
    }

    @Override
    int getMinYear() {
        return -292275054;
    }

    @Override
    boolean isLeapYear(int n) {
        return (n & 3) == 0 && (n % 100 != 0 || n % 400 == 0);
    }

    @Override
    public Chronology withUTC() {
        return INSTANCE_UTC;
    }

    @Override
    public Chronology withZone(DateTimeZone dateTimeZone) {
        DateTimeZone dateTimeZone2 = dateTimeZone;
        if (dateTimeZone == null) {
            dateTimeZone2 = DateTimeZone.getDefault();
        }
        if (dateTimeZone2 == this.getZone()) {
            return this;
        }
        return GregorianChronology.getInstance(dateTimeZone2);
    }
}

