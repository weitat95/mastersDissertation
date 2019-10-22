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
import com.getqardio.shared.wearable.datamodel.MeasurementDescription;

public class BPMeasurementsDescription
extends MeasurementDescription
implements Parcelable {
    public static final Parcelable.Creator<BPMeasurementsDescription> CREATOR = new Parcelable.Creator<BPMeasurementsDescription>(){

        public BPMeasurementsDescription createFromParcel(Parcel parcel) {
            return new BPMeasurementsDescription(parcel);
        }

        public BPMeasurementsDescription[] newArray(int n) {
            return new BPMeasurementsDescription[n];
        }
    };
    public String dia;
    public String pulse;
    public String sys;

    public BPMeasurementsDescription() {
    }

    public BPMeasurementsDescription(Parcel parcel) {
        this.userName = parcel.readString();
        this.sys = parcel.readString();
        this.dia = parcel.readString();
        this.pulse = parcel.readString();
        this.date = parcel.readString();
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = true;
        boolean bl2 = false;
        if (this == object) {
            return true;
        }
        boolean bl3 = bl2;
        if (object == null) return bl3;
        bl3 = bl2;
        if (this.getClass() != object.getClass()) return bl3;
        object = (BPMeasurementsDescription)object;
        if (this.userName != null) {
            bl3 = bl2;
            if (!this.userName.equals(((BPMeasurementsDescription)object).userName)) return bl3;
        } else if (((BPMeasurementsDescription)object).userName != null) {
            return false;
        }
        if (this.date != null) {
            bl3 = bl2;
            if (!this.date.equals(((BPMeasurementsDescription)object).date)) return bl3;
        } else if (((BPMeasurementsDescription)object).date != null) {
            return false;
        }
        if (this.sys != null) {
            bl3 = bl2;
            if (!this.sys.equals(((BPMeasurementsDescription)object).sys)) return bl3;
        } else if (((BPMeasurementsDescription)object).sys != null) {
            return false;
        }
        if (this.dia != null) {
            bl3 = bl2;
            if (!this.dia.equals(((BPMeasurementsDescription)object).dia)) return bl3;
        } else if (((BPMeasurementsDescription)object).dia != null) {
            return false;
        }
        if (this.pulse == null) {
            if (((BPMeasurementsDescription)object).pulse != null) return false;
            return bl;
        }
        bl3 = bl;
        if (this.pulse.equals(((BPMeasurementsDescription)object).pulse)) return bl3;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.userName != null ? this.userName.hashCode() : 0;
        int n3 = this.date != null ? this.date.hashCode() : 0;
        int n4 = this.sys != null ? this.sys.hashCode() : 0;
        int n5 = this.dia != null ? this.dia.hashCode() : 0;
        if (this.pulse != null) {
            n = this.pulse.hashCode();
        }
        return (((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.userName);
        parcel.writeString(this.sys);
        parcel.writeString(this.dia);
        parcel.writeString(this.pulse);
        parcel.writeString(this.date);
    }

}

