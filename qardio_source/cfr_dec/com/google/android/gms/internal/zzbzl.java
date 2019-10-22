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
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.zzbj;
import com.google.android.gms.internal.zzbwb;
import com.google.android.gms.internal.zzbwg;
import com.google.android.gms.internal.zzbxq;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbzi;
import com.google.android.gms.internal.zzcac;

final class zzbzl
extends zzbwg {
    private /* synthetic */ Subscription zzhfi;

    zzbzl(zzbzi zzbzi2, GoogleApiClient googleApiClient, Subscription subscription) {
        this.zzhfi = subscription;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzbwb)zzb2;
        zzcac zzcac2 = new zzcac(this);
        ((zzbxq)((zzd)((Object)zzb2)).zzakn()).zza(new zzbj(this.zzhfi, false, zzcac2));
    }
}

