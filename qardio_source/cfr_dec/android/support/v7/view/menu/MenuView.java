/*
 * Decompiled with CFR 0.147.
 */
package android.support.v7.view.menu;

import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;

public interface MenuView {
    public void initialize(MenuBuilder var1);

    public static interface ItemView {
        public MenuItemImpl getItemData();

        public void initialize(MenuItemImpl var1, int var2);

        public boolean prefersCondensedTitle();
    }

}

