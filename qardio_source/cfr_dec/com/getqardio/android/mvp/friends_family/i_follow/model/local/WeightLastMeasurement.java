/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;
import io.realm.RealmObject;
import io.realm.WeightLastMeasurementRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

public class WeightLastMeasurement
extends RealmObject
implements WeightLastMeasurementRealmProxyInterface {
    private WeightExtraInfo extraInfo;
    private Integer impedance;
    private Long time;
    private long userId;
    private Float weight;

    public WeightLastMeasurement() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public WeightExtraInfo getExtraInfo() {
        return this.realmGet$extraInfo();
    }

    public Long getTime() {
        return this.realmGet$time();
    }

    public Float getWeight() {
        return this.realmGet$weight();
    }

    @Override
    public WeightExtraInfo realmGet$extraInfo() {
        return this.extraInfo;
    }

    @Override
    public Integer realmGet$impedance() {
        return this.impedance;
    }

    @Override
    public Long realmGet$time() {
        return this.time;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public Float realmGet$weight() {
        return this.weight;
    }

    @Override
    public void realmSet$extraInfo(WeightExtraInfo weightExtraInfo) {
        this.extraInfo = weightExtraInfo;
    }

    @Override
    public void realmSet$impedance(Integer n) {
        this.impedance = n;
    }

    @Override
    public void realmSet$time(Long l) {
        this.time = l;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }

    @Override
    public void realmSet$weight(Float f) {
        this.weight = f;
    }

    public void setExtraInfo(WeightExtraInfo weightExtraInfo) {
        this.realmSet$extraInfo(weightExtraInfo);
    }

    public void setImpedance(Integer n) {
        this.realmSet$impedance(n);
    }

    public void setTime(Long l) {
        this.realmSet$time(l);
    }

    public void setUserId(long l) {
        this.realmSet$userId(l);
    }

    public void setWeight(Float f) {
        this.realmSet$weight(f);
    }
}

