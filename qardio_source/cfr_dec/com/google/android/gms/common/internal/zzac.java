/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;

final class zzac
implements zzf {
    private /* synthetic */ GoogleApiClient.ConnectionCallbacks zzfzz;

    zzac(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzfzz = connectionCallbacks;
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zzfzz.onConnected(bundle);
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zzfzz.onConnectionSuspended(n);
    }
}

