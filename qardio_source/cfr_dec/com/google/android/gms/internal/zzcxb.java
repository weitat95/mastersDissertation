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
import com.google.android.gms.internal.zzcxe;
import com.google.android.gms.internal.zzcxn;

final class zzcxb
extends Api.zza<zzcxn, zzcxe> {
    zzcxb() {
    }

    @Override
    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzr zzr2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if ((object = (zzcxe)object) == null) {
            object = zzcxe.zzkbs;
        }
        return new zzcxn(context, looper, true, zzr2, (zzcxe)object, connectionCallbacks, onConnectionFailedListener);
    }
}

