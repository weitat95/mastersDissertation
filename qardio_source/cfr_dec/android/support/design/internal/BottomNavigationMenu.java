/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package android.support.design.internal;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.MenuItem;
import android.view.SubMenu;

public final class BottomNavigationMenu
extends MenuBuilder {
    public BottomNavigationMenu(Context context) {
        super(context);
    }

    @Override
    protected MenuItem addInternal(int n, int n2, int n3, CharSequence charSequence) {
        if (this.size() + 1 > 5) {
            throw new IllegalArgumentException("Maximum number of items supported by BottomNavigationView is 5. Limit can be checked with BottomNavigationView#getMaxItemCount()");
        }
        this.stopDispatchingItemsChanged();
        charSequence = super.addInternal(n, n2, n3, charSequence);
        if (charSequence instanceof MenuItemImpl) {
            ((MenuItemImpl)((Object)charSequence)).setExclusiveCheckable(true);
        }
        this.startDispatchingItemsChanged();
        return charSequence;
    }

    @Override
    public SubMenu addSubMenu(int n, int n2, int n3, CharSequence charSequence) {
        throw new UnsupportedOperationException("BottomNavigationView does not support submenus");
    }
}

