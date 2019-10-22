/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.BPReadDataActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class BPReadDataActivity$$Lambda$1
implements GoogleApiClient.OnConnectionFailedListener {
    private final BPReadDataActivity arg$1;

    private BPReadDataActivity$$Lambda$1(BPReadDataActivity bPReadDataActivity) {
        this.arg$1 = bPReadDataActivity;
    }

    public static GoogleApiClient.OnConnectionFailedListener lambdaFactory$(BPReadDataActivity bPReadDataActivity) {
        return new BPReadDataActivity$$Lambda$1(bPReadDataActivity);
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.arg$1.lambda$onCreate$0(connectionResult);
    }
}

