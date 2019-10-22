/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBProgressOnboardingFragment$$Lambda$4
implements Runnable {
    private final QBProgressOnboardingFragment arg$1;

    private QBProgressOnboardingFragment$$Lambda$4(QBProgressOnboardingFragment qBProgressOnboardingFragment) {
        this.arg$1 = qBProgressOnboardingFragment;
    }

    public static Runnable lambdaFactory$(QBProgressOnboardingFragment qBProgressOnboardingFragment) {
        return new QBProgressOnboardingFragment$$Lambda$4(qBProgressOnboardingFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$proceed$3();
    }
}

