/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;

public interface IFollowUserRealmProxyInterface {
    public String realmGet$accessStatus();

    public BpLastMeasurement realmGet$bpLastMeasurement();

    public String realmGet$firstName();

    public String realmGet$lastName();

    public boolean realmGet$notificationsEnabled();

    public int realmGet$syncStatus();

    public String realmGet$userEmail();

    public long realmGet$userId();

    public String realmGet$watchingEmail();

    public WeightLastMeasurement realmGet$weightLastMeasurement();

    public void realmSet$accessStatus(String var1);

    public void realmSet$bpLastMeasurement(BpLastMeasurement var1);

    public void realmSet$firstName(String var1);

    public void realmSet$lastName(String var1);

    public void realmSet$notificationsEnabled(boolean var1);

    public void realmSet$syncStatus(int var1);

    public void realmSet$userEmail(String var1);

    public void realmSet$userId(long var1);

    public void realmSet$watchingEmail(String var1);

    public void realmSet$weightLastMeasurement(WeightLastMeasurement var1);
}

