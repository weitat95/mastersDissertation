/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateErrorFragment;
import java.lang.invoke.LambdaForm;

final class QBFirmwareUpdateErrorFragment$$Lambda$3
implements View.OnClickListener {
    private final QBFirmwareUpdateErrorFragment arg$1;

    private QBFirmwareUpdateErrorFragment$$Lambda$3(QBFirmwareUpdateErrorFragment qBFirmwareUpdateErrorFragment) {
        this.arg$1 = qBFirmwareUpdateErrorFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBFirmwareUpdateErrorFragment qBFirmwareUpdateErrorFragment) {
        return new QBFirmwareUpdateErrorFragment$$Lambda$3(qBFirmwareUpdateErrorFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$2(view);
    }
}

