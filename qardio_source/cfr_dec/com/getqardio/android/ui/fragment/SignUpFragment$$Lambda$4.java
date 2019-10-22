/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SignUpFragment;
import java.lang.invoke.LambdaForm;

final class SignUpFragment$$Lambda$4
implements View.OnFocusChangeListener {
    private final SignUpFragment arg$1;

    private SignUpFragment$$Lambda$4(SignUpFragment signUpFragment) {
        this.arg$1 = signUpFragment;
    }

    public static View.OnFocusChangeListener lambdaFactory$(SignUpFragment signUpFragment) {
        return new SignUpFragment$$Lambda$4(signUpFragment);
    }

    @LambdaForm.Hidden
    public void onFocusChange(View view, boolean bl) {
        this.arg$1.lambda$onActivityCreated$3(view, bl);
    }
}

