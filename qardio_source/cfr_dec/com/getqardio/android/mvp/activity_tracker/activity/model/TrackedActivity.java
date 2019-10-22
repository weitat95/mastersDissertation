/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.activity.model;

import com.getqardio.android.mvp.activity_tracker.activity.model.TrackedDetectedActivity;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByHour;
import com.getqardio.android.mvp.activity_tracker.today.model.local.ActivityTrackedGroupedByMinute;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.TrackedActivityRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

public class TrackedActivity
extends RealmObject
implements TrackedActivityRealmProxyInterface {
    public RealmList<TrackedDetectedActivity> buffer;
    public RealmList<ActivityTrackedGroupedByDay> days;
    public RealmList<ActivityTrackedGroupedByHour> hours;
    public RealmList<ActivityTrackedGroupedByMinute> minutes;
    private long userId;

    public TrackedActivity() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public RealmList realmGet$buffer() {
        return this.buffer;
    }

    public RealmList realmGet$days() {
        return this.days;
    }

    public RealmList realmGet$hours() {
        return this.hours;
    }

    public RealmList realmGet$minutes() {
        return this.minutes;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    public void realmSet$buffer(RealmList realmList) {
        this.buffer = realmList;
    }

    public void realmSet$days(RealmList realmList) {
        this.days = realmList;
    }

    public void realmSet$hours(RealmList realmList) {
        this.hours = realmList;
    }

    public void realmSet$minutes(RealmList realmList) {
        this.minutes = realmList;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }
}

