/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBOnboardingStepOnStateFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingStepOnStateFragment$$Lambda$1
implements Runnable {
    private final QBOnboardingStepOnStateFragment arg$1;

    private QBOnboardingStepOnStateFragment$$Lambda$1(QBOnboardingStepOnStateFragment qBOnboardingStepOnStateFragment) {
        this.arg$1 = qBOnboardingStepOnStateFragment;
    }

    public static Runnable lambdaFactory$(QBOnboardingStepOnStateFragment qBOnboardingStepOnStateFragment) {
        return new QBOnboardingStepOnStateFragment$$Lambda$1(qBOnboardingStepOnStateFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$handleConnectionError$0();
    }
}

