/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.getqardio.android.ui.fragment;

import android.view.MotionEvent;
import android.view.View;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment;
import java.lang.invoke.LambdaForm;

final class SignForgotPasswordFragment$$Lambda$3
implements View.OnTouchListener {
    private final SignForgotPasswordFragment arg$1;

    private SignForgotPasswordFragment$$Lambda$3(SignForgotPasswordFragment signForgotPasswordFragment) {
        this.arg$1 = signForgotPasswordFragment;
    }

    public static View.OnTouchListener lambdaFactory$(SignForgotPasswordFragment signForgotPasswordFragment) {
        return new SignForgotPasswordFragment$$Lambda$3(signForgotPasswordFragment);
    }

    @LambdaForm.Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$setEmailTextChangedListener$2(view, motionEvent);
    }
}

