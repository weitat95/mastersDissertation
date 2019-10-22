/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBOnboardingReadyFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingReadyFragment$$Lambda$1
implements View.OnClickListener {
    private final QBOnboardingReadyFragment arg$1;

    private QBOnboardingReadyFragment$$Lambda$1(QBOnboardingReadyFragment qBOnboardingReadyFragment) {
        this.arg$1 = qBOnboardingReadyFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBOnboardingReadyFragment qBOnboardingReadyFragment) {
        return new QBOnboardingReadyFragment$$Lambda$1(qBOnboardingReadyFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

