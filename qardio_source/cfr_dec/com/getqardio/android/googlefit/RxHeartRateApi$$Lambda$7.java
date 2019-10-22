/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxHeartRateApi;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxHeartRateApi$$Lambda$7
implements Function {
    private static final RxHeartRateApi$$Lambda$7 instance = new RxHeartRateApi$$Lambda$7();

    private RxHeartRateApi$$Lambda$7() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxHeartRateApi.lambda$deleteBloodPressureFromGoogleFit$10((Throwable)object);
    }
}

