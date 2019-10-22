/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHeartRateApi;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxHeartRateApi$$Lambda$6
implements SingleOnSubscribe {
    private final long arg$1;
    private final GoogleApiClient arg$2;

    private RxHeartRateApi$$Lambda$6(long l, GoogleApiClient googleApiClient) {
        this.arg$1 = l;
        this.arg$2 = googleApiClient;
    }

    public static SingleOnSubscribe lambdaFactory$(long l, GoogleApiClient googleApiClient) {
        return new RxHeartRateApi$$Lambda$6(l, googleApiClient);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxHeartRateApi.lambda$deleteBloodPressureFromGoogleFit$9(this.arg$1, this.arg$2, singleEmitter);
    }
}

