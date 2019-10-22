/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.MenuItem;
import com.getqardio.android.ui.fragment.SupportFragment;
import java.lang.invoke.LambdaForm;

final class SupportFragment$$Lambda$1
implements MenuItem.OnMenuItemClickListener {
    private final SupportFragment arg$1;

    private SupportFragment$$Lambda$1(SupportFragment supportFragment) {
        this.arg$1 = supportFragment;
    }

    public static MenuItem.OnMenuItemClickListener lambdaFactory$(SupportFragment supportFragment) {
        return new SupportFragment$$Lambda$1(supportFragment);
    }

    @LambdaForm.Hidden
    public boolean onMenuItemClick(MenuItem menuItem) {
        return this.arg$1.lambda$onCreateOptionsMenu$0(menuItem);
    }
}

