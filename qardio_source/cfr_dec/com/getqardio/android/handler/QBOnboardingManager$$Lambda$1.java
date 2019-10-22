/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.handler;

import com.getqardio.android.handler.QBOnboardingManager;
import java.lang.invoke.LambdaForm;

final class QBOnboardingManager$$Lambda$1
implements Runnable {
    private final QBOnboardingManager arg$1;
    private final long arg$2;
    private final String arg$3;

    private QBOnboardingManager$$Lambda$1(QBOnboardingManager qBOnboardingManager, long l, String string2) {
        this.arg$1 = qBOnboardingManager;
        this.arg$2 = l;
        this.arg$3 = string2;
    }

    public static Runnable lambdaFactory$(QBOnboardingManager qBOnboardingManager, long l, String string2) {
        return new QBOnboardingManager$$Lambda$1(qBOnboardingManager, l, string2);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$storeUniqueId$0(this.arg$2, this.arg$3);
    }
}

