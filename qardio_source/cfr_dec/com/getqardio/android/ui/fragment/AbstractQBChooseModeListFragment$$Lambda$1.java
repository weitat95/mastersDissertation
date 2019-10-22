/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment;
import java.lang.invoke.LambdaForm;

final class AbstractQBChooseModeListFragment$$Lambda$1
implements View.OnClickListener {
    private final AbstractQBChooseModeListFragment arg$1;

    private AbstractQBChooseModeListFragment$$Lambda$1(AbstractQBChooseModeListFragment abstractQBChooseModeListFragment) {
        this.arg$1 = abstractQBChooseModeListFragment;
    }

    public static View.OnClickListener lambdaFactory$(AbstractQBChooseModeListFragment abstractQBChooseModeListFragment) {
        return new AbstractQBChooseModeListFragment$$Lambda$1(abstractQBChooseModeListFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}

