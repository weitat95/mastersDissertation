/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.lang.invoke.LambdaForm;

final class RxRecordingApi$$Lambda$3
implements SingleOnSubscribe {
    private final RxRecordingApi arg$1;

    private RxRecordingApi$$Lambda$3(RxRecordingApi rxRecordingApi) {
        this.arg$1 = rxRecordingApi;
    }

    public static SingleOnSubscribe lambdaFactory$(RxRecordingApi rxRecordingApi) {
        return new RxRecordingApi$$Lambda$3(rxRecordingApi);
    }

    @LambdaForm.Hidden
    public void subscribe(SingleEmitter singleEmitter) {
        this.arg$1.lambda$checkSubscriptions$5(singleEmitter);
    }
}

