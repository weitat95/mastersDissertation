/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsHistoryFragment$$Lambda$3
implements BackPanelListViewHelper.BackPanelCallbacks {
    private static final BPMeasurementsHistoryFragment$$Lambda$3 instance = new BPMeasurementsHistoryFragment$$Lambda$3();

    private BPMeasurementsHistoryFragment$$Lambda$3() {
    }

    public static BackPanelListViewHelper.BackPanelCallbacks lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public boolean hasBackPanel(int n) {
        return BPMeasurementsHistoryFragment.lambda$setupList$2(n);
    }
}

