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
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.maps.model.zzf;

public final class LatLng
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<LatLng> CREATOR = new zzf();
    public final double latitude;
    public final double longitude;

    /*
     * Enabled aggressive block sorting
     */
    public LatLng(double d, double d2) {
        this.longitude = -180.0 <= d2 && d2 < 180.0 ? d2 : ((d2 - 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
        this.latitude = Math.max(-90.0, Math.min(90.0, d));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof LatLng)) {
                    return false;
                }
                object = (LatLng)object;
                if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(((LatLng)object).latitude) || Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(((LatLng)object).longitude)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        long l = Double.doubleToLongBits(this.latitude);
        int n = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.longitude);
        return (n + 31) * 31 + (int)(l ^ l >>> 32);
    }

    public final String toString() {
        double d = this.latitude;
        double d2 = this.longitude;
        return new StringBuilder(60).append("lat/lng: (").append(d).append(",").append(d2).append(")").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.latitude);
        zzbfp.zza(parcel, 3, this.longitude);
        zzbfp.zzai(parcel, n);
    }
}

