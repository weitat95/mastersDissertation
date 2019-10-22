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

public class Reminder
extends BaseEntity
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public Reminder createFromParcel(Parcel parcel) {
            return new Reminder(parcel);
        }

        public Reminder[] newArray(int n) {
            return new Reminder[n];
        }
    };
    public Integer days;
    public Long remindTime;
    public Long reminderId;
    public Boolean repeat;
    public Integer syncStatus;
    public String type;
    public Long userId;

    public Reminder() {
    }

    public Reminder(Parcel parcel) {
        this._id = ParcelHelper.readLong(parcel, null);
        this.reminderId = ParcelHelper.readLong(parcel, null);
        this.userId = ParcelHelper.readLong(parcel, null);
        this.syncStatus = ParcelHelper.readInt(parcel, null);
        this.days = ParcelHelper.readInt(parcel, null);
        this.remindTime = ParcelHelper.readLong(parcel, null);
        this.repeat = ParcelHelper.readBool(parcel, null);
        this.type = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Reminder)) {
            return false;
        }
        object = (Reminder)object;
        if (this.days != null) {
            if (!this.days.equals(((Reminder)object).days)) {
                return false;
            }
        } else if (((Reminder)object).days != null) return false;
        if (this.remindTime != null) {
            if (!this.remindTime.equals(((Reminder)object).remindTime)) {
                return false;
            }
        } else if (((Reminder)object).remindTime != null) return false;
        if (this.reminderId != null) {
            if (!this.reminderId.equals(((Reminder)object).reminderId)) {
                return false;
            }
        } else if (((Reminder)object).reminderId != null) return false;
        if (this.repeat != null) {
            if (!this.repeat.equals(((Reminder)object).repeat)) {
                return false;
            }
        } else if (((Reminder)object).repeat != null) return false;
        if (this.syncStatus != null) {
            if (!this.syncStatus.equals(((Reminder)object).syncStatus)) {
                return false;
            }
        } else if (((Reminder)object).syncStatus != null) return false;
        if (this.type != null) {
            if (!this.type.equals(((Reminder)object).type)) {
                return false;
            }
        } else if (((Reminder)object).type != null) return false;
        if (this.userId != null) {
            if (this.userId.equals(((Reminder)object).userId)) return true;
            return false;
        }
        if (((Reminder)object).userId == null) return true;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.reminderId != null ? this.reminderId.hashCode() : 0;
        int n3 = this.userId != null ? this.userId.hashCode() : 0;
        int n4 = this.syncStatus != null ? this.syncStatus.hashCode() : 0;
        int n5 = this.days != null ? this.days.hashCode() : 0;
        int n6 = this.remindTime != null ? this.remindTime.hashCode() : 0;
        int n7 = this.repeat != null ? this.repeat.hashCode() : 0;
        if (this.type != null) {
            n = this.type.hashCode();
        }
        return (((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        ParcelHelper.writeLong(parcel, this._id);
        ParcelHelper.writeLong(parcel, this.reminderId);
        ParcelHelper.writeLong(parcel, this.userId);
        ParcelHelper.writeInt(parcel, this.syncStatus);
        ParcelHelper.writeInt(parcel, this.days);
        ParcelHelper.writeLong(parcel, this.remindTime);
        ParcelHelper.writeBool(parcel, this.repeat);
        parcel.writeString(this.type);
    }

}

