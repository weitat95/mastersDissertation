/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewParent
 */
package com.getqardio.android.utils.ui;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;

public class UIUtils {
    /*
     * Enabled aggressive block sorting
     */
    public static void enableMenuItemAndChangeColor(MenuItem menuItem, boolean bl) {
        if (menuItem == null) {
            return;
        }
        Drawable drawable2 = menuItem.getIcon();
        int n = bl ? 255 : 178;
        drawable2.setAlpha(n);
        menuItem.setEnabled(bl);
    }

    public static TextInputLayout findTopTextInputLayout(View view) {
        View view2 = view;
        for (int i = 0; i < 2; ++i) {
            if (!((view2 = view2.getParent()) instanceof TextInputLayout)) continue;
            return (TextInputLayout)view2;
        }
        throw new IllegalArgumentException(view.toString());
    }
}

