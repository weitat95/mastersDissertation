/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxRecordingApi;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxRecordingApi$$Lambda$5
implements Function {
    private static final RxRecordingApi$$Lambda$5 instance = new RxRecordingApi$$Lambda$5();

    private RxRecordingApi$$Lambda$5() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxRecordingApi.lambda$checkSubscriptions$7((Throwable)object);
    }
}

