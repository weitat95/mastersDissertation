/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Menu
 */
package android.support.design.internal;

import android.content.Context;
import android.support.design.internal.NavigationMenu;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.Menu;

public class NavigationSubMenu
extends SubMenuBuilder {
    public NavigationSubMenu(Context context, NavigationMenu navigationMenu, MenuItemImpl menuItemImpl) {
        super(context, navigationMenu, menuItemImpl);
    }

    @Override
    public void onItemsChanged(boolean bl) {
        super.onItemsChanged(bl);
        ((MenuBuilder)this.getParentMenu()).onItemsChanged(bl);
    }
}

