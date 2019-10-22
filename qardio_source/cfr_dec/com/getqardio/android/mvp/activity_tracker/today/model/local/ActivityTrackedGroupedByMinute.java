/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.model.local;

import io.realm.ActivityTrackedGroupedByMinuteRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class ActivityTrackedGroupedByMinute
extends RealmObject
implements ActivityTrackedGroupedByMinuteRealmProxyInterface {
    public String activityType;
    public long duration;
    public long startTimestamp;
    public long steps;
    private long userId;

    public ActivityTrackedGroupedByMinute() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    @Override
    public String realmGet$activityType() {
        return this.activityType;
    }

    @Override
    public long realmGet$duration() {
        return this.duration;
    }

    @Override
    public long realmGet$startTimestamp() {
        return this.startTimestamp;
    }

    @Override
    public long realmGet$steps() {
        return this.steps;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public void realmSet$activityType(String string2) {
        this.activityType = string2;
    }

    @Override
    public void realmSet$duration(long l) {
        this.duration = l;
    }

    @Override
    public void realmSet$startTimestamp(long l) {
        this.startTimestamp = l;
    }

    @Override
    public void realmSet$steps(long l) {
        this.steps = l;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }
}

