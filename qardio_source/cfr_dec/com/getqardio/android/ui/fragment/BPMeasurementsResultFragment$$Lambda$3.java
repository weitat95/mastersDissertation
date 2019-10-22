/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$3
implements Runnable {
    private final BPMeasurementsResultFragment arg$1;

    private BPMeasurementsResultFragment$$Lambda$3(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        this.arg$1 = bPMeasurementsResultFragment;
    }

    public static Runnable lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        return new BPMeasurementsResultFragment$$Lambda$3(bPMeasurementsResultFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        BPMeasurementsResultFragment.access$lambda$0(this.arg$1);
    }
}

