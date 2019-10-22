/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOnModeHostFragment$$Lambda$1
implements Runnable {
    private final QBStepOnModeHostFragment arg$1;

    private QBStepOnModeHostFragment$$Lambda$1(QBStepOnModeHostFragment qBStepOnModeHostFragment) {
        this.arg$1 = qBStepOnModeHostFragment;
    }

    public static Runnable lambdaFactory$(QBStepOnModeHostFragment qBStepOnModeHostFragment) {
        return new QBStepOnModeHostFragment$$Lambda$1(qBStepOnModeHostFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        QBStepOnModeHostFragment.access$lambda$0(this.arg$1);
    }
}

