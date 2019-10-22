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
import com.google.android.gms.fitness.request.zzaa;
import com.google.android.gms.internal.zzbvk;
import com.google.android.gms.internal.zzbvp;
import com.google.android.gms.internal.zzbxk;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyq;
import com.google.android.gms.internal.zzcac;

final class zzbyt
extends zzbvp {
    zzbyt(zzbyq zzbyq2, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbvk)zzb2;
        zzcac zzcac2 = new zzcac(this);
        ((zzbxk)((zzd)((Object)zzb2)).zzakn()).zza(new zzaa(zzcac2));
    }
}

