/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.os.Handler;
import android.view.View;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment;
import java.lang.invoke.LambdaForm;

final class ChooseDeviceFragment$$Lambda$1
implements View.OnClickListener {
    private final ChooseDeviceFragment arg$1;
    private final Handler arg$2;

    private ChooseDeviceFragment$$Lambda$1(ChooseDeviceFragment chooseDeviceFragment, Handler handler) {
        this.arg$1 = chooseDeviceFragment;
        this.arg$2 = handler;
    }

    public static View.OnClickListener lambdaFactory$(ChooseDeviceFragment chooseDeviceFragment, Handler handler) {
        return new ChooseDeviceFragment$$Lambda$1(chooseDeviceFragment, handler);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initViews$1(this.arg$2, view);
    }
}

