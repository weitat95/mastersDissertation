/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.StartBPFragment;
import java.lang.invoke.LambdaForm;

final class StartBPFragment$$Lambda$2
implements View.OnClickListener {
    private final StartBPFragment arg$1;

    private StartBPFragment$$Lambda$2(StartBPFragment startBPFragment) {
        this.arg$1 = startBPFragment;
    }

    public static View.OnClickListener lambdaFactory$(StartBPFragment startBPFragment) {
        return new StartBPFragment$$Lambda$2(startBPFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onActivityCreated$1(view);
    }
}

