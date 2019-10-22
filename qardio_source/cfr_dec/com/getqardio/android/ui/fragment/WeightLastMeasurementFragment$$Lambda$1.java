/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.WeightLastMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class WeightLastMeasurementFragment$$Lambda$1
implements Runnable {
    private final WeightLastMeasurementFragment arg$1;

    private WeightLastMeasurementFragment$$Lambda$1(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        this.arg$1 = weightLastMeasurementFragment;
    }

    public static Runnable lambdaFactory$(WeightLastMeasurementFragment weightLastMeasurementFragment) {
        return new WeightLastMeasurementFragment$$Lambda$1(weightLastMeasurementFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$checkBLERadioEnabled$1();
    }
}

