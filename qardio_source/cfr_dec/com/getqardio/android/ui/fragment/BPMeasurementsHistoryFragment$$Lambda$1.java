/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.RadioGroup;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsHistoryFragment$$Lambda$1
implements RadioGroup.OnCheckedChangeListener {
    private final BPMeasurementsHistoryFragment arg$1;

    private BPMeasurementsHistoryFragment$$Lambda$1(BPMeasurementsHistoryFragment bPMeasurementsHistoryFragment) {
        this.arg$1 = bPMeasurementsHistoryFragment;
    }

    public static RadioGroup.OnCheckedChangeListener lambdaFactory$(BPMeasurementsHistoryFragment bPMeasurementsHistoryFragment) {
        return new BPMeasurementsHistoryFragment$$Lambda$1(bPMeasurementsHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(RadioGroup radioGroup, int n) {
        this.arg$1.lambda$init$0(radioGroup, n);
    }
}

