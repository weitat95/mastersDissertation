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
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$$Lambda$2
implements AdapterView.OnItemClickListener {
    private final QBSelectWiFiOnboardingFragment arg$1;

    private QBSelectWiFiOnboardingFragment$$Lambda$2(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        this.arg$1 = qBSelectWiFiOnboardingFragment;
    }

    public static AdapterView.OnItemClickListener lambdaFactory$(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        return new QBSelectWiFiOnboardingFragment$$Lambda$2(qBSelectWiFiOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.arg$1.lambda$new$8(adapterView, view, n, l);
    }
}

