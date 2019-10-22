/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import java.util.Date;

public class ClaimMeasurement {
    public String data1;
    public String data2;
    public String data3;
    public String deviceId;
    public String fullname;
    public int id;
    public Boolean irregularHeartBeat;
    public Double latitude;
    public Double longitude;
    public Date measureDate;
    public String measurementId;
    public Long memberId;
    public String note;
    public Integer source;
    public Integer syncStatus;
    public Integer tag;
    public String timezone;
    public Long userId;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) {
            return true;
        }
        boolean bl2 = bl;
        if (object == null) return bl2;
        bl2 = bl;
        if (this.getClass() != object.getClass()) return bl2;
        object = (ClaimMeasurement)object;
        bl2 = bl;
        if (!this.fullname.equals(((ClaimMeasurement)object).fullname)) return bl2;
        return this.measureDate.equals(((ClaimMeasurement)object).measureDate);
    }

    public int hashCode() {
        return this.fullname.hashCode() * 31 + this.measureDate.hashCode();
    }
}

