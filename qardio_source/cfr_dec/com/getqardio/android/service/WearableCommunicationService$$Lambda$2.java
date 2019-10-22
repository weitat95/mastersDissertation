/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.service;

import com.getqardio.android.service.WearableCommunicationService;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class WearableCommunicationService$$Lambda$2
implements Consumer {
    private final GoogleApiClient arg$1;

    private WearableCommunicationService$$Lambda$2(GoogleApiClient googleApiClient) {
        this.arg$1 = googleApiClient;
    }

    public static Consumer lambdaFactory$(GoogleApiClient googleApiClient) {
        return new WearableCommunicationService$$Lambda$2(googleApiClient);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        WearableCommunicationService.lambda$null$0(this.arg$1, (List)object);
    }
}

