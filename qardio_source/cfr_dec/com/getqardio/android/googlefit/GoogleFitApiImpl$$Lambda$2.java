/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class GoogleFitApiImpl$$Lambda$2
implements Function {
    private static final GoogleFitApiImpl$$Lambda$2 instance = new GoogleFitApiImpl$$Lambda$2();

    private GoogleFitApiImpl$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return GoogleFitApiImpl.lambda$fetchMonthHistory$1((Throwable)object);
    }
}

