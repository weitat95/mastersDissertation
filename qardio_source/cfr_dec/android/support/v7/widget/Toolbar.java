/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.RtlSpacingHelper;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
extends ViewGroup {
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity = 8388627;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews = new ArrayList();
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public Toolbar(Context object, AttributeSet object2, int n) {
        super((Context)object, object2, n);
        int n2;
        this.mHiddenViews = new ArrayList();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable(){

            @Override
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        object = TintTypedArray.obtainStyledAttributes(this.getContext(), object2, R.styleable.Toolbar, n, 0);
        this.mTitleTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = ((TintTypedArray)object).getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = ((TintTypedArray)object).getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = ((TintTypedArray)object).getInteger(R.styleable.Toolbar_buttonGravity, 48);
        n = n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
        if (((TintTypedArray)object).hasValue(R.styleable.Toolbar_titleMargins)) {
            n = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, n2);
        }
        this.mTitleMarginBottom = n;
        this.mTitleMarginTop = n;
        this.mTitleMarginEnd = n;
        this.mTitleMarginStart = n;
        n = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (n >= 0) {
            this.mTitleMarginStart = n;
        }
        if ((n = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1)) >= 0) {
            this.mTitleMarginEnd = n;
        }
        if ((n = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1)) >= 0) {
            this.mTitleMarginTop = n;
        }
        if ((n = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1)) >= 0) {
            this.mTitleMarginBottom = n;
        }
        this.mMaxButtonHeight = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        n = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int n3 = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        int n4 = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n3, n4);
        if (n != Integer.MIN_VALUE || n2 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(n, n2);
        }
        this.mContentInsetStartWithNavigation = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = ((TintTypedArray)object).getDrawable(R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = ((TintTypedArray)object).getText(R.styleable.Toolbar_collapseContentDescription);
        object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            this.setTitle((CharSequence)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_subtitle)))) {
            this.setSubtitle((CharSequence)object2);
        }
        this.mPopupContext = this.getContext();
        this.setPopupTheme(((TintTypedArray)object).getResourceId(R.styleable.Toolbar_popupTheme, 0));
        object2 = ((TintTypedArray)object).getDrawable(R.styleable.Toolbar_navigationIcon);
        if (object2 != null) {
            this.setNavigationIcon((Drawable)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_navigationContentDescription)))) {
            this.setNavigationContentDescription((CharSequence)object2);
        }
        if ((object2 = ((TintTypedArray)object).getDrawable(R.styleable.Toolbar_logo)) != null) {
            this.setLogo((Drawable)object2);
        }
        if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.Toolbar_logoDescription)))) {
            this.setLogoDescription((CharSequence)object2);
        }
        if (((TintTypedArray)object).hasValue(R.styleable.Toolbar_titleTextColor)) {
            this.setTitleTextColor(((TintTypedArray)object).getColor(R.styleable.Toolbar_titleTextColor, -1));
        }
        if (((TintTypedArray)object).hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            this.setSubtitleTextColor(((TintTypedArray)object).getColor(R.styleable.Toolbar_subtitleTextColor, -1));
        }
        ((TintTypedArray)object).recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addCustomViewsWithGravity(List<View> list, int n) {
        boolean bl = true;
        if (ViewCompat.getLayoutDirection((View)this) != 1) {
            bl = false;
        }
        int n2 = this.getChildCount();
        int n3 = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection((View)this));
        list.clear();
        if (bl) {
            for (n = n2 - 1; n >= 0; --n) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n3) continue;
                list.add(view);
            }
            return;
        } else {
            for (n = 0; n < n2; ++n) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n3) continue;
                list.add(view);
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void addSystemView(View view, boolean bl) {
        void var3_5;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            LayoutParams layoutParams2 = this.generateDefaultLayoutParams();
        } else if (!this.checkLayoutParams(layoutParams)) {
            LayoutParams layoutParams3 = this.generateLayoutParams(layoutParams);
        } else {
            LayoutParams layoutParams4 = (LayoutParams)layoutParams;
        }
        var3_5.mViewType = 1;
        if (bl && this.mExpandedActionView != null) {
            view.setLayoutParams((ViewGroup.LayoutParams)var3_5);
            this.mHiddenViews.add(view);
            return;
        }
        this.addView(view, (ViewGroup.LayoutParams)var3_5);
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(this.getContext());
        }
    }

    private void ensureMenu() {
        this.ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(this.getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 0x800005 | this.mButtonGravity & 0x70;
            this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.addSystemView((View)this.mMenuView, false);
        }
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 0x800003 | this.mButtonGravity & 0x70;
            this.mNavButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getChildHorizontalGravity(int n) {
        int n2;
        int n3 = ViewCompat.getLayoutDirection((View)this);
        n = n2 = GravityCompat.getAbsoluteGravity(n, n3) & 7;
        switch (n2) {
            default: {
                if (n3 != 1) return 3;
                n = 5;
            }
            case 1: 
            case 3: 
            case 5: {
                return n;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getChildTop(View view, int n) {
        int n2;
        int n3;
        int n4;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n5 = view.getMeasuredHeight();
        n = n > 0 ? (n5 - n) / 2 : 0;
        switch (this.getChildVerticalGravity(layoutParams.gravity)) {
            default: {
                n2 = this.getPaddingTop();
                n = this.getPaddingBottom();
                n4 = this.getHeight();
                n3 = (n4 - n2 - n - n5) / 2;
                if (n3 >= layoutParams.topMargin) break;
                n = layoutParams.topMargin;
                return n2 + n;
            }
            case 48: {
                return this.getPaddingTop() - n;
            }
            case 80: {
                return this.getHeight() - this.getPaddingBottom() - n5 - layoutParams.bottomMargin - n;
            }
        }
        n5 = n4 - n - n5 - n3 - n2;
        n = n3;
        if (n5 >= layoutParams.bottomMargin) return n2 + n;
        n = Math.max(0, n3 - (layoutParams.bottomMargin - n5));
        return n2 + n;
    }

    private int getChildVerticalGravity(int n) {
        int n2;
        n = n2 = n & 0x70;
        switch (n2) {
            default: {
                n = this.mGravity & 0x70;
            }
            case 16: 
            case 48: 
            case 80: 
        }
        return n;
    }

    private int getHorizontalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)view) + MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)view);
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.getContext());
    }

    private int getVerticalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return view.topMargin + view.bottomMargin;
    }

    private int getViewListMeasuredWidth(List<View> list, int[] view) {
        int n = view[0];
        int n2 = view[1];
        int n3 = 0;
        int n4 = list.size();
        for (int i = 0; i < n4; ++i) {
            view = list.get(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n = layoutParams.leftMargin - n;
            n2 = layoutParams.rightMargin - n2;
            int n5 = Math.max(0, n);
            int n6 = Math.max(0, n2);
            n = Math.max(0, -n);
            n2 = Math.max(0, -n2);
            n3 += view.getMeasuredWidth() + n5 + n6;
        }
        return n3;
    }

    private boolean isChildOrHidden(View view) {
        return view.getParent() == this || this.mHiddenViews.contains((Object)view);
    }

    private int layoutChildLeft(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.leftMargin - arrn[0];
        n += Math.max(0, n3);
        arrn[0] = Math.max(0, -n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n, n2, n + n3, view.getMeasuredHeight() + n2);
        return n + (layoutParams.rightMargin + n3);
    }

    private int layoutChildRight(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.rightMargin - arrn[1];
        n -= Math.max(0, n3);
        arrn[1] = Math.max(0, -n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n - n3, n2, n, view.getMeasuredHeight() + n2);
        return n - (layoutParams.leftMargin + n3);
    }

    private int measureChildCollapseMargins(View view, int n, int n2, int n3, int n4, int[] arrn) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n5 = marginLayoutParams.leftMargin - arrn[0];
        int n6 = marginLayoutParams.rightMargin - arrn[1];
        int n7 = Math.max(0, n5) + Math.max(0, n6);
        arrn[0] = Math.max(0, -n5);
        arrn[1] = Math.max(0, -n6);
        view.measure(Toolbar.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + n7 + n2), (int)marginLayoutParams.width), Toolbar.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4), (int)marginLayoutParams.height));
        return view.getMeasuredWidth() + n7;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void measureChildConstrained(View view, int n, int n2, int n3, int n4, int n5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n6 = Toolbar.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2), (int)marginLayoutParams.width);
        n2 = Toolbar.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4), (int)marginLayoutParams.height);
        n3 = View.MeasureSpec.getMode((int)n2);
        n = n2;
        if (n3 != 1073741824) {
            n = n2;
            if (n5 >= 0) {
                n = n3 != 0 ? Math.min(View.MeasureSpec.getSize((int)n2), n5) : n5;
                n = View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824);
            }
        }
        view.measure(n6, n);
    }

    private void postShowOverflowMenu() {
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
        this.post(this.mShowOverflowMenuRunnable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldCollapse() {
        if (this.mCollapsible) {
            int n = this.getChildCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return true;
                }
                View view = this.getChildAt(n2);
                if (this.shouldLayout(view) && view.getMeasuredWidth() > 0 && view.getMeasuredHeight() > 0) break;
                ++n2;
            } while (true);
        }
        return false;
    }

    private boolean shouldLayout(View view) {
        return view != null && view.getParent() == this && view.getVisibility() != 8;
    }

    void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; --i) {
            this.addView(this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }

    public boolean canShowOverflowMenu() {
        return this.getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void collapseActionView() {
        if (this.mExpandedMenuPresenter == null) {
            return;
        }
        MenuItemImpl menuItemImpl = this.mExpandedMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl == null) return;
        menuItemImpl.collapseActionView();
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new AppCompatImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams layoutParams = this.generateDefaultLayoutParams();
            layoutParams.gravity = 0x800003 | this.mButtonGravity & 0x70;
            layoutParams.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd();
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        if (this.mContentInsetEndWithActions != Integer.MIN_VALUE) {
            return this.mContentInsetEndWithActions;
        }
        return this.getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft();
        }
        return 0;
    }

    public int getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight();
        }
        return 0;
    }

    public int getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        if (this.mContentInsetStartWithNavigation != Integer.MIN_VALUE) {
            return this.mContentInsetStartWithNavigation;
        }
        return this.getContentInsetStart();
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getCurrentContentInsetEnd() {
        boolean bl = false;
        if (this.mMenuView != null) {
            MenuBuilder menuBuilder = this.mMenuView.peekMenu();
            bl = menuBuilder != null && menuBuilder.hasVisibleItems();
        }
        if (bl) {
            return Math.max(this.getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return this.getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return this.getCurrentContentInsetEnd();
        }
        return this.getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return this.getCurrentContentInsetStart();
        }
        return this.getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        if (this.getNavigationIcon() != null) {
            return Math.max(this.getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return this.getContentInsetStart();
    }

    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        this.ensureMenu();
        return this.mMenuView.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }

    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    public Drawable getOverflowIcon() {
        this.ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    public boolean hasExpandedActionView() {
        return this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null;
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n == 9 && !bl) {
                this.mEatingHover = true;
            }
        }
        if (n == 10 || n == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block38: {
            block39: {
                block37: {
                    var8_6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                    var12_7 = this.getWidth();
                    var14_8 = this.getHeight();
                    var11_9 = this.getPaddingLeft();
                    var13_10 = this.getPaddingRight();
                    var10_11 = this.getPaddingTop();
                    var15_12 = this.getPaddingBottom();
                    var4_4 = var11_9;
                    var7_13 = var12_7 - var13_10;
                    var19_14 = this.mTempMargins;
                    var19_14[1] = 0;
                    var19_14[0] = 0;
                    var2_2 = ViewCompat.getMinimumHeight((View)this);
                    var6_15 = var2_2 >= 0 ? Math.min(var2_2, var5_5 - var3_3) : 0;
                    var2_2 = var4_4;
                    var3_3 = var7_13;
                    if (this.shouldLayout((View)this.mNavButtonView)) {
                        if (var8_6 != 0) {
                            var3_3 = this.layoutChildRight((View)this.mNavButtonView, var7_13, var19_14, var6_15);
                            var2_2 = var4_4;
                        } else {
                            var2_2 = this.layoutChildLeft((View)this.mNavButtonView, var4_4, var19_14, var6_15);
                            var3_3 = var7_13;
                        }
                    }
                    var4_4 = var2_2;
                    var5_5 = var3_3;
                    if (this.shouldLayout((View)this.mCollapseButtonView)) {
                        if (var8_6 != 0) {
                            var5_5 = this.layoutChildRight((View)this.mCollapseButtonView, var3_3, var19_14, var6_15);
                            var4_4 = var2_2;
                        } else {
                            var4_4 = this.layoutChildLeft((View)this.mCollapseButtonView, var2_2, var19_14, var6_15);
                            var5_5 = var3_3;
                        }
                    }
                    var3_3 = var4_4;
                    var2_2 = var5_5;
                    if (this.shouldLayout((View)this.mMenuView)) {
                        if (var8_6 != 0) {
                            var3_3 = this.layoutChildLeft((View)this.mMenuView, var4_4, var19_14, var6_15);
                            var2_2 = var5_5;
                        } else {
                            var2_2 = this.layoutChildRight((View)this.mMenuView, var5_5, var19_14, var6_15);
                            var3_3 = var4_4;
                        }
                    }
                    var4_4 = this.getCurrentContentInsetLeft();
                    var5_5 = this.getCurrentContentInsetRight();
                    var19_14[0] = Math.max(0, var4_4 - var3_3);
                    var19_14[1] = Math.max(0, var5_5 - (var12_7 - var13_10 - var2_2));
                    var4_4 = Math.max(var3_3, var4_4);
                    var5_5 = Math.min(var2_2, var12_7 - var13_10 - var5_5);
                    var2_2 = var4_4;
                    var3_3 = var5_5;
                    if (this.shouldLayout(this.mExpandedActionView)) {
                        if (var8_6 != 0) {
                            var3_3 = this.layoutChildRight(this.mExpandedActionView, var5_5, var19_14, var6_15);
                            var2_2 = var4_4;
                        } else {
                            var2_2 = this.layoutChildLeft(this.mExpandedActionView, var4_4, var19_14, var6_15);
                            var3_3 = var5_5;
                        }
                    }
                    var4_4 = var2_2;
                    var5_5 = var3_3;
                    if (this.shouldLayout((View)this.mLogoView)) {
                        if (var8_6 != 0) {
                            var5_5 = this.layoutChildRight((View)this.mLogoView, var3_3, var19_14, var6_15);
                            var4_4 = var2_2;
                        } else {
                            var4_4 = this.layoutChildLeft((View)this.mLogoView, var2_2, var19_14, var6_15);
                            var5_5 = var3_3;
                        }
                    }
                    var1_1 = this.shouldLayout((View)this.mTitleTextView);
                    var16_16 = this.shouldLayout((View)this.mSubtitleTextView);
                    var2_2 = 0;
                    if (var1_1) {
                        var17_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                        var2_2 = 0 + (var17_17.topMargin + this.mTitleTextView.getMeasuredHeight() + var17_17.bottomMargin);
                    }
                    var9_18 = var2_2;
                    if (var16_16) {
                        var17_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                        var9_18 = var2_2 + (var17_17.topMargin + this.mSubtitleTextView.getMeasuredHeight() + var17_17.bottomMargin);
                    }
                    if (var1_1) break block37;
                    var3_3 = var4_4;
                    var2_2 = var5_5;
                    if (!var16_16) break block38;
                }
                var17_17 = var1_1 != false ? this.mTitleTextView : this.mSubtitleTextView;
                var18_19 /* !! */  = var16_16 != false ? this.mSubtitleTextView : this.mTitleTextView;
                var17_17 = (LayoutParams)var17_17.getLayoutParams();
                var18_19 /* !! */  = (LayoutParams)var18_19 /* !! */ .getLayoutParams();
                var7_13 = var1_1 != false && this.mTitleTextView.getMeasuredWidth() > 0 || var16_16 != false && this.mSubtitleTextView.getMeasuredWidth() > 0 ? 1 : 0;
                switch (this.mGravity & 112) {
                    default: {
                        var3_3 = (var14_8 - var10_11 - var15_12 - var9_18) / 2;
                        if (var3_3 >= var17_17.topMargin + this.mTitleMarginTop) ** GOTO lbl95
                        var2_2 = var17_17.topMargin + this.mTitleMarginTop;
                        ** GOTO lbl99
                    }
                    case 48: {
                        var2_2 = this.getPaddingTop() + var17_17.topMargin + this.mTitleMarginTop;
                        break block39;
                    }
lbl95:
                    // 1 sources
                    var9_18 = var14_8 - var15_12 - var9_18 - var3_3 - var10_11;
                    var2_2 = var3_3;
                    if (var9_18 < var17_17.bottomMargin + this.mTitleMarginBottom) {
                        var2_2 = Math.max(0, var3_3 - (var18_19 /* !! */ .bottomMargin + this.mTitleMarginBottom - var9_18));
                    }
lbl99:
                    // 4 sources
                    var2_2 = var10_11 + var2_2;
                    break block39;
                    case 80: 
                }
                var2_2 = var14_8 - var15_12 - var18_19 /* !! */ .bottomMargin - this.mTitleMarginBottom - var9_18;
            }
            if (var8_6 != 0) {
                var3_3 = var7_13 != 0 ? this.mTitleMarginStart : 0;
                var19_14[1] = Math.max(0, -var3_3);
                var9_18 = var5_5 -= Math.max(0, var3_3 -= var19_14[1]);
                var3_3 = var5_5;
                var8_6 = var9_18;
                var10_11 = var2_2;
                if (var1_1) {
                    var17_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                    var8_6 = var9_18 - this.mTitleTextView.getMeasuredWidth();
                    var10_11 = var2_2 + this.mTitleTextView.getMeasuredHeight();
                    this.mTitleTextView.layout(var8_6, var2_2, var9_18, var10_11);
                    var8_6 -= this.mTitleMarginEnd;
                    var10_11 += var17_17.bottomMargin;
                }
                var9_18 = var3_3;
                if (var16_16) {
                    var17_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    var2_2 = var10_11 + var17_17.topMargin;
                    var9_18 = this.mSubtitleTextView.getMeasuredWidth();
                    var10_11 = var2_2 + this.mSubtitleTextView.getMeasuredHeight();
                    this.mSubtitleTextView.layout(var3_3 - var9_18, var2_2, var3_3, var10_11);
                    var9_18 = var3_3 - this.mTitleMarginEnd;
                    var2_2 = var17_17.bottomMargin;
                }
                var3_3 = var4_4;
                var2_2 = var5_5;
                if (var7_13 != 0) {
                    var2_2 = Math.min(var8_6, var9_18);
                    var3_3 = var4_4;
                }
            } else {
                var3_3 = var7_13 != 0 ? this.mTitleMarginStart : 0;
                var8_6 = var3_3 - var19_14[0];
                var3_3 = var4_4 + Math.max(0, var8_6);
                var19_14[0] = Math.max(0, -var8_6);
                var9_18 = var3_3;
                var4_4 = var3_3;
                var8_6 = var9_18;
                var10_11 = var2_2;
                if (var1_1) {
                    var17_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                    var8_6 = var9_18 + this.mTitleTextView.getMeasuredWidth();
                    var10_11 = var2_2 + this.mTitleTextView.getMeasuredHeight();
                    this.mTitleTextView.layout(var9_18, var2_2, var8_6, var10_11);
                    var8_6 += this.mTitleMarginEnd;
                    var10_11 += var17_17.bottomMargin;
                }
                var9_18 = var4_4;
                if (var16_16) {
                    var17_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    var2_2 = var10_11 + var17_17.topMargin;
                    var9_18 = var4_4 + this.mSubtitleTextView.getMeasuredWidth();
                    var10_11 = var2_2 + this.mSubtitleTextView.getMeasuredHeight();
                    this.mSubtitleTextView.layout(var4_4, var2_2, var9_18, var10_11);
                    var9_18 += this.mTitleMarginEnd;
                    var2_2 = var17_17.bottomMargin;
                }
                var2_2 = var5_5;
                if (var7_13 != 0) {
                    var3_3 = Math.max(var8_6, var9_18);
                    var2_2 = var5_5;
                }
            }
        }
        this.addCustomViewsWithGravity(this.mTempViews, 3);
        var5_5 = this.mTempViews.size();
        for (var4_4 = 0; var4_4 < var5_5; ++var4_4) {
            var3_3 = this.layoutChildLeft(this.mTempViews.get(var4_4), var3_3, var19_14, var6_15);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 5);
        var7_13 = this.mTempViews.size();
        var5_5 = 0;
        var4_4 = var2_2;
        for (var2_2 = var5_5; var2_2 < var7_13; ++var2_2) {
            var4_4 = this.layoutChildRight(this.mTempViews.get(var2_2), var4_4, var19_14, var6_15);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 1);
        var2_2 = this.getViewListMeasuredWidth(this.mTempViews, var19_14);
        var5_5 = var11_9 + (var12_7 - var11_9 - var13_10) / 2 - var2_2 / 2;
        var7_13 = var5_5 + var2_2;
        if (var5_5 < var3_3) {
            var2_2 = var3_3;
        } else {
            var2_2 = var5_5;
            if (var7_13 > var4_4) {
                var2_2 = var5_5 - (var7_13 - var4_4);
            }
        }
        var4_4 = this.mTempViews.size();
        var3_3 = 0;
        do {
            if (var3_3 >= var4_4) {
                this.mTempViews.clear();
                return;
            }
            var2_2 = this.layoutChildLeft(this.mTempViews.get(var3_3), var2_2, var19_14, var6_15);
            ++var3_3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5 = 0;
        int n6 = 0;
        int[] arrn = this.mTempMargins;
        if (ViewUtils.isLayoutRtl((View)this)) {
            n3 = 1;
            n4 = 0;
        } else {
            n3 = 0;
            n4 = 1;
        }
        int n7 = 0;
        if (this.shouldLayout((View)this.mNavButtonView)) {
            this.measureChildConstrained((View)this.mNavButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n7 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mNavButtonView);
            n5 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mNavButtonView));
            n6 = View.combineMeasuredStates((int)0, (int)this.mNavButtonView.getMeasuredState());
        }
        int n8 = n6;
        int n9 = n5;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            this.measureChildConstrained((View)this.mCollapseButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n7 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mCollapseButtonView);
            n9 = Math.max(n5, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mCollapseButtonView));
            n8 = View.combineMeasuredStates((int)n6, (int)this.mCollapseButtonView.getMeasuredState());
        }
        n6 = this.getCurrentContentInsetStart();
        int n10 = 0 + Math.max(n6, n7);
        arrn[n3] = Math.max(0, n6 - n7);
        n7 = 0;
        n6 = n8;
        n5 = n9;
        if (this.shouldLayout((View)this.mMenuView)) {
            this.measureChildConstrained((View)this.mMenuView, n, n10, n2, 0, this.mMaxButtonHeight);
            n7 = this.mMenuView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mMenuView);
            n5 = Math.max(n9, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins((View)this.mMenuView));
            n6 = View.combineMeasuredStates((int)n8, (int)this.mMenuView.getMeasuredState());
        }
        n8 = this.getCurrentContentInsetEnd();
        n3 = n10 + Math.max(n8, n7);
        arrn[n4] = Math.max(0, n8 - n7);
        n4 = n3;
        n8 = n6;
        n9 = n5;
        if (this.shouldLayout(this.mExpandedActionView)) {
            n4 = n3 + this.measureChildCollapseMargins(this.mExpandedActionView, n, n3, n2, 0, arrn);
            n9 = Math.max(n5, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
            n8 = View.combineMeasuredStates((int)n6, (int)this.mExpandedActionView.getMeasuredState());
        }
        n6 = n4;
        n5 = n8;
        n7 = n9;
        if (this.shouldLayout((View)this.mLogoView)) {
            n6 = n4 + this.measureChildCollapseMargins((View)this.mLogoView, n, n4, n2, 0, arrn);
            n7 = Math.max(n9, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins((View)this.mLogoView));
            n5 = View.combineMeasuredStates((int)n8, (int)this.mLogoView.getMeasuredState());
        }
        n10 = this.getChildCount();
        n4 = n7;
        n8 = n5;
        n7 = n6;
        for (n9 = 0; n9 < n10; ++n9) {
            View view = this.getChildAt(n9);
            n6 = n7;
            n5 = n8;
            n3 = n4;
            if (((LayoutParams)view.getLayoutParams()).mViewType == 0) {
                if (!this.shouldLayout(view)) {
                    n3 = n4;
                    n5 = n8;
                    n6 = n7;
                } else {
                    n6 = n7 + this.measureChildCollapseMargins(view, n, n7, n2, 0, arrn);
                    n3 = Math.max(n4, view.getMeasuredHeight() + this.getVerticalMargins(view));
                    n5 = View.combineMeasuredStates((int)n8, (int)view.getMeasuredState());
                }
            }
            n7 = n6;
            n8 = n5;
            n4 = n3;
        }
        n5 = 0;
        n6 = 0;
        int n11 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int n12 = this.mTitleMarginStart + this.mTitleMarginEnd;
        n9 = n8;
        if (this.shouldLayout((View)this.mTitleTextView)) {
            this.measureChildCollapseMargins((View)this.mTitleTextView, n, n7 + n12, n2, n11, arrn);
            n5 = this.mTitleTextView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mTitleTextView);
            n6 = this.mTitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mTitleTextView);
            n9 = View.combineMeasuredStates((int)n8, (int)this.mTitleTextView.getMeasuredState());
        }
        n3 = n9;
        n10 = n6;
        n8 = n5;
        if (this.shouldLayout((View)this.mSubtitleTextView)) {
            n8 = Math.max(n5, this.measureChildCollapseMargins((View)this.mSubtitleTextView, n, n7 + n12, n2, n6 + n11, arrn));
            n10 = n6 + (this.mSubtitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mSubtitleTextView));
            n3 = View.combineMeasuredStates((int)n9, (int)this.mSubtitleTextView.getMeasuredState());
        }
        n9 = Math.max(n4, n10);
        n4 = this.getPaddingLeft();
        n10 = this.getPaddingRight();
        n6 = this.getPaddingTop();
        n5 = this.getPaddingBottom();
        n8 = View.resolveSizeAndState((int)Math.max(n7 + n8 + (n4 + n10), this.getSuggestedMinimumWidth()), (int)n, (int)(0xFF000000 & n3));
        n = View.resolveSizeAndState((int)Math.max(n9 + (n6 + n5), this.getSuggestedMinimumHeight()), (int)n2, (int)(n3 << 16));
        if (this.shouldCollapse()) {
            n = 0;
        }
        this.setMeasuredDimension(n8, n);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        } else {
            MenuItem menuItem;
            void var1_3;
            SavedState savedState = (SavedState)object;
            super.onRestoreInstanceState(savedState.getSuperState());
            if (this.mMenuView != null) {
                MenuBuilder menuBuilder = this.mMenuView.peekMenu();
            } else {
                Object var1_5 = null;
            }
            if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && var1_3 != null && (menuItem = var1_3.findItem(savedState.expandedMenuItemId)) != null) {
                menuItem.expandActionView();
            }
            if (!savedState.isOverflowOpen) return;
            {
                this.postShowOverflowMenu();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRtlPropertiesChanged(int n) {
        boolean bl = true;
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(n);
        }
        this.ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (n != 1) {
            bl = false;
        }
        rtlSpacingHelper.setDirection(bl);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = this.isOverflowMenuShowing();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n == 0 && !bl) {
                this.mEatingTouch = true;
            }
        }
        if (n == 1 || n == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    void removeChildrenForExpandedActionView() {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            View view = this.getChildAt(i);
            if (((LayoutParams)view.getLayoutParams()).mViewType == 2 || view == this.mMenuView) continue;
            this.removeViewAt(i);
            this.mHiddenViews.add(view);
        }
    }

    public void setCollapsible(boolean bl) {
        this.mCollapsible = bl;
        this.requestLayout();
    }

    public void setContentInsetEndWithActions(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = n2;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = n2;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetsAbsolute(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n, n2);
    }

    public void setContentInsetsRelative(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setRelative(n, n2);
    }

    public void setLogo(int n) {
        this.setLogo(AppCompatResources.getDrawable(this.getContext(), n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setLogo(Drawable drawable2) {
        if (drawable2 != null) {
            this.ensureLogoView();
            if (!this.isChildOrHidden((View)this.mLogoView)) {
                this.addSystemView((View)this.mLogoView, true);
            }
        } else if (this.mLogoView != null && this.isChildOrHidden((View)this.mLogoView)) {
            this.removeView((View)this.mLogoView);
            this.mHiddenViews.remove((Object)this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable2);
        }
    }

    public void setLogoDescription(int n) {
        this.setLogoDescription(this.getContext().getText(n));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(charSequence);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        MenuBuilder menuBuilder2;
        block8: {
            block7: {
                if (menuBuilder == null && this.mMenuView == null) break block7;
                this.ensureMenuView();
                menuBuilder2 = this.mMenuView.peekMenu();
                if (menuBuilder2 != menuBuilder) break block8;
            }
            return;
        }
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mOuterActionMenuPresenter);
            menuBuilder2.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setNavigationContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getContext().getText(n) : null;
        this.setNavigationContentDescription(charSequence);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(int n) {
        this.setNavigationIcon(AppCompatResources.getDrawable(this.getContext(), n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setNavigationIcon(Drawable drawable2) {
        if (drawable2 != null) {
            this.ensureNavButtonView();
            if (!this.isChildOrHidden((View)this.mNavButtonView)) {
                this.addSystemView((View)this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && this.isChildOrHidden((View)this.mNavButtonView)) {
            this.removeView((View)this.mNavButtonView);
            this.mHiddenViews.remove((Object)this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(drawable2);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(Drawable drawable2) {
        this.ensureMenu();
        this.mMenuView.setOverflowIcon(drawable2);
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

    public void setSubtitle(int n) {
        this.setSubtitle(this.getContext().getText(n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mSubtitleTextView == null) {
                Context context = this.getContext();
                this.mSubtitleTextView = new AppCompatTextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mSubtitleTextView)) {
                this.addSystemView((View)this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && this.isChildOrHidden((View)this.mSubtitleTextView)) {
            this.removeView((View)this.mSubtitleTextView);
            this.mHiddenViews.remove((Object)this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setSubtitleTextAppearance(Context context, int n) {
        this.mSubtitleTextAppearance = n;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, n);
        }
    }

    public void setSubtitleTextColor(int n) {
        this.mSubtitleTextColor = n;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(n);
        }
    }

    public void setTitle(int n) {
        this.setTitle(this.getContext().getText(n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mTitleTextView == null) {
                Context context = this.getContext();
                this.mTitleTextView = new AppCompatTextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mTitleTextView)) {
                this.addSystemView((View)this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && this.isChildOrHidden((View)this.mTitleTextView)) {
            this.removeView((View)this.mTitleTextView);
            this.mHiddenViews.remove((Object)this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public void setTitleMargin(int n, int n2, int n3, int n4) {
        this.mTitleMarginStart = n;
        this.mTitleMarginTop = n2;
        this.mTitleMarginEnd = n3;
        this.mTitleMarginBottom = n4;
        this.requestLayout();
    }

    public void setTitleMarginBottom(int n) {
        this.mTitleMarginBottom = n;
        this.requestLayout();
    }

    public void setTitleMarginEnd(int n) {
        this.mTitleMarginEnd = n;
        this.requestLayout();
    }

    public void setTitleMarginStart(int n) {
        this.mTitleMarginStart = n;
        this.requestLayout();
    }

    public void setTitleMarginTop(int n) {
        this.mTitleMarginTop = n;
        this.requestLayout();
    }

    public void setTitleTextAppearance(Context context, int n) {
        this.mTitleTextAppearance = n;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, n);
        }
    }

    public void setTitleTextColor(int n) {
        this.mTitleTextColor = n;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(n);
        }
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    private class ExpandedActionViewMenuPresenter
    implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        ExpandedActionViewMenuPresenter() {
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
            Toolbar.this.removeView((View)Toolbar.this.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView();
            if (Toolbar.this.mCollapseButtonView.getParent() != Toolbar.this) {
                Toolbar.this.addView((View)Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
            this.mCurrentExpandedItem = menuItemImpl;
            if (Toolbar.this.mExpandedActionView.getParent() != Toolbar.this) {
                object = Toolbar.this.generateDefaultLayoutParams();
                ((LayoutParams)object).gravity = 0x800003 | Toolbar.this.mButtonGravity & 0x70;
                ((LayoutParams)object).mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)object);
                Toolbar.this.addView(Toolbar.this.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override
        public boolean flagActionItems() {
            return false;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            if (this.mMenu != null && this.mCurrentExpandedItem != null) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menuBuilder;
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override
        public void setCallback(MenuPresenter.Callback callback) {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void updateMenuView(boolean bl) {
            boolean bl2;
            if (this.mCurrentExpandedItem == null) return;
            boolean bl3 = bl2 = false;
            if (this.mMenu != null) {
                int n = this.mMenu.size();
                int n2 = 0;
                do {
                    bl3 = bl2;
                    if (n2 >= n) break;
                    if (this.mMenu.getItem(n2) == this.mCurrentExpandedItem) {
                        return;
                    }
                    ++n2;
                } while (true);
            }
            if (bl3) return;
            this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
        }
    }

    public static class LayoutParams
    extends ActionBar.LayoutParams {
        int mViewType = 0;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.gravity = 8388627;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = layoutParams.mViewType;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super((ViewGroup.LayoutParams)marginLayoutParams);
            this.copyMarginsFromCompat(marginLayoutParams);
        }

        void copyMarginsFromCompat(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int expandedMenuItemId;
        boolean isOverflowOpen;

        /*
         * Enabled aggressive block sorting
         */
        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.expandedMenuItemId = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.isOverflowOpen = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.expandedMenuItemId);
            n = this.isOverflowOpen ? 1 : 0;
            parcel.writeInt(n);
        }

    }

}

