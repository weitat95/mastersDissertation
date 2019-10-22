/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHistoryApi;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxHistoryApi$$Lambda$5
implements SingleOnSubscribe {
    private final long arg$1;
    private final long arg$2;
    private final GoogleApiClient arg$3;

    private RxHistoryApi$$Lambda$5(long l, long l2, GoogleApiClient googleApiClient) {
        this.arg$1 = l;
        this.arg$2 = l2;
        this.arg$3 = googleApiClient;
    }

    public static SingleOnSubscribe lambdaFactory$(long l, long l2, GoogleApiClient googleApiClient) {
        return new RxHistoryApi$$Lambda$5(l, l2, googleApiClient);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxHistoryApi.lambda$fetchDayStepsTimeline$9(this.arg$1, this.arg$2, this.arg$3, singleEmitter);
    }
}

