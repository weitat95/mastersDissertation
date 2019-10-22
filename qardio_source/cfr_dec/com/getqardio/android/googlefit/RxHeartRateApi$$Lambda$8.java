/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHeartRateApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;

final class RxHeartRateApi$$Lambda$8
implements ResultCallback {
    private final SingleEmitter arg$1;

    private RxHeartRateApi$$Lambda$8(SingleEmitter singleEmitter) {
        this.arg$1 = singleEmitter;
    }

    public static ResultCallback lambdaFactory$(SingleEmitter singleEmitter) {
        return new RxHeartRateApi$$Lambda$8(singleEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxHeartRateApi.lambda$null$8(this.arg$1, (Status)result);
    }
}

