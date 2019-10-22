/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$3$$Lambda$1
implements SwipeRefreshLayout.OnRefreshListener {
    private final QBSelectWiFiOnboardingFragment.3 arg$1;

    private QBSelectWiFiOnboardingFragment$3$$Lambda$1(QBSelectWiFiOnboardingFragment.3 var1_1) {
        this.arg$1 = var1_1;
    }

    public static SwipeRefreshLayout.OnRefreshListener lambdaFactory$(QBSelectWiFiOnboardingFragment.3 var0) {
        return new QBSelectWiFiOnboardingFragment$3$$Lambda$1(var0);
    }

    @LambdaForm.Hidden
    @Override
    public void onRefresh() {
        this.arg$1.lambda$onReceive$0();
    }
}

