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
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzceb;
import com.google.android.gms.internal.zzcem;
import com.google.android.gms.internal.zzcen;
import com.google.android.gms.internal.zzceu;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.internal.zzcgc;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzcec
extends zzcem {
    private /* synthetic */ LocationRequest zzild;
    private /* synthetic */ LocationListener zzile;

    zzcec(zzceb zzceb2, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
        this.zzild = locationRequest;
        this.zzile = locationListener;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzb2 = (zzcfk)zzb2;
        zzcen zzcen2 = new zzcen(this);
        ((zzcfk)zzb2).zza(this.zzild, zzcm.zzb(this.zzile, zzcgc.zzavy(), LocationListener.class.getSimpleName()), zzcen2);
    }
}

