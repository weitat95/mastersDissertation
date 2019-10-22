/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryDetailsFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsHistoryDetailsFragment$$Lambda$1
implements View.OnClickListener {
    private final BPMeasurementsHistoryDetailsFragment arg$1;

    private BPMeasurementsHistoryDetailsFragment$$Lambda$1(BPMeasurementsHistoryDetailsFragment bPMeasurementsHistoryDetailsFragment) {
        this.arg$1 = bPMeasurementsHistoryDetailsFragment;
    }

    public static View.OnClickListener lambdaFactory$(BPMeasurementsHistoryDetailsFragment bPMeasurementsHistoryDetailsFragment) {
        return new BPMeasurementsHistoryDetailsFragment$$Lambda$1(bPMeasurementsHistoryDetailsFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}

