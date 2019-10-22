/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$4
implements View.OnClickListener {
    private final BPMeasurementsResultFragment arg$1;

    private BPMeasurementsResultFragment$$Lambda$4(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        this.arg$1 = bPMeasurementsResultFragment;
    }

    public static View.OnClickListener lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment) {
        return new BPMeasurementsResultFragment$$Lambda$4(bPMeasurementsResultFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$2(view);
    }
}

