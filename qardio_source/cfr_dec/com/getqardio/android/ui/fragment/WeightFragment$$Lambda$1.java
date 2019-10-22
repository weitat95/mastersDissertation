/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.WeightFragment;
import java.lang.invoke.LambdaForm;

final class WeightFragment$$Lambda$1
implements Runnable {
    private final WeightFragment arg$1;

    private WeightFragment$$Lambda$1(WeightFragment weightFragment) {
        this.arg$1 = weightFragment;
    }

    public static Runnable lambdaFactory$(WeightFragment weightFragment) {
        return new WeightFragment$$Lambda$1(weightFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$doDisconnectAndRestart$0();
    }
}

