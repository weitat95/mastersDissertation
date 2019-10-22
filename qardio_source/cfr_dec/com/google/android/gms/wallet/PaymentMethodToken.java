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
import com.google.android.gms.wallet.zzaf;

public final class PaymentMethodToken
extends zzbfm {
    public static final Parcelable.Creator<PaymentMethodToken> CREATOR = new zzaf();
    private int zzldi;
    private String zzldj;

    private PaymentMethodToken() {
    }

    PaymentMethodToken(int n, String string2) {
        this.zzldi = n;
        this.zzldj = string2;
    }

    public final String getToken() {
        return this.zzldj;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 2, this.zzldi);
        zzbfp.zza(parcel, 3, this.zzldj, false);
        zzbfp.zzai(parcel, n);
    }
}

