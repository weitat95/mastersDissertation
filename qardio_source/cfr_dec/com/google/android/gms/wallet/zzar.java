/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.zzas;

public final class zzar
extends zzbfm {
    public static final Parcelable.Creator<zzar> CREATOR = new zzas();
    private String zzlee;

    private zzar() {
    }

    zzar(String string2) {
        this.zzlee = string2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlee, false);
        zzbfp.zzai(parcel, n);
    }
}

