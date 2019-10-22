/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxConfigApi;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxConfigApi$$Lambda$1
implements CompletableOnSubscribe {
    private final GoogleApiClient arg$1;

    private RxConfigApi$$Lambda$1(GoogleApiClient googleApiClient) {
        this.arg$1 = googleApiClient;
    }

    public static CompletableOnSubscribe lambdaFactory$(GoogleApiClient googleApiClient) {
        return new RxConfigApi$$Lambda$1(googleApiClient);
    }

    @LambdaForm.Hidden
    @Override
    public void subscribe(CompletableEmitter completableEmitter) {
        RxConfigApi.lambda$disconnectFromFit$1(this.arg$1, completableEmitter);
    }
}

