/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiFromBaseOnboardingFragment$$Lambda$4
implements SwipeRefreshLayout.OnRefreshListener {
    private final QBSelectWiFiFromBaseOnboardingFragment arg$1;

    private QBSelectWiFiFromBaseOnboardingFragment$$Lambda$4(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        this.arg$1 = qBSelectWiFiFromBaseOnboardingFragment;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        return new QBSelectWiFiFromBaseOnboardingFragment$$Lambda$4(qBSelectWiFiFromBaseOnboardingFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$onViewCreated$1();
    }
}

