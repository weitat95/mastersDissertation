/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBOnboardingGetReadyStateFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingGetReadyStateFragment$$Lambda$1
implements View.OnClickListener {
    private final QBOnboardingGetReadyStateFragment arg$1;

    private QBOnboardingGetReadyStateFragment$$Lambda$1(QBOnboardingGetReadyStateFragment qBOnboardingGetReadyStateFragment) {
        this.arg$1 = qBOnboardingGetReadyStateFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBOnboardingGetReadyStateFragment qBOnboardingGetReadyStateFragment) {
        return new QBOnboardingGetReadyStateFragment$$Lambda$1(qBOnboardingGetReadyStateFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

