/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class AddWeightManualMeasurementFragment$$Lambda$4
implements View.OnClickListener {
    private final AddWeightManualMeasurementFragment arg$1;

    private AddWeightManualMeasurementFragment$$Lambda$4(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        this.arg$1 = addWeightManualMeasurementFragment;
    }

    public static View.OnClickListener lambdaFactory$(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        return new AddWeightManualMeasurementFragment$$Lambda$4(addWeightManualMeasurementFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$1(view);
    }
}

