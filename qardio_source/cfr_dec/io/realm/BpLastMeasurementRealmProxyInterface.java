/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;

public interface BpLastMeasurementRealmProxyInterface {
    public LastBpMeasurementData realmGet$averageLastDay();

    public LastBpMeasurementData realmGet$averageLastMonth();

    public LastBpMeasurementData realmGet$averageLastWeek();

    public Integer realmGet$dia();

    public Integer realmGet$pulse();

    public Integer realmGet$sys();

    public long realmGet$time();

    public long realmGet$userId();

    public void realmSet$averageLastDay(LastBpMeasurementData var1);

    public void realmSet$averageLastMonth(LastBpMeasurementData var1);

    public void realmSet$averageLastWeek(LastBpMeasurementData var1);

    public void realmSet$dia(Integer var1);

    public void realmSet$pulse(Integer var1);

    public void realmSet$sys(Integer var1);

    public void realmSet$time(long var1);

    public void realmSet$userId(long var1);
}

