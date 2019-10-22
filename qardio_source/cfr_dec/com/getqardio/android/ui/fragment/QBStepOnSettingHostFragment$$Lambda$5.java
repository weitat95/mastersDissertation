/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOnSettingHostFragment$$Lambda$5
implements Runnable {
    private final QBStepOnSettingHostFragment arg$1;

    private QBStepOnSettingHostFragment$$Lambda$5(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        this.arg$1 = qBStepOnSettingHostFragment;
    }

    public static Runnable lambdaFactory$(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        return new QBStepOnSettingHostFragment$$Lambda$5(qBStepOnSettingHostFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        QBStepOnSettingHostFragment.access$lambda$1(this.arg$1);
    }
}

