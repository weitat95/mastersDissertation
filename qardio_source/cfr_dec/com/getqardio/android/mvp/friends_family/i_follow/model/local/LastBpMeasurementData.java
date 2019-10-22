/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import com.getqardio.android.mvp.friends_family.i_follow.model.remote.Average;
import io.realm.LastBpMeasurementDataRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class LastBpMeasurementData
extends RealmObject
implements LastBpMeasurementDataRealmProxyInterface {
    private String data1;
    private String data2;
    private String data3;
    private long userId;

    public LastBpMeasurementData() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public LastBpMeasurementData(Average average) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
        this.realmSet$data1(average.getData1());
        this.realmSet$data2(average.getData2());
        this.realmSet$data3(average.getData3());
    }

    public String getData1() {
        return this.realmGet$data1();
    }

    public String getData2() {
        return this.realmGet$data2();
    }

    public String getData3() {
        return this.realmGet$data3();
    }

    @Override
    public String realmGet$data1() {
        return this.data1;
    }

    @Override
    public String realmGet$data2() {
        return this.data2;
    }

    @Override
    public String realmGet$data3() {
        return this.data3;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public void realmSet$data1(String string2) {
        this.data1 = string2;
    }

    @Override
    public void realmSet$data2(String string2) {
        this.data2 = string2;
    }

    @Override
    public void realmSet$data3(String string2) {
        this.data3 = string2;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }
}

