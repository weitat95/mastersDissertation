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
import com.google.android.gms.wallet.zzak;

@Deprecated
public final class ProxyCard
extends zzbfm {
    public static final Parcelable.Creator<ProxyCard> CREATOR = new zzak();
    private String zzldn;
    private String zzldo;
    private int zzldp;
    private int zzldq;

    public ProxyCard(String string2, String string3, int n, int n2) {
        this.zzldn = string2;
        this.zzldo = string3;
        this.zzldp = n;
        this.zzldq = n2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, this.zzldn, false);
        zzbfp.zza(parcel, 3, this.zzldo, false);
        zzbfp.zzc(parcel, 4, this.zzldp);
        zzbfp.zzc(parcel, 5, this.zzldq);
        zzbfp.zzai(parcel, n);
    }
}

