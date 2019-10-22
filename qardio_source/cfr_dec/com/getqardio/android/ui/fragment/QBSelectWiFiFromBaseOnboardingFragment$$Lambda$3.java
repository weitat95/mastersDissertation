/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnKeyListener
 */
package com.getqardio.android.ui.fragment;

import android.view.KeyEvent;
import android.view.View;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiFromBaseOnboardingFragment$$Lambda$3
implements View.OnKeyListener {
    private final QBSelectWiFiFromBaseOnboardingFragment arg$1;

    private QBSelectWiFiFromBaseOnboardingFragment$$Lambda$3(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        this.arg$1 = qBSelectWiFiFromBaseOnboardingFragment;
    }

    public static View.OnKeyListener lambdaFactory$(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        return new QBSelectWiFiFromBaseOnboardingFragment$$Lambda$3(qBSelectWiFiFromBaseOnboardingFragment);
    }

    @LambdaForm.Hidden
    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        return this.arg$1.lambda$onViewCreated$0(view, n, keyEvent);
    }
}

