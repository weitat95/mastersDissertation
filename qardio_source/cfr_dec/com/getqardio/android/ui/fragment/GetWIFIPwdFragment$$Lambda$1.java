/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.GetWIFIPwdFragment;
import java.lang.invoke.LambdaForm;

final class GetWIFIPwdFragment$$Lambda$1
implements View.OnClickListener {
    private final GetWIFIPwdFragment arg$1;

    private GetWIFIPwdFragment$$Lambda$1(GetWIFIPwdFragment getWIFIPwdFragment) {
        this.arg$1 = getWIFIPwdFragment;
    }

    public static View.OnClickListener lambdaFactory$(GetWIFIPwdFragment getWIFIPwdFragment) {
        return new GetWIFIPwdFragment$$Lambda$1(getWIFIPwdFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

