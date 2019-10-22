/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker;

import com.getqardio.android.mvp.activity_tracker.ActivityRecognitionManager;
import com.getqardio.android.mvp.activity_tracker.ActivityTrackerSetting;
import com.getqardio.android.mvp.activity_tracker.StepCounterManager;

public class ActivityTrackerAggregator {
    private ActivityRecognitionManager activityRecognitionManager;
    private ActivityTrackerSetting setting;
    private StepCounterManager stepCounterManager;

    public ActivityTrackerAggregator(ActivityTrackerSetting activityTrackerSetting, ActivityRecognitionManager activityRecognitionManager, StepCounterManager stepCounterManager) {
        this.setting = activityTrackerSetting;
        this.activityRecognitionManager = activityRecognitionManager;
        this.stepCounterManager = stepCounterManager;
    }
}

