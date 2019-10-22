/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzae;
import com.google.android.gms.location.zzv;
import java.util.Arrays;

public final class LocationAvailability
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationAvailability> CREATOR = new zzv();
    @Deprecated
    private int zzijl;
    @Deprecated
    private int zzijm;
    private long zzijn;
    private int zzijo;
    private zzae[] zzijp;

    LocationAvailability(int n, int n2, int n3, long l, zzae[] arrzzae) {
        this.zzijo = n;
        this.zzijl = n2;
        this.zzijm = n3;
        this.zzijn = l;
        this.zzijp = arrzzae;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (LocationAvailability)object;
                if (this.zzijl != ((LocationAvailability)object).zzijl || this.zzijm != ((LocationAvailability)object).zzijm || this.zzijn != ((LocationAvailability)object).zzijn || this.zzijo != ((LocationAvailability)object).zzijo || !Arrays.equals(this.zzijp, ((LocationAvailability)object).zzijp)) break block5;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzijo, this.zzijl, this.zzijm, this.zzijn, this.zzijp});
    }

    public final boolean isLocationAvailable() {
        return this.zzijo < 1000;
    }

    public final String toString() {
        boolean bl = this.isLocationAvailable();
        return new StringBuilder(48).append("LocationAvailability[isLocationAvailable: ").append(bl).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzijl);
        zzbfp.zzc(parcel, 2, this.zzijm);
        zzbfp.zza(parcel, 3, this.zzijn);
        zzbfp.zzc(parcel, 4, this.zzijo);
        zzbfp.zza((Parcel)parcel, (int)5, (Parcelable[])this.zzijp, (int)n, (boolean)false);
        zzbfp.zzai(parcel, n2);
    }
}

