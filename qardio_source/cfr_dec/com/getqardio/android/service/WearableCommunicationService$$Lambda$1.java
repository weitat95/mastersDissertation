/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.service;

import com.getqardio.android.service.WearableCommunicationService;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm;

final class WearableCommunicationService$$Lambda$1
implements Runnable {
    private final WearableCommunicationService arg$1;
    private final GoogleApiClient arg$2;

    private WearableCommunicationService$$Lambda$1(WearableCommunicationService wearableCommunicationService, GoogleApiClient googleApiClient) {
        this.arg$1 = wearableCommunicationService;
        this.arg$2 = googleApiClient;
    }

    public static Runnable lambdaFactory$(WearableCommunicationService wearableCommunicationService, GoogleApiClient googleApiClient) {
        return new WearableCommunicationService$$Lambda$1(wearableCommunicationService, googleApiClient);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$doSendFollowingsData$1(this.arg$2);
    }
}

