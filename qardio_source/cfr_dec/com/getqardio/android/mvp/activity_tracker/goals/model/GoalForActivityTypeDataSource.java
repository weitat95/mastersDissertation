/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.model;

import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import io.reactivex.Single;

public interface GoalForActivityTypeDataSource {
    public Single<ActivityTrackedGroupedByDay> setGoalForActivityType(long var1, int var3, int var4);
}

