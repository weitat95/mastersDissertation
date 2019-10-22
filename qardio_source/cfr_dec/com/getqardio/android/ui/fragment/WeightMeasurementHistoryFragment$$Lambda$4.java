/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementHistoryFragment$$Lambda$4
implements BackPanelListViewHelper.BackPanelCallbacks {
    private static final WeightMeasurementHistoryFragment$$Lambda$4 instance = new WeightMeasurementHistoryFragment$$Lambda$4();

    private WeightMeasurementHistoryFragment$$Lambda$4() {
    }

    public static BackPanelListViewHelper.BackPanelCallbacks lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public boolean hasBackPanel(int n) {
        return WeightMeasurementHistoryFragment.lambda$init$3(n);
    }
}

