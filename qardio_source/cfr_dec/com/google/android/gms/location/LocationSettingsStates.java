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
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.location.zzad;

public final class LocationSettingsStates
extends zzbfm {
    public static final Parcelable.Creator<LocationSettingsStates> CREATOR = new zzad();
    private final boolean zzikf;
    private final boolean zzikg;
    private final boolean zzikh;
    private final boolean zziki;
    private final boolean zzikj;
    private final boolean zzikk;

    public LocationSettingsStates(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.zzikf = bl;
        this.zzikg = bl2;
        this.zzikh = bl3;
        this.zziki = bl4;
        this.zzikj = bl5;
        this.zzikk = bl6;
    }

    public final boolean isBlePresent() {
        return this.zzikk;
    }

    public final boolean isBleUsable() {
        return this.zzikh;
    }

    public final boolean isGpsPresent() {
        return this.zziki;
    }

    public final boolean isGpsUsable() {
        return this.zzikf;
    }

    public final boolean isNetworkLocationPresent() {
        return this.zzikj;
    }

    public final boolean isNetworkLocationUsable() {
        return this.zzikg;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.isGpsUsable());
        zzbfp.zza(parcel, 2, this.isNetworkLocationUsable());
        zzbfp.zza(parcel, 3, this.isBleUsable());
        zzbfp.zza(parcel, 4, this.isGpsPresent());
        zzbfp.zza(parcel, 5, this.isNetworkLocationPresent());
        zzbfp.zza(parcel, 6, this.isBlePresent());
        zzbfp.zzai(parcel, n);
    }
}

