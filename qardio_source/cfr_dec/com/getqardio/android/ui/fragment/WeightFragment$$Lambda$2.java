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

final class WeightFragment$$Lambda$2
implements View.OnClickListener {
    private final WeightFragment arg$1;

    private WeightFragment$$Lambda$2(WeightFragment weightFragment) {
        this.arg$1 = weightFragment;
    }

    public static View.OnClickListener lambdaFactory$(WeightFragment weightFragment) {
        return new WeightFragment$$Lambda$2(weightFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$1(view);
    }
}

