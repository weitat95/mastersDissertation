/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package android.support.v7.view.menu;

import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class MenuAdapter
extends BaseAdapter {
    static final int ITEM_LAYOUT = R.layout.abc_popup_menu_item_layout;
    MenuBuilder mAdapterMenu;
    private int mExpandedIndex = -1;
    private boolean mForceShowIcon;
    private final LayoutInflater mInflater;
    private final boolean mOverflowOnly;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater layoutInflater, boolean bl) {
        this.mOverflowOnly = bl;
        this.mInflater = layoutInflater;
        this.mAdapterMenu = menuBuilder;
        this.findExpandedIndex();
    }

    void findExpandedIndex() {
        MenuItemImpl menuItemImpl = this.mAdapterMenu.getExpandedItem();
        if (menuItemImpl != null) {
            ArrayList<MenuItemImpl> arrayList = this.mAdapterMenu.getNonActionItems();
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                if (arrayList.get(i) != menuItemImpl) continue;
                this.mExpandedIndex = i;
                return;
            }
        }
        this.mExpandedIndex = -1;
    }

    public MenuBuilder getAdapterMenu() {
        return this.mAdapterMenu;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getCount() {
        ArrayList<MenuItemImpl> arrayList = this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
        if (this.mExpandedIndex < 0) {
            return arrayList.size();
        }
        return arrayList.size() - 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MenuItemImpl getItem(int n) {
        ArrayList<MenuItemImpl> arrayList = this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
        int n2 = n;
        if (this.mExpandedIndex >= 0) {
            n2 = n;
            if (n >= this.mExpandedIndex) {
                n2 = n + 1;
            }
        }
        return arrayList.get(n2);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View object, ViewGroup viewGroup) {
        View view = object;
        if (object == null) {
            view = this.mInflater.inflate(ITEM_LAYOUT, viewGroup, false);
        }
        object = (MenuView.ItemView)view;
        if (this.mForceShowIcon) {
            ((ListMenuItemView)view).setForceShowIcon(true);
        }
        object.initialize(this.getItem(n), 0);
        return view;
    }

    public void notifyDataSetChanged() {
        this.findExpandedIndex();
        super.notifyDataSetChanged();
    }

    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
    }
}

