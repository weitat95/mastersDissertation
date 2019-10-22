/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.zzbq;

public final class zzt
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    public final Api<?> zzfin;
    private final boolean zzfpg;
    private zzu zzfph;

    public zzt(Api<?> api, boolean bl) {
        this.zzfin = api;
        this.zzfpg = bl;
    }

    private final void zzahj() {
        zzbq.checkNotNull(this.zzfph, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zzahj();
        this.zzfph.onConnected(bundle);
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzahj();
        this.zzfph.zza(connectionResult, this.zzfin, this.zzfpg);
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zzahj();
        this.zzfph.onConnectionSuspended(n);
    }

    public final void zza(zzu zzu2) {
        this.zzfph = zzu2;
    }
}

