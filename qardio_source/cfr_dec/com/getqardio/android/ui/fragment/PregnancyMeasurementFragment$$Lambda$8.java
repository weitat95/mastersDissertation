/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class PregnancyMeasurementFragment$$Lambda$8
implements Consumer {
    private static final PregnancyMeasurementFragment$$Lambda$8 instance = new PregnancyMeasurementFragment$$Lambda$8();

    private PregnancyMeasurementFragment$$Lambda$8() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

