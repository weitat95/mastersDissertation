/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class WeightLastMeasurementFragment$$Lambda$3
implements View.OnClickListener {
    private final WeightLastMeasurementFragment arg$1;

    private WeightLastMeasurementFragment$$Lambda$3(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        this.arg$1 = weightLastMeasurementFragment;
    }

    public static View.OnClickListener lambdaFactory$(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return new WeightLastMeasurementFragment$$Lambda$3(weightLastMeasurementFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$3(view);
    }
}

