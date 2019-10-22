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
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.ProxyCard;
import com.google.android.gms.wallet.zza;
import com.google.android.gms.wallet.zzk;

public final class FullWallet
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<FullWallet> CREATOR = new zzk();
    private String zzlaa;
    private String zzlab;
    private ProxyCard zzlac;
    private String zzlad;
    private zza zzlae;
    private zza zzlaf;
    private String[] zzlag;
    private UserAddress zzlah;
    private UserAddress zzlai;
    private InstrumentInfo[] zzlaj;
    private PaymentMethodToken zzlak;

    private FullWallet() {
    }

    FullWallet(String string2, String string3, ProxyCard proxyCard, String string4, zza zza2, zza zza3, String[] arrstring, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] arrinstrumentInfo, PaymentMethodToken paymentMethodToken) {
        this.zzlaa = string2;
        this.zzlab = string3;
        this.zzlac = proxyCard;
        this.zzlad = string4;
        this.zzlae = zza2;
        this.zzlaf = zza3;
        this.zzlag = arrstring;
        this.zzlah = userAddress;
        this.zzlai = userAddress2;
        this.zzlaj = arrinstrumentInfo;
        this.zzlak = paymentMethodToken;
    }

    public final UserAddress getBuyerBillingAddress() {
        return this.zzlah;
    }

    public final String getEmail() {
        return this.zzlad;
    }

    public final PaymentMethodToken getPaymentMethodToken() {
        return this.zzlak;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlaa, false);
        zzbfp.zza(parcel, 3, this.zzlab, false);
        zzbfp.zza(parcel, 4, this.zzlac, n, false);
        zzbfp.zza(parcel, 5, this.zzlad, false);
        zzbfp.zza(parcel, 6, this.zzlae, n, false);
        zzbfp.zza(parcel, 7, this.zzlaf, n, false);
        zzbfp.zza(parcel, 8, this.zzlag, false);
        zzbfp.zza(parcel, 9, this.zzlah, n, false);
        zzbfp.zza(parcel, 10, this.zzlai, n, false);
        zzbfp.zza((Parcel)parcel, (int)11, (Parcelable[])this.zzlaj, (int)n, (boolean)false);
        zzbfp.zza(parcel, 12, this.zzlak, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

