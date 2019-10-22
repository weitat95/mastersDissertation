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
import com.google.android.gms.wallet.zzh;

@Deprecated
public class CountrySpecification
extends zzbfm {
    public static final Parcelable.Creator<CountrySpecification> CREATOR = new zzh();
    private String zzctp;

    public CountrySpecification(String string2) {
        this.zzctp = string2;
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzctp, false);
        zzbfp.zzai(parcel, n);
    }
}

