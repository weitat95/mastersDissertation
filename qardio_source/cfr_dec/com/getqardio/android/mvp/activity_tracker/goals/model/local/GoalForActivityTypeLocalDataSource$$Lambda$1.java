/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.model.local;

import com.getqardio.android.mvp.activity_tracker.goals.model.local.GoalForActivityTypeLocalDataSource;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;

final class GoalForActivityTypeLocalDataSource$$Lambda$1
implements Realm.Transaction {
    private final int arg$1;
    private final ActivityTrackedGroupedByDay arg$2;
    private final int arg$3;

    private GoalForActivityTypeLocalDataSource$$Lambda$1(int n, ActivityTrackedGroupedByDay activityTrackedGroupedByDay, int n2) {
        this.arg$1 = n;
        this.arg$2 = activityTrackedGroupedByDay;
        this.arg$3 = n2;
    }

    public static Realm.Transaction lambdaFactory$(int n, ActivityTrackedGroupedByDay activityTrackedGroupedByDay, int n2) {
        return new GoalForActivityTypeLocalDataSource$$Lambda$1(n, activityTrackedGroupedByDay, n2);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        GoalForActivityTypeLocalDataSource.lambda$setGoalForActivityType$0(this.arg$1, this.arg$2, this.arg$3, realm);
    }
}

