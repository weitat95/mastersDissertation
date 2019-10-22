/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.activity;

import android.view.View;
import com.getqardio.android.ui.activity.SignActivity;
import java.lang.invoke.LambdaForm;

final class SignActivity$$Lambda$4
implements View.OnClickListener {
    private final SignActivity arg$1;

    private SignActivity$$Lambda$4(SignActivity signActivity) {
        this.arg$1 = signActivity;
    }

    public static View.OnClickListener lambdaFactory$(SignActivity signActivity) {
        return new SignActivity$$Lambda$4(signActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$updateBottomPanel$3(view);
    }
}

