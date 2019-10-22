/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHistoryApi;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxHistoryApi$$Lambda$2
implements SingleOnSubscribe {
    private final GoogleApiClient arg$1;

    private RxHistoryApi$$Lambda$2(GoogleApiClient googleApiClient) {
        this.arg$1 = googleApiClient;
    }

    public static SingleOnSubscribe lambdaFactory$(GoogleApiClient googleApiClient) {
        return new RxHistoryApi$$Lambda$2(googleApiClient);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxHistoryApi.lambda$fetchCurrentDaySteps$3(this.arg$1, singleEmitter);
    }
}

