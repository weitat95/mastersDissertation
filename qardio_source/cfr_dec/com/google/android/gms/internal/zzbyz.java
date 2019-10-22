/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.request.zzk;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbwa;
import com.google.android.gms.internal.zzbxo;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.internal.zzcac;

final class zzbyz
extends zzbwa {
    private /* synthetic */ DataSet zzhew;
    private /* synthetic */ boolean zzhex;

    zzbyz(zzbyy zzbyy2, GoogleApiClient googleApiClient, DataSet dataSet, boolean bl) {
        this.zzhew = dataSet;
        this.zzhex = false;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbvv)zzb2;
        zzcac zzcac2 = new zzcac(this);
        ((zzbxo)((zzd)((Object)zzb2)).zzakn()).zza(new zzk(this.zzhew, zzcac2, this.zzhex));
    }
}

