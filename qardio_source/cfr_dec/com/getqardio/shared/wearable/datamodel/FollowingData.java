/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.shared.wearable.datamodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.shared.wearable.datamodel.BPMeasurementsDescription;

public class FollowingData
implements Parcelable {
    public static final Parcelable.Creator<FollowingData> CREATOR = new Parcelable.Creator<FollowingData>(){

        public FollowingData createFromParcel(Parcel parcel) {
            return new FollowingData(parcel);
        }

        public FollowingData[] newArray(int n) {
            return new FollowingData[n];
        }
    };
    public String firstName;
    public BPMeasurementsDescription lastBPMeasurement;
    public String lastName;
    public BPMeasurementsDescription monthlyBPMeasurement;
    public BPMeasurementsDescription weeklyBPMeasurement;

    public FollowingData() {
    }

    public FollowingData(Parcel parcel) {
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.lastBPMeasurement = (BPMeasurementsDescription)parcel.readParcelable(BPMeasurementsDescription.class.getClassLoader());
        this.weeklyBPMeasurement = (BPMeasurementsDescription)parcel.readParcelable(BPMeasurementsDescription.class.getClassLoader());
        this.monthlyBPMeasurement = (BPMeasurementsDescription)parcel.readParcelable(BPMeasurementsDescription.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getFullName() {
        String string2 = this.firstName != null ? this.firstName + " " : "";
        String string3 = string2;
        if (this.lastName == null) return string3;
        return string2 + this.lastName;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeParcelable((Parcelable)this.lastBPMeasurement, 0);
        parcel.writeParcelable((Parcelable)this.weeklyBPMeasurement, 0);
        parcel.writeParcelable((Parcelable)this.monthlyBPMeasurement, 0);
    }

}

