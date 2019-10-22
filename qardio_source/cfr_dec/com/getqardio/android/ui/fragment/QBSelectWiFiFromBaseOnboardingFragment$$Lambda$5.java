/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiFromBaseOnboardingFragment$$Lambda$5
implements View.OnClickListener {
    private final QBSelectWiFiFromBaseOnboardingFragment arg$1;

    private QBSelectWiFiFromBaseOnboardingFragment$$Lambda$5(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        this.arg$1 = qBSelectWiFiFromBaseOnboardingFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        return new QBSelectWiFiFromBaseOnboardingFragment$$Lambda$5(qBSelectWiFiFromBaseOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$2(view);
    }
}

