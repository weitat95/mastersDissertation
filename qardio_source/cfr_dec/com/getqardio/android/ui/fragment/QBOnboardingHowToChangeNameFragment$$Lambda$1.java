/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBOnboardingHowToChangeNameFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingHowToChangeNameFragment$$Lambda$1
implements View.OnClickListener {
    private final QBOnboardingHowToChangeNameFragment arg$1;

    private QBOnboardingHowToChangeNameFragment$$Lambda$1(QBOnboardingHowToChangeNameFragment qBOnboardingHowToChangeNameFragment) {
        this.arg$1 = qBOnboardingHowToChangeNameFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBOnboardingHowToChangeNameFragment qBOnboardingHowToChangeNameFragment) {
        return new QBOnboardingHowToChangeNameFragment$$Lambda$1(qBOnboardingHowToChangeNameFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

