/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import com.getqardio.android.utils.PregnancyUtils;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class PregnancyUtils$$Lambda$2
implements Consumer {
    private static final PregnancyUtils$$Lambda$2 instance = new PregnancyUtils$$Lambda$2();

    private PregnancyUtils$$Lambda$2() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        PregnancyUtils.lambda$stopPregnancyModeAsync$1(object);
    }
}

