/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingConfirmProfileFragment$$Lambda$3
implements View.OnClickListener {
    private final QBOnboardingConfirmProfileFragment arg$1;

    private QBOnboardingConfirmProfileFragment$$Lambda$3(QBOnboardingConfirmProfileFragment qBOnboardingConfirmProfileFragment) {
        this.arg$1 = qBOnboardingConfirmProfileFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBOnboardingConfirmProfileFragment qBOnboardingConfirmProfileFragment) {
        return new QBOnboardingConfirmProfileFragment$$Lambda$3(qBOnboardingConfirmProfileFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$2(view);
    }
}

