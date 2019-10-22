/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.wobs.zzl;

public final class UriData
extends zzbfm {
    public static final Parcelable.Creator<UriData> CREATOR = new zzl();
    private String description;
    private String zzdzv;

    UriData() {
    }

    public UriData(String string2, String string3) {
        this.zzdzv = string2;
        this.description = string3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzdzv, false);
        zzbfp.zza(parcel, 3, this.description, false);
        zzbfp.zzai(parcel, n);
    }
}

