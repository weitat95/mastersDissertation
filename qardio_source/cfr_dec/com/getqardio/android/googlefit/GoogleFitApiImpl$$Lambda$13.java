/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class GoogleFitApiImpl$$Lambda$13
implements Function {
    private final Context arg$1;
    private final long arg$2;

    private GoogleFitApiImpl$$Lambda$13(Context context, long l) {
        this.arg$1 = context;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(Context context, long l) {
        return new GoogleFitApiImpl$$Lambda$13(context, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return GoogleFitApiImpl.lambda$saveBloodPressureMeasurements$13(this.arg$1, this.arg$2, (Boolean)object);
    }
}

