/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import org.joda.time.DateTimeZone;

public class CachedDateTimeZone
extends DateTimeZone {
    private static final int cInfoCacheMask;
    private final transient Info[] iInfoCache = new Info[cInfoCacheMask + 1];
    private final DateTimeZone iZone;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        int n;
        Integer n2;
        try {
            n2 = Integer.getInteger("org.joda.time.tz.CachedDateTimeZone.size");
        }
        catch (SecurityException securityException) {
            n2 = null;
        }
        if (n2 == null) {
            n = 512;
        } else {
            int n3 = 0;
            for (n = n2 - 1; n > 0; ++n3, n >>= 1) {
            }
            n = 1 << n3;
        }
        cInfoCacheMask = n - 1;
    }

    private CachedDateTimeZone(DateTimeZone dateTimeZone) {
        super(dateTimeZone.getID());
        this.iZone = dateTimeZone;
    }

    private Info createInfo(long l) {
        Info info;
        long l2 = l & 0xFFFFFFFF00000000L;
        Info info2 = info = new Info(this.iZone, l2);
        l = l2;
        long l3;
        while ((l3 = this.iZone.nextTransition(l)) != l && l3 <= (l2 | 0xFFFFFFFFL)) {
            Info info3;
            info2.iNextInfo = info3 = new Info(this.iZone, l3);
            info2 = info3;
            l = l3;
        }
        return info;
    }

    public static CachedDateTimeZone forZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone instanceof CachedDateTimeZone) {
            return (CachedDateTimeZone)dateTimeZone;
        }
        return new CachedDateTimeZone(dateTimeZone);
    }

    private Info getInfo(long l) {
        Info info;
        block3: {
            block2: {
                Info[] arrinfo = this.iInfoCache;
                int n = (int)(l >> 32);
                int n2 = n & cInfoCacheMask;
                Info info2 = arrinfo[n2];
                if (info2 == null) break block2;
                info = info2;
                if ((int)(info2.iPeriodStart >> 32) == n) break block3;
            }
            arrinfo[n2] = info = this.createInfo(l);
        }
        return info;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof CachedDateTimeZone) {
            return this.iZone.equals(((CachedDateTimeZone)object).iZone);
        }
        return false;
    }

    @Override
    public String getNameKey(long l) {
        return this.getInfo(l).getNameKey(l);
    }

    @Override
    public int getOffset(long l) {
        return this.getInfo(l).getOffset(l);
    }

    @Override
    public int getStandardOffset(long l) {
        return this.getInfo(l).getStandardOffset(l);
    }

    @Override
    public int hashCode() {
        return this.iZone.hashCode();
    }

    @Override
    public boolean isFixed() {
        return this.iZone.isFixed();
    }

    @Override
    public long nextTransition(long l) {
        return this.iZone.nextTransition(l);
    }

    @Override
    public long previousTransition(long l) {
        return this.iZone.previousTransition(l);
    }

    private static final class Info {
        private String iNameKey;
        Info iNextInfo;
        private int iOffset = Integer.MIN_VALUE;
        public final long iPeriodStart;
        private int iStandardOffset = Integer.MIN_VALUE;
        public final DateTimeZone iZoneRef;

        Info(DateTimeZone dateTimeZone, long l) {
            this.iPeriodStart = l;
            this.iZoneRef = dateTimeZone;
        }

        public String getNameKey(long l) {
            if (this.iNextInfo == null || l < this.iNextInfo.iPeriodStart) {
                if (this.iNameKey == null) {
                    this.iNameKey = this.iZoneRef.getNameKey(this.iPeriodStart);
                }
                return this.iNameKey;
            }
            return this.iNextInfo.getNameKey(l);
        }

        public int getOffset(long l) {
            if (this.iNextInfo == null || l < this.iNextInfo.iPeriodStart) {
                if (this.iOffset == Integer.MIN_VALUE) {
                    this.iOffset = this.iZoneRef.getOffset(this.iPeriodStart);
                }
                return this.iOffset;
            }
            return this.iNextInfo.getOffset(l);
        }

        public int getStandardOffset(long l) {
            if (this.iNextInfo == null || l < this.iNextInfo.iPeriodStart) {
                if (this.iStandardOffset == Integer.MIN_VALUE) {
                    this.iStandardOffset = this.iZoneRef.getStandardOffset(this.iPeriodStart);
                }
                return this.iStandardOffset;
            }
            return this.iNextInfo.getStandardOffset(l);
        }
    }

}

