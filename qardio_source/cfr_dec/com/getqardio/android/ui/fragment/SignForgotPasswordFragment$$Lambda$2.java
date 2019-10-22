/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.KeyEvent
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.getqardio.android.ui.fragment;

import android.view.KeyEvent;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment;
import java.lang.invoke.LambdaForm;

final class SignForgotPasswordFragment$$Lambda$2
implements TextView.OnEditorActionListener {
    private final SignForgotPasswordFragment arg$1;

    private SignForgotPasswordFragment$$Lambda$2(SignForgotPasswordFragment signForgotPasswordFragment) {
        this.arg$1 = signForgotPasswordFragment;
    }

    public static TextView.OnEditorActionListener lambdaFactory$(SignForgotPasswordFragment signForgotPasswordFragment) {
        return new SignForgotPasswordFragment$$Lambda$2(signForgotPasswordFragment);
    }

    @LambdaForm.Hidden
    public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
        return this.arg$1.lambda$setEmailTextChangedListener$1(textView, n, keyEvent);
    }
}

