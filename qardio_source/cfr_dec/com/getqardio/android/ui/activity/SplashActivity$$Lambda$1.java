/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.activity;

import com.getqardio.android.ui.activity.SplashActivity;
import java.lang.invoke.LambdaForm;

final class SplashActivity$$Lambda$1
implements Runnable {
    private final SplashActivity arg$1;

    private SplashActivity$$Lambda$1(SplashActivity splashActivity) {
        this.arg$1 = splashActivity;
    }

    public static Runnable lambdaFactory$(SplashActivity splashActivity) {
        return new SplashActivity$$Lambda$1(splashActivity);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onCreate$0();
    }
}

