/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxConfigApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import io.reactivex.CompletableEmitter;
import java.lang.invoke.LambdaForm;

final class RxConfigApi$$Lambda$2
implements ResultCallback {
    private final CompletableEmitter arg$1;

    private RxConfigApi$$Lambda$2(CompletableEmitter completableEmitter) {
        this.arg$1 = completableEmitter;
    }

    public static ResultCallback lambdaFactory$(CompletableEmitter completableEmitter) {
        return new RxConfigApi$$Lambda$2(completableEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxConfigApi.lambda$null$0(this.arg$1, (Status)result);
    }
}

