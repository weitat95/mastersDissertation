/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import android.support.v4.util.Pair;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class GoogleFitApiImpl$$Lambda$1
implements Function {
    private final GoogleFitApiImpl arg$1;

    private GoogleFitApiImpl$$Lambda$1(GoogleFitApiImpl googleFitApiImpl) {
        this.arg$1 = googleFitApiImpl;
    }

    public static Function lambdaFactory$(GoogleFitApiImpl googleFitApiImpl) {
        return new GoogleFitApiImpl$$Lambda$1(googleFitApiImpl);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$fetchMonthHistory$0((List)object);
    }
}

