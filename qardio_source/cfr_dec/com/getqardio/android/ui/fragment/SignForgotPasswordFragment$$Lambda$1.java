/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment;
import java.lang.invoke.LambdaForm;

final class SignForgotPasswordFragment$$Lambda$1
implements View.OnClickListener {
    private final SignForgotPasswordFragment arg$1;

    private SignForgotPasswordFragment$$Lambda$1(SignForgotPasswordFragment signForgotPasswordFragment) {
        this.arg$1 = signForgotPasswordFragment;
    }

    public static View.OnClickListener lambdaFactory$(SignForgotPasswordFragment signForgotPasswordFragment) {
        return new SignForgotPasswordFragment$$Lambda$1(signForgotPasswordFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onActivityCreated$0(view);
    }
}

