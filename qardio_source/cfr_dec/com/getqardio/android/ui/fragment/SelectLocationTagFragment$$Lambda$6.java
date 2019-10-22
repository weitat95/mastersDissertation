/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.SelectLocationTagFragment;
import java.lang.invoke.LambdaForm;

final class SelectLocationTagFragment$$Lambda$6
implements View.OnClickListener {
    private final SelectLocationTagFragment arg$1;

    private SelectLocationTagFragment$$Lambda$6(SelectLocationTagFragment selectLocationTagFragment) {
        this.arg$1 = selectLocationTagFragment;
    }

    public static View.OnClickListener lambdaFactory$(SelectLocationTagFragment selectLocationTagFragment) {
        return new SelectLocationTagFragment$$Lambda$6(selectLocationTagFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$null$0(view);
    }
}

