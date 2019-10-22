/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.support.v7.app.AlertDialog;
import android.view.View;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiFromBaseOnboardingFragment$$Lambda$7
implements View.OnClickListener {
    private final QBSelectWiFiFromBaseOnboardingFragment arg$1;
    private final AlertDialog arg$2;

    private QBSelectWiFiFromBaseOnboardingFragment$$Lambda$7(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment, AlertDialog alertDialog) {
        this.arg$1 = qBSelectWiFiFromBaseOnboardingFragment;
        this.arg$2 = alertDialog;
    }

    public static View.OnClickListener lambdaFactory$(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment, AlertDialog alertDialog) {
        return new QBSelectWiFiFromBaseOnboardingFragment$$Lambda$7(qBSelectWiFiFromBaseOnboardingFragment, alertDialog);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showPreseledtedWifDialog$4(this.arg$2, view);
    }
}

