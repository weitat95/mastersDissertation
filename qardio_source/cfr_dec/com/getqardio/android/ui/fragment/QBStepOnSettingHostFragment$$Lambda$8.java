/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOnSettingHostFragment$$Lambda$8
implements View.OnClickListener {
    private final QBStepOnSettingHostFragment arg$1;
    private final String arg$2;

    private QBStepOnSettingHostFragment$$Lambda$8(QBStepOnSettingHostFragment qBStepOnSettingHostFragment, String string2) {
        this.arg$1 = qBStepOnSettingHostFragment;
        this.arg$2 = string2;
    }

    public static View.OnClickListener lambdaFactory$(QBStepOnSettingHostFragment qBStepOnSettingHostFragment, String string2) {
        return new QBStepOnSettingHostFragment$$Lambda$8(qBStepOnSettingHostFragment, string2);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$enableUpdateFirmware$5(this.arg$2, view);
    }
}

