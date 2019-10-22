/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOnSettingHostFragment$6$$Lambda$1
implements Runnable {
    private final QBStepOnSettingHostFragment.6 arg$1;

    private QBStepOnSettingHostFragment$6$$Lambda$1(QBStepOnSettingHostFragment.6 var1_1) {
        this.arg$1 = var1_1;
    }

    public static Runnable lambdaFactory$(QBStepOnSettingHostFragment.6 var0) {
        return new QBStepOnSettingHostFragment$6$$Lambda$1(var0);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$onReceive$0();
    }
}

