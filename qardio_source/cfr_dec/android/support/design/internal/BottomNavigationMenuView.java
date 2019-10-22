/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.design.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.R;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.design.internal.TextScale;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.util.Pools;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class BottomNavigationMenuView
extends ViewGroup
implements MenuView {
    private final int mActiveItemMaxWidth;
    private BottomNavigationItemView[] mButtons;
    private final int mInactiveItemMaxWidth;
    private final int mInactiveItemMinWidth;
    private int mItemBackgroundRes;
    private final int mItemHeight;
    private ColorStateList mItemIconTint;
    private final Pools.Pool<BottomNavigationItemView> mItemPool = new Pools.SynchronizedPool<BottomNavigationItemView>(5);
    private ColorStateList mItemTextColor;
    private MenuBuilder mMenu;
    private final View.OnClickListener mOnClickListener;
    private BottomNavigationPresenter mPresenter;
    private int mSelectedItemId = 0;
    private int mSelectedItemPosition = 0;
    private final TransitionSet mSet;
    private boolean mShiftingMode = true;
    private int[] mTempChildWidths;

    public BottomNavigationMenuView(Context context) {
        this(context, null);
    }

    public BottomNavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = this.getResources();
        this.mInactiveItemMaxWidth = context.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.mInactiveItemMinWidth = context.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.mActiveItemMaxWidth = context.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.mItemHeight = context.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
        this.mSet = new AutoTransition();
        this.mSet.setOrdering(0);
        this.mSet.setDuration(115L);
        this.mSet.setInterpolator((TimeInterpolator)new FastOutSlowInInterpolator());
        this.mSet.addTransition(new TextScale());
        this.mOnClickListener = new View.OnClickListener(){

            public void onClick(View object) {
                object = ((BottomNavigationItemView)object).getItemData();
                if (!BottomNavigationMenuView.this.mMenu.performItemAction((MenuItem)object, BottomNavigationMenuView.this.mPresenter, 0)) {
                    object.setChecked(true);
                }
            }
        };
        this.mTempChildWidths = new int[5];
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView bottomNavigationItemView;
        BottomNavigationItemView bottomNavigationItemView2 = bottomNavigationItemView = this.mItemPool.acquire();
        if (bottomNavigationItemView == null) {
            bottomNavigationItemView2 = new BottomNavigationItemView(this.getContext());
        }
        return bottomNavigationItemView2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void buildMenuView() {
        this.removeAllViews();
        if (this.mButtons != null) {
            for (BottomNavigationItemView bottomNavigationItemView : this.mButtons) {
                this.mItemPool.release(bottomNavigationItemView);
            }
        }
        if (this.mMenu.size() == 0) {
            this.mSelectedItemId = 0;
            this.mSelectedItemPosition = 0;
            this.mButtons = null;
            return;
        }
        this.mButtons = new BottomNavigationItemView[this.mMenu.size()];
        boolean bl = this.mMenu.size() > 3;
        this.mShiftingMode = bl;
        int n = 0;
        do {
            Object object;
            if (n >= this.mMenu.size()) {
                this.mSelectedItemPosition = Math.min(this.mMenu.size() - 1, this.mSelectedItemPosition);
                this.mMenu.getItem(this.mSelectedItemPosition).setChecked(true);
                return;
            }
            this.mPresenter.setUpdateSuspended(true);
            this.mMenu.getItem(n).setCheckable(true);
            this.mPresenter.setUpdateSuspended(false);
            this.mButtons[n] = object = this.getNewItem();
            ((BottomNavigationItemView)object).setIconTintList(this.mItemIconTint);
            ((BottomNavigationItemView)object).setTextColor(this.mItemTextColor);
            ((BottomNavigationItemView)object).setItemBackground(this.mItemBackgroundRes);
            ((BottomNavigationItemView)object).setShiftingMode(this.mShiftingMode);
            ((BottomNavigationItemView)object).initialize((MenuItemImpl)this.mMenu.getItem(n), 0);
            ((BottomNavigationItemView)object).setItemPosition(n);
            object.setOnClickListener(this.mOnClickListener);
            this.addView((View)object);
            ++n;
        } while (true);
    }

    public ColorStateList getIconTintList() {
        return this.mItemIconTint;
    }

    public int getItemBackgroundRes() {
        return this.mItemBackgroundRes;
    }

    public ColorStateList getItemTextColor() {
        return this.mItemTextColor;
    }

    public int getSelectedItemId() {
        return this.mSelectedItemId;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        n3 -= n;
        n4 -= n2;
        n2 = 0;
        n = 0;
        while (n < n5) {
            View view = this.getChildAt(n);
            if (view.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection((View)this) == 1) {
                    view.layout(n3 - n2 - view.getMeasuredWidth(), 0, n3 - n2, n4);
                } else {
                    view.layout(n2, 0, view.getMeasuredWidth() + n2, n4);
                }
                n2 += view.getMeasuredWidth();
            }
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int[] arrn;
        n2 = View.MeasureSpec.getSize((int)n);
        int n5 = this.getChildCount();
        int n6 = View.MeasureSpec.makeMeasureSpec((int)this.mItemHeight, (int)1073741824);
        if (!this.mShiftingMode) {
            n = n5 == 0 ? 1 : n5;
            n3 = Math.min(n2 / n, this.mActiveItemMaxWidth);
            n2 -= n3 * n5;
            for (n = 0; n < n5; ++n) {
                this.mTempChildWidths[n] = n3;
                n4 = n2;
                if (n2 > 0) {
                    arrn = this.mTempChildWidths;
                    arrn[n] = arrn[n] + 1;
                    n4 = n2 - 1;
                }
                n2 = n4;
            }
        } else {
            n = n5 - 1;
            n3 = Math.min(n2 - this.mInactiveItemMinWidth * n, this.mActiveItemMaxWidth);
            int n7 = Math.min((n2 - n3) / n, this.mInactiveItemMaxWidth);
            n2 = n2 - n3 - n7 * n;
            for (n = 0; n < n5; ++n) {
                arrn = this.mTempChildWidths;
                n4 = n == this.mSelectedItemPosition ? n3 : n7;
                arrn[n] = n4;
                n4 = n2;
                if (n2 > 0) {
                    arrn = this.mTempChildWidths;
                    arrn[n] = arrn[n] + 1;
                    n4 = n2 - 1;
                }
                n2 = n4;
            }
        }
        n2 = 0;
        n = 0;
        do {
            if (n >= n5) {
                this.setMeasuredDimension(View.resolveSizeAndState((int)n2, (int)View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824), (int)0), View.resolveSizeAndState((int)this.mItemHeight, (int)n6, (int)0));
                return;
            }
            arrn = this.getChildAt(n);
            if (arrn.getVisibility() != 8) {
                arrn.measure(View.MeasureSpec.makeMeasureSpec((int)this.mTempChildWidths[n], (int)1073741824), n6);
                arrn.getLayoutParams().width = arrn.getMeasuredWidth();
                n2 += arrn.getMeasuredWidth();
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setIconTintList(ColorStateList colorStateList) {
        this.mItemIconTint = colorStateList;
        if (this.mButtons != null) {
            BottomNavigationItemView[] arrbottomNavigationItemView = this.mButtons;
            int n = arrbottomNavigationItemView.length;
            for (int i = 0; i < n; ++i) {
                arrbottomNavigationItemView[i].setIconTintList(colorStateList);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setItemBackgroundRes(int n) {
        this.mItemBackgroundRes = n;
        if (this.mButtons != null) {
            BottomNavigationItemView[] arrbottomNavigationItemView = this.mButtons;
            int n2 = arrbottomNavigationItemView.length;
            for (int i = 0; i < n2; ++i) {
                arrbottomNavigationItemView[i].setItemBackground(n);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setItemTextColor(ColorStateList colorStateList) {
        this.mItemTextColor = colorStateList;
        if (this.mButtons != null) {
            BottomNavigationItemView[] arrbottomNavigationItemView = this.mButtons;
            int n = arrbottomNavigationItemView.length;
            for (int i = 0; i < n; ++i) {
                arrbottomNavigationItemView[i].setTextColor(colorStateList);
            }
        }
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        this.mPresenter = bottomNavigationPresenter;
    }

    void tryRestoreSelectedItemId(int n) {
        int n2 = this.mMenu.size();
        int n3 = 0;
        do {
            block4: {
                block3: {
                    if (n3 >= n2) break block3;
                    MenuItem menuItem = this.mMenu.getItem(n3);
                    if (n != menuItem.getItemId()) break block4;
                    this.mSelectedItemId = n;
                    this.mSelectedItemPosition = n3;
                    menuItem.setChecked(true);
                }
                return;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void updateMenuView() {
        int n = this.mMenu.size();
        if (n != this.mButtons.length) {
            this.buildMenuView();
            return;
        } else {
            int n2;
            int n3 = this.mSelectedItemId;
            for (n2 = 0; n2 < n; ++n2) {
                MenuItem menuItem = this.mMenu.getItem(n2);
                if (!menuItem.isChecked()) continue;
                this.mSelectedItemId = menuItem.getItemId();
                this.mSelectedItemPosition = n2;
            }
            if (n3 != this.mSelectedItemId) {
                TransitionManager.beginDelayedTransition(this, this.mSet);
            }
            for (n2 = 0; n2 < n; ++n2) {
                this.mPresenter.setUpdateSuspended(true);
                this.mButtons[n2].initialize((MenuItemImpl)this.mMenu.getItem(n2), 0);
                this.mPresenter.setUpdateSuspended(false);
            }
        }
    }

}

