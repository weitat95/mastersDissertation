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
import com.google.android.gms.wallet.wobs.zzj;

public final class TextModuleData
extends zzbfm {
    public static final Parcelable.Creator<TextModuleData> CREATOR = new zzj();
    private String body;
    private String zzlgd;

    TextModuleData() {
    }

    public TextModuleData(String string2, String string3) {
        this.zzlgd = string2;
        this.body = string3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzlgd, false);
        zzbfp.zza(parcel, 3, this.body, false);
        zzbfp.zzai(parcel, n);
    }
}

