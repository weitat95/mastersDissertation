/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxBloodPressureApi;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxBloodPressureApi$$Lambda$5
implements SingleOnSubscribe {
    private final GoogleApiClient arg$1;

    private RxBloodPressureApi$$Lambda$5(GoogleApiClient googleApiClient) {
        this.arg$1 = googleApiClient;
    }

    public static SingleOnSubscribe lambdaFactory$(GoogleApiClient googleApiClient) {
        return new RxBloodPressureApi$$Lambda$5(googleApiClient);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxBloodPressureApi.lambda$readBloodPressureFromGoogleFit$7(this.arg$1, singleEmitter);
    }
}

