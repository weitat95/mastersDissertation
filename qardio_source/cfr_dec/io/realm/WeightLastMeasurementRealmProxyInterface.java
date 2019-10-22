/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;

public interface WeightLastMeasurementRealmProxyInterface {
    public WeightExtraInfo realmGet$extraInfo();

    public Integer realmGet$impedance();

    public Long realmGet$time();

    public long realmGet$userId();

    public Float realmGet$weight();

    public void realmSet$extraInfo(WeightExtraInfo var1);

    public void realmSet$impedance(Integer var1);

    public void realmSet$time(Long var1);

    public void realmSet$userId(long var1);

    public void realmSet$weight(Float var1);
}

