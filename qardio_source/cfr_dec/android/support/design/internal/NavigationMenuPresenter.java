/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.R;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class NavigationMenuPresenter
implements MenuPresenter {
    NavigationMenuAdapter mAdapter;
    private MenuPresenter.Callback mCallback;
    LinearLayout mHeaderLayout;
    ColorStateList mIconTintList;
    private int mId;
    Drawable mItemBackground;
    LayoutInflater mLayoutInflater;
    MenuBuilder mMenu;
    private NavigationMenuView mMenuView;
    final View.OnClickListener mOnClickListener = new View.OnClickListener(){

        public void onClick(View object) {
            object = (NavigationMenuItemView)object;
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            object = ((NavigationMenuItemView)object).getItemData();
            boolean bl = NavigationMenuPresenter.this.mMenu.performItemAction((MenuItem)object, NavigationMenuPresenter.this, 0);
            if (object != null && ((MenuItemImpl)object).isCheckable() && bl) {
                NavigationMenuPresenter.this.mAdapter.setCheckedItem((MenuItemImpl)object);
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            NavigationMenuPresenter.this.updateMenuView(false);
        }
    };
    int mPaddingSeparator;
    private int mPaddingTopDefault;
    int mTextAppearance;
    boolean mTextAppearanceSet;
    ColorStateList mTextColor;

    public void addHeaderView(View view) {
        this.mHeaderLayout.addView(view);
        this.mMenuView.setPadding(0, 0, 0, this.mMenuView.getPaddingBottom());
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        int n = windowInsetsCompat.getSystemWindowInsetTop();
        if (this.mPaddingTopDefault != n) {
            this.mPaddingTopDefault = n;
            if (this.mHeaderLayout.getChildCount() == 0) {
                this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
            }
        }
        ViewCompat.dispatchApplyWindowInsets((View)this.mHeaderLayout, windowInsetsCompat);
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    public int getHeaderCount() {
        return this.mHeaderLayout.getChildCount();
    }

    public View getHeaderView(int n) {
        return this.mHeaderLayout.getChildAt(n);
    }

    @Override
    public int getId() {
        return this.mId;
    }

    public Drawable getItemBackground() {
        return this.mItemBackground;
    }

    public ColorStateList getItemTextColor() {
        return this.mTextColor;
    }

    public ColorStateList getItemTintList() {
        return this.mIconTintList;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (NavigationMenuView)this.mLayoutInflater.inflate(R.layout.design_navigation_menu, viewGroup, false);
            if (this.mAdapter == null) {
                this.mAdapter = new NavigationMenuAdapter();
            }
            this.mHeaderLayout = (LinearLayout)this.mLayoutInflater.inflate(R.layout.design_navigation_item_header, (ViewGroup)this.mMenuView, false);
            this.mMenuView.setAdapter(this.mAdapter);
        }
        return this.mMenuView;
    }

    public View inflateHeaderView(int n) {
        View view = this.mLayoutInflater.inflate(n, (ViewGroup)this.mHeaderLayout, false);
        this.addHeaderView(view);
        return view;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.mLayoutInflater = LayoutInflater.from((Context)context);
        this.mMenu = menuBuilder;
        this.mPaddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, bl);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            SparseArray sparseArray = (parcelable = (Bundle)parcelable).getSparseParcelableArray("android:menu:list");
            if (sparseArray != null) {
                this.mMenuView.restoreHierarchyState(sparseArray);
            }
            if ((sparseArray = parcelable.getBundle("android:menu:adapter")) != null) {
                this.mAdapter.restoreInstanceState((Bundle)sparseArray);
            }
            if ((parcelable = parcelable.getSparseParcelableArray("android:menu:header")) != null) {
                this.mHeaderLayout.restoreHierarchyState((SparseArray)parcelable);
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (Build.VERSION.SDK_INT >= 11) {
            SparseArray sparseArray;
            Bundle bundle = new Bundle();
            if (this.mMenuView != null) {
                sparseArray = new SparseArray();
                this.mMenuView.saveHierarchyState(sparseArray);
                bundle.putSparseParcelableArray("android:menu:list", sparseArray);
            }
            if (this.mAdapter != null) {
                bundle.putBundle("android:menu:adapter", this.mAdapter.createInstanceState());
            }
            if (this.mHeaderLayout != null) {
                sparseArray = new SparseArray();
                this.mHeaderLayout.saveHierarchyState(sparseArray);
                bundle.putSparseParcelableArray("android:menu:header", sparseArray);
            }
            return bundle;
        }
        return null;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        this.mAdapter.setCheckedItem(menuItemImpl);
    }

    public void setId(int n) {
        this.mId = n;
    }

    public void setItemBackground(Drawable drawable2) {
        this.mItemBackground = drawable2;
        this.updateMenuView(false);
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.updateMenuView(false);
    }

    public void setItemTextAppearance(int n) {
        this.mTextAppearance = n;
        this.mTextAppearanceSet = true;
        this.updateMenuView(false);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.mTextColor = colorStateList;
        this.updateMenuView(false);
    }

    public void setUpdateSuspended(boolean bl) {
        if (this.mAdapter != null) {
            this.mAdapter.setUpdateSuspended(bl);
        }
    }

    @Override
    public void updateMenuView(boolean bl) {
        if (this.mAdapter != null) {
            this.mAdapter.update();
        }
    }

    private static class HeaderViewHolder
    extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private class NavigationMenuAdapter
    extends RecyclerView.Adapter<ViewHolder> {
        private MenuItemImpl mCheckedItem;
        private final ArrayList<NavigationMenuItem> mItems = new ArrayList();
        private boolean mUpdateSuspended;

        NavigationMenuAdapter() {
            this.prepareMenuItems();
        }

        private void appendTransparentIconIfMissing(int n, int n2) {
            while (n < n2) {
                ((NavigationMenuTextItem)this.mItems.get((int)n)).needsEmptyIcon = true;
                ++n;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void prepareMenuItems() {
            if (this.mUpdateSuspended) {
                return;
            }
            this.mUpdateSuspended = true;
            this.mItems.clear();
            this.mItems.add(new NavigationMenuHeaderItem());
            int n = -1;
            int n2 = 0;
            boolean bl = false;
            int n3 = 0;
            int n4 = NavigationMenuPresenter.this.mMenu.getVisibleItems().size();
            do {
                int n5;
                int n6;
                int n7;
                boolean bl2;
                if (n3 >= n4) {
                    this.mUpdateSuspended = false;
                    return;
                }
                Object object = NavigationMenuPresenter.this.mMenu.getVisibleItems().get(n3);
                if (((MenuItemImpl)object).isChecked()) {
                    this.setCheckedItem((MenuItemImpl)object);
                }
                if (((MenuItemImpl)object).isCheckable()) {
                    ((MenuItemImpl)object).setExclusiveCheckable(false);
                }
                if (((MenuItemImpl)object).hasSubMenu()) {
                    SubMenu subMenu = ((MenuItemImpl)object).getSubMenu();
                    bl2 = bl;
                    n7 = n;
                    n6 = n2;
                    if (subMenu.hasVisibleItems()) {
                        if (n3 != 0) {
                            this.mItems.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.mPaddingSeparator, 0));
                        }
                        this.mItems.add(new NavigationMenuTextItem((MenuItemImpl)object));
                        n5 = 0;
                        int n8 = this.mItems.size();
                        int n9 = subMenu.size();
                        for (n6 = 0; n6 < n9; ++n6) {
                            MenuItemImpl menuItemImpl = (MenuItemImpl)subMenu.getItem(n6);
                            n7 = n5;
                            if (menuItemImpl.isVisible()) {
                                n7 = n5;
                                if (n5 == 0) {
                                    n7 = n5;
                                    if (menuItemImpl.getIcon() != null) {
                                        n7 = 1;
                                    }
                                }
                                if (menuItemImpl.isCheckable()) {
                                    menuItemImpl.setExclusiveCheckable(false);
                                }
                                if (((MenuItemImpl)object).isChecked()) {
                                    this.setCheckedItem((MenuItemImpl)object);
                                }
                                this.mItems.add(new NavigationMenuTextItem(menuItemImpl));
                            }
                            n5 = n7;
                        }
                        bl2 = bl;
                        n7 = n;
                        n6 = n2;
                        if (n5 != 0) {
                            this.appendTransparentIconIfMissing(n8, this.mItems.size());
                            n6 = n2;
                            n7 = n;
                            bl2 = bl;
                        }
                    }
                } else {
                    n7 = ((MenuItemImpl)object).getGroupId();
                    if (n7 != n) {
                        n2 = this.mItems.size();
                        bl = ((MenuItemImpl)object).getIcon() != null;
                        bl2 = bl;
                        n5 = n2;
                        if (n3 != 0) {
                            n5 = n2 + 1;
                            this.mItems.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.mPaddingSeparator, NavigationMenuPresenter.this.mPaddingSeparator));
                            bl2 = bl;
                        }
                    } else {
                        bl2 = bl;
                        n5 = n2;
                        if (!bl) {
                            bl2 = bl;
                            n5 = n2;
                            if (((MenuItemImpl)object).getIcon() != null) {
                                bl2 = true;
                                this.appendTransparentIconIfMissing(n2, this.mItems.size());
                                n5 = n2;
                            }
                        }
                    }
                    object = new NavigationMenuTextItem((MenuItemImpl)object);
                    ((NavigationMenuTextItem)object).needsEmptyIcon = bl2;
                    this.mItems.add((NavigationMenuItem)object);
                    n6 = n5;
                }
                ++n3;
                bl = bl2;
                n = n7;
                n2 = n6;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         */
        public Bundle createInstanceState() {
            Bundle bundle = new Bundle();
            if (this.mCheckedItem != null) {
                bundle.putInt("android:menu:checked", this.mCheckedItem.getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            int n = 0;
            int n2 = this.mItems.size();
            do {
                MenuItemImpl menuItemImpl;
                if (n >= n2) {
                    bundle.putSparseParcelableArray("android:menu:action_views", sparseArray);
                    return bundle;
                }
                NavigationMenuItem navigationMenuItem = this.mItems.get(n);
                if (navigationMenuItem instanceof NavigationMenuTextItem && (navigationMenuItem = (menuItemImpl = ((NavigationMenuTextItem)navigationMenuItem).getMenuItem()) != null ? menuItemImpl.getActionView() : null) != null) {
                    ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                    navigationMenuItem.saveHierarchyState((SparseArray)parcelableSparseArray);
                    sparseArray.put(menuItemImpl.getItemId(), (Object)parcelableSparseArray);
                }
                ++n;
            } while (true);
        }

        @Override
        public int getItemCount() {
            return this.mItems.size();
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public int getItemViewType(int n) {
            NavigationMenuItem navigationMenuItem = this.mItems.get(n);
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (navigationMenuItem instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (navigationMenuItem instanceof NavigationMenuTextItem) {
                if (((NavigationMenuTextItem)navigationMenuItem).getMenuItem().hasSubMenu()) {
                    return 1;
                }
                return 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onBindViewHolder(ViewHolder object, int n) {
            switch (this.getItemViewType(n)) {
                default: {
                    return;
                }
                case 0: {
                    NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)((ViewHolder)object).itemView;
                    navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.mIconTintList);
                    if (NavigationMenuPresenter.this.mTextAppearanceSet) {
                        navigationMenuItemView.setTextAppearance(NavigationMenuPresenter.this.mTextAppearance);
                    }
                    if (NavigationMenuPresenter.this.mTextColor != null) {
                        navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.mTextColor);
                    }
                    object = NavigationMenuPresenter.this.mItemBackground != null ? NavigationMenuPresenter.this.mItemBackground.getConstantState().newDrawable() : null;
                    ViewCompat.setBackground((View)navigationMenuItemView, (Drawable)object);
                    object = (NavigationMenuTextItem)this.mItems.get(n);
                    navigationMenuItemView.setNeedsEmptyIcon(((NavigationMenuTextItem)object).needsEmptyIcon);
                    navigationMenuItemView.initialize(((NavigationMenuTextItem)object).getMenuItem(), 0);
                    return;
                }
                case 1: {
                    ((TextView)((ViewHolder)object).itemView).setText(((NavigationMenuTextItem)this.mItems.get(n)).getMenuItem().getTitle());
                    return;
                }
                case 2: 
            }
            NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuSeparatorItem)this.mItems.get(n);
            ((ViewHolder)object).itemView.setPadding(0, navigationMenuSeparatorItem.getPaddingTop(), 0, navigationMenuSeparatorItem.getPaddingBottom());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 0: {
                    return new NormalViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup, NavigationMenuPresenter.this.mOnClickListener);
                }
                case 1: {
                    return new SubheaderViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
                }
                case 2: {
                    return new SeparatorViewHolder(NavigationMenuPresenter.this.mLayoutInflater, viewGroup);
                }
                case 3: 
            }
            return new HeaderViewHolder((View)NavigationMenuPresenter.this.mHeaderLayout);
        }

        @Override
        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder instanceof NormalViewHolder) {
                ((NavigationMenuItemView)viewHolder.itemView).recycle();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public void restoreInstanceState(Bundle bundle) {
            int n;
            int n2;
            Object object;
            block6: {
                n2 = bundle.getInt("android:menu:checked", 0);
                if (n2 == 0) break block6;
                this.mUpdateSuspended = true;
                n = 0;
                int n3 = this.mItems.size();
                do {
                    block8: {
                        block7: {
                            if (n >= n3) break block7;
                            object = this.mItems.get(n);
                            if (!(object instanceof NavigationMenuTextItem) || (object = ((NavigationMenuTextItem)object).getMenuItem()) == null || ((MenuItemImpl)object).getItemId() != n2) break block8;
                            this.setCheckedItem((MenuItemImpl)object);
                        }
                        this.mUpdateSuspended = false;
                        this.prepareMenuItems();
                        break;
                    }
                    ++n;
                } while (true);
            }
            if ((bundle = bundle.getSparseParcelableArray("android:menu:action_views")) == null) return;
            n = 0;
            n2 = this.mItems.size();
            while (n < n2) {
                ParcelableSparseArray parcelableSparseArray;
                MenuItemImpl menuItemImpl;
                object = this.mItems.get(n);
                if (object instanceof NavigationMenuTextItem && (menuItemImpl = ((NavigationMenuTextItem)object).getMenuItem()) != null && (object = menuItemImpl.getActionView()) != null && (parcelableSparseArray = (ParcelableSparseArray)((Object)bundle.get(menuItemImpl.getItemId()))) != null) {
                    object.restoreHierarchyState((SparseArray)parcelableSparseArray);
                }
                ++n;
            }
        }

        public void setCheckedItem(MenuItemImpl menuItemImpl) {
            if (this.mCheckedItem == menuItemImpl || !menuItemImpl.isCheckable()) {
                return;
            }
            if (this.mCheckedItem != null) {
                this.mCheckedItem.setChecked(false);
            }
            this.mCheckedItem = menuItemImpl;
            menuItemImpl.setChecked(true);
        }

        public void setUpdateSuspended(boolean bl) {
            this.mUpdateSuspended = bl;
        }

        public void update() {
            this.prepareMenuItems();
            this.notifyDataSetChanged();
        }
    }

    private static class NavigationMenuHeaderItem
    implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    private static interface NavigationMenuItem {
    }

    private static class NavigationMenuSeparatorItem
    implements NavigationMenuItem {
        private final int mPaddingBottom;
        private final int mPaddingTop;

        public NavigationMenuSeparatorItem(int n, int n2) {
            this.mPaddingTop = n;
            this.mPaddingBottom = n2;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }
    }

    private static class NavigationMenuTextItem
    implements NavigationMenuItem {
        private final MenuItemImpl mMenuItem;
        boolean needsEmptyIcon;

        NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            this.mMenuItem = menuItemImpl;
        }

        public MenuItemImpl getMenuItem() {
            return this.mMenuItem;
        }
    }

    private static class NormalViewHolder
    extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    private static class SeparatorViewHolder
    extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    private static class SubheaderViewHolder
    extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    private static abstract class ViewHolder
    extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

}

