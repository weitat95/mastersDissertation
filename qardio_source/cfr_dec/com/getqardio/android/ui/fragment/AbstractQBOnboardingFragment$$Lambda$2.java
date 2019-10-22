/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class AbstractQBOnboardingFragment$$Lambda$2
implements Runnable {
    private final AbstractQBOnboardingFragment arg$1;
    private final int arg$2;

    private AbstractQBOnboardingFragment$$Lambda$2(AbstractQBOnboardingFragment abstractQBOnboardingFragment, int n) {
        this.arg$1 = abstractQBOnboardingFragment;
        this.arg$2 = n;
    }

    public static Runnable lambdaFactory$(AbstractQBOnboardingFragment abstractQBOnboardingFragment, int n) {
        return new AbstractQBOnboardingFragment$$Lambda$2(abstractQBOnboardingFragment, n);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onResume$1(this.arg$2);
    }
}

