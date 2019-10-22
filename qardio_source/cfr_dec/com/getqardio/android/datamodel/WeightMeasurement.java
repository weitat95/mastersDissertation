/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.android.datamodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.datamodel.BaseEntity;
import com.getqardio.android.utils.ParcelHelper;
import java.util.Date;

public class WeightMeasurement
extends BaseEntity
implements Parcelable {
    public static final Parcelable.Creator<WeightMeasurement> CREATOR = new Parcelable.Creator<WeightMeasurement>(){

        public WeightMeasurement createFromParcel(Parcel parcel) {
            return new WeightMeasurement(parcel);
        }

        public WeightMeasurement[] newArray(int n) {
            return new WeightMeasurement[n];
        }
    };
    public Integer age;
    public Integer battery;
    public Integer bone;
    public String deviceId;
    public String deviceSerialNumber;
    public Integer fat;
    public String firmwareVersion;
    public String fullName;
    public Integer height;
    public Date measureDate;
    public String measurementId;
    public Integer measurementSource;
    public String memberName;
    public Integer muscle;
    public String note;
    public Long qbUserId;
    public String sex;
    public String source;
    public Integer syncStatus;
    public String timezone;
    public String user;
    public Long userId;
    public boolean visitor;
    public Integer water;
    public Float weight;
    public Integer z;

    public WeightMeasurement() {
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public WeightMeasurement(Parcel parcel) {
        void var2_4;
        this.userId = ParcelHelper.readLong(parcel, null);
        this.syncStatus = ParcelHelper.readInt(parcel, null);
        this.weight = ParcelHelper.readFloat(parcel, null);
        this.fullName = parcel.readString();
        this.deviceId = parcel.readString();
        this.height = ParcelHelper.readInt(parcel, null);
        this.sex = parcel.readString();
        this.battery = ParcelHelper.readInt(parcel, null);
        this.age = ParcelHelper.readInt(parcel, null);
        this.fat = ParcelHelper.readInt(parcel, null);
        this.muscle = ParcelHelper.readInt(parcel, null);
        this.water = ParcelHelper.readInt(parcel, null);
        this.bone = ParcelHelper.readInt(parcel, null);
        this.z = ParcelHelper.readInt(parcel, null);
        this.muscle = ParcelHelper.readInt(parcel, null);
        this.water = ParcelHelper.readInt(parcel, null);
        this.bone = ParcelHelper.readInt(parcel, null);
        this.source = parcel.readString();
        this.measurementId = parcel.readString();
        this.qbUserId = ParcelHelper.readLong(parcel, null);
        this.user = parcel.readString();
        Long l = ParcelHelper.readLong(parcel, null);
        if (l != null) {
            Date date = new Date(l);
        } else {
            Object var2_5 = null;
        }
        this.measureDate = var2_4;
        this.timezone = parcel.readString();
        this.note = parcel.readString();
        this.memberName = parcel.readString();
        this.measurementSource = ParcelHelper.readInt(parcel, null);
        this.deviceSerialNumber = parcel.readString();
        this.visitor = ParcelHelper.readBool(parcel, false);
        this.deviceSerialNumber = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isWeightEqual(WeightMeasurement weightMeasurement) {
        boolean bl = false;
        if (this == weightMeasurement) {
            return true;
        }
        boolean bl2 = bl;
        if (weightMeasurement == null) return bl2;
        bl2 = bl;
        if (this.getClass() != weightMeasurement.getClass()) return bl2;
        bl2 = bl;
        if (!this.weight.equals(weightMeasurement.weight)) return bl2;
        return this.measureDate.equals(weightMeasurement.measureDate);
    }

    public String toString() {
        return "WeightMeasurement{userId=" + this.userId + ", syncStatus=" + this.syncStatus + ", weight=" + this.weight + ", fullName='" + this.fullName + '\'' + ", deviceId='" + this.deviceId + '\'' + ", height=" + this.height + ", sex='" + this.sex + '\'' + ", battery=" + this.battery + ", age=" + this.age + ", fat=" + this.fat + ", muscle=" + this.muscle + ", water=" + this.water + ", bone=" + this.bone + ", z=" + this.z + ", source='" + this.source + '\'' + ", measurementId='" + this.measurementId + '\'' + ", qbUserId=" + this.qbUserId + ", user='" + this.user + '\'' + ", firmwareVersion='" + this.firmwareVersion + '\'' + ", measureDate=" + this.measureDate + ", timezone='" + this.timezone + '\'' + ", note='" + this.note + '\'' + ", memberName='" + this.memberName + '\'' + ", visitor=" + this.visitor + ", measurementSource=" + this.measurementSource + ", deviceSerialNumber='" + this.deviceSerialNumber + '\'' + '}';
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        ParcelHelper.writeLong(parcel, this.userId);
        ParcelHelper.writeInt(parcel, this.syncStatus);
        ParcelHelper.writeFloat(parcel, this.weight);
        parcel.writeString(this.fullName);
        parcel.writeString(this.deviceId);
        ParcelHelper.writeInt(parcel, this.height);
        parcel.writeString(this.sex);
        ParcelHelper.writeInt(parcel, this.battery);
        ParcelHelper.writeInt(parcel, this.age);
        ParcelHelper.writeInt(parcel, this.fat);
        ParcelHelper.writeInt(parcel, this.muscle);
        ParcelHelper.writeInt(parcel, this.water);
        ParcelHelper.writeInt(parcel, this.bone);
        ParcelHelper.writeInt(parcel, this.z);
        parcel.writeString(this.source);
        parcel.writeString(this.measurementId);
        ParcelHelper.writeLong(parcel, this.qbUserId);
        parcel.writeString(this.user);
        Long l = this.measureDate != null ? Long.valueOf(this.measureDate.getTime()) : null;
        ParcelHelper.writeLong(parcel, l);
        parcel.writeString(this.timezone);
        parcel.writeString(this.note);
        parcel.writeString(this.memberName);
        ParcelHelper.writeInt(parcel, this.measurementSource);
        parcel.writeString(this.deviceSerialNumber);
        ParcelHelper.writeBool(parcel, this.visitor);
        parcel.writeString(this.firmwareVersion);
    }

}

