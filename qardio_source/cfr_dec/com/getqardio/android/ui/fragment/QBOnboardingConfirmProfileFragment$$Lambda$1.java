/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.RadioGroup;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment;
import java.lang.invoke.LambdaForm;

final class QBOnboardingConfirmProfileFragment$$Lambda$1
implements RadioGroup.OnCheckedChangeListener {
    private final QBOnboardingConfirmProfileFragment arg$1;

    private QBOnboardingConfirmProfileFragment$$Lambda$1(QBOnboardingConfirmProfileFragment qBOnboardingConfirmProfileFragment) {
        this.arg$1 = qBOnboardingConfirmProfileFragment;
    }

    public static RadioGroup.OnCheckedChangeListener lambdaFactory$(QBOnboardingConfirmProfileFragment qBOnboardingConfirmProfileFragment) {
        return new QBOnboardingConfirmProfileFragment$$Lambda$1(qBOnboardingConfirmProfileFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(RadioGroup radioGroup, int n) {
        this.arg$1.lambda$onViewCreated$0(radioGroup, n);
    }
}

