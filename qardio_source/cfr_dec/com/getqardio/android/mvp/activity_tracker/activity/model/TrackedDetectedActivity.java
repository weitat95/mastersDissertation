/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.activity.model;

import io.realm.RealmObject;
import io.realm.TrackedDetectedActivityRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

public class TrackedDetectedActivity
extends RealmObject
implements TrackedDetectedActivityRealmProxyInterface {
    public int confidence;
    public long endTimestamp;
    public long startTimestamp;
    public int type;
    private long userId;

    public TrackedDetectedActivity() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object instanceof TrackedDetectedActivity) {
            object = (TrackedDetectedActivity)object;
            bl2 = bl;
            if (this.realmGet$startTimestamp() == ((TrackedDetectedActivity)object).realmGet$startTimestamp()) {
                bl2 = bl;
                if (this.realmGet$type() == ((TrackedDetectedActivity)object).realmGet$type()) {
                    bl2 = bl;
                    if (this.realmGet$confidence() == ((TrackedDetectedActivity)object).realmGet$confidence()) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    @Override
    public int realmGet$confidence() {
        return this.confidence;
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
    public int realmGet$type() {
        return this.type;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public void realmSet$confidence(int n) {
        this.confidence = n;
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
    public void realmSet$type(int n) {
        this.type = n;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }
}

