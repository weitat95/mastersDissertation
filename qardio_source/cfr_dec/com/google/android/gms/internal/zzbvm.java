/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbvk;
import com.google.android.gms.internal.zzbvl;

final class zzbvm
extends Api.zza<zzbvk, Object> {
    private zzbvm() {
    }

    /* synthetic */ zzbvm(zzbvl zzbvl2) {
        this();
    }

    @Override
    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzr zzr2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzbvk(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener, null);
    }
}

