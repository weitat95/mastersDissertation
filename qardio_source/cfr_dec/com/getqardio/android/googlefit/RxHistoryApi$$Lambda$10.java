/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHistoryApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.result.DataReadResult;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;

final class RxHistoryApi$$Lambda$10
implements ResultCallback {
    private final SingleEmitter arg$1;

    private RxHistoryApi$$Lambda$10(SingleEmitter singleEmitter) {
        this.arg$1 = singleEmitter;
    }

    public static ResultCallback lambdaFactory$(SingleEmitter singleEmitter) {
        return new RxHistoryApi$$Lambda$10(singleEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxHistoryApi.lambda$null$0(this.arg$1, (DataReadResult)result);
    }
}

