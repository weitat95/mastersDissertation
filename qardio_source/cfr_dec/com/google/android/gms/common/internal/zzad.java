/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzg;

final class zzad
implements zzg {
    private /* synthetic */ GoogleApiClient.OnConnectionFailedListener zzgaa;

    zzad(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzgaa = onConnectionFailedListener;
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzgaa.onConnectionFailed(connectionResult);
    }
}

