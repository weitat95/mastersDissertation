/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class GoogleFitApiImpl$$Lambda$5
implements Function {
    private final GoogleFitApiImpl arg$1;

    private GoogleFitApiImpl$$Lambda$5(GoogleFitApiImpl googleFitApiImpl) {
        this.arg$1 = googleFitApiImpl;
    }

    public static Function lambdaFactory$(GoogleFitApiImpl googleFitApiImpl) {
        return new GoogleFitApiImpl$$Lambda$5(googleFitApiImpl);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$fetchCurrentDayActivity$4((List)object);
    }
}

