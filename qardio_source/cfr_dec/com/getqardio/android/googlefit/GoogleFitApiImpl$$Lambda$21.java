/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import java.lang.invoke.LambdaForm;
import java.util.concurrent.Callable;

final class GoogleFitApiImpl$$Lambda$21
implements Callable {
    private final Context arg$1;
    private final long arg$2;

    private GoogleFitApiImpl$$Lambda$21(Context context, long l) {
        this.arg$1 = context;
        this.arg$2 = l;
    }

    public static Callable lambdaFactory$(Context context, long l) {
        return new GoogleFitApiImpl$$Lambda$21(context, l);
    }

    @LambdaForm.Hidden
    public Object call() {
        return GoogleFitApiImpl.lambda$null$12(this.arg$1, this.arg$2);
    }
}

