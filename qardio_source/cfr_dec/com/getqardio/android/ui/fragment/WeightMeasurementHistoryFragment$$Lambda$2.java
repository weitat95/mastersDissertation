/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.RadioGroup;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementHistoryFragment$$Lambda$2
implements RadioGroup.OnCheckedChangeListener {
    private final WeightMeasurementHistoryFragment arg$1;

    private WeightMeasurementHistoryFragment$$Lambda$2(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        this.arg$1 = weightMeasurementHistoryFragment;
    }

    public static RadioGroup.OnCheckedChangeListener lambdaFactory$(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        return new WeightMeasurementHistoryFragment$$Lambda$2(weightMeasurementHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(RadioGroup radioGroup, int n) {
        this.arg$1.lambda$init$1(radioGroup, n);
    }
}

