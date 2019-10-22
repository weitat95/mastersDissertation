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
import com.google.android.gms.wallet.wobs.CommonWalletObject;
import com.google.android.gms.wallet.zzab;

public final class OfferWalletObject
extends zzbfm {
    public static final Parcelable.Creator<OfferWalletObject> CREATOR = new zzab();
    private final int zzeck;
    CommonWalletObject zzlan;
    String zzlct;
    String zzwc;

    OfferWalletObject() {
        this.zzeck = 3;
    }

    OfferWalletObject(int n, String string2, String string3, CommonWalletObject commonWalletObject) {
        this.zzeck = n;
        this.zzlct = string3;
        if (n < 3) {
            this.zzlan = CommonWalletObject.zzbkb().zznm(string2).zzbkc();
            return;
        }
        this.zzlan = commonWalletObject;
    }

    public final int getVersionCode() {
        return this.zzeck;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.getVersionCode());
        zzbfp.zza(parcel, 2, this.zzwc, false);
        zzbfp.zza(parcel, 3, this.zzlct, false);
        zzbfp.zza(parcel, 4, this.zzlan, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

