/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBShowSwitchScreenFragment;
import java.lang.invoke.LambdaForm;

final class QBShowSwitchScreenFragment$$Lambda$1
implements View.OnClickListener {
    private final QBShowSwitchScreenFragment arg$1;

    private QBShowSwitchScreenFragment$$Lambda$1(QBShowSwitchScreenFragment qBShowSwitchScreenFragment) {
        this.arg$1 = qBShowSwitchScreenFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBShowSwitchScreenFragment qBShowSwitchScreenFragment) {
        return new QBShowSwitchScreenFragment$$Lambda$1(qBShowSwitchScreenFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

