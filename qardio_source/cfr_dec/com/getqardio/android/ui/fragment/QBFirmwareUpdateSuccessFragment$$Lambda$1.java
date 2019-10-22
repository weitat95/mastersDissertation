/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateSuccessFragment;
import java.lang.invoke.LambdaForm;

final class QBFirmwareUpdateSuccessFragment$$Lambda$1
implements View.OnClickListener {
    private final QBFirmwareUpdateSuccessFragment arg$1;

    private QBFirmwareUpdateSuccessFragment$$Lambda$1(QBFirmwareUpdateSuccessFragment qBFirmwareUpdateSuccessFragment) {
        this.arg$1 = qBFirmwareUpdateSuccessFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBFirmwareUpdateSuccessFragment qBFirmwareUpdateSuccessFragment) {
        return new QBFirmwareUpdateSuccessFragment$$Lambda$1(qBFirmwareUpdateSuccessFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}

