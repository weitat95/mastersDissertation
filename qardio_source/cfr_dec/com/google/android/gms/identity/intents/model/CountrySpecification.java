/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.identity.intents.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.identity.intents.model.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public class CountrySpecification
extends zzbfm
implements ReflectedParcelable {
    public static final Parcelable.Creator<CountrySpecification> CREATOR = new zza();
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

