/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.MenuItem
 */
package com.getqardio.android.utils;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

public class UIUtils {
    public static int getNavigationViewCheckedItemId(NavigationView navigationView) {
        navigationView = navigationView.getMenu();
        for (int i = 0; i < navigationView.size(); ++i) {
            MenuItem menuItem = navigationView.getItem(i);
            if (!menuItem.isChecked()) continue;
            return menuItem.getItemId();
        }
        return -1;
    }
}

