/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementHistoryFragment$$Lambda$3
implements AdapterView.OnItemClickListener {
    private final WeightMeasurementHistoryFragment arg$1;

    private WeightMeasurementHistoryFragment$$Lambda$3(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        this.arg$1 = weightMeasurementHistoryFragment;
    }

    public static AdapterView.OnItemClickListener lambdaFactory$(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        return new WeightMeasurementHistoryFragment$$Lambda$3(weightMeasurementHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.arg$1.lambda$init$2(adapterView, view, n, l);
    }
}

