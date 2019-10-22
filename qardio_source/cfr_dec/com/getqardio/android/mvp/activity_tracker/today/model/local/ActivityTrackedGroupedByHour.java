/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.today.model.local;

import io.realm.ActivityTrackedGroupedByHourRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class ActivityTrackedGroupedByHour
extends RealmObject
implements ActivityTrackedGroupedByHourRealmProxyInterface {
    public long endTimestamp;
    public long startTimestamp;
    public long steps;
    private long userId;

    public ActivityTrackedGroupedByHour() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    @Override
    public long realmGet$endTimestamp() {
        return this.endTimestamp;
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
    public void realmSet$endTimestamp(long l) {
        this.endTimestamp = l;
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

