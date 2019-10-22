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

final class QBSelectWiFiOnboardingFragment$$Lambda$1
implements View.OnClickListener {
    private static final QBSelectWiFiOnboardingFragment$$Lambda$1 instance = new QBSelectWiFiOnboardingFragment$$Lambda$1();

    private QBSelectWiFiOnboardingFragment$$Lambda$1() {
    }

    public static View.OnClickListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        QBSelectWiFiOnboardingFragment.lambda$new$0(view);
    }
}

