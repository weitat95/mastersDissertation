/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.request.zzl;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;

public final class zzk
extends zzbfm {
    public static final Parcelable.Creator<zzk> CREATOR = new zzl();
    private final int zzeck;
    private final DataSet zzhdx;
    private final zzbyf zzhgc;
    private final boolean zzhgj;

    zzk(int n, DataSet dataSet, IBinder iBinder, boolean bl) {
        this.zzeck = n;
        this.zzhdx = dataSet;
        this.zzhgc = zzbyg.zzba(iBinder);
        this.zzhgj = bl;
    }

    public zzk(DataSet dataSet, zzbyf zzbyf2, boolean bl) {
        this.zzeck = 4;
        this.zzhdx = dataSet;
        this.zzhgc = zzbyf2;
        this.zzhgj = bl;
    }

    public final boolean equals(Object object) {
        block3: {
            block2: {
                if (object == this) break block2;
                if (!(object instanceof zzk)) break block3;
                object = (zzk)object;
                if (!zzbg.equal(this.zzhdx, ((zzk)object).zzhdx)) break block3;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhdx});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("dataSet", this.zzhdx).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhdx, n, false);
        IBinder iBinder = this.zzhgc == null ? null : this.zzhgc.asBinder();
        zzbfp.zza(parcel, 2, iBinder, false);
        zzbfp.zza(parcel, 4, this.zzhgj);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, n2);
    }
}

