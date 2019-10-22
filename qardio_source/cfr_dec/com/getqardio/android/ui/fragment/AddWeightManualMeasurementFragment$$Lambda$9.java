/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.content.DialogInterface;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class AddWeightManualMeasurementFragment$$Lambda$9
implements DialogInterface.OnClickListener {
    private final AddWeightManualMeasurementFragment arg$1;

    private AddWeightManualMeasurementFragment$$Lambda$9(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        this.arg$1 = addWeightManualMeasurementFragment;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        return new AddWeightManualMeasurementFragment$$Lambda$9(addWeightManualMeasurementFragment);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$null$3(dialogInterface, n);
    }
}

