/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$8
implements Consumer {
    private static final BPMeasurementsResultFragment$$Lambda$8 instance = new BPMeasurementsResultFragment$$Lambda$8();

    private BPMeasurementsResultFragment$$Lambda$8() {
    }

    public static Consumer lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        BPMeasurementsResultFragment.lambda$onMeasurementFinished$6((Boolean)object);
    }
}

