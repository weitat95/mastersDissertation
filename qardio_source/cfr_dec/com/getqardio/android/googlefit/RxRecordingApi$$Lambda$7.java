/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;

final class RxRecordingApi$$Lambda$7
implements ResultCallback {
    private final SingleEmitter arg$1;

    private RxRecordingApi$$Lambda$7(SingleEmitter singleEmitter) {
        this.arg$1 = singleEmitter;
    }

    public static ResultCallback lambdaFactory$(SingleEmitter singleEmitter) {
        return new RxRecordingApi$$Lambda$7(singleEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxRecordingApi.lambda$null$3(this.arg$1, (Status)result);
    }
}

