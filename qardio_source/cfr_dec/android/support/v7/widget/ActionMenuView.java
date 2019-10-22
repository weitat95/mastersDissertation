/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewDebug
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView
extends LinearLayoutCompat
implements MenuBuilder.ItemInvoker,
MenuView {
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int)(56.0f * f);
        this.mGeneratedItemPadding = (int)(4.0f * f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    static int measureChildForCells(View view, int n, int n2, int n3, int n4) {
        LayoutParams layoutParams;
        int n5;
        block6: {
            int n6;
            block7: {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n5 = View.MeasureSpec.makeMeasureSpec((int)(View.MeasureSpec.getSize((int)n3) - n4), (int)View.MeasureSpec.getMode((int)n3));
                ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView)view : null;
                n4 = actionMenuItemView != null && actionMenuItemView.hasText() ? 1 : 0;
                n3 = n6 = 0;
                if (n2 <= 0) break block6;
                if (n4 == 0) break block7;
                n3 = n6;
                if (n2 < 2) break block6;
            }
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n * n2), (int)Integer.MIN_VALUE), n5);
            n6 = view.getMeasuredWidth();
            n2 = n3 = n6 / n;
            if (n6 % n != 0) {
                n2 = n3 + 1;
            }
            n3 = n2;
            if (n4 != 0) {
                n3 = n2;
                if (n2 < 2) {
                    n3 = 2;
                }
            }
        }
        boolean bl = !layoutParams.isOverflowButton && n4 != 0;
        layoutParams.expandable = bl;
        layoutParams.cellsUsed = n3;
        view.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 * n), (int)1073741824), n5);
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onMeasureExactFormat(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        block39: {
            int n7;
            long l;
            int n8;
            int n9;
            int n10;
            LayoutParams layoutParams;
            int n11;
            int n12;
            n5 = View.MeasureSpec.getMode((int)n2);
            n = View.MeasureSpec.getSize((int)n);
            n4 = View.MeasureSpec.getSize((int)n2);
            n3 = this.getPaddingLeft();
            int n13 = this.getPaddingRight();
            int n14 = this.getPaddingTop() + this.getPaddingBottom();
            int n15 = ActionMenuView.getChildMeasureSpec((int)n2, (int)n14, (int)-2);
            n6 = n - (n3 + n13);
            n = n6 / this.mMinCellSize;
            n2 = this.mMinCellSize;
            if (n == 0) {
                this.setMeasuredDimension(n6, 0);
                return;
            }
            int n16 = this.mMinCellSize + n6 % n2 / n;
            n3 = 0;
            int n17 = 0;
            int n18 = 0;
            int n19 = 0;
            n13 = 0;
            long l2 = 0L;
            int n20 = this.getChildCount();
            for (n7 = 0; n7 < n20; ++n7) {
                View view = this.getChildAt(n7);
                if (view.getVisibility() == 8) {
                    l = l2;
                    n8 = n13;
                } else {
                    boolean bl = view instanceof ActionMenuItemView;
                    n12 = n19 + 1;
                    if (bl) {
                        view.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                    }
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    layoutParams.expanded = false;
                    layoutParams.extraPixels = 0;
                    layoutParams.cellsUsed = 0;
                    layoutParams.expandable = false;
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = 0;
                    bl = bl && ((ActionMenuItemView)view).hasText();
                    layoutParams.preventEdgeOffset = bl;
                    n2 = layoutParams.isOverflowButton ? 1 : n;
                    int n21 = ActionMenuView.measureChildForCells(view, n16, n2, n15, n14);
                    n9 = Math.max(n17, n21);
                    n2 = n18;
                    if (layoutParams.expandable) {
                        n2 = n18 + 1;
                    }
                    if (layoutParams.isOverflowButton) {
                        n13 = 1;
                    }
                    n11 = n - n21;
                    n10 = Math.max(n3, view.getMeasuredHeight());
                    n = n11;
                    n18 = n2;
                    n8 = n13;
                    n17 = n9;
                    n3 = n10;
                    l = l2;
                    n19 = n12;
                    if (n21 == 1) {
                        l = l2 | (long)(1 << n7);
                        n = n11;
                        n18 = n2;
                        n8 = n13;
                        n17 = n9;
                        n3 = n10;
                        n19 = n12;
                    }
                }
                n13 = n8;
                l2 = l;
            }
            n7 = n13 != 0 && n19 == 2 ? 1 : 0;
            n2 = 0;
            n8 = n;
            do {
                long l3;
                block41: {
                    block42: {
                        float f;
                        block43: {
                            block40: {
                                l = l2;
                                if (n18 <= 0) break block40;
                                l = l2;
                                if (n8 <= 0) break block40;
                                n12 = Integer.MAX_VALUE;
                                l3 = 0L;
                                n10 = 0;
                                for (n9 = 0; n9 < n20; ++n9) {
                                    LayoutParams layoutParams2 = (LayoutParams)this.getChildAt(n9).getLayoutParams();
                                    if (!layoutParams2.expandable) {
                                        l = l3;
                                        n = n10;
                                        n11 = n12;
                                    } else if (layoutParams2.cellsUsed < n12) {
                                        n11 = layoutParams2.cellsUsed;
                                        l = 1 << n9;
                                        n = 1;
                                    } else {
                                        n11 = n12;
                                        n = n10;
                                        l = l3;
                                        if (layoutParams2.cellsUsed == n12) {
                                            l = l3 | (long)(1 << n9);
                                            n = n10 + 1;
                                            n11 = n12;
                                        }
                                    }
                                    n12 = n11;
                                    n10 = n;
                                    l3 = l;
                                }
                                l2 |= l3;
                                if (n10 <= n8) break block41;
                                l = l2;
                            }
                            n = n13 == 0 && n19 == 1 ? 1 : 0;
                            n13 = n2;
                            if (n8 <= 0) break block42;
                            n13 = n2;
                            if (l == 0L) break block42;
                            if (n8 < n19 - 1 || n != 0) break block43;
                            n13 = n2;
                            if (n17 <= true) break block42;
                        }
                        float f2 = f = (float)Long.bitCount(l);
                        if (n == 0) {
                            float f3 = f;
                            if ((1L & l) != 0L) {
                                f3 = f;
                                if (!((LayoutParams)this.getChildAt((int)0).getLayoutParams()).preventEdgeOffset) {
                                    f3 = f - 0.5f;
                                }
                            }
                            f2 = f3;
                            if (((long)(1 << n20 - 1) & l) != 0L) {
                                f2 = f3;
                                if (!((LayoutParams)this.getChildAt((int)(n20 - 1)).getLayoutParams()).preventEdgeOffset) {
                                    f2 = f3 - 0.5f;
                                }
                            }
                        }
                        n13 = f2 > 0.0f ? (int)((float)(n8 * n16) / f2) : 0;
                        for (n18 = 0; n18 < n20; ++n18) {
                            if (((long)(1 << n18) & l) == 0L) {
                                n = n2;
                            } else {
                                View view = this.getChildAt(n18);
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (view instanceof ActionMenuItemView) {
                                    layoutParams.extraPixels = n13;
                                    layoutParams.expanded = true;
                                    if (n18 == 0 && !layoutParams.preventEdgeOffset) {
                                        layoutParams.leftMargin = -n13 / 2;
                                    }
                                    n = 1;
                                } else if (layoutParams.isOverflowButton) {
                                    layoutParams.extraPixels = n13;
                                    layoutParams.expanded = true;
                                    layoutParams.rightMargin = -n13 / 2;
                                    n = 1;
                                } else {
                                    if (n18 != 0) {
                                        layoutParams.leftMargin = n13 / 2;
                                    }
                                    n = n2;
                                    if (n18 != n20 - 1) {
                                        layoutParams.rightMargin = n13 / 2;
                                        n = n2;
                                    }
                                }
                            }
                            n2 = n;
                        }
                        n13 = n2;
                    }
                    if (n13 != 0) {
                        break;
                    }
                    break block39;
                }
                for (n = 0; n < n20; ++n) {
                    View view = this.getChildAt(n);
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    if (((long)(1 << n) & l3) == 0L) {
                        n2 = n8;
                        l = l2;
                        if (layoutParams.cellsUsed == n12 + 1) {
                            l = l2 | (long)(1 << n);
                            n2 = n8;
                        }
                    } else {
                        if (n7 != 0 && layoutParams.preventEdgeOffset && n8 == 1) {
                            view.setPadding(this.mGeneratedItemPadding + n16, 0, this.mGeneratedItemPadding, 0);
                        }
                        ++layoutParams.cellsUsed;
                        layoutParams.expanded = true;
                        n2 = n8 - 1;
                        l = l2;
                    }
                    n8 = n2;
                    l2 = l;
                }
                n2 = 1;
            } while (true);
            for (n = 0; n < n20; ++n) {
                View view = this.getChildAt(n);
                layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.expanded) continue;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(layoutParams.cellsUsed * n16 + layoutParams.extraPixels), (int)1073741824), n15);
            }
        }
        n = n4;
        if (n5 != 1073741824) {
            n = n3;
        }
        this.setMeasuredDimension(n6, n);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && layoutParams instanceof LayoutParams;
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        void var1_3;
        if (object == null) {
            return this.generateDefaultLayoutParams();
        }
        if (object instanceof LayoutParams) {
            LayoutParams layoutParams = new LayoutParams((LayoutParams)((Object)object));
        } else {
            LayoutParams layoutParams = new LayoutParams((ViewGroup.LayoutParams)object);
        }
        if (var1_3.gravity <= 0) {
            var1_3.gravity = 16;
        }
        return var1_3;
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams layoutParams = this.generateDefaultLayoutParams();
        layoutParams.isOverflowButton = true;
        return layoutParams;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public Menu getMenu() {
        if (this.mMenu == null) {
            void var1_3;
            Context context = this.getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter = this.mPresenter;
            if (this.mActionMenuPresenterCallback != null) {
                MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            } else {
                ActionMenuPresenterCallback actionMenuPresenterCallback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter.setCallback((MenuPresenter.Callback)var1_3);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public Drawable getOverflowIcon() {
        this.getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public int getWindowAnimations() {
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean hasSupportDividerBeforeChildAt(int n) {
        boolean bl;
        if (n == 0) {
            return false;
        }
        View view = this.getChildAt(n - 1);
        View view2 = this.getChildAt(n);
        boolean bl2 = bl = false;
        if (n < this.getChildCount()) {
            bl2 = bl;
            if (view instanceof ActionMenuChildView) {
                bl2 = false | ((ActionMenuChildView)view).needsDividerAfter();
            }
        }
        bl = bl2;
        if (n <= 0) return bl;
        bl = bl2;
        if (!(view2 instanceof ActionMenuChildView)) return bl;
        return bl2 | ((ActionMenuChildView)view2).needsDividerBefore();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dismissPopupMenus();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        LayoutParams layoutParams;
        View view;
        int n5;
        if (!this.mFormatItems) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
        int n6 = this.getChildCount();
        int n7 = (n4 - n2) / 2;
        int n8 = this.getDividerWidth();
        int n9 = 0;
        n4 = 0;
        n2 = n3 - n - this.getPaddingRight() - this.getPaddingLeft();
        int n10 = 0;
        bl = ViewUtils.isLayoutRtl((View)this);
        for (n5 = 0; n5 < n6; ++n5) {
            int n11;
            view = this.getChildAt(n5);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isOverflowButton) {
                int n12;
                n10 = n11 = view.getMeasuredWidth();
                if (this.hasSupportDividerBeforeChildAt(n5)) {
                    n10 = n11 + n8;
                }
                int n13 = view.getMeasuredHeight();
                if (bl) {
                    n11 = this.getPaddingLeft() + layoutParams.leftMargin;
                    n12 = n11 + n10;
                } else {
                    n12 = this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin;
                    n11 = n12 - n10;
                }
                int n14 = n7 - n13 / 2;
                view.layout(n11, n14, n12, n14 + n13);
                n2 -= n10;
                n10 = 1;
                continue;
            }
            n11 = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            n9 += n11;
            n11 = n2 - n11;
            n2 = n9;
            if (this.hasSupportDividerBeforeChildAt(n5)) {
                n2 = n9 + n8;
            }
            ++n4;
            n9 = n2;
            n2 = n11;
        }
        if (n6 == 1 && n10 == 0) {
            view = this.getChildAt(0);
            n2 = view.getMeasuredWidth();
            n4 = view.getMeasuredHeight();
            n = (n3 - n) / 2 - n2 / 2;
            n3 = n7 - n4 / 2;
            view.layout(n, n3, n + n2, n3 + n4);
            return;
        }
        n = n10 != 0 ? 0 : 1;
        n = (n = n4 - n) > 0 ? n2 / n : 0;
        n4 = Math.max(0, n);
        if (bl) {
            n2 = this.getWidth() - this.getPaddingRight();
            n = 0;
            while (n < n6) {
                view = this.getChildAt(n);
                layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = n2;
                if (view.getVisibility() != 8) {
                    if (layoutParams.isOverflowButton) {
                        n3 = n2;
                    } else {
                        n3 = view.getMeasuredWidth();
                        n5 = view.getMeasuredHeight();
                        n9 = n7 - n5 / 2;
                        view.layout((n2 -= layoutParams.rightMargin) - n3, n9, n2, n9 + n5);
                        n3 = n2 - (layoutParams.leftMargin + n3 + n4);
                    }
                }
                ++n;
                n2 = n3;
            }
            return;
        }
        n2 = this.getPaddingLeft();
        n = 0;
        while (n < n6) {
            view = this.getChildAt(n);
            layoutParams = (LayoutParams)view.getLayoutParams();
            n3 = n2;
            if (view.getVisibility() != 8) {
                if (layoutParams.isOverflowButton) {
                    n3 = n2;
                } else {
                    n3 = view.getMeasuredWidth();
                    n5 = view.getMeasuredHeight();
                    n9 = n7 - n5 / 2;
                    view.layout(n2 += layoutParams.leftMargin, n9, n2 + n3, n9 + n5);
                    n3 = n2 + (layoutParams.rightMargin + n3 + n4);
                }
            }
            ++n;
            n2 = n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onMeasure(int n, int n2) {
        boolean bl = this.mFormatItems;
        boolean bl2 = View.MeasureSpec.getMode((int)n) == 1073741824;
        this.mFormatItems = bl2;
        if (bl != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int n3 = View.MeasureSpec.getSize((int)n);
        if (this.mFormatItems && this.mMenu != null && n3 != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = n3;
            this.mMenu.onItemsChanged(true);
        }
        int n4 = this.getChildCount();
        if (this.mFormatItems && n4 > 0) {
            this.onMeasureExactFormat(n, n2);
            return;
        }
        n3 = 0;
        do {
            if (n3 >= n4) {
                super.onMeasure(n, n2);
                return;
            }
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(n3).getLayoutParams();
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
            ++n3;
        } while (true);
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mPresenter.setExpandedActionViewsExclusive(bl);
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(Drawable drawable2) {
        this.getMenu();
        this.mPresenter.setOverflowIcon(drawable2);
    }

    public void setOverflowReserved(boolean bl) {
        this.mReserveOverflow = bl;
    }

    public void setPopupTheme(int n) {
        block3: {
            block2: {
                if (this.mPopupTheme == n) break block2;
                this.mPopupTheme = n;
                if (n != 0) break block3;
                this.mPopupContext = this.getContext();
            }
            return;
        }
        this.mPopupContext = new ContextThemeWrapper(this.getContext(), n);
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        this.mPresenter.setMenuView(this);
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public static interface ActionMenuChildView {
        public boolean needsDividerAfter();

        public boolean needsDividerBefore();
    }

    private static class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.isOverflowButton = false;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    private class MenuBuilderCallback
    implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
            }
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

}

