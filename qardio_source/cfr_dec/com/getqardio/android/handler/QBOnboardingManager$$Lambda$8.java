/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.handler;

import com.getqardio.android.handler.QBOnboardingManager;
import java.lang.invoke.LambdaForm;

final class QBOnboardingManager$$Lambda$8
implements Runnable {
    private final QBOnboardingManager arg$1;

    private QBOnboardingManager$$Lambda$8(QBOnboardingManager qBOnboardingManager) {
        this.arg$1 = qBOnboardingManager;
    }

    public static Runnable lambdaFactory$(QBOnboardingManager qBOnboardingManager) {
        return new QBOnboardingManager$$Lambda$8(qBOnboardingManager);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$writeDisableConfigurationModeDelayed$7();
    }
}

