/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.CompoundButton;
import com.getqardio.android.ui.fragment.SendHistoryFragment;
import java.lang.invoke.LambdaForm;

final class SendHistoryFragment$$Lambda$3
implements CompoundButton.OnCheckedChangeListener {
    private final SendHistoryFragment arg$1;

    private SendHistoryFragment$$Lambda$3(SendHistoryFragment sendHistoryFragment) {
        this.arg$1 = sendHistoryFragment;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(SendHistoryFragment sendHistoryFragment) {
        return new SendHistoryFragment$$Lambda$3(sendHistoryFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        this.arg$1.lambda$onActivityCreated$2(compoundButton, bl);
    }
}

