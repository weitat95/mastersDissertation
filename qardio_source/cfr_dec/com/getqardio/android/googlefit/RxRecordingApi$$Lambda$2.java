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

final class RxRecordingApi$$Lambda$2
implements SingleOnSubscribe {
    private final GoogleApiClient arg$1;
    private final DataType arg$2;

    private RxRecordingApi$$Lambda$2(GoogleApiClient googleApiClient, DataType dataType) {
        this.arg$1 = googleApiClient;
        this.arg$2 = dataType;
    }

    public static SingleOnSubscribe lambdaFactory$(GoogleApiClient googleApiClient, DataType dataType) {
        return new RxRecordingApi$$Lambda$2(googleApiClient, dataType);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        RxRecordingApi.lambda$stopRecording$4(this.arg$1, this.arg$2, singleEmitter);
    }
}

