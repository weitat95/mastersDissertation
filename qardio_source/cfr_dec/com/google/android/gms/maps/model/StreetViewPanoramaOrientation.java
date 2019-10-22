/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.model.zzp;
import java.util.Arrays;

public class StreetViewPanoramaOrientation
extends zzbfm {
    public static final Parcelable.Creator<StreetViewPanoramaOrientation> CREATOR = new zzp();
    public final float bearing;
    public final float tilt;

    /*
     * Enabled aggressive block sorting
     */
    public StreetViewPanoramaOrientation(float f, float f2) {
        boolean bl = -90.0f <= f && f <= 90.0f;
        zzbq.checkArgument(bl, new StringBuilder(62).append("Tilt needs to be between -90 and 90 inclusive: ").append(f).toString());
        this.tilt = 0.0f + f;
        f = f2;
        if ((double)f2 <= 0.0) {
            f = f2 % 360.0f + 360.0f;
        }
        this.bearing = f % 360.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof StreetViewPanoramaOrientation)) {
                    return false;
                }
                object = (StreetViewPanoramaOrientation)object;
                if (Float.floatToIntBits(this.tilt) != Float.floatToIntBits(((StreetViewPanoramaOrientation)object).tilt) || Float.floatToIntBits(this.bearing) != Float.floatToIntBits(((StreetViewPanoramaOrientation)object).bearing)) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.tilt), Float.valueOf(this.bearing)});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("tilt", Float.valueOf(this.tilt)).zzg("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.tilt);
        zzbfp.zza(parcel, 3, this.bearing);
        zzbfp.zzai(parcel, n);
    }

    public static final class Builder {
        public float bearing;
        public float tilt;

        public final Builder bearing(float f) {
            this.bearing = f;
            return this;
        }

        public final StreetViewPanoramaOrientation build() {
            return new StreetViewPanoramaOrientation(this.tilt, this.bearing);
        }

        public final Builder tilt(float f) {
            this.tilt = f;
            return this;
        }
    }

}

