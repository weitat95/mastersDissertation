/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.StartBPFragment;
import java.lang.invoke.LambdaForm;

final class StartBPFragment$$Lambda$8
implements Runnable {
    private final StartBPFragment arg$1;

    private StartBPFragment$$Lambda$8(StartBPFragment startBPFragment) {
        this.arg$1 = startBPFragment;
    }

    public static Runnable lambdaFactory$(StartBPFragment startBPFragment) {
        return new StartBPFragment$$Lambda$8(startBPFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$startMeasurementActivity$9();
    }
}

