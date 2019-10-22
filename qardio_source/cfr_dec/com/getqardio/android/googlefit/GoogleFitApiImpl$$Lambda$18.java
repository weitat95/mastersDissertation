/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.googlefit;

import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class GoogleFitApiImpl$$Lambda$18
implements Function {
    private final GoogleFitApiImpl arg$1;
    private final long arg$2;

    private GoogleFitApiImpl$$Lambda$18(GoogleFitApiImpl googleFitApiImpl, long l) {
        this.arg$1 = googleFitApiImpl;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(GoogleFitApiImpl googleFitApiImpl, long l) {
        return new GoogleFitApiImpl$$Lambda$18(googleFitApiImpl, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$18(this.arg$2, (Boolean)object);
    }
}

