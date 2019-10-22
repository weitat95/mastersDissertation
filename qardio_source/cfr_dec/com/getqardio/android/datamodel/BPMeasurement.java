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

public class BPMeasurement
extends BaseEntity
implements Parcelable {
    public static final Parcelable.Creator<BPMeasurement> CREATOR = new Parcelable.Creator<BPMeasurement>(){

        public BPMeasurement createFromParcel(Parcel parcel) {
            return new BPMeasurement(parcel);
        }

        public BPMeasurement[] newArray(int n) {
            return new BPMeasurement[n];
        }
    };
    public String deviceId;
    public Integer dia;
    public Boolean irregularHeartBeat;
    public Double latitude;
    public Double longitude;
    public Date measureDate;
    public String note;
    public Integer pulse;
    public int revision = 0;
    public Integer source;
    public Integer syncStatus;
    public Integer sys;
    public Integer tag;
    public String timezone;
    public Long userId;

    public BPMeasurement() {
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public BPMeasurement(Parcel parcel) {
        void var2_4;
        this.userId = ParcelHelper.readLong(parcel, null);
        this.syncStatus = ParcelHelper.readInt(parcel, null);
        this.revision = parcel.readInt();
        this.pulse = ParcelHelper.readInt(parcel, null);
        this.dia = ParcelHelper.readInt(parcel, null);
        this.sys = ParcelHelper.readInt(parcel, null);
        this.irregularHeartBeat = ParcelHelper.readBool(parcel, null);
        Long l = ParcelHelper.readLong(parcel, null);
        if (l != null) {
            Date date = new Date(l);
        } else {
            Object var2_5 = null;
        }
        this.measureDate = var2_4;
        this.timezone = parcel.readString();
        this.note = parcel.readString();
        this.deviceId = parcel.readString();
        this.latitude = ParcelHelper.readDouble(parcel, null);
        this.longitude = ParcelHelper.readDouble(parcel, null);
        this.tag = ParcelHelper.readInt(parcel, null);
        this.source = ParcelHelper.readInt(parcel, null);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isBloodPressureEqual(BPMeasurement bPMeasurement) {
        boolean bl = false;
        if (this == bPMeasurement) {
            return true;
        }
        boolean bl2 = bl;
        if (bPMeasurement == null) return bl2;
        bl2 = bl;
        if (this.getClass() != bPMeasurement.getClass()) return bl2;
        if (this.pulse != null) {
            bl2 = bl;
            if (!this.pulse.equals(bPMeasurement.pulse)) return bl2;
        } else if (bPMeasurement.pulse != null) {
            return false;
        }
        bl2 = bl;
        if (!this.dia.equals(bPMeasurement.dia)) return bl2;
        return this.sys.equals(bPMeasurement.sys);
    }

    public String toString() {
        return "BPMeasurement{userId=" + this.userId + ", syncStatus=" + this.syncStatus + ", revision=" + this.revision + ", pulse=" + this.pulse + ", dia=" + this.dia + ", sys=" + this.sys + ", irregularHeartBeat=" + this.irregularHeartBeat + ", measureDate=" + this.measureDate + ", timezone='" + this.timezone + '\'' + ", note='" + this.note + '\'' + ", deviceId='" + this.deviceId + '\'' + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", tag=" + this.tag + ", source=" + this.source + '}';
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        ParcelHelper.writeLong(parcel, this.userId);
        ParcelHelper.writeInt(parcel, this.syncStatus);
        parcel.writeInt(this.revision);
        ParcelHelper.writeInt(parcel, this.pulse);
        ParcelHelper.writeInt(parcel, this.dia);
        ParcelHelper.writeInt(parcel, this.sys);
        ParcelHelper.writeBool(parcel, this.irregularHeartBeat);
        Long l = this.measureDate != null ? Long.valueOf(this.measureDate.getTime()) : null;
        ParcelHelper.writeLong(parcel, l);
        parcel.writeString(this.timezone);
        parcel.writeString(this.note);
        parcel.writeString(this.deviceId);
        ParcelHelper.writeDouble(parcel, this.latitude);
        ParcelHelper.writeDouble(parcel, this.longitude);
        ParcelHelper.writeInt(parcel, this.tag);
        ParcelHelper.writeInt(parcel, this.source);
    }

}

