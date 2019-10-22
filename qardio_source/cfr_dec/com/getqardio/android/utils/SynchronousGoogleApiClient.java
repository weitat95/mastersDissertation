/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import java.util.concurrent.TimeUnit;

public class SynchronousGoogleApiClient {
    private GoogleApiClient googleApiClient;

    public void disconnect() {
        if (this.googleApiClient != null && (this.googleApiClient.isConnected() || this.googleApiClient.isConnecting())) {
            this.googleApiClient.disconnect();
        }
    }

    public GoogleApiClient getGoogleApiClient(Context context) {
        if (this.googleApiClient == null) {
            this.googleApiClient = new GoogleApiClient.Builder(context).addApi(Wearable.API).build();
        }
        if (!this.googleApiClient.isConnected()) {
            this.googleApiClient.blockingConnect(30L, TimeUnit.SECONDS);
        }
        return this.googleApiClient;
    }
}

