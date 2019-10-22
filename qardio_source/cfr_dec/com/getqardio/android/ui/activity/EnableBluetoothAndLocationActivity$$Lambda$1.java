/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.ui.activity;

import android.content.DialogInterface;
import com.getqardio.android.ui.activity.EnableBluetoothAndLocationActivity;
import java.lang.invoke.LambdaForm;

final class EnableBluetoothAndLocationActivity$$Lambda$1
implements DialogInterface.OnClickListener {
    private final EnableBluetoothAndLocationActivity arg$1;

    private EnableBluetoothAndLocationActivity$$Lambda$1(EnableBluetoothAndLocationActivity enableBluetoothAndLocationActivity) {
        this.arg$1 = enableBluetoothAndLocationActivity;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(EnableBluetoothAndLocationActivity enableBluetoothAndLocationActivity) {
        return new EnableBluetoothAndLocationActivity$$Lambda$1(enableBluetoothAndLocationActivity);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$explainUserWhyTheAppNeedLocationDialog$0(dialogInterface, n);
    }
}

