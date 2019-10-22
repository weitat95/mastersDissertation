/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzap;
import com.google.android.gms.common.internal.zzbu;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;

public final class zzbt
extends zzbfm {
    public static final Parcelable.Creator<zzbt> CREATOR = new zzbu();
    private int zzeck;
    private ConnectionResult zzfoo;
    private boolean zzfri;
    private IBinder zzgbn;
    private boolean zzgbo;

    zzbt(int n, IBinder iBinder, ConnectionResult connectionResult, boolean bl, boolean bl2) {
        this.zzeck = n;
        this.zzgbn = iBinder;
        this.zzfoo = connectionResult;
        this.zzfri = bl;
        this.zzgbo = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof zzbt)) {
                    return false;
                }
                object = (zzbt)object;
                if (!this.zzfoo.equals(((zzbt)object).zzfoo) || !this.zzalp().equals(((zzbt)object).zzalp())) break block5;
            }
            return true;
        }
        return false;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, this.zzgbn, false);
        zzbfp.zza(parcel, 3, this.zzfoo, n, false);
        zzbfp.zza(parcel, 4, this.zzfri);
        zzbfp.zza(parcel, 5, this.zzgbo);
        zzbfp.zzai(parcel, n2);
    }

    public final ConnectionResult zzahf() {
        return this.zzfoo;
    }

    public final zzan zzalp() {
        IBinder iBinder = this.zzgbn;
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
        if (iInterface instanceof zzan) {
            return (zzan)iInterface;
        }
        return new zzap(iBinder);
    }

    public final boolean zzalq() {
        return this.zzfri;
    }

    public final boolean zzalr() {
        return this.zzgbo;
    }
}

