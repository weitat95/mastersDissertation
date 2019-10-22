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
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.internal.zzcfk;
import com.google.android.gms.internal.zzcfv;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

final class zzcfw
extends LocationServices.zza<LocationSettingsResult> {
    private /* synthetic */ LocationSettingsRequest zzime;
    private /* synthetic */ String zzimf;

    zzcfw(zzcfv zzcfv2, GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String string2) {
        this.zzime = locationSettingsRequest;
        this.zzimf = null;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        ((zzcfk)zzb2).zza(this.zzime, this, this.zzimf);
    }

    @Override
    public final /* synthetic */ Result zzb(Status status) {
        return new LocationSettingsResult(status);
    }
}

