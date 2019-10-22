/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SendHistoryFragment;
import java.lang.invoke.LambdaForm;

final class SendHistoryFragment$$Lambda$4
implements View.OnClickListener {
    private final SendHistoryFragment arg$1;

    private SendHistoryFragment$$Lambda$4(SendHistoryFragment sendHistoryFragment) {
        this.arg$1 = sendHistoryFragment;
    }

    public static View.OnClickListener lambdaFactory$(SendHistoryFragment sendHistoryFragment) {
        return new SendHistoryFragment$$Lambda$4(sendHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onActivityCreated$3(view);
    }
}

