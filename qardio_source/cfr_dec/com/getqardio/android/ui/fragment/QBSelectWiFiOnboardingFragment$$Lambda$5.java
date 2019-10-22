/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$$Lambda$5
implements SwipeRefreshLayout.OnRefreshListener {
    private final QBSelectWiFiOnboardingFragment arg$1;

    private QBSelectWiFiOnboardingFragment$$Lambda$5(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        this.arg$1 = qBSelectWiFiOnboardingFragment;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        return new QBSelectWiFiOnboardingFragment$$Lambda$5(qBSelectWiFiOnboardingFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$onViewCreated$3();
    }
}

