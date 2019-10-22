/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 */
package com.getqardio.android.ui.fragment;

import android.content.DialogInterface;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$$Lambda$6
implements DialogInterface.OnDismissListener {
    private final QBSelectWiFiOnboardingFragment arg$1;

    private QBSelectWiFiOnboardingFragment$$Lambda$6(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        this.arg$1 = qBSelectWiFiOnboardingFragment;
    }

    public static DialogInterface.OnDismissListener lambdaFactory$(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        return new QBSelectWiFiOnboardingFragment$$Lambda$6(qBSelectWiFiOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onDismiss(DialogInterface dialogInterface) {
        this.arg$1.lambda$showPreseledtedWifDialog$5(dialogInterface);
    }
}

