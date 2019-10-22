/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class PregnancyMeasurementFragment$$Lambda$4
implements Action {
    private static final PregnancyMeasurementFragment$$Lambda$4 instance = new PregnancyMeasurementFragment$$Lambda$4();

    private PregnancyMeasurementFragment$$Lambda$4() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        PregnancyMeasurementFragment.lambda$saveMeasurement$3();
    }
}

