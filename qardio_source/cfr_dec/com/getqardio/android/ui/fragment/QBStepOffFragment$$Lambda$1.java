/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.QBStepOffFragment;
import java.lang.invoke.LambdaForm;

final class QBStepOffFragment$$Lambda$1
implements View.OnClickListener {
    private final QBStepOffFragment arg$1;

    private QBStepOffFragment$$Lambda$1(QBStepOffFragment qBStepOffFragment) {
        this.arg$1 = qBStepOffFragment;
    }

    public static View.OnClickListener lambdaFactory$(QBStepOffFragment qBStepOffFragment) {
        return new QBStepOffFragment$$Lambda$1(qBStepOffFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

