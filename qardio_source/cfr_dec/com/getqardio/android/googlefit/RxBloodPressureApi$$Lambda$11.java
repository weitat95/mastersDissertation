/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxBloodPressureApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSource;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;

final class RxBloodPressureApi$$Lambda$11
implements ResultCallback {
    private final DataSource arg$1;
    private final SingleEmitter arg$2;

    private RxBloodPressureApi$$Lambda$11(DataSource dataSource, SingleEmitter singleEmitter) {
        this.arg$1 = dataSource;
        this.arg$2 = singleEmitter;
    }

    public static ResultCallback lambdaFactory$(DataSource dataSource, SingleEmitter singleEmitter) {
        return new RxBloodPressureApi$$Lambda$11(dataSource, singleEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxBloodPressureApi.lambda$null$0(this.arg$1, this.arg$2, (Status)result);
    }
}

