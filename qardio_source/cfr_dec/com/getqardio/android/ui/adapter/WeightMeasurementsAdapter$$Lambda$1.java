/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.adapter;

import android.view.View;
import com.getqardio.android.ui.adapter.WeightMeasurementsAdapter;
import java.lang.invoke.LambdaForm;

final class WeightMeasurementsAdapter$$Lambda$1
implements View.OnClickListener {
    private final WeightMeasurementsAdapter arg$1;

    private WeightMeasurementsAdapter$$Lambda$1(WeightMeasurementsAdapter weightMeasurementsAdapter) {
        this.arg$1 = weightMeasurementsAdapter;
    }

    public static View.OnClickListener lambdaFactory$(WeightMeasurementsAdapter weightMeasurementsAdapter) {
        return new WeightMeasurementsAdapter$$Lambda$1(weightMeasurementsAdapter);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$0(view);
    }
}

