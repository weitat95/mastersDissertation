/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.device_related_services.fit;

import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class GoogleFitUtils$$Lambda$1
implements GoogleApiClient.OnConnectionFailedListener {
    private static final GoogleFitUtils$$Lambda$1 instance = new GoogleFitUtils$$Lambda$1();

    private GoogleFitUtils$$Lambda$1() {
    }

    public static GoogleApiClient.OnConnectionFailedListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        GoogleFitUtils.lambda$buildNewClient$0(connectionResult);
    }
}

