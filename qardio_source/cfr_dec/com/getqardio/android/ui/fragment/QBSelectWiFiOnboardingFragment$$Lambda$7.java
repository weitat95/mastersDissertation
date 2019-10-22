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
import com.getqardio.android.utils.wifi.WifiAp;
import java.lang.invoke.LambdaForm;

final class QBSelectWiFiOnboardingFragment$$Lambda$7
implements View.OnClickListener {
    private final QBSelectWiFiOnboardingFragment arg$1;
    private final WifiAp arg$2;

    private QBSelectWiFiOnboardingFragment$$Lambda$7(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment, WifiAp wifiAp) {
        this.arg$1 = qBSelectWiFiOnboardingFragment;
        this.arg$2 = wifiAp;
    }

    public static View.OnClickListener lambdaFactory$(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment, WifiAp wifiAp) {
        return new QBSelectWiFiOnboardingFragment$$Lambda$7(qBSelectWiFiOnboardingFragment, wifiAp);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showPreseledtedWifDialog$6(this.arg$2, view);
    }
}

