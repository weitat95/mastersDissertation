/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import io.reactivex.functions.Action;
import java.lang.invoke.LambdaForm;

final class PregnancyMeasurementFragment$$Lambda$7
implements Action {
    private static final PregnancyMeasurementFragment$$Lambda$7 instance = new PregnancyMeasurementFragment$$Lambda$7();

    private PregnancyMeasurementFragment$$Lambda$7() {
    }

    public static Action lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        PregnancyMeasurementFragment.lambda$processPhoto$5();
    }
}

