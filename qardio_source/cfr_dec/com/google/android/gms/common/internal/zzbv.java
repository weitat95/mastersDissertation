/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbw;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzbv
extends zzbfm {
    public static final Parcelable.Creator<zzbv> CREATOR = new zzbw();
    private int zzeck;
    private final int zzgbp;
    private final int zzgbq;
    @Deprecated
    private final Scope[] zzgbr;

    zzbv(int n, int n2, int n3, Scope[] arrscope) {
        this.zzeck = n;
        this.zzgbp = n2;
        this.zzgbq = n3;
        this.zzgbr = arrscope;
    }

    public zzbv(int n, int n2, Scope[] arrscope) {
        this(1, n, n2, null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zzc(parcel, 2, this.zzgbp);
        zzbfp.zzc(parcel, 3, this.zzgbq);
        zzbfp.zza((Parcel)parcel, (int)4, (Parcelable[])this.zzgbr, (int)n, (boolean)false);
        zzbfp.zzai(parcel, n2);
    }
}

