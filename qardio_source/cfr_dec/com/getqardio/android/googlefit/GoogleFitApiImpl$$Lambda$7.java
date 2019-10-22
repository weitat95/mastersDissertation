/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class GoogleFitApiImpl$$Lambda$7
implements Function {
    private final GoogleFitApiImpl arg$1;

    private GoogleFitApiImpl$$Lambda$7(GoogleFitApiImpl googleFitApiImpl) {
        this.arg$1 = googleFitApiImpl;
    }

    public static Function lambdaFactory$(GoogleFitApiImpl googleFitApiImpl) {
        return new GoogleFitApiImpl$$Lambda$7(googleFitApiImpl);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$fetchDayHistory$6((List)object);
    }
}

