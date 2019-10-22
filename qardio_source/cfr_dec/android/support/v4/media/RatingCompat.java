/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;

public final class RatingCompat
implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>(){

        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        public RatingCompat[] newArray(int n) {
            return new RatingCompat[n];
        }
    };
    private final int mRatingStyle;
    private final float mRatingValue;

    RatingCompat(int n, float f) {
        this.mRatingStyle = n;
        this.mRatingValue = f;
    }

    public int describeContents() {
        return this.mRatingStyle;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder().append("Rating:style=").append(this.mRatingStyle).append(" rating=");
        if (this.mRatingValue < 0.0f) {
            string2 = "unrated";
            do {
                return stringBuilder.append(string2).toString();
                break;
            } while (true);
        }
        string2 = String.valueOf(this.mRatingValue);
        return stringBuilder.append(string2).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

}

