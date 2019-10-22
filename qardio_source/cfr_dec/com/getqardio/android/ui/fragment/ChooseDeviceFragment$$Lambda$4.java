/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.ChooseDeviceFragment;
import java.lang.invoke.LambdaForm;

final class ChooseDeviceFragment$$Lambda$4
implements Runnable {
    private final ChooseDeviceFragment arg$1;

    private ChooseDeviceFragment$$Lambda$4(ChooseDeviceFragment chooseDeviceFragment) {
        this.arg$1 = chooseDeviceFragment;
    }

    public static Runnable lambdaFactory$(ChooseDeviceFragment chooseDeviceFragment) {
        return new ChooseDeviceFragment$$Lambda$4(chooseDeviceFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$null$2();
    }
}

