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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.wallet.InstrumentInfo;
import com.google.android.gms.wallet.LoyaltyWalletObject;
import com.google.android.gms.wallet.OfferWalletObject;
import com.google.android.gms.wallet.zza;
import com.google.android.gms.wallet.zzx;

public final class MaskedWallet
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<MaskedWallet> CREATOR = new zzx();
    String zzlaa;
    String zzlab;
    String zzlad;
    private zza zzlae;
    private zza zzlaf;
    String[] zzlag;
    UserAddress zzlah;
    UserAddress zzlai;
    InstrumentInfo[] zzlaj;
    private LoyaltyWalletObject[] zzlcc;
    private OfferWalletObject[] zzlcd;

    private MaskedWallet() {
    }

    MaskedWallet(String string2, String string3, String[] arrstring, String string4, zza zza2, zza zza3, LoyaltyWalletObject[] arrloyaltyWalletObject, OfferWalletObject[] arrofferWalletObject, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] arrinstrumentInfo) {
        this.zzlaa = string2;
        this.zzlab = string3;
        this.zzlag = arrstring;
        this.zzlad = string4;
        this.zzlae = zza2;
        this.zzlaf = zza3;
        this.zzlcc = arrloyaltyWalletObject;
        this.zzlcd = arrofferWalletObject;
        this.zzlah = userAddress;
        this.zzlai = userAddress2;
        this.zzlaj = arrinstrumentInfo;
    }

    public final UserAddress getBuyerShippingAddress() {
        return this.zzlai;
    }

    public final String getGoogleTransactionId() {
        return this.zzlaa;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlaa, false);
        zzbfp.zza(parcel, 3, this.zzlab, false);
        zzbfp.zza(parcel, 4, this.zzlag, false);
        zzbfp.zza(parcel, 5, this.zzlad, false);
        zzbfp.zza(parcel, 6, this.zzlae, n, false);
        zzbfp.zza(parcel, 7, this.zzlaf, n, false);
        zzbfp.zza((Parcel)parcel, (int)8, (Parcelable[])this.zzlcc, (int)n, (boolean)false);
        zzbfp.zza((Parcel)parcel, (int)9, (Parcelable[])this.zzlcd, (int)n, (boolean)false);
        zzbfp.zza(parcel, 10, this.zzlah, n, false);
        zzbfp.zza(parcel, 11, this.zzlai, n, false);
        zzbfp.zza((Parcel)parcel, (int)12, (Parcelable[])this.zzlaj, (int)n, (boolean)false);
        zzbfp.zzai(parcel, n2);
    }
}

