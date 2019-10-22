/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.model.remote;

import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import io.reactivex.Single;

public class GoalForActivityTypeRemoteDataSource
implements GoalForActivityTypeDataSource {
    private AccountContextHelper accountContextHelper;
    private final ServerInterface serverInterface;

    public GoalForActivityTypeRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        this.serverInterface = serverInterface;
        this.accountContextHelper = accountContextHelper;
    }

    @Override
    public Single<ActivityTrackedGroupedByDay> setGoalForActivityType(long l, int n, int n2) {
        return null;
    }
}

