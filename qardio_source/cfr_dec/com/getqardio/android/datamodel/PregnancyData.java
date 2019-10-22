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
import java.util.Date;

public class PregnancyData
implements Parcelable {
    public static final Parcelable.Creator<PregnancyData> CREATOR = new Parcelable.Creator<PregnancyData>(){

        public PregnancyData createFromParcel(Parcel parcel) {
            return new PregnancyData(parcel);
        }

        public PregnancyData[] newArray(int n) {
            return new PregnancyData[n];
        }
    };
    public Long cloudId;
    public Date dueDate;
    public Date endDate;
    public Long id;
    public Boolean isShowWeight;
    public float prePregnancyWeight;
    public Date startDate;
    public Integer syncStatus;

    public PregnancyData() {
        this.isShowWeight = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected PregnancyData(Parcel parcel) {
        Object var6_2 = null;
        this.isShowWeight = false;
        this.id = (Long)parcel.readValue(Long.class.getClassLoader());
        this.cloudId = (Long)parcel.readValue(Long.class.getClassLoader());
        long l = parcel.readLong();
        Date date = l == -1L ? null : new Date(l);
        this.startDate = date;
        l = parcel.readLong();
        date = l == -1L ? null : new Date(l);
        this.dueDate = date;
        l = parcel.readLong();
        date = l == -1L ? var6_2 : new Date(l);
        this.endDate = date;
        this.prePregnancyWeight = parcel.readFloat();
        this.syncStatus = (Integer)parcel.readValue(Integer.class.getClassLoader());
        boolean bl = parcel.readByte() != 0;
        this.isShowWeight = bl;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        long l = -1L;
        parcel.writeValue((Object)this.id);
        parcel.writeValue((Object)this.cloudId);
        long l2 = this.startDate != null ? this.startDate.getTime() : -1L;
        parcel.writeLong(l2);
        l2 = this.dueDate != null ? this.dueDate.getTime() : -1L;
        parcel.writeLong(l2);
        l2 = l;
        if (this.endDate != null) {
            l2 = this.endDate.getTime();
        }
        parcel.writeLong(l2);
        parcel.writeFloat(this.prePregnancyWeight);
        parcel.writeValue((Object)this.syncStatus);
        byte by = this.isShowWeight != false ? (byte)1 : 0;
        parcel.writeByte(by);
    }

}

