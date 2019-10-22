/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class AddBPManualMeasurementFragment$$Lambda$2
implements View.OnClickListener {
    private final AddBPManualMeasurementFragment arg$1;

    private AddBPManualMeasurementFragment$$Lambda$2(AddBPManualMeasurementFragment addBPManualMeasurementFragment) {
        this.arg$1 = addBPManualMeasurementFragment;
    }

    public static View.OnClickListener lambdaFactory$(AddBPManualMeasurementFragment addBPManualMeasurementFragment) {
        return new AddBPManualMeasurementFragment$$Lambda$2(addBPManualMeasurementFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$1(view);
    }
}

