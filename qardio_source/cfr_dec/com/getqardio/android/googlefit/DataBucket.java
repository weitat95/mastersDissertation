/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

public class DataBucket {
    private long endTimeStamp;
    private long startTimeStamp;

    public DataBucket(long l, long l2) {
        this.startTimeStamp = l;
        this.endTimeStamp = l2;
    }

    public long getEndTimeStamp() {
        return this.endTimeStamp;
    }

    public long getStartTimeStamp() {
        return this.startTimeStamp;
    }

    public void setStartTimeStamp(long l) {
        this.startTimeStamp = l;
    }
}

