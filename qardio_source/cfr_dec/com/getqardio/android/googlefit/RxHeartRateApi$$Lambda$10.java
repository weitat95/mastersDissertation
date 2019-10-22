/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHeartRateApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataSource;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class RxHeartRateApi$$Lambda$10
implements ResultCallback {
    private final DataSource arg$1;
    private final SingleEmitter arg$2;
    private final List arg$3;

    private RxHeartRateApi$$Lambda$10(DataSource dataSource, SingleEmitter singleEmitter, List list) {
        this.arg$1 = dataSource;
        this.arg$2 = singleEmitter;
        this.arg$3 = list;
    }

    public static ResultCallback lambdaFactory$(DataSource dataSource, SingleEmitter singleEmitter, List list) {
        return new RxHeartRateApi$$Lambda$10(dataSource, singleEmitter, list);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        RxHeartRateApi.lambda$null$3(this.arg$1, this.arg$2, this.arg$3, (Status)result);
    }
}

