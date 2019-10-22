/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment;
import java.lang.invoke.LambdaForm;

final class PregnancyMeasurementFragment$$Lambda$3
implements Runnable {
    private final PregnancyMeasurementFragment arg$1;
    private final String arg$2;

    private PregnancyMeasurementFragment$$Lambda$3(PregnancyMeasurementFragment pregnancyMeasurementFragment, String string2) {
        this.arg$1 = pregnancyMeasurementFragment;
        this.arg$2 = string2;
    }

    public static Runnable lambdaFactory$(PregnancyMeasurementFragment pregnancyMeasurementFragment, String string2) {
        return new PregnancyMeasurementFragment$$Lambda$3(pregnancyMeasurementFragment, string2);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$saveMeasurement$2(this.arg$2);
    }
}

