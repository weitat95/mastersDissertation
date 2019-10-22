/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBProgressOnboardingFragment$$Lambda$1
implements Runnable {
    private final QBProgressOnboardingFragment arg$1;
    private final boolean arg$2;

    private QBProgressOnboardingFragment$$Lambda$1(QBProgressOnboardingFragment qBProgressOnboardingFragment, boolean bl) {
        this.arg$1 = qBProgressOnboardingFragment;
        this.arg$2 = bl;
    }

    public static Runnable lambdaFactory$(QBProgressOnboardingFragment qBProgressOnboardingFragment, boolean bl) {
        return new QBProgressOnboardingFragment$$Lambda$1(qBProgressOnboardingFragment, bl);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onboardingFinished$0(this.arg$2);
    }
}

