/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHistoryApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.result.DailyTotalResult;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;

final class RxHistoryApi$$Lambda$9
implements ResultCallback {
    private final SingleEmitter arg$1;

    private RxHistoryApi$$Lambda$9(SingleEmitter singleEmitter) {
        this.arg$1 = singleEmitter;
    }

    public static ResultCallback lambdaFactory$(SingleEmitter singleEmitter) {
        return new RxHistoryApi$$Lambda$9(singleEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxHistoryApi.lambda$null$2(this.arg$1, (DailyTotalResult)result);
    }
}

