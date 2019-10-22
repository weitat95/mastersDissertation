/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.activity;

import android.view.View;
import com.getqardio.android.ui.activity.MainActivity;
import java.lang.invoke.LambdaForm;

final class MainActivity$$Lambda$4
implements View.OnClickListener {
    private final MainActivity arg$1;

    private MainActivity$$Lambda$4(MainActivity mainActivity) {
        this.arg$1 = mainActivity;
    }

    public static View.OnClickListener lambdaFactory$(MainActivity mainActivity) {
        return new MainActivity$$Lambda$4(mainActivity);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setupDrawerContent$3(view);
    }
}

