/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedDetectedActivity;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByHour;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByMinute;
import io.realm.RealmList;

public interface TrackedActivityRealmProxyInterface {
    public RealmList<TrackedDetectedActivity> realmGet$buffer();

    public RealmList<ActivityTrackedGroupedByDay> realmGet$days();

    public RealmList<ActivityTrackedGroupedByHour> realmGet$hours();

    public RealmList<ActivityTrackedGroupedByMinute> realmGet$minutes();

    public long realmGet$userId();

    public void realmSet$buffer(RealmList<TrackedDetectedActivity> var1);

    public void realmSet$days(RealmList<ActivityTrackedGroupedByDay> var1);

    public void realmSet$hours(RealmList<ActivityTrackedGroupedByHour> var1);

    public void realmSet$minutes(RealmList<ActivityTrackedGroupedByMinute> var1);

    public void realmSet$userId(long var1);
}

