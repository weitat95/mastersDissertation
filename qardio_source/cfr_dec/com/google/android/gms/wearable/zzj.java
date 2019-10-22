/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.wearable;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.internal.zzhg;

final class zzj
extends Api.zza<zzhg, Wearable.WearableOptions> {
    zzj() {
    }

    @Override
    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzr zzr2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if ((Wearable.WearableOptions)object == null) {
            new Wearable.WearableOptions(new Wearable.WearableOptions.Builder(), null);
        }
        return new zzhg(context, looper, connectionCallbacks, onConnectionFailedListener, zzr2);
    }
}

