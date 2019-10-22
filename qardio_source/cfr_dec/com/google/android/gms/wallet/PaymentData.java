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
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.zzac;

public final class PaymentData
extends zzbfm {
    public static final Parcelable.Creator<PaymentData> CREATOR = new zzac();
    private String zzegs;
    private CardInfo zzlcv;
    private UserAddress zzlcw;
    private PaymentMethodToken zzlcx;
    private String zzlcy;

    private PaymentData() {
    }

    PaymentData(String string2, CardInfo cardInfo, UserAddress userAddress, PaymentMethodToken paymentMethodToken, String string3) {
        this.zzegs = string2;
        this.zzlcv = cardInfo;
        this.zzlcw = userAddress;
        this.zzlcx = paymentMethodToken;
        this.zzlcy = string3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzegs, false);
        zzbfp.zza(parcel, 2, this.zzlcv, n, false);
        zzbfp.zza(parcel, 3, this.zzlcw, n, false);
        zzbfp.zza(parcel, 4, this.zzlcx, n, false);
        zzbfp.zza(parcel, 5, this.zzlcy, false);
        zzbfp.zzai(parcel, n2);
    }
}

