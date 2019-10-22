/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity;

import com.getqardio.android.mvp.activity.ActivityTrackingDataSource;

public class ActivityTrackingRepository
implements ActivityTrackingDataSource {
    private final ActivityTrackingDataSource localDataSource;

    public ActivityTrackingRepository(ActivityTrackingDataSource activityTrackingDataSource) {
        this.localDataSource = activityTrackingDataSource;
    }
}

