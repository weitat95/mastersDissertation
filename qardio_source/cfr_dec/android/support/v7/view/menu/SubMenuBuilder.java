/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class SubMenuBuilder
extends MenuBuilder
implements SubMenu {
    private MenuItemImpl mItem;
    private MenuBuilder mParentMenu;

    public SubMenuBuilder(Context context, MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        super(context);
        this.mParentMenu = menuBuilder;
        this.mItem = menuItemImpl;
    }

    @Override
    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.collapseItemActionView(menuItemImpl);
    }

    @Override
    boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return super.dispatchMenuItemSelected(menuBuilder, menuItem) || this.mParentMenu.dispatchMenuItemSelected(menuBuilder, menuItem);
    }

    @Override
    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.expandItemActionView(menuItemImpl);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String getActionViewStatesKey() {
        if (this.mItem == null) return null;
        int n = this.mItem.getItemId();
        if (n != 0) return super.getActionViewStatesKey() + ":" + n;
        return null;
    }

    public MenuItem getItem() {
        return this.mItem;
    }

    public Menu getParentMenu() {
        return this.mParentMenu;
    }

    @Override
    public MenuBuilder getRootMenu() {
        return this.mParentMenu.getRootMenu();
    }

    @Override
    public boolean isQwertyMode() {
        return this.mParentMenu.isQwertyMode();
    }

    @Override
    public boolean isShortcutsVisible() {
        return this.mParentMenu.isShortcutsVisible();
    }

    @Override
    public void setCallback(MenuBuilder.Callback callback) {
        this.mParentMenu.setCallback(callback);
    }

    public SubMenu setHeaderIcon(int n) {
        return (SubMenu)super.setHeaderIconInt(n);
    }

    public SubMenu setHeaderIcon(Drawable drawable2) {
        return (SubMenu)super.setHeaderIconInt(drawable2);
    }

    public SubMenu setHeaderTitle(int n) {
        return (SubMenu)super.setHeaderTitleInt(n);
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        return (SubMenu)super.setHeaderTitleInt(charSequence);
    }

    public SubMenu setHeaderView(View view) {
        return (SubMenu)super.setHeaderViewInt(view);
    }

    public SubMenu setIcon(int n) {
        this.mItem.setIcon(n);
        return this;
    }

    public SubMenu setIcon(Drawable drawable2) {
        this.mItem.setIcon(drawable2);
        return this;
    }

    @Override
    public void setQwertyMode(boolean bl) {
        this.mParentMenu.setQwertyMode(bl);
    }
}

