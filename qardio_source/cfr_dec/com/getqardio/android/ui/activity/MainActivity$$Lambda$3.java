/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.MainActivity;
import java.lang.invoke.LambdaForm;

final class MainActivity$$Lambda$3
implements NavigationView.OnNavigationItemSelectedListener {
    private final MainActivity arg$1;

    private MainActivity$$Lambda$3(MainActivity mainActivity) {
        this.arg$1 = mainActivity;
    }

    public static NavigationView.OnNavigationItemSelectedListener lambdaFactory$(MainActivity mainActivity) {
        return new MainActivity$$Lambda$3(mainActivity);
    }

    @LambdaForm.Hidden
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return this.arg$1.lambda$setupDrawerContent$2(menuItem);
    }
}

