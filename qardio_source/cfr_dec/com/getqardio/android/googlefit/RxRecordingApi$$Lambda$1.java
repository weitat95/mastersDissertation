/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataType;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxRecordingApi$$Lambda$1
implements SingleOnSubscribe {
    private final RxRecordingApi arg$1;
    private final GoogleApiClient arg$2;
    private final DataType arg$3;

    private RxRecordingApi$$Lambda$1(RxRecordingApi rxRecordingApi, GoogleApiClient googleApiClient, DataType dataType) {
        this.arg$1 = rxRecordingApi;
        this.arg$2 = googleApiClient;
        this.arg$3 = dataType;
    }

    public static SingleOnSubscribe lambdaFactory$(RxRecordingApi rxRecordingApi, GoogleApiClient googleApiClient, DataType dataType) {
        return new RxRecordingApi$$Lambda$1(rxRecordingApi, googleApiClient, dataType);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        this.arg$1.lambda$startRecording$2(this.arg$2, this.arg$3, singleEmitter);
    }
}

