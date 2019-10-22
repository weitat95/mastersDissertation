/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;
import io.realm.BpLastMeasurementRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class BpLastMeasurement
extends RealmObject
implements BpLastMeasurementRealmProxyInterface {
    private LastBpMeasurementData averageLastDay;
    private LastBpMeasurementData averageLastMonth;
    private LastBpMeasurementData averageLastWeek;
    private Integer dia;
    private Integer pulse;
    private Integer sys;
    private long time;
    private long userId;

    public BpLastMeasurement() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public LastBpMeasurementData getAverageLastMonth() {
        return this.realmGet$averageLastMonth();
    }

    public LastBpMeasurementData getAverageLastWeek() {
        return this.realmGet$averageLastWeek();
    }

    public Integer getDia() {
        return this.realmGet$dia();
    }

    public Integer getPulse() {
        return this.realmGet$pulse();
    }

    public Integer getSys() {
        return this.realmGet$sys();
    }

    public long getTime() {
        return this.realmGet$time();
    }

    @Override
    public LastBpMeasurementData realmGet$averageLastDay() {
        return this.averageLastDay;
    }

    @Override
    public LastBpMeasurementData realmGet$averageLastMonth() {
        return this.averageLastMonth;
    }

    @Override
    public LastBpMeasurementData realmGet$averageLastWeek() {
        return this.averageLastWeek;
    }

    @Override
    public Integer realmGet$dia() {
        return this.dia;
    }

    @Override
    public Integer realmGet$pulse() {
        return this.pulse;
    }

    @Override
    public Integer realmGet$sys() {
        return this.sys;
    }

    @Override
    public long realmGet$time() {
        return this.time;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public void realmSet$averageLastDay(LastBpMeasurementData lastBpMeasurementData) {
        this.averageLastDay = lastBpMeasurementData;
    }

    @Override
    public void realmSet$averageLastMonth(LastBpMeasurementData lastBpMeasurementData) {
        this.averageLastMonth = lastBpMeasurementData;
    }

    @Override
    public void realmSet$averageLastWeek(LastBpMeasurementData lastBpMeasurementData) {
        this.averageLastWeek = lastBpMeasurementData;
    }

    @Override
    public void realmSet$dia(Integer n) {
        this.dia = n;
    }

    @Override
    public void realmSet$pulse(Integer n) {
        this.pulse = n;
    }

    @Override
    public void realmSet$sys(Integer n) {
        this.sys = n;
    }

    @Override
    public void realmSet$time(long l) {
        this.time = l;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }

    public void setAverageLastDay(LastBpMeasurementData lastBpMeasurementData) {
        this.realmSet$averageLastDay(lastBpMeasurementData);
    }

    public void setAverageLastMonth(LastBpMeasurementData lastBpMeasurementData) {
        this.realmSet$averageLastMonth(lastBpMeasurementData);
    }

    public void setAverageLastWeek(LastBpMeasurementData lastBpMeasurementData) {
        this.realmSet$averageLastWeek(lastBpMeasurementData);
    }

    public void setDia(Integer n) {
        this.realmSet$dia(n);
    }

    public void setPulse(Integer n) {
        this.realmSet$pulse(n);
    }

    public void setSys(Integer n) {
        this.realmSet$sys(n);
    }

    public void setTime(long l) {
        this.realmSet$time(l);
    }

    public void setUserId(long l) {
        this.realmSet$userId(l);
    }
}

