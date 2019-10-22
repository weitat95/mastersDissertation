/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi;
import io.reactivex.functions.Function3;
import java.lang.invoke.LambdaForm;

final class RxRecordingApi$$Lambda$4
implements Function3 {
    private final RxRecordingApi arg$1;

    private RxRecordingApi$$Lambda$4(RxRecordingApi rxRecordingApi) {
        this.arg$1 = rxRecordingApi;
    }

    public static Function3 lambdaFactory$(RxRecordingApi rxRecordingApi) {
        return new RxRecordingApi$$Lambda$4(rxRecordingApi);
    }

    @LambdaForm.Hidden
    public Object apply(Object object, Object object2, Object object3) {
        return this.arg$1.lambda$checkSubscriptions$6((Boolean)object, (Boolean)object2, (Boolean)object3);
    }
}

