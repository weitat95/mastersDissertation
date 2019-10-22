/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SignUpFragment;
import java.lang.invoke.LambdaForm;

final class SignUpFragment$$Lambda$6
implements View.OnClickListener {
    private final SignUpFragment arg$1;

    private SignUpFragment$$Lambda$6(SignUpFragment signUpFragment) {
        this.arg$1 = signUpFragment;
    }

    public static View.OnClickListener lambdaFactory$(SignUpFragment signUpFragment) {
        return new SignUpFragment$$Lambda$6(signUpFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onActivityCreated$5(view);
    }
}

