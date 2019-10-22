/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import io.reactivex.SingleEmitter;
import java.lang.invoke.LambdaForm;

final class RxRecordingApi$$Lambda$8
implements ResultCallback {
    private final RxRecordingApi arg$1;
    private final DataType arg$2;
    private final GoogleApiClient arg$3;
    private final SingleEmitter arg$4;

    private RxRecordingApi$$Lambda$8(RxRecordingApi rxRecordingApi, DataType dataType, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) {
        this.arg$1 = rxRecordingApi;
        this.arg$2 = dataType;
        this.arg$3 = googleApiClient;
        this.arg$4 = singleEmitter;
    }

    public static ResultCallback lambdaFactory$(RxRecordingApi rxRecordingApi, DataType dataType, GoogleApiClient googleApiClient, SingleEmitter singleEmitter) {
        return new RxRecordingApi$$Lambda$8(rxRecordingApi, dataType, googleApiClient, singleEmitter);
    }

    @LambdaForm.Hidden
    public void onResult(Result result) {
        this.arg$1.lambda$null$1(this.arg$2, this.arg$3, this.arg$4, (ListSubscriptionsResult)result);
    }
}

