/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementHistoryFragment$$Lambda$5
implements SwipeRefreshLayout.OnRefreshListener {
    private final WeightMeasurementHistoryFragment arg$1;

    private WeightMeasurementHistoryFragment$$Lambda$5(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        this.arg$1 = weightMeasurementHistoryFragment;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(WeightMeasurementHistoryFragment weightMeasurementHistoryFragment) {
        return new WeightMeasurementHistoryFragment$$Lambda$5(weightMeasurementHistoryFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        WeightMeasurementHistoryFragment.access$lambda$0(this.arg$1);
    }
}

