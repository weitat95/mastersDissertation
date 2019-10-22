/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOnSettingHostFragment$$Lambda$7
implements Runnable {
    private final QBStepOnSettingHostFragment arg$1;
    private final String arg$2;
    private final String arg$3;

    private QBStepOnSettingHostFragment$$Lambda$7(QBStepOnSettingHostFragment qBStepOnSettingHostFragment, String string2, String string3) {
        this.arg$1 = qBStepOnSettingHostFragment;
        this.arg$2 = string2;
        this.arg$3 = string3;
    }

    public static Runnable lambdaFactory$(QBStepOnSettingHostFragment qBStepOnSettingHostFragment, String string2, String string3) {
        return new QBStepOnSettingHostFragment$$Lambda$7(qBStepOnSettingHostFragment, string2, string3);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$changeWifi$4(this.arg$2, this.arg$3);
    }
}

