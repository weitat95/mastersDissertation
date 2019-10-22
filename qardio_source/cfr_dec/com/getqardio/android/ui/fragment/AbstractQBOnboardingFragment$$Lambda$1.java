/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import java.lang.invoke.LambdaForm;

final class AbstractQBOnboardingFragment$$Lambda$1
implements View.OnClickListener {
    private final AbstractQBOnboardingFragment arg$1;

    private AbstractQBOnboardingFragment$$Lambda$1(AbstractQBOnboardingFragment abstractQBOnboardingFragment) {
        this.arg$1 = abstractQBOnboardingFragment;
    }

    public static View.OnClickListener lambdaFactory$(AbstractQBOnboardingFragment abstractQBOnboardingFragment) {
        return new AbstractQBOnboardingFragment$$Lambda$1(abstractQBOnboardingFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreateView$0(view);
    }
}

