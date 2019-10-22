/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$$Lambda$8
implements View.OnClickListener {
    private final QBSelectWiFiOnboardingFragment arg$1;

    private QBSelectWiFiOnboardingFragment$$Lambda$8(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        this.arg$1 = qBSelectWiFiOnboardingFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        return new QBSelectWiFiOnboardingFragment$$Lambda$8(qBSelectWiFiOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showPreseledtedWifDialog$7(view);
    }
}

