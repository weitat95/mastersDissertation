/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;
import timber.log.Timber;

final class BPMeasurementsResultFragment$$Lambda$11
implements Consumer {
    private static final BPMeasurementsResultFragment$$Lambda$11 instance = new BPMeasurementsResultFragment$$Lambda$11();

    private BPMeasurementsResultFragment$$Lambda$11() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        Timber.e((Throwable)object);
    }
}

