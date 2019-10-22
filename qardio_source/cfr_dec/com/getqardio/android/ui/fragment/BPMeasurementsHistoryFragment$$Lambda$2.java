/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Spinner
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import android.widget.Spinner;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsHistoryFragment$$Lambda$2
implements View.OnClickListener {
    private final Spinner arg$1;

    private BPMeasurementsHistoryFragment$$Lambda$2(Spinner spinner) {
        this.arg$1 = spinner;
    }

    public static View.OnClickListener lambdaFactory$(Spinner spinner) {
        return new BPMeasurementsHistoryFragment$$Lambda$2(spinner);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        BPMeasurementsHistoryFragment.lambda$init$1(this.arg$1, view);
    }
}

