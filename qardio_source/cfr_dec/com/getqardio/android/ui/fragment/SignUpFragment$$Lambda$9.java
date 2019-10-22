/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.fragment;

import com.getqardio.android.ui.fragment.SignUpFragment;
import java.lang.invoke.LambdaForm;

final class SignUpFragment$$Lambda$9
implements Runnable {
    private final SignUpFragment arg$1;

    private SignUpFragment$$Lambda$9(SignUpFragment signUpFragment) {
        this.arg$1 = signUpFragment;
    }

    public static Runnable lambdaFactory$(SignUpFragment signUpFragment) {
        return new SignUpFragment$$Lambda$9(signUpFragment);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$null$8();
    }
}

