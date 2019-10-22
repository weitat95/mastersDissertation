/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableVolumeInfo
implements Parcelable {
    public static final Parcelable.Creator<ParcelableVolumeInfo> CREATOR = new Parcelable.Creator<ParcelableVolumeInfo>(){

        public ParcelableVolumeInfo createFromParcel(Parcel parcel) {
            return new ParcelableVolumeInfo(parcel);
        }

        public ParcelableVolumeInfo[] newArray(int n) {
            return new ParcelableVolumeInfo[n];
        }
    };
    public int audioStream;
    public int controlType;
    public int currentVolume;
    public int maxVolume;
    public int volumeType;

    public ParcelableVolumeInfo(Parcel parcel) {
        this.volumeType = parcel.readInt();
        this.controlType = parcel.readInt();
        this.maxVolume = parcel.readInt();
        this.currentVolume = parcel.readInt();
        this.audioStream = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.volumeType);
        parcel.writeInt(this.controlType);
        parcel.writeInt(this.maxVolume);
        parcel.writeInt(this.currentVolume);
        parcel.writeInt(this.audioStream);
    }

}

