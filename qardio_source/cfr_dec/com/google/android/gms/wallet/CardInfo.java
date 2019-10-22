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
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.zzc;

public final class CardInfo
extends zzbfm {
    public static final Parcelable.Creator<CardInfo> CREATOR = new zzc();
    private String zzkzh;
    private String zzkzi;
    private String zzkzj;
    private int zzkzk;
    private UserAddress zzkzl;

    private CardInfo() {
    }

    CardInfo(String string2, String string3, String string4, int n, UserAddress userAddress) {
        this.zzkzh = string2;
        this.zzkzi = string3;
        this.zzkzj = string4;
        this.zzkzk = n;
        this.zzkzl = userAddress;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzkzh, false);
        zzbfp.zza(parcel, 2, this.zzkzi, false);
        zzbfp.zza(parcel, 3, this.zzkzj, false);
        zzbfp.zzc(parcel, 4, this.zzkzk);
        zzbfp.zza(parcel, 5, this.zzkzl, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

