/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package com.getqardio.android.ui.fragment;

import android.widget.CompoundButton;
import com.getqardio.android.ui.fragment.StartBPFragment;
import java.lang.invoke.LambdaForm;

final class StartBPFragment$$Lambda$1
implements CompoundButton.OnCheckedChangeListener {
    private final StartBPFragment arg$1;

    private StartBPFragment$$Lambda$1(StartBPFragment startBPFragment) {
        this.arg$1 = startBPFragment;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(StartBPFragment startBPFragment) {
        return new StartBPFragment$$Lambda$1(startBPFragment);
    }

    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        this.arg$1.lambda$onActivityCreated$0(compoundButton, bl);
    }
}

