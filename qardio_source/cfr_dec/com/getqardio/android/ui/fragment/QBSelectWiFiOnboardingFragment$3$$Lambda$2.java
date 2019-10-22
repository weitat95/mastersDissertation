/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$3$$Lambda$2
implements Runnable {
    private final QBSelectWiFiOnboardingFragment.3 arg$1;

    private QBSelectWiFiOnboardingFragment$3$$Lambda$2(QBSelectWiFiOnboardingFragment.3 var1_1) {
        this.arg$1 = var1_1;
    }

    public static Runnable lambdaFactory$(QBSelectWiFiOnboardingFragment.3 var0) {
        return new QBSelectWiFiOnboardingFragment$3$$Lambda$2(var0);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onReceive$1();
    }
}

