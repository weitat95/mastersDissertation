/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOnSettingHostFragment$$Lambda$2
implements Runnable {
    private final QBStepOnSettingHostFragment arg$1;

    private QBStepOnSettingHostFragment$$Lambda$2(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        this.arg$1 = qBStepOnSettingHostFragment;
    }

    public static Runnable lambdaFactory$(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        return new QBStepOnSettingHostFragment$$Lambda$2(qBStepOnSettingHostFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$handleConnection$1();
    }
}

