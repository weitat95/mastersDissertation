/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.dialog;

import android.content.DialogInterface;
import com.getqardio.android.ui.dialog.QBFirmwareUpdateAvailable;
import java.lang.invoke.LambdaForm;

final class QBFirmwareUpdateAvailable$$Lambda$1
implements DialogInterface.OnClickListener {
    private final QBFirmwareUpdateAvailable arg$1;

    private QBFirmwareUpdateAvailable$$Lambda$1(QBFirmwareUpdateAvailable qBFirmwareUpdateAvailable) {
        this.arg$1 = qBFirmwareUpdateAvailable;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(QBFirmwareUpdateAvailable qBFirmwareUpdateAvailable) {
        return new QBFirmwareUpdateAvailable$$Lambda$1(qBFirmwareUpdateAvailable);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$onCreateDialog$0(dialogInterface, n);
    }
}

