/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.model;

import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import io.reactivex.Single;

public class GoalForActivityTypeRepository
implements GoalForActivityTypeDataSource {
    private final GoalForActivityTypeDataSource localDataSource;
    private final GoalForActivityTypeDataSource remoteDatasource;

    public GoalForActivityTypeRepository(GoalForActivityTypeDataSource goalForActivityTypeDataSource, GoalForActivityTypeDataSource goalForActivityTypeDataSource2) {
        this.localDataSource = goalForActivityTypeDataSource;
        this.remoteDatasource = goalForActivityTypeDataSource2;
    }

    @Override
    public Single<ActivityTrackedGroupedByDay> setGoalForActivityType(long l, int n, int n2) {
        return this.localDataSource.setGoalForActivityType(l, n, n2);
    }
}

