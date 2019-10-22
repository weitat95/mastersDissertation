/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxBloodPressureApi;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxBloodPressureApi$$Lambda$7
implements Function {
    private static final RxBloodPressureApi$$Lambda$7 instance = new RxBloodPressureApi$$Lambda$7();

    private RxBloodPressureApi$$Lambda$7() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxBloodPressureApi.lambda$deleteBloodPressureFromGoogleFit$10((Throwable)object);
    }
}

