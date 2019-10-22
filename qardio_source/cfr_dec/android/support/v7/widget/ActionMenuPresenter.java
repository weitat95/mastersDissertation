/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.SparseBooleanArray
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopup;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.ArrayList;

class ActionMenuPresenter
extends BaseMenuPresenter
implements ActionProvider.SubUiVisibilityListener {
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    OverflowMenuButton mOverflowButton;
    OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private View mScrapActionButtonView;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup)this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int n = viewGroup.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = viewGroup.getChildAt(n2);
            if (view instanceof MenuView.ItemView) {
                View view2 = view;
                if (((MenuView.ItemView)view).getItemData() == menuItem) return view2;
            }
            ++n2;
        }
        return null;
    }

    @Override
    public void bindItemView(MenuItemImpl object, MenuView.ItemView itemView) {
        itemView.initialize((MenuItemImpl)object, 0);
        object = (ActionMenuView)this.mMenuView;
        itemView = (ActionMenuItemView)itemView;
        ((ActionMenuItemView)itemView).setItemInvoker((MenuBuilder.ItemInvoker)object);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        ((ActionMenuItemView)itemView).setPopupCallback(this.mPopupCallback);
    }

    public boolean dismissPopupMenus() {
        return this.hideOverflowMenu() | this.hideSubMenus();
    }

    @Override
    public boolean filterLeftoverView(ViewGroup viewGroup, int n) {
        if (viewGroup.getChildAt(n) == this.mOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean flagActionItems() {
        MenuItemImpl menuItemImpl;
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        ArrayList<MenuItemImpl> arrayList;
        int n6;
        ViewGroup viewGroup;
        int n7;
        int n8;
        int n9;
        int n10;
        block40: {
            block41: {
                if (this.mMenu != null) {
                    arrayList = this.mMenu.getVisibleItems();
                    n3 = arrayList.size();
                } else {
                    arrayList = null;
                    n3 = 0;
                }
                n9 = this.mMaxItems;
                n = this.mActionItemWidthLimit;
                n6 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                viewGroup = (ViewGroup)this.mMenuView;
                n8 = 0;
                n2 = 0;
                n5 = 0;
                n10 = 0;
                for (n7 = 0; n7 < n3; ++n7) {
                    menuItemImpl = arrayList.get(n7);
                    if (menuItemImpl.requiresActionButton()) {
                        ++n8;
                    } else if (menuItemImpl.requestsActionButton()) {
                        ++n2;
                    } else {
                        n10 = 1;
                    }
                    n4 = n9;
                    if (this.mExpandedActionViewsExclusive) {
                        n4 = n9;
                        if (menuItemImpl.isActionViewExpanded()) {
                            n4 = 0;
                        }
                    }
                    n9 = n4;
                }
                n7 = n9;
                if (!this.mReserveOverflow) break block40;
                if (n10 != 0) break block41;
                n7 = n9;
                if (n8 + n2 <= n9) break block40;
            }
            n7 = n9 - 1;
        }
        n7 -= n8;
        menuItemImpl = this.mActionButtonGroups;
        menuItemImpl.clear();
        int n11 = 0;
        n8 = 0;
        if (this.mStrictWidthLimit) {
            n8 = n / this.mMinCellSize;
            n9 = this.mMinCellSize;
            n11 = this.mMinCellSize + n % n9 / n8;
        }
        n9 = 0;
        n10 = n;
        n = n9;
        n9 = n5;
        while (n < n3) {
            Object object;
            MenuItemImpl menuItemImpl2 = arrayList.get(n);
            if (menuItemImpl2.requiresActionButton()) {
                object = this.getItemView(menuItemImpl2, this.mScrapActionButtonView, viewGroup);
                if (this.mScrapActionButtonView == null) {
                    this.mScrapActionButtonView = object;
                }
                if (this.mStrictWidthLimit) {
                    n8 -= ActionMenuView.measureChildForCells((View)object, n11, n8, n6, 0);
                } else {
                    object.measure(n6, n6);
                }
                n4 = object.getMeasuredWidth();
                n2 = n10 - n4;
                n10 = n9;
                if (n9 == 0) {
                    n10 = n4;
                }
                if ((n9 = menuItemImpl2.getGroupId()) != 0) {
                    menuItemImpl.put(n9, true);
                }
                menuItemImpl2.setIsActionButton(true);
                n9 = n10;
            } else if (menuItemImpl2.requestsActionButton()) {
                int n12 = menuItemImpl2.getGroupId();
                boolean bl = menuItemImpl.get(n12);
                int n13 = !(n7 <= 0 && !bl || n10 <= 0 || this.mStrictWidthLimit && n8 <= 0) ? 1 : 0;
                n5 = n8;
                n4 = n9;
                int n14 = n13;
                n2 = n10;
                if (n13 != 0) {
                    object = this.getItemView(menuItemImpl2, this.mScrapActionButtonView, viewGroup);
                    if (this.mScrapActionButtonView == null) {
                        this.mScrapActionButtonView = object;
                    }
                    if (this.mStrictWidthLimit) {
                        n4 = ActionMenuView.measureChildForCells((View)object, n11, n8, n6, 0);
                        n8 = n2 = n8 - n4;
                        if (n4 == 0) {
                            n13 = 0;
                            n8 = n2;
                        }
                    } else {
                        object.measure(n6, n6);
                    }
                    n5 = object.getMeasuredWidth();
                    n2 = n10 - n5;
                    n4 = n9;
                    if (n9 == 0) {
                        n4 = n5;
                    }
                    if (this.mStrictWidthLimit) {
                        n9 = n2 >= 0 ? 1 : 0;
                        n14 = n13 & n9;
                        n5 = n8;
                    } else {
                        n9 = n2 + n4 > 0 ? 1 : 0;
                        n14 = n13 & n9;
                        n5 = n8;
                    }
                }
                if (n14 != 0 && n12 != 0) {
                    menuItemImpl.put(n12, true);
                    n9 = n7;
                } else {
                    n9 = n7;
                    if (bl) {
                        menuItemImpl.put(n12, false);
                        n8 = 0;
                        do {
                            n9 = n7;
                            if (n8 >= n) break;
                            object = arrayList.get(n8);
                            n9 = n7;
                            if (((MenuItemImpl)object).getGroupId() == n12) {
                                n9 = n7;
                                if (((MenuItemImpl)object).isActionButton()) {
                                    n9 = n7 + 1;
                                }
                                ((MenuItemImpl)object).setIsActionButton(false);
                            }
                            ++n8;
                            n7 = n9;
                        } while (true);
                    }
                }
                n7 = n9;
                if (n14 != 0) {
                    n7 = n9 - 1;
                }
                menuItemImpl2.setIsActionButton(n14 != 0);
                n8 = n5;
                n9 = n4;
            } else {
                menuItemImpl2.setIsActionButton(false);
                n2 = n10;
            }
            ++n;
            n10 = n2;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public View getItemView(MenuItemImpl object, View view, ViewGroup viewGroup) {
        View view2 = ((MenuItemImpl)object).getActionView();
        if (view2 == null || ((MenuItemImpl)object).hasCollapsibleActionView()) {
            view2 = super.getItemView((MenuItemImpl)object, view, viewGroup);
        }
        int n = ((MenuItemImpl)object).isActionViewExpanded() ? 8 : 0;
        view2.setVisibility(n);
        object = (ActionMenuView)viewGroup;
        view = view2.getLayoutParams();
        if (!((ActionMenuView)object).checkLayoutParams((ViewGroup.LayoutParams)view)) {
            view2.setLayoutParams((ViewGroup.LayoutParams)((ActionMenuView)object).generateLayoutParams((ViewGroup.LayoutParams)view));
        }
        return view2;
    }

    @Override
    public MenuView getMenuView(ViewGroup object) {
        MenuView menuView = this.mMenuView;
        if (menuView != (object = super.getMenuView((ViewGroup)object))) {
            ((ActionMenuView)object).setPresenter(this);
        }
        return object;
    }

    public Drawable getOverflowIcon() {
        if (this.mOverflowButton != null) {
            return this.mOverflowButton.getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((View)this.mMenuView).removeCallbacks((Runnable)this.mPostedOpenRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup != null) {
            overflowPopup.dismiss();
            return true;
        }
        return false;
    }

    public boolean hideSubMenus() {
        if (this.mActionButtonPopup != null) {
            this.mActionButtonPopup.dismiss();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initForMenu(Context object, MenuBuilder menuBuilder) {
        super.initForMenu((Context)object, menuBuilder);
        menuBuilder = object.getResources();
        object = ActionBarPolicy.get((Context)object);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = ((ActionBarPolicy)object).showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = ((ActionBarPolicy)object).getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ((ActionBarPolicy)object).getMaxActionButtons();
        }
        int n = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int n2 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                this.mOverflowButton.measure(n2, n2);
            }
            n -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = n;
        this.mMinCellSize = (int)(56.0f * menuBuilder.getDisplayMetrics().density);
        this.mScrapActionButtonView = null;
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || this.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowing() {
        return this.mOverflowPopup != null && this.mOverflowPopup.isShowing();
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        this.dismissPopupMenus();
        super.onCloseMenu(menuBuilder, bl);
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        block3: {
            block2: {
                if (!(parcelable instanceof SavedState)) break block2;
                parcelable = (SavedState)parcelable;
                if (parcelable.openSubMenuId > 0 && (parcelable = this.mMenu.findItem(parcelable.openSubMenuId)) != null) break block3;
            }
            return;
        }
        this.onSubMenuSelected((SubMenuBuilder)parcelable.getSubMenu());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        SubMenuBuilder subMenuBuilder2;
        block7: {
            block6: {
                if (!subMenuBuilder.hasVisibleItems()) break block6;
                subMenuBuilder2 = subMenuBuilder;
                while (subMenuBuilder2.getParentMenu() != this.mMenu) {
                    subMenuBuilder2 = (SubMenuBuilder)subMenuBuilder2.getParentMenu();
                }
                if ((subMenuBuilder2 = this.findViewForItem(subMenuBuilder2.getItem())) != null) break block7;
            }
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        boolean bl = false;
        int n = subMenuBuilder.size();
        int n2 = 0;
        do {
            block9: {
                boolean bl2;
                block8: {
                    bl2 = bl;
                    if (n2 >= n) break block8;
                    MenuItem menuItem = subMenuBuilder.getItem(n2);
                    if (!menuItem.isVisible() || menuItem.getIcon() == null) break block9;
                    bl2 = true;
                }
                this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, subMenuBuilder, (View)subMenuBuilder2);
                this.mActionButtonPopup.setForceShowIcon(bl2);
                this.mActionButtonPopup.show();
                super.onSubMenuSelected(subMenuBuilder);
                return true;
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onSubUiVisibilityChanged(boolean bl) {
        if (bl) {
            super.onSubMenuSelected(null);
            return;
        } else {
            if (this.mMenu == null) return;
            {
                this.mMenu.close(false);
                return;
            }
        }
    }

    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mExpandedActionViewsExclusive = bl;
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
    }

    public void setOverflowIcon(Drawable drawable2) {
        if (this.mOverflowButton != null) {
            this.mOverflowButton.setImageDrawable(drawable2);
            return;
        }
        this.mPendingOverflowIconSet = true;
        this.mPendingOverflowIcon = drawable2;
    }

    public void setReserveOverflow(boolean bl) {
        this.mReserveOverflow = bl;
        this.mReserveOverflowSet = true;
    }

    @Override
    public boolean shouldIncludeItem(int n, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    public boolean showOverflowMenu() {
        if (this.mReserveOverflow && !this.isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
            this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, (View)this.mOverflowButton, true));
            ((View)this.mMenuView).post((Runnable)this.mPostedOpenRunnable);
            super.onSubMenuSelected(null);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void updateMenuView(boolean bl) {
        int n;
        int n2;
        Object object;
        super.updateMenuView(bl);
        ((View)this.mMenuView).requestLayout();
        if (this.mMenu != null) {
            object = this.mMenu.getActionItems();
            n = object.size();
            for (n2 = 0; n2 < n; ++n2) {
                ActionProvider actionProvider = object.get(n2).getSupportActionProvider();
                if (actionProvider == null) continue;
                actionProvider.setSubUiVisibilityListener(this);
            }
        }
        object = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        n2 = n = 0;
        if (this.mReserveOverflow) {
            n2 = n;
            if (object != null) {
                n2 = object.size();
                n2 = n2 == 1 ? (!((MenuItemImpl)object.get(0)).isActionViewExpanded() ? 1 : 0) : (n2 > 0 ? 1 : 0);
            }
        }
        if (n2 != 0) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
            }
            if ((object = (ViewGroup)this.mOverflowButton.getParent()) != this.mMenuView) {
                if (object != null) {
                    object.removeView((View)this.mOverflowButton);
                }
                object = (ActionMenuView)this.mMenuView;
                object.addView((View)this.mOverflowButton, (ViewGroup.LayoutParams)object.generateOverflowButtonLayoutParams());
            }
        } else if (this.mOverflowButton != null && this.mOverflowButton.getParent() == this.mMenuView) {
            ((ViewGroup)this.mMenuView).removeView((View)this.mOverflowButton);
        }
        ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    private class ActionButtonSubmenu
    extends MenuPopupHelper {
        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public ActionButtonSubmenu(Context object, SubMenuBuilder subMenuBuilder, View view) {
            void var3_6;
            void var4_7;
            super((Context)object, (MenuBuilder)var3_6, (View)var4_7, false, R.attr.actionOverflowMenuStyle);
            if (!((MenuItemImpl)var3_6.getItem()).isActionButton()) {
                void var2_4;
                if (ActionMenuPresenter.this.mOverflowButton == null) {
                    View view2 = (View)ActionMenuPresenter.this.mMenuView;
                } else {
                    OverflowMenuButton overflowMenuButton = ActionMenuPresenter.this.mOverflowButton;
                }
                this.setAnchorView((View)var2_4);
            }
            this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        protected void onDismiss() {
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class ActionMenuPopupCallback
    extends ActionMenuItemView.PopupCallback {
        ActionMenuPopupCallback() {
        }

        @Override
        public ShowableListMenu getPopup() {
            if (ActionMenuPresenter.this.mActionButtonPopup != null) {
                return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
            }
            return null;
        }
    }

    private class OpenOverflowRunnable
    implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override
        public void run() {
            View view;
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            if ((view = (View)ActionMenuPresenter.this.mMenuView) != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class OverflowMenuButton
    extends AppCompatImageView
    implements ActionMenuView.ActionMenuChildView {
        private final float[] mTempPts;

        public OverflowMenuButton(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            this.mTempPts = new float[2];
            this.setClickable(true);
            this.setFocusable(true);
            this.setVisibility(0);
            this.setEnabled(true);
            TooltipCompat.setTooltipText((View)this, this.getContentDescription());
            this.setOnTouchListener((View.OnTouchListener)new ForwardingListener((View)this){

                @Override
                public ShowableListMenu getPopup() {
                    if (ActionMenuPresenter.this.mOverflowPopup == null) {
                        return null;
                    }
                    return ActionMenuPresenter.this.mOverflowPopup.getPopup();
                }

                @Override
                public boolean onForwardingStarted() {
                    ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                @Override
                public boolean onForwardingStopped() {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        @Override
        public boolean needsDividerAfter() {
            return false;
        }

        @Override
        public boolean needsDividerBefore() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            this.playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        protected boolean setFrame(int n, int n2, int n3, int n4) {
            boolean bl = super.setFrame(n, n2, n3, n4);
            Drawable drawable2 = this.getDrawable();
            Drawable drawable3 = this.getBackground();
            if (drawable2 != null && drawable3 != null) {
                int n5 = this.getWidth();
                n2 = this.getHeight();
                n = Math.max(n5, n2) / 2;
                int n6 = this.getPaddingLeft();
                int n7 = this.getPaddingRight();
                n3 = this.getPaddingTop();
                n4 = this.getPaddingBottom();
                n5 = (n5 + (n6 - n7)) / 2;
                n2 = (n2 + (n3 - n4)) / 2;
                DrawableCompat.setHotspotBounds(drawable3, n5 - n, n2 - n, n5 + n, n2 + n);
            }
            return bl;
        }

    }

    private class OverflowPopup
    extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean bl) {
            super(context, menuBuilder, view, bl, R.attr.actionOverflowMenuStyle);
            this.setGravity(8388613);
            this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        protected void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    private class PopupPresenterCallback
    implements MenuPresenter.Callback {
        PopupPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            MenuPresenter.Callback callback;
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            if ((callback = ActionMenuPresenter.this.getCallback()) != null) {
                callback.onCloseMenu(menuBuilder, bl);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback == null) return false;
            return callback.onOpenSubMenu(menuBuilder);
        }
    }

    private static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int openSubMenuId;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.openSubMenuId);
        }

    }

}

