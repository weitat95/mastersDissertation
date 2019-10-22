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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.zzm;
import java.util.Arrays;

public class StreetViewPanoramaCamera
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<StreetViewPanoramaCamera> CREATOR = new zzm();
    public final float bearing;
    public final float tilt;
    public final float zoom;
    private final StreetViewPanoramaOrientation zzivx;

    /*
     * Enabled aggressive block sorting
     */
    public StreetViewPanoramaCamera(float f, float f2, float f3) {
        boolean bl = -90.0f <= f2 && f2 <= 90.0f;
        zzbq.checkArgument(bl, new StringBuilder(62).append("Tilt needs to be between -90 and 90 inclusive: ").append(f2).toString());
        float f4 = f;
        if ((double)f <= 0.0) {
            f4 = 0.0f;
        }
        this.zoom = f4;
        this.tilt = f2 + 0.0f;
        f = (double)f3 <= 0.0 ? f3 % 360.0f + 360.0f : f3;
        this.bearing = f % 360.0f;
        this.zzivx = new StreetViewPanoramaOrientation.Builder().tilt(f2).bearing(f3).build();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof StreetViewPanoramaCamera)) {
                    return false;
                }
                object = (StreetViewPanoramaCamera)object;
                if (Float.floatToIntBits(this.zoom) != Float.floatToIntBits(((StreetViewPanoramaCamera)object).zoom) || Float.floatToIntBits(this.tilt) != Float.floatToIntBits(((StreetViewPanoramaCamera)object).tilt) || Float.floatToIntBits(this.bearing) != Float.floatToIntBits(((StreetViewPanoramaCamera)object).bearing)) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing)});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("zoom", Float.valueOf(this.zoom)).zzg("tilt", Float.valueOf(this.tilt)).zzg("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zoom);
        zzbfp.zza(parcel, 3, this.tilt);
        zzbfp.zza(parcel, 4, this.bearing);
        zzbfp.zzai(parcel, n);
    }
}

