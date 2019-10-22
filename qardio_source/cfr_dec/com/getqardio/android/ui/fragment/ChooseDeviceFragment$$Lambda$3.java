/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment;
import java.lang.invoke.LambdaForm;

final class ChooseDeviceFragment$$Lambda$3
implements View.OnClickListener {
    private final ChooseDeviceFragment arg$1;

    private ChooseDeviceFragment$$Lambda$3(ChooseDeviceFragment chooseDeviceFragment) {
        this.arg$1 = chooseDeviceFragment;
    }

    public static View.OnClickListener lambdaFactory$(ChooseDeviceFragment chooseDeviceFragment) {
        return new ChooseDeviceFragment$$Lambda$3(chooseDeviceFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initViews$4(view);
    }
}

