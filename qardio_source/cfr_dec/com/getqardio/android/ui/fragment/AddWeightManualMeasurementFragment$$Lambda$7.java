/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class AddWeightManualMeasurementFragment$$Lambda$7
implements View.OnFocusChangeListener {
    private final AddWeightManualMeasurementFragment arg$1;

    private AddWeightManualMeasurementFragment$$Lambda$7(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        this.arg$1 = addWeightManualMeasurementFragment;
    }

    public static View.OnFocusChangeListener lambdaFactory$(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        return new AddWeightManualMeasurementFragment$$Lambda$7(addWeightManualMeasurementFragment);
    }

    @LambdaForm.Hidden
    public void onFocusChange(View view, boolean bl) {
        this.arg$1.lambda$init$5(view, bl);
    }
}

