/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$2
implements Runnable {
    private final BPMeasurementsResultFragment arg$1;

    private BPMeasurementsResultFragment$$Lambda$2(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        this.arg$1 = bPMeasurementsResultFragment;
    }

    public static Runnable lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        return new BPMeasurementsResultFragment$$Lambda$2(bPMeasurementsResultFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$init$1();
    }
}

