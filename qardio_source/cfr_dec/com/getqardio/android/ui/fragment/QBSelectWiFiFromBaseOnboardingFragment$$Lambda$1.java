/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiFromBaseOnboardingFragment$$Lambda$1
implements AdapterView.OnItemClickListener {
    private final QBSelectWiFiFromBaseOnboardingFragment arg$1;

    private QBSelectWiFiFromBaseOnboardingFragment$$Lambda$1(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        this.arg$1 = qBSelectWiFiFromBaseOnboardingFragment;
    }

    public static AdapterView.OnItemClickListener lambdaFactory$(QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment) {
        return new QBSelectWiFiFromBaseOnboardingFragment$$Lambda$1(qBSelectWiFiFromBaseOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.arg$1.lambda$new$5(adapterView, view, n, l);
    }
}

