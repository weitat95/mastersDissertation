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
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.zzn;

public final class WalletObjectMessage
extends zzbfm {
    public static final Parcelable.Creator<WalletObjectMessage> CREATOR = new zzn();
    String body;
    String zzlgd;
    TimeInterval zzlgg;
    UriData zzlgh;
    UriData zzlgi;

    WalletObjectMessage() {
    }

    WalletObjectMessage(String string2, String string3, TimeInterval timeInterval, UriData uriData, UriData uriData2) {
        this.zzlgd = string2;
        this.body = string3;
        this.zzlgg = timeInterval;
        this.zzlgh = uriData;
        this.zzlgi = uriData2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlgd, false);
        zzbfp.zza(parcel, 3, this.body, false);
        zzbfp.zza(parcel, 4, this.zzlgg, n, false);
        zzbfp.zza(parcel, 5, this.zzlgh, n, false);
        zzbfp.zza(parcel, 6, this.zzlgi, n, false);
        zzbfp.zzai(parcel, n2);
    }
}

