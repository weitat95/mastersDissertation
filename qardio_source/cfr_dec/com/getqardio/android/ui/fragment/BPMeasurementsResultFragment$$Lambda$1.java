/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$1
implements GoogleApiClient.OnConnectionFailedListener {
    private final BPMeasurementsResultFragment arg$1;

    private BPMeasurementsResultFragment$$Lambda$1(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        this.arg$1 = bPMeasurementsResultFragment;
    }

    public static GoogleApiClient.OnConnectionFailedListener lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        return new BPMeasurementsResultFragment$$Lambda$1(bPMeasurementsResultFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.arg$1.lambda$onActivityCreated$0(connectionResult);
    }
}

