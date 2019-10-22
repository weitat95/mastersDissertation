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
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementHistoryFragment$$Lambda$8
implements View.OnClickListener {
    private final Spinner arg$1;

    private WeightMeasurementHistoryFragment$$Lambda$8(Spinner spinner) {
        this.arg$1 = spinner;
    }

    public static View.OnClickListener lambdaFactory$(Spinner spinner) {
        return new WeightMeasurementHistoryFragment$$Lambda$8(spinner);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        WeightMeasurementHistoryFragment.lambda$init$5(this.arg$1, view);
    }
}

