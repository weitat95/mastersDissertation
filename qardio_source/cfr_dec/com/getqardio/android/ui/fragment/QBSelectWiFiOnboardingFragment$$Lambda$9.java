/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$$Lambda$9
implements Runnable {
    private final QBSelectWiFiOnboardingFragment arg$1;

    private QBSelectWiFiOnboardingFragment$$Lambda$9(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        this.arg$1 = qBSelectWiFiOnboardingFragment;
    }

    public static Runnable lambdaFactory$(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        return new QBSelectWiFiOnboardingFragment$$Lambda$9(qBSelectWiFiOnboardingFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$null$4();
    }
}

