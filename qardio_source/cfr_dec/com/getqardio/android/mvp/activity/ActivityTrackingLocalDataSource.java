/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity;

import com.getqardio.android.mvp.activity.ActivityTrackingDataSource;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerAggregator;
import io.realm.Realm;

public class ActivityTrackingLocalDataSource
implements ActivityTrackingDataSource {
    private int bufferTimeInS = 60;
    private final ActivityTrackerAggregator mActivityTrackerAggregator;
    private Realm realm;

    public ActivityTrackingLocalDataSource(Realm realm, int n, ActivityTrackerAggregator activityTrackerAggregator) {
        this.realm = realm;
        this.bufferTimeInS = n;
        this.mActivityTrackerAggregator = activityTrackerAggregator;
    }
}

