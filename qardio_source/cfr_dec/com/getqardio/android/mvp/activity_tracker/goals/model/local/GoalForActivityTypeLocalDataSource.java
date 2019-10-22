/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.model.local;

import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedActivity;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeDataSource;
import com.getqardio.android.mvp.activity_tracker.goals.model.local.GoalForActivityTypeLocalDataSource$$Lambda$1;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmQuery;

public class GoalForActivityTypeLocalDataSource
implements GoalForActivityTypeDataSource {
    private Realm realm;

    public GoalForActivityTypeLocalDataSource(Realm realm) {
        this.realm = realm;
    }

    private ActivityTrackedGroupedByDay getLastDayInserted(long l) {
        return this.realm.where(TrackedActivity.class).equalTo("userId", l).findFirst().realmGet$days().last(null);
    }

    static /* synthetic */ void lambda$setGoalForActivityType$0(int n, ActivityTrackedGroupedByDay activityTrackedGroupedByDay, int n2, Realm realm) {
        switch (n) {
            default: {
                return;
            }
            case 0: {
                activityTrackedGroupedByDay.realmSet$goalWalk(n2);
                realm.copyToRealmOrUpdate(activityTrackedGroupedByDay);
                return;
            }
            case 1: {
                activityTrackedGroupedByDay.realmSet$goalRun(n2);
                realm.copyToRealmOrUpdate(activityTrackedGroupedByDay);
                return;
            }
            case 2: {
                activityTrackedGroupedByDay.realmSet$goalCycle(n2);
                realm.copyToRealmOrUpdate(activityTrackedGroupedByDay);
                return;
            }
            case 3: {
                activityTrackedGroupedByDay.realmSet$goalSteps(n2);
                realm.copyToRealmOrUpdate(activityTrackedGroupedByDay);
                return;
            }
            case 5: 
        }
        activityTrackedGroupedByDay.realmSet$goalActivity(n2);
        realm.copyToRealmOrUpdate(activityTrackedGroupedByDay);
    }

    @Override
    public Single<ActivityTrackedGroupedByDay> setGoalForActivityType(long l, int n, int n2) {
        ActivityTrackedGroupedByDay activityTrackedGroupedByDay = this.getLastDayInserted(l);
        if (activityTrackedGroupedByDay != null) {
            this.realm.executeTransaction(GoalForActivityTypeLocalDataSource$$Lambda$1.lambdaFactory$(n, activityTrackedGroupedByDay, n2));
        }
        return Single.just(activityTrackedGroupedByDay);
    }
}

