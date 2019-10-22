/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHistoryApi;
import com.google.android.gms.common.api.GoogleApiClient;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxHistoryApi$$Lambda$1
implements SingleOnSubscribe {
    private final long arg$1;
    private final GoogleApiClient arg$2;

    private RxHistoryApi$$Lambda$1(long l, GoogleApiClient googleApiClient) {
        this.arg$1 = l;
        this.arg$2 = googleApiClient;
    }

    public static SingleOnSubscribe lambdaFactory$(long l, GoogleApiClient googleApiClient) {
        return new RxHistoryApi$$Lambda$1(l, googleApiClient);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxHistoryApi.lambda$fetchMonthHistory$1(this.arg$1, this.arg$2, singleEmitter);
    }
}

