/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SendHistoryFragment;
import java.lang.invoke.LambdaForm;

final class SendHistoryFragment$$Lambda$1
implements View.OnFocusChangeListener {
    private final SendHistoryFragment arg$1;

    private SendHistoryFragment$$Lambda$1(SendHistoryFragment sendHistoryFragment) {
        this.arg$1 = sendHistoryFragment;
    }

    public static View.OnFocusChangeListener lambdaFactory$(SendHistoryFragment sendHistoryFragment) {
        return new SendHistoryFragment$$Lambda$1(sendHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onFocusChange(View view, boolean bl) {
        this.arg$1.lambda$onActivityCreated$0(view, bl);
    }
}

