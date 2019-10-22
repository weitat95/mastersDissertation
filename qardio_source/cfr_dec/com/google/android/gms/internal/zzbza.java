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
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbwa;
import com.google.android.gms.internal.zzbxo;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyy;
import com.google.android.gms.internal.zzcac;

final class zzbza
extends zzbwa {
    private /* synthetic */ DataDeleteRequest zzhey;

    zzbza(zzbyy zzbyy2, GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        this.zzhey = dataDeleteRequest;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbvv)zzb2;
        zzcac zzcac2 = new zzcac(this);
        ((zzbxo)((zzd)((Object)zzb2)).zzakn()).zza(new DataDeleteRequest(this.zzhey, zzcac2));
    }
}

