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
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment;
import java.lang.invoke.LambdaForm;

final class BPMeasurementsHistoryFragment$$Lambda$4
implements AdapterView.OnItemClickListener {
    private final BPMeasurementsHistoryFragment arg$1;

    private BPMeasurementsHistoryFragment$$Lambda$4(BPMeasurementsHistoryFragment bPMeasurementsHistoryFragment) {
        this.arg$1 = bPMeasurementsHistoryFragment;
    }

    public static AdapterView.OnItemClickListener lambdaFactory$(BPMeasurementsHistoryFragment bPMeasurementsHistoryFragment) {
        return new BPMeasurementsHistoryFragment$$Lambda$4(bPMeasurementsHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.arg$1.lambda$setupList$3(adapterView, view, n, l);
    }
}

