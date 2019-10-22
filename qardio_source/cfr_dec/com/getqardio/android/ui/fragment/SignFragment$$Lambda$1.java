/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SignFragment;
import java.lang.invoke.LambdaForm;

final class SignFragment$$Lambda$1
implements View.OnClickListener {
    private final SignFragment arg$1;

    private SignFragment$$Lambda$1(SignFragment signFragment) {
        this.arg$1 = signFragment;
    }

    public static View.OnClickListener lambdaFactory$(SignFragment signFragment) {
        return new SignFragment$$Lambda$1(signFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onActivityCreated$0(view);
    }
}

