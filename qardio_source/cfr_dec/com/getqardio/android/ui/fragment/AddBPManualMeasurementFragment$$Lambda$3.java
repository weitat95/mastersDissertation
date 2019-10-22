/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class AddBPManualMeasurementFragment$$Lambda$3
implements View.OnFocusChangeListener {
    private final AddBPManualMeasurementFragment arg$1;

    private AddBPManualMeasurementFragment$$Lambda$3(AddBPManualMeasurementFragment addBPManualMeasurementFragment) {
        this.arg$1 = addBPManualMeasurementFragment;
    }

    public static View.OnFocusChangeListener lambdaFactory$(AddBPManualMeasurementFragment addBPManualMeasurementFragment) {
        return new AddBPManualMeasurementFragment$$Lambda$3(addBPManualMeasurementFragment);
    }

    @LambdaForm.Hidden
    public void onFocusChange(View view, boolean bl) {
        this.arg$1.lambda$init$2(view, bl);
    }
}

