/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.local;

import io.realm.RealmObject;
import io.realm.WeightExtraInfoRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

public class WeightExtraInfo
extends RealmObject
implements WeightExtraInfoRealmProxyInterface {
    private Integer age;
    private Integer battery;
    private Float bmi;
    private Integer bone;
    private Integer fat;
    private Integer height;
    private Integer impedance;
    private String measurementId;
    private Integer muscle;
    private String sex;
    private String source;
    private String user;
    private Long userId;
    private Integer water;

    public WeightExtraInfo() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
    }

    public Float getBmi() {
        return this.realmGet$bmi();
    }

    public Integer getHeight() {
        return this.realmGet$height();
    }

    @Override
    public Integer realmGet$age() {
        return this.age;
    }

    @Override
    public Integer realmGet$battery() {
        return this.battery;
    }

    @Override
    public Float realmGet$bmi() {
        return this.bmi;
    }

    @Override
    public Integer realmGet$bone() {
        return this.bone;
    }

    @Override
    public Integer realmGet$fat() {
        return this.fat;
    }

    @Override
    public Integer realmGet$height() {
        return this.height;
    }

    @Override
    public Integer realmGet$impedance() {
        return this.impedance;
    }

    @Override
    public String realmGet$measurementId() {
        return this.measurementId;
    }

    @Override
    public Integer realmGet$muscle() {
        return this.muscle;
    }

    @Override
    public String realmGet$sex() {
        return this.sex;
    }

    @Override
    public String realmGet$source() {
        return this.source;
    }

    @Override
    public String realmGet$user() {
        return this.user;
    }

    @Override
    public Long realmGet$userId() {
        return this.userId;
    }

    @Override
    public Integer realmGet$water() {
        return this.water;
    }

    @Override
    public void realmSet$age(Integer n) {
        this.age = n;
    }

    @Override
    public void realmSet$battery(Integer n) {
        this.battery = n;
    }

    @Override
    public void realmSet$bmi(Float f) {
        this.bmi = f;
    }

    @Override
    public void realmSet$bone(Integer n) {
        this.bone = n;
    }

    @Override
    public void realmSet$fat(Integer n) {
        this.fat = n;
    }

    @Override
    public void realmSet$height(Integer n) {
        this.height = n;
    }

    @Override
    public void realmSet$impedance(Integer n) {
        this.impedance = n;
    }

    @Override
    public void realmSet$measurementId(String string2) {
        this.measurementId = string2;
    }

    @Override
    public void realmSet$muscle(Integer n) {
        this.muscle = n;
    }

    @Override
    public void realmSet$sex(String string2) {
        this.sex = string2;
    }

    @Override
    public void realmSet$source(String string2) {
        this.source = string2;
    }

    @Override
    public void realmSet$user(String string2) {
        this.user = string2;
    }

    @Override
    public void realmSet$userId(Long l) {
        this.userId = l;
    }

    @Override
    public void realmSet$water(Integer n) {
        this.water = n;
    }

    public void setAge(Integer n) {
        this.realmSet$age(n);
    }

    public void setBattery(Integer n) {
        this.realmSet$battery(n);
    }

    public void setBmi(Float f) {
        this.realmSet$bmi(f);
    }

    public void setBone(Integer n) {
        this.realmSet$bone(n);
    }

    public void setFat(Integer n) {
        this.realmSet$fat(n);
    }

    public void setHeight(Integer n) {
        this.realmSet$height(n);
    }

    public void setImpedance(Integer n) {
        this.realmSet$impedance(n);
    }

    public void setMeasurementId(String string2) {
        this.realmSet$measurementId(string2);
    }

    public void setMuscle(Integer n) {
        this.realmSet$muscle(n);
    }

    public void setSex(String string2) {
        this.realmSet$sex(string2);
    }

    public void setSource(String string2) {
        this.realmSet$source(string2);
    }

    public void setUser(String string2) {
        this.realmSet$user(string2);
    }

    public void setUserId(Long l) {
        this.realmSet$userId(l);
    }

    public void setWater(Integer n) {
        this.realmSet$water(n);
    }

    public String toString() {
        return "WeightExtraInfo{measurementId='" + this.realmGet$measurementId() + '\'' + ", height=" + this.realmGet$height() + ", battery=" + this.realmGet$battery() + ", sex='" + this.realmGet$sex() + '\'' + ", age=" + this.realmGet$age() + ", userId=" + this.realmGet$userId() + ", user='" + this.realmGet$user() + '\'' + ", fat=" + this.realmGet$fat() + ", bone=" + this.realmGet$bone() + ", water=" + this.realmGet$water() + ", muscle=" + this.realmGet$muscle() + ", source='" + this.realmGet$source() + '\'' + ", impedance=" + this.realmGet$impedance() + ", bmi=" + this.realmGet$bmi() + '}';
    }
}

