/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class GoogleFitApiImpl$$Lambda$20
implements Function {
    private final GoogleFitApiImpl arg$1;
    private final List arg$2;

    private GoogleFitApiImpl$$Lambda$20(GoogleFitApiImpl googleFitApiImpl, List list) {
        this.arg$1 = googleFitApiImpl;
        this.arg$2 = list;
    }

    public static Function lambdaFactory$(GoogleFitApiImpl googleFitApiImpl, List list) {
        return new GoogleFitApiImpl$$Lambda$20(googleFitApiImpl, list);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$14(this.arg$2, (Integer)object);
    }
}

