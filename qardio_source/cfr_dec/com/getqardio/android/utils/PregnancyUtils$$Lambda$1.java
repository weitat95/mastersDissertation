/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils;

import android.content.Context;
import com.getqardio.android.utils.PregnancyUtils;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class PregnancyUtils$$Lambda$1
implements Function {
    private final Context arg$1;
    private final long arg$2;

    private PregnancyUtils$$Lambda$1(Context context, long l) {
        this.arg$1 = context;
        this.arg$2 = l;
    }

    public static Function lambdaFactory$(Context context, long l) {
        return new PregnancyUtils$$Lambda$1(context, l);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return PregnancyUtils.lambda$stopPregnancyModeAsync$0(this.arg$1, this.arg$2, object);
    }
}

