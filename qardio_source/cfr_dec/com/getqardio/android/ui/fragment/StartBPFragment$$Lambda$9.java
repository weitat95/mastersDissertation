/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.StartBPFragment;
import java.lang.invoke.LambdaForm;

final class StartBPFragment$$Lambda$9
implements Runnable {
    private final StartBPFragment arg$1;

    private StartBPFragment$$Lambda$9(StartBPFragment startBPFragment) {
        this.arg$1 = startBPFragment;
    }

    public static Runnable lambdaFactory$(StartBPFragment startBPFragment) {
        return new StartBPFragment$$Lambda$9(startBPFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$null$8();
    }
}

