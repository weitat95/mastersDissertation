/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.CompoundButton;
import com.getqardio.android.ui.fragment.SignUpFragment;
import java.lang.invoke.LambdaForm;

final class SignUpFragment$$Lambda$1
implements CompoundButton.OnCheckedChangeListener {
    private final SignUpFragment arg$1;

    private SignUpFragment$$Lambda$1(SignUpFragment signUpFragment) {
        this.arg$1 = signUpFragment;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(SignUpFragment signUpFragment) {
        return new SignUpFragment$$Lambda$1(signUpFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        this.arg$1.lambda$onCreateView$0(compoundButton, bl);
    }
}

