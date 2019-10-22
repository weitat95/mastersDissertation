/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class GoogleFitApiImpl$$Lambda$6
implements Function {
    private static final GoogleFitApiImpl$$Lambda$6 instance = new GoogleFitApiImpl$$Lambda$6();

    private GoogleFitApiImpl$$Lambda$6() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return GoogleFitApiImpl.lambda$fetchCurrentDayActivity$5((Throwable)object);
    }
}

