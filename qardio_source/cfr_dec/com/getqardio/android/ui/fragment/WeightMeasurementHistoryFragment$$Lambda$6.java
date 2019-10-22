/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.WeightMeasurementHistoryFragment;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementHistoryFragment$$Lambda$6
implements BackPanelListViewHelper.BackPanelCallbacks {
    private static final WeightMeasurementHistoryFragment$$Lambda$6 instance = new WeightMeasurementHistoryFragment$$Lambda$6();

    private WeightMeasurementHistoryFragment$$Lambda$6() {
    }

    public static BackPanelListViewHelper.BackPanelCallbacks lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public boolean hasBackPanel(int n) {
        return WeightMeasurementHistoryFragment.lambda$init$4(n);
    }
}

