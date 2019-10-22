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
import com.google.android.gms.wallet.wobs.zzc;

public final class LabelValue
extends zzbfm {
    public static final Parcelable.Creator<LabelValue> CREATOR = new zzc();
    private String label;
    private String value;

    LabelValue() {
    }

    public LabelValue(String string2, String string3) {
        this.label = string2;
        this.value = string3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.label, false);
        zzbfp.zza(parcel, 3, this.value, false);
        zzbfp.zzai(parcel, n);
    }
}

