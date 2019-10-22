/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.device_related_services.map;

import com.getqardio.android.device_related_services.map.LocationDetectorImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class LocationDetectorImpl$$Lambda$1
implements GoogleApiClient.OnConnectionFailedListener {
    private static final LocationDetectorImpl$$Lambda$1 instance = new LocationDetectorImpl$$Lambda$1();

    private LocationDetectorImpl$$Lambda$1() {
    }

    public static GoogleApiClient.OnConnectionFailedListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LocationDetectorImpl.lambda$startTrackLocation$0(connectionResult);
    }
}

