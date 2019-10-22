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

public class WeightMeasurementsDescription
extends MeasurementDescription
implements Parcelable {
    public static final Parcelable.Creator<WeightMeasurementsDescription> CREATOR = new Parcelable.Creator<WeightMeasurementsDescription>(){

        public WeightMeasurementsDescription createFromParcel(Parcel parcel) {
            return new WeightMeasurementsDescription(parcel);
        }

        public WeightMeasurementsDescription[] newArray(int n) {
            return new WeightMeasurementsDescription[n];
        }
    };
    public String fat;
    public String muscle;
    public String weight;

    public WeightMeasurementsDescription() {
    }

    public WeightMeasurementsDescription(Parcel parcel) {
        this.userName = parcel.readString();
        this.weight = parcel.readString();
        this.fat = parcel.readString();
        this.muscle = parcel.readString();
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
        object = (WeightMeasurementsDescription)object;
        if (this.userName != null) {
            bl3 = bl2;
            if (!this.userName.equals(((WeightMeasurementsDescription)object).userName)) return bl3;
        } else if (((WeightMeasurementsDescription)object).userName != null) {
            return false;
        }
        if (this.date != null) {
            bl3 = bl2;
            if (!this.date.equals(((WeightMeasurementsDescription)object).date)) return bl3;
        } else if (((WeightMeasurementsDescription)object).date != null) {
            return false;
        }
        if (this.weight != null) {
            bl3 = bl2;
            if (!this.weight.equals(((WeightMeasurementsDescription)object).weight)) return bl3;
        } else if (((WeightMeasurementsDescription)object).weight != null) {
            return false;
        }
        if (this.fat != null) {
            bl3 = bl2;
            if (!this.fat.equals(((WeightMeasurementsDescription)object).fat)) return bl3;
        } else if (((WeightMeasurementsDescription)object).fat != null) {
            return false;
        }
        if (this.muscle == null) {
            if (((WeightMeasurementsDescription)object).muscle != null) return false;
            return bl;
        }
        bl3 = bl;
        if (this.muscle.equals(((WeightMeasurementsDescription)object).muscle)) return bl3;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.userName != null ? this.userName.hashCode() : 0;
        int n3 = this.date != null ? this.date.hashCode() : 0;
        int n4 = this.weight != null ? this.weight.hashCode() : 0;
        int n5 = this.fat != null ? this.fat.hashCode() : 0;
        if (this.muscle != null) {
            n = this.muscle.hashCode();
        }
        return (((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.userName);
        parcel.writeString(this.weight);
        parcel.writeString(this.fat);
        parcel.writeString(this.muscle);
        parcel.writeString(this.date);
    }

}

