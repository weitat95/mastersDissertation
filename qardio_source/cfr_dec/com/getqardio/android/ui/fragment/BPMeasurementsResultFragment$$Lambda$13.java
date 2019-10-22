/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import com.getqardio.android.ui.fragment.BPMeasurementsResultFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsResultFragment$$Lambda$13
implements Runnable {
    private final BPMeasurementsResultFragment arg$1;
    private final Activity arg$2;
    private final boolean arg$3;
    private final boolean arg$4;

    private BPMeasurementsResultFragment$$Lambda$13(BPMeasurementsResultFragment bPMeasurementsResultFragment, Activity activity, boolean bl, boolean bl2) {
        this.arg$1 = bPMeasurementsResultFragment;
        this.arg$2 = activity;
        this.arg$3 = bl;
        this.arg$4 = bl2;
    }

    public static Runnable lambdaFactory$(BPMeasurementsResultFragment bPMeasurementsResultFragment, Activity activity, boolean bl, boolean bl2) {
        return new BPMeasurementsResultFragment$$Lambda$13(bPMeasurementsResultFragment, activity, bl, bl2);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onEngagementScreenClosed$8(this.arg$2, this.arg$3, this.arg$4);
    }
}

