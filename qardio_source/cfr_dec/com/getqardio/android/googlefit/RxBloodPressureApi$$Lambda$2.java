/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.RxBloodPressureApi;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RxBloodPressureApi$$Lambda$2
implements Function {
    private static final RxBloodPressureApi$$Lambda$2 instance = new RxBloodPressureApi$$Lambda$2();

    private RxBloodPressureApi$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RxBloodPressureApi.lambda$saveBloodPressureToGoogleFit$2((Throwable)object);
    }
}

