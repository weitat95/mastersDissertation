/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.DataBucket;

public class ActivityDataBucket
extends DataBucket {
    private String activityType;
    private long duration;

    public ActivityDataBucket(long l, long l2, long l3, String string2) {
        super(l2, l3);
        this.activityType = string2;
        this.duration = l;
    }

    public String getActivityType() {
        return this.activityType;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long l) {
        this.duration = l;
    }
}

