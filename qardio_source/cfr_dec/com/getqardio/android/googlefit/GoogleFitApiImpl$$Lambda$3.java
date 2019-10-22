/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.google.android.gms.fitness.data.DataSet;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class GoogleFitApiImpl$$Lambda$3
implements Function {
    private static final GoogleFitApiImpl$$Lambda$3 instance = new GoogleFitApiImpl$$Lambda$3();

    private GoogleFitApiImpl$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return GoogleFitApiImpl.lambda$fetchCurrentDaySteps$2((DataSet)object);
    }
}

