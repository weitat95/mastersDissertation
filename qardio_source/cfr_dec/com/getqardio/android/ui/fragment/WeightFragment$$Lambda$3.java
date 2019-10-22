/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.WeightFragment;
import java.lang.invoke.LambdaForm;

final class WeightFragment$$Lambda$3
implements View.OnClickListener {
    private final WeightFragment arg$1;

    private WeightFragment$$Lambda$3(WeightFragment weightFragment) {
        this.arg$1 = weightFragment;
    }

    public static View.OnClickListener lambdaFactory$(WeightFragment weightFragment) {
        return new WeightFragment$$Lambda$3(weightFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$2(view);
    }
}

