/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.FrameLayout
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.ShowableListMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

abstract class MenuPopup
implements MenuPresenter,
ShowableListMenu,
AdapterView.OnItemClickListener {
    private Rect mEpicenterBounds;

    MenuPopup() {
    }

    protected static int measureIndividualMenuWidth(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int n) {
        int n2 = 0;
        ViewGroup viewGroup2 = null;
        int n3 = 0;
        int n4 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n5 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        int n6 = listAdapter.getCount();
        ViewGroup viewGroup3 = viewGroup;
        viewGroup = viewGroup2;
        for (int i = 0; i < n6; ++i) {
            int n7 = listAdapter.getItemViewType(i);
            int n8 = n3;
            if (n7 != n3) {
                n8 = n7;
                viewGroup = null;
            }
            viewGroup2 = viewGroup3;
            if (viewGroup3 == null) {
                viewGroup2 = new FrameLayout(context);
            }
            viewGroup = listAdapter.getView(i, (View)viewGroup, viewGroup2);
            viewGroup.measure(n4, n5);
            n3 = viewGroup.getMeasuredWidth();
            if (n3 >= n) {
                return n;
            }
            n7 = n2;
            if (n3 > n2) {
                n7 = n3;
            }
            n3 = n8;
            n2 = n7;
            viewGroup3 = viewGroup2;
        }
        return n2;
    }

    protected static boolean shouldPreserveIconSpacing(MenuBuilder menuBuilder) {
        boolean bl = false;
        int n = menuBuilder.size();
        int n2 = 0;
        do {
            block4: {
                boolean bl2;
                block3: {
                    bl2 = bl;
                    if (n2 >= n) break block3;
                    MenuItem menuItem = menuBuilder.getItem(n2);
                    if (!menuItem.isVisible() || menuItem.getIcon() == null) break block4;
                    bl2 = true;
                }
                return bl2;
            }
            ++n2;
        } while (true);
    }

    protected static MenuAdapter toMenuAdapter(ListAdapter listAdapter) {
        if (listAdapter instanceof HeaderViewListAdapter) {
            return (MenuAdapter)((HeaderViewListAdapter)listAdapter).getWrappedAdapter();
        }
        return (MenuAdapter)listAdapter;
    }

    public abstract void addMenu(MenuBuilder var1);

    protected boolean closeMenuOnSubMenuOpened() {
        return true;
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public Rect getEpicenterBounds() {
        return this.mEpicenterBounds;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onItemClick(AdapterView<?> object, View view, int n, long l) {
        view = (ListAdapter)object.getAdapter();
        object = MenuPopup.toMenuAdapter((ListAdapter)view).mAdapterMenu;
        view = (MenuItem)view.getItem(n);
        n = this.closeMenuOnSubMenuOpened() ? 0 : 4;
        ((MenuBuilder)object).performItemAction((MenuItem)view, this, n);
    }

    public abstract void setAnchorView(View var1);

    public void setEpicenterBounds(Rect rect) {
        this.mEpicenterBounds = rect;
    }

    public abstract void setForceShowIcon(boolean var1);

    public abstract void setGravity(int var1);

    public abstract void setHorizontalOffset(int var1);

    public abstract void setOnDismissListener(PopupWindow.OnDismissListener var1);

    public abstract void setShowTitle(boolean var1);

    public abstract void setVerticalOffset(int var1);
}

