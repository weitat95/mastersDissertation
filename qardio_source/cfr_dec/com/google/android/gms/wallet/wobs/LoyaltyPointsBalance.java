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
import com.google.android.gms.wallet.wobs.zzh;

public final class LoyaltyPointsBalance
extends zzbfm {
    public static final Parcelable.Creator<LoyaltyPointsBalance> CREATOR = new zzh();
    String zzlar;
    int zzlfx;
    String zzlfy;
    double zzlfz;
    long zzlga;
    int zzlgb;

    LoyaltyPointsBalance() {
        this.zzlgb = -1;
        this.zzlfx = -1;
        this.zzlfz = -1.0;
    }

    LoyaltyPointsBalance(int n, String string2, double d, String string3, long l, int n2) {
        this.zzlfx = n;
        this.zzlfy = string2;
        this.zzlfz = d;
        this.zzlar = string3;
        this.zzlga = l;
        this.zzlgb = n2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.zzlfx);
        zzbfp.zza(parcel, 3, this.zzlfy, false);
        zzbfp.zza(parcel, 4, this.zzlfz);
        zzbfp.zza(parcel, 5, this.zzlar, false);
        zzbfp.zza(parcel, 6, this.zzlga);
        zzbfp.zzc(parcel, 7, this.zzlgb);
        zzbfp.zzai(parcel, n);
    }
}

