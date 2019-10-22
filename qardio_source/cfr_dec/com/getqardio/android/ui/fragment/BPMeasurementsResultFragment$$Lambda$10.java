/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import io.reactivex.functions.Consumer;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$10
implements Consumer {
    private final BPMeasurementsResultFragment arg$1;

    private BPMeasurementsResultFragment$$Lambda$10(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        this.arg$1 = bPMeasurementsResultFragment;
    }

    public static Consumer lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        return new BPMeasurementsResultFragment$$Lambda$10(bPMeasurementsResultFragment);
    }

    @LambdaForm.Hidden
    public void accept(Object object) {
        BPMeasurementsResultFragment.access$lambda$1(this.arg$1, (Integer)object);
    }
}

