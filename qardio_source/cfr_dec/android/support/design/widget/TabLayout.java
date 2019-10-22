/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.text.Layout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.Interpolator
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.TabItem;
import android.support.design.widget.ThemeUtils;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.TooltipCompat;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout
extends HorizontalScrollView {
    private static final Pools.Pool<Tab> sTabPool = new Pools.SynchronizedPool<Tab>(16);
    private AdapterChangeListener mAdapterChangeListener;
    private int mContentInsetStart;
    private OnTabSelectedListener mCurrentVpSelectedListener;
    int mMode;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    private final int mRequestedTabMaxWidth;
    private final int mRequestedTabMinWidth;
    private ValueAnimator mScrollAnimator;
    private final int mScrollableTabMinWidth;
    private OnTabSelectedListener mSelectedListener;
    private final ArrayList<OnTabSelectedListener> mSelectedListeners;
    private Tab mSelectedTab;
    private boolean mSetupViewPagerImplicitly;
    final int mTabBackgroundResId;
    int mTabGravity;
    int mTabMaxWidth;
    int mTabPaddingBottom;
    int mTabPaddingEnd;
    int mTabPaddingStart;
    int mTabPaddingTop;
    private final SlidingTabStrip mTabStrip;
    int mTabTextAppearance;
    ColorStateList mTabTextColors;
    float mTabTextMultiLineSize;
    float mTabTextSize;
    private final Pools.Pool<TabView> mTabViewPool;
    private final ArrayList<Tab> mTabs;
    ViewPager mViewPager;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TabLayout(Context context, AttributeSet attributeSet, int n) {
        block4: {
            super(context, attributeSet, n);
            this.mTabs = new ArrayList();
            this.mTabMaxWidth = Integer.MAX_VALUE;
            this.mSelectedListeners = new ArrayList();
            this.mTabViewPool = new Pools.SimplePool<TabView>(12);
            ThemeUtils.checkAppCompatTheme(context);
            this.setHorizontalScrollBarEnabled(false);
            this.mTabStrip = new SlidingTabStrip(context);
            super.addView((View)this.mTabStrip, 0, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1));
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.TabLayout, n, R.style.Widget_Design_TabLayout);
            this.mTabStrip.setSelectedIndicatorHeight(attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, 0));
            this.mTabStrip.setSelectedIndicatorColor(attributeSet.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
            this.mTabPaddingBottom = n = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
            this.mTabPaddingEnd = n;
            this.mTabPaddingTop = n;
            this.mTabPaddingStart = n;
            this.mTabPaddingStart = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.mTabPaddingStart);
            this.mTabPaddingTop = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
            this.mTabPaddingEnd = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.mTabPaddingEnd);
            this.mTabPaddingBottom = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
            this.mTabTextAppearance = attributeSet.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
            context = context.obtainStyledAttributes(this.mTabTextAppearance, R.styleable.TextAppearance);
            this.mTabTextSize = context.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
            this.mTabTextColors = context.getColorStateList(R.styleable.TextAppearance_android_textColor);
            if (!attributeSet.hasValue(R.styleable.TabLayout_tabTextColor)) break block4;
            this.mTabTextColors = attributeSet.getColorStateList(R.styleable.TabLayout_tabTextColor);
        }
        if (attributeSet.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
            n = attributeSet.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0);
            this.mTabTextColors = TabLayout.createColorStateList(this.mTabTextColors.getDefaultColor(), n);
        }
        this.mRequestedTabMinWidth = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
        this.mRequestedTabMaxWidth = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
        this.mTabBackgroundResId = attributeSet.getResourceId(R.styleable.TabLayout_tabBackground, 0);
        this.mContentInsetStart = attributeSet.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
        this.mMode = attributeSet.getInt(R.styleable.TabLayout_tabMode, 1);
        this.mTabGravity = attributeSet.getInt(R.styleable.TabLayout_tabGravity, 0);
        attributeSet.recycle();
        context = this.getResources();
        this.mTabTextMultiLineSize = context.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
        this.mScrollableTabMinWidth = context.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
        this.applyModeAndGravity();
        return;
        finally {
            context.recycle();
        }
    }

    private void addTabFromItemView(TabItem tabItem) {
        Tab tab = this.newTab();
        if (tabItem.mText != null) {
            tab.setText(tabItem.mText);
        }
        if (tabItem.mIcon != null) {
            tab.setIcon(tabItem.mIcon);
        }
        if (tabItem.mCustomLayout != 0) {
            tab.setCustomView(tabItem.mCustomLayout);
        }
        if (!TextUtils.isEmpty((CharSequence)tabItem.getContentDescription())) {
            tab.setContentDescription(tabItem.getContentDescription());
        }
        this.addTab(tab);
    }

    private void addTabView(Tab tab) {
        TabView tabView = tab.mView;
        this.mTabStrip.addView((View)tabView, tab.getPosition(), (ViewGroup.LayoutParams)this.createLayoutParamsForTabs());
    }

    private void addViewInternal(View view) {
        if (view instanceof TabItem) {
            this.addTabFromItemView((TabItem)view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private void animateToTab(int n) {
        int n2;
        if (n == -1) {
            return;
        }
        if (this.getWindowToken() == null || !ViewCompat.isLaidOut((View)this) || this.mTabStrip.childrenNeedLayout()) {
            this.setScrollPosition(n, 0.0f, true);
            return;
        }
        int n3 = this.getScrollX();
        if (n3 != (n2 = this.calculateScrollXForTab(n, 0.0f))) {
            this.ensureScrollAnimator();
            this.mScrollAnimator.setIntValues(new int[]{n3, n2});
            this.mScrollAnimator.start();
        }
        this.mTabStrip.animateIndicatorToPosition(n, 300);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyModeAndGravity() {
        int n = 0;
        if (this.mMode == 0) {
            n = Math.max(0, this.mContentInsetStart - this.mTabPaddingStart);
        }
        ViewCompat.setPaddingRelative((View)this.mTabStrip, n, 0, 0, 0);
        switch (this.mMode) {
            case 1: {
                this.mTabStrip.setGravity(1);
            }
            default: {
                break;
            }
            case 0: {
                this.mTabStrip.setGravity(8388611);
            }
        }
        this.updateTabViews(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int calculateScrollXForTab(int n, float f) {
        int n2 = 0;
        if (this.mMode != 0) return 0;
        View view = this.mTabStrip.getChildAt(n);
        View view2 = n + 1 < this.mTabStrip.getChildCount() ? this.mTabStrip.getChildAt(n + 1) : null;
        n = view != null ? view.getWidth() : 0;
        if (view2 != null) {
            n2 = view2.getWidth();
        }
        int n3 = view.getLeft() + n / 2 - this.getWidth() / 2;
        n = (int)((float)(n + n2) * 0.5f * f);
        if (ViewCompat.getLayoutDirection((View)this) == 0) {
            return n3 + n;
        }
        return n3 - n;
    }

    private void configureTab(Tab tab, int n) {
        tab.setPosition(n);
        this.mTabs.add(n, tab);
        int n2 = this.mTabs.size();
        ++n;
        while (n < n2) {
            this.mTabs.get(n).setPosition(n);
            ++n;
        }
    }

    private static ColorStateList createColorStateList(int n, int n2) {
        int[][] arrarrn = new int[2][];
        int[] arrn = new int[2];
        arrarrn[0] = SELECTED_STATE_SET;
        arrn[0] = n2;
        n2 = 0 + 1;
        arrarrn[n2] = EMPTY_STATE_SET;
        arrn[n2] = n;
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        this.updateTabViewLayoutParams(layoutParams);
        return layoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    private TabView createTabView(Tab tab) {
        TabView tabView = this.mTabViewPool != null ? this.mTabViewPool.acquire() : null;
        TabView tabView2 = tabView;
        if (tabView == null) {
            tabView2 = new TabView(this.getContext());
        }
        tabView2.setTab(tab);
        tabView2.setFocusable(true);
        tabView2.setMinimumWidth(this.getTabMinWidth());
        return tabView2;
    }

    private void dispatchTabReselected(Tab tab) {
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; --i) {
            this.mSelectedListeners.get(i).onTabReselected(tab);
        }
    }

    private void dispatchTabSelected(Tab tab) {
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; --i) {
            this.mSelectedListeners.get(i).onTabSelected(tab);
        }
    }

    private void dispatchTabUnselected(Tab tab) {
        for (int i = this.mSelectedListeners.size() - 1; i >= 0; --i) {
            this.mSelectedListeners.get(i).onTabUnselected(tab);
        }
    }

    private void ensureScrollAnimator() {
        if (this.mScrollAnimator == null) {
            this.mScrollAnimator = new ValueAnimator();
            this.mScrollAnimator.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.mScrollAnimator.setDuration(300L);
            this.mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TabLayout.this.scrollTo(((Integer)valueAnimator.getAnimatedValue()).intValue(), 0);
                }
            });
        }
    }

    private int getDefaultHeight() {
        boolean bl = false;
        int n = 0;
        int n2 = this.mTabs.size();
        do {
            block4: {
                boolean bl2;
                block3: {
                    bl2 = bl;
                    if (n >= n2) break block3;
                    Tab tab = this.mTabs.get(n);
                    if (tab == null || tab.getIcon() == null || TextUtils.isEmpty((CharSequence)tab.getText())) break block4;
                    bl2 = true;
                }
                if (!bl2) break;
                return 72;
            }
            ++n;
        } while (true);
        return 48;
    }

    private float getScrollPosition() {
        return this.mTabStrip.getIndicatorPosition();
    }

    private int getTabMinWidth() {
        if (this.mRequestedTabMinWidth != -1) {
            return this.mRequestedTabMinWidth;
        }
        if (this.mMode == 0) {
            return this.mScrollableTabMinWidth;
        }
        return 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, this.mTabStrip.getWidth() - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
    }

    private void removeTabViewAt(int n) {
        TabView tabView = (TabView)this.mTabStrip.getChildAt(n);
        this.mTabStrip.removeViewAt(n);
        if (tabView != null) {
            tabView.reset();
            this.mTabViewPool.release(tabView);
        }
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSelectedTabView(int n) {
        int n2 = this.mTabStrip.getChildCount();
        if (n < n2) {
            for (int i = 0; i < n2; ++i) {
                View view = this.mTabStrip.getChildAt(i);
                boolean bl = i == n;
                view.setSelected(bl);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setupWithViewPager(ViewPager viewPager, boolean bl, boolean bl2) {
        if (this.mViewPager != null) {
            if (this.mPageChangeListener != null) {
                this.mViewPager.removeOnPageChangeListener(this.mPageChangeListener);
            }
            if (this.mAdapterChangeListener != null) {
                this.mViewPager.removeOnAdapterChangeListener(this.mAdapterChangeListener);
            }
        }
        if (this.mCurrentVpSelectedListener != null) {
            this.removeOnTabSelectedListener(this.mCurrentVpSelectedListener);
            this.mCurrentVpSelectedListener = null;
        }
        if (viewPager != null) {
            this.mViewPager = viewPager;
            if (this.mPageChangeListener == null) {
                this.mPageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            this.mPageChangeListener.reset();
            viewPager.addOnPageChangeListener(this.mPageChangeListener);
            this.mCurrentVpSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
            this.addOnTabSelectedListener(this.mCurrentVpSelectedListener);
            PagerAdapter pagerAdapter = viewPager.getAdapter();
            if (pagerAdapter != null) {
                this.setPagerAdapter(pagerAdapter, bl);
            }
            if (this.mAdapterChangeListener == null) {
                this.mAdapterChangeListener = new AdapterChangeListener();
            }
            this.mAdapterChangeListener.setAutoRefresh(bl);
            viewPager.addOnAdapterChangeListener(this.mAdapterChangeListener);
            this.setScrollPosition(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.mViewPager = null;
            this.setPagerAdapter(null, false);
        }
        this.mSetupViewPagerImplicitly = bl2;
    }

    private void updateAllTabs() {
        int n = this.mTabs.size();
        for (int i = 0; i < n; ++i) {
            this.mTabs.get(i).updateView();
        }
    }

    private void updateTabViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (this.mMode == 1 && this.mTabGravity == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    public void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        if (!this.mSelectedListeners.contains(onTabSelectedListener)) {
            this.mSelectedListeners.add(onTabSelectedListener);
        }
    }

    public void addTab(Tab tab) {
        this.addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, int n, boolean bl) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        this.configureTab(tab, n);
        this.addTabView(tab);
        if (bl) {
            tab.select();
        }
    }

    public void addTab(Tab tab, boolean bl) {
        this.addTab(tab, this.mTabs.size(), bl);
    }

    public void addView(View view) {
        this.addViewInternal(view);
    }

    public void addView(View view, int n) {
        this.addViewInternal(view);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        this.addViewInternal(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.addViewInternal(view);
    }

    int dpToPx(int n) {
        return Math.round(this.getResources().getDisplayMetrics().density * (float)n);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return this.generateDefaultLayoutParams();
    }

    public int getSelectedTabPosition() {
        if (this.mSelectedTab != null) {
            return this.mSelectedTab.getPosition();
        }
        return -1;
    }

    public Tab getTabAt(int n) {
        if (n < 0 || n >= this.getTabCount()) {
            return null;
        }
        return this.mTabs.get(n);
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    int getTabMaxWidth() {
        return this.mTabMaxWidth;
    }

    public int getTabMode() {
        return this.mMode;
    }

    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    public Tab newTab() {
        Tab tab;
        Tab tab2 = tab = sTabPool.acquire();
        if (tab == null) {
            tab2 = new Tab();
        }
        tab2.mParent = this;
        tab2.mView = this.createTabView(tab2);
        return tab2;
    }

    protected void onAttachedToWindow() {
        ViewParent viewParent;
        super.onAttachedToWindow();
        if (this.mViewPager == null && (viewParent = this.getParent()) instanceof ViewPager) {
            this.setupWithViewPager((ViewPager)viewParent, true, true);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mSetupViewPagerImplicitly) {
            this.setupWithViewPager(null);
            this.mSetupViewPagerImplicitly = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3 = this.dpToPx(this.getDefaultHeight()) + this.getPaddingTop() + this.getPaddingBottom();
        switch (View.MeasureSpec.getMode((int)n2)) {
            case Integer.MIN_VALUE: {
                n2 = View.MeasureSpec.makeMeasureSpec((int)Math.min(n3, View.MeasureSpec.getSize((int)n2)), (int)1073741824);
                break;
            }
            case 0: {
                n2 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824);
            }
        }
        n3 = View.MeasureSpec.getSize((int)n);
        if (View.MeasureSpec.getMode((int)n) != 0) {
            n3 = this.mRequestedTabMaxWidth > 0 ? this.mRequestedTabMaxWidth : (n3 -= this.dpToPx(56));
            this.mTabMaxWidth = n3;
        }
        super.onMeasure(n, n2);
        if (this.getChildCount() != 1) return;
        View view = this.getChildAt(0);
        n = 0;
        switch (this.mMode) {
            case 0: {
                if (view.getMeasuredWidth() < this.getMeasuredWidth()) {
                    n = 1;
                    break;
                }
                n = 0;
            }
            default: {
                break;
            }
            case 1: {
                if (view.getMeasuredWidth() == this.getMeasuredWidth()) return;
                n = 1;
                break;
            }
        }
        if (n == 0) return;
        n = TabLayout.getChildMeasureSpec((int)n2, (int)(this.getPaddingTop() + this.getPaddingBottom()), (int)view.getLayoutParams().height);
        view.measure(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824), n);
    }

    void populateFromPagerAdapter() {
        this.removeAllTabs();
        if (this.mPagerAdapter != null) {
            int n;
            int n2 = this.mPagerAdapter.getCount();
            for (n = 0; n < n2; ++n) {
                this.addTab(this.newTab().setText(this.mPagerAdapter.getPageTitle(n)), false);
            }
            if (this.mViewPager != null && n2 > 0 && (n = this.mViewPager.getCurrentItem()) != this.getSelectedTabPosition() && n < this.getTabCount()) {
                this.selectTab(this.getTabAt(n));
            }
        }
    }

    public void removeAllTabs() {
        for (int i = this.mTabStrip.getChildCount() - 1; i >= 0; --i) {
            this.removeTabViewAt(i);
        }
        Iterator<Tab> iterator = this.mTabs.iterator();
        while (iterator.hasNext()) {
            Tab tab = iterator.next();
            iterator.remove();
            tab.reset();
            sTabPool.release(tab);
        }
        this.mSelectedTab = null;
    }

    public void removeOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mSelectedListeners.remove(onTabSelectedListener);
    }

    void selectTab(Tab tab) {
        this.selectTab(tab, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void selectTab(Tab tab, boolean bl) {
        Tab tab2 = this.mSelectedTab;
        if (tab2 == tab) {
            if (tab2 == null) return;
            {
                this.dispatchTabReselected(tab);
                this.animateToTab(tab.getPosition());
                return;
            }
        } else {
            int n = tab != null ? tab.getPosition() : -1;
            if (bl) {
                if ((tab2 == null || tab2.getPosition() == -1) && n != -1) {
                    this.setScrollPosition(n, 0.0f, true);
                } else {
                    this.animateToTab(n);
                }
                if (n != -1) {
                    this.setSelectedTabView(n);
                }
            }
            if (tab2 != null) {
                this.dispatchTabUnselected(tab2);
            }
            this.mSelectedTab = tab;
            if (tab == null) return;
            {
                this.dispatchTabSelected(tab);
                return;
            }
        }
    }

    @Deprecated
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        if (this.mSelectedListener != null) {
            this.removeOnTabSelectedListener(this.mSelectedListener);
        }
        this.mSelectedListener = onTabSelectedListener;
        if (onTabSelectedListener != null) {
            this.addOnTabSelectedListener(onTabSelectedListener);
        }
    }

    void setPagerAdapter(PagerAdapter pagerAdapter, boolean bl) {
        if (this.mPagerAdapter != null && this.mPagerAdapterObserver != null) {
            this.mPagerAdapter.unregisterDataSetObserver(this.mPagerAdapterObserver);
        }
        this.mPagerAdapter = pagerAdapter;
        if (bl && pagerAdapter != null) {
            if (this.mPagerAdapterObserver == null) {
                this.mPagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter.registerDataSetObserver(this.mPagerAdapterObserver);
        }
        this.populateFromPagerAdapter();
    }

    void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.ensureScrollAnimator();
        this.mScrollAnimator.addListener(animatorListener);
    }

    public void setScrollPosition(int n, float f, boolean bl) {
        this.setScrollPosition(n, f, bl, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setScrollPosition(int n, float f, boolean bl, boolean bl2) {
        int n2;
        block6: {
            block5: {
                n2 = Math.round((float)n + f);
                if (n2 < 0 || n2 >= this.mTabStrip.getChildCount()) break block5;
                if (bl2) {
                    this.mTabStrip.setIndicatorPositionFromTabPosition(n, f);
                }
                if (this.mScrollAnimator != null && this.mScrollAnimator.isRunning()) {
                    this.mScrollAnimator.cancel();
                }
                this.scrollTo(this.calculateScrollXForTab(n, f), 0);
                if (bl) break block6;
            }
            return;
        }
        this.setSelectedTabView(n2);
    }

    public void setSelectedTabIndicatorColor(int n) {
        this.mTabStrip.setSelectedIndicatorColor(n);
    }

    public void setSelectedTabIndicatorHeight(int n) {
        this.mTabStrip.setSelectedIndicatorHeight(n);
    }

    public void setTabGravity(int n) {
        if (this.mTabGravity != n) {
            this.mTabGravity = n;
            this.applyModeAndGravity();
        }
    }

    public void setTabMode(int n) {
        if (n != this.mMode) {
            this.mMode = n;
            this.applyModeAndGravity();
        }
    }

    public void setTabTextColors(int n, int n2) {
        this.setTabTextColors(TabLayout.createColorStateList(n, n2));
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.mTabTextColors != colorStateList) {
            this.mTabTextColors = colorStateList;
            this.updateAllTabs();
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(PagerAdapter pagerAdapter) {
        this.setPagerAdapter(pagerAdapter, false);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(ViewPager viewPager, boolean bl) {
        this.setupWithViewPager(viewPager, bl, false);
    }

    public boolean shouldDelayChildPressedState() {
        return this.getTabScrollRange() > 0;
    }

    void updateTabViews(boolean bl) {
        for (int i = 0; i < this.mTabStrip.getChildCount(); ++i) {
            View view = this.mTabStrip.getChildAt(i);
            view.setMinimumWidth(this.getTabMinWidth());
            this.updateTabViewLayoutParams((LinearLayout.LayoutParams)view.getLayoutParams());
            if (!bl) continue;
            view.requestLayout();
        }
    }

    private class AdapterChangeListener
    implements ViewPager.OnAdapterChangeListener {
        private boolean mAutoRefresh;

        AdapterChangeListener() {
        }

        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            if (TabLayout.this.mViewPager == viewPager) {
                TabLayout.this.setPagerAdapter(pagerAdapter2, this.mAutoRefresh);
            }
        }

        void setAutoRefresh(boolean bl) {
            this.mAutoRefresh = bl;
        }
    }

    public static interface OnTabSelectedListener {
        public void onTabReselected(Tab var1);

        public void onTabSelected(Tab var1);

        public void onTabUnselected(Tab var1);
    }

    private class PagerAdapterObserver
    extends DataSetObserver {
        PagerAdapterObserver() {
        }

        public void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    private class SlidingTabStrip
    extends LinearLayout {
        private ValueAnimator mIndicatorAnimator;
        private int mIndicatorLeft;
        private int mIndicatorRight;
        private int mLayoutDirection;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        int mSelectedPosition;
        float mSelectionOffset;

        SlidingTabStrip(Context context) {
            super(context);
            this.mSelectedPosition = -1;
            this.mLayoutDirection = -1;
            this.mIndicatorLeft = -1;
            this.mIndicatorRight = -1;
            this.setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateIndicatorPosition() {
            int n;
            int n2;
            View view = this.getChildAt(this.mSelectedPosition);
            if (view != null && view.getWidth() > 0) {
                int n3 = view.getLeft();
                int n4 = view.getRight();
                n = n3;
                n2 = n4;
                if (this.mSelectionOffset > 0.0f) {
                    n = n3;
                    n2 = n4;
                    if (this.mSelectedPosition < this.getChildCount() - 1) {
                        view = this.getChildAt(this.mSelectedPosition + 1);
                        n = (int)(this.mSelectionOffset * (float)view.getLeft() + (1.0f - this.mSelectionOffset) * (float)n3);
                        n2 = (int)(this.mSelectionOffset * (float)view.getRight() + (1.0f - this.mSelectionOffset) * (float)n4);
                    }
                }
            } else {
                n2 = -1;
                n = -1;
            }
            this.setIndicatorPosition(n, n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        void animateIndicatorToPosition(final int n, int n2) {
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            final int n3 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
            View view = this.getChildAt(n);
            if (view == null) {
                this.updateIndicatorPosition();
                return;
            } else {
                int n4;
                final int n5 = view.getLeft();
                final int n6 = view.getRight();
                if (Math.abs(n - this.mSelectedPosition) <= 1) {
                    n3 = this.mIndicatorLeft;
                    n4 = this.mIndicatorRight;
                } else {
                    n4 = TabLayout.this.dpToPx(24);
                    n3 = n < this.mSelectedPosition ? (n3 != 0 ? (n4 = n5 - n4) : (n4 = n6 + n4)) : (n3 != 0 ? (n4 = n6 + n4) : (n4 = n5 - n4));
                }
                if (n3 == n5 && n4 == n6) return;
                {
                    view = new ValueAnimator();
                    this.mIndicatorAnimator = view;
                    view.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                    view.setDuration((long)n2);
                    view.setFloatValues(new float[]{0.0f, 1.0f});
                    view.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float f = valueAnimator.getAnimatedFraction();
                            SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(n3, n5, f), AnimationUtils.lerp(n4, n6, f));
                        }
                    });
                    view.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                        public void onAnimationEnd(Animator animator2) {
                            SlidingTabStrip.this.mSelectedPosition = n;
                            SlidingTabStrip.this.mSelectionOffset = 0.0f;
                        }
                    });
                    view.start();
                    return;
                }
            }
        }

        boolean childrenNeedLayout() {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                if (this.getChildAt(i).getWidth() > 0) continue;
                return true;
            }
            return false;
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
                canvas.drawRect((float)this.mIndicatorLeft, (float)(this.getHeight() - this.mSelectedIndicatorHeight), (float)this.mIndicatorRight, (float)this.getHeight(), this.mSelectedIndicatorPaint);
            }
        }

        float getIndicatorPosition() {
            return (float)this.mSelectedPosition + this.mSelectionOffset;
        }

        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
            super.onLayout(bl, n, n2, n3, n4);
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
                long l = this.mIndicatorAnimator.getDuration();
                this.animateIndicatorToPosition(this.mSelectedPosition, Math.round((1.0f - this.mIndicatorAnimator.getAnimatedFraction()) * (float)l));
                return;
            }
            this.updateIndicatorPosition();
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onMeasure(int n, int n2) {
            block12: {
                block11: {
                    int n3;
                    int n4;
                    View view;
                    int n5;
                    super.onMeasure(n, n2);
                    if (View.MeasureSpec.getMode((int)n) != 1073741824 || TabLayout.this.mMode != 1 || TabLayout.this.mTabGravity != 1) break block11;
                    int n6 = this.getChildCount();
                    int n7 = 0;
                    for (n5 = 0; n5 < n6; ++n5) {
                        view = this.getChildAt(n5);
                        n3 = n7;
                        if (view.getVisibility() == 0) {
                            n3 = Math.max(n7, view.getMeasuredWidth());
                        }
                        n7 = n3;
                    }
                    if (n7 <= 0) break block11;
                    n3 = TabLayout.this.dpToPx(16);
                    n5 = 0;
                    if (n7 * n6 <= this.getMeasuredWidth() - n3 * 2) {
                        n3 = 0;
                        do {
                            n4 = n5;
                            if (n3 < n6) {
                                view = (LinearLayout.LayoutParams)this.getChildAt(n3).getLayoutParams();
                                if (view.width != n7 || view.weight != 0.0f) {
                                    view.width = n7;
                                    view.weight = 0.0f;
                                    n5 = 1;
                                }
                                ++n3;
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        TabLayout.this.mTabGravity = 0;
                        TabLayout.this.updateTabViews(false);
                        n4 = 1;
                    }
                    if (n4 != 0) break block12;
                }
                return;
            }
            super.onMeasure(n, n2);
        }

        public void onRtlPropertiesChanged(int n) {
            super.onRtlPropertiesChanged(n);
            if (Build.VERSION.SDK_INT < 23 && this.mLayoutDirection != n) {
                this.requestLayout();
                this.mLayoutDirection = n;
            }
        }

        void setIndicatorPosition(int n, int n2) {
            if (n != this.mIndicatorLeft || n2 != this.mIndicatorRight) {
                this.mIndicatorLeft = n;
                this.mIndicatorRight = n2;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

        void setIndicatorPositionFromTabPosition(int n, float f) {
            if (this.mIndicatorAnimator != null && this.mIndicatorAnimator.isRunning()) {
                this.mIndicatorAnimator.cancel();
            }
            this.mSelectedPosition = n;
            this.mSelectionOffset = f;
            this.updateIndicatorPosition();
        }

        void setSelectedIndicatorColor(int n) {
            if (this.mSelectedIndicatorPaint.getColor() != n) {
                this.mSelectedIndicatorPaint.setColor(n);
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

        void setSelectedIndicatorHeight(int n) {
            if (this.mSelectedIndicatorHeight != n) {
                this.mSelectedIndicatorHeight = n;
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
        }

    }

    public static final class Tab {
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        TabLayout mParent;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;
        TabView mView;

        Tab() {
        }

        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        public View getCustomView() {
            return this.mCustomView;
        }

        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public boolean isSelected() {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return this.mParent.getSelectedTabPosition() == this.mPosition;
        }

        void reset() {
            this.mParent = null;
            this.mView = null;
            this.mTag = null;
            this.mIcon = null;
            this.mText = null;
            this.mContentDesc = null;
            this.mPosition = -1;
            this.mCustomView = null;
        }

        public void select() {
            if (this.mParent == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            this.mParent.selectTab(this);
        }

        public Tab setContentDescription(CharSequence charSequence) {
            this.mContentDesc = charSequence;
            this.updateView();
            return this;
        }

        public Tab setCustomView(int n) {
            return this.setCustomView(LayoutInflater.from((Context)this.mView.getContext()).inflate(n, (ViewGroup)this.mView, false));
        }

        public Tab setCustomView(View view) {
            this.mCustomView = view;
            this.updateView();
            return this;
        }

        public Tab setIcon(Drawable drawable2) {
            this.mIcon = drawable2;
            this.updateView();
            return this;
        }

        void setPosition(int n) {
            this.mPosition = n;
        }

        public Tab setText(CharSequence charSequence) {
            this.mText = charSequence;
            this.updateView();
            return this;
        }

        void updateView() {
            if (this.mView != null) {
                this.mView.update();
            }
        }
    }

    public static class TabLayoutOnPageChangeListener
    implements ViewPager.OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<TabLayout> mTabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.mTabLayoutRef = new WeakReference<TabLayout>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int n) {
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = n;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onPageScrolled(int n, float f, int n2) {
            TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
            if (tabLayout != null) {
                boolean bl = this.mScrollState != 2 || this.mPreviousScrollState == 1;
                boolean bl2 = this.mScrollState != 2 || this.mPreviousScrollState != 0;
                tabLayout.setScrollPosition(n, f, bl, bl2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onPageSelected(int n) {
            TabLayout tabLayout = (TabLayout)((Object)this.mTabLayoutRef.get());
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != n && n < tabLayout.getTabCount()) {
                boolean bl = this.mScrollState == 0 || this.mScrollState == 2 && this.mPreviousScrollState == 0;
                tabLayout.selectTab(tabLayout.getTabAt(n), bl);
            }
        }

        void reset() {
            this.mScrollState = 0;
            this.mPreviousScrollState = 0;
        }
    }

    class TabView
    extends LinearLayout {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private int mDefaultMaxLines;
        private ImageView mIconView;
        private Tab mTab;
        private TextView mTextView;

        public TabView(Context context) {
            super(context);
            this.mDefaultMaxLines = 2;
            if (TabLayout.this.mTabBackgroundResId != 0) {
                ViewCompat.setBackground((View)this, AppCompatResources.getDrawable(context, TabLayout.this.mTabBackgroundResId));
            }
            ViewCompat.setPaddingRelative((View)this, TabLayout.this.mTabPaddingStart, TabLayout.this.mTabPaddingTop, TabLayout.this.mTabPaddingEnd, TabLayout.this.mTabPaddingBottom);
            this.setGravity(17);
            this.setOrientation(1);
            this.setClickable(true);
            ViewCompat.setPointerIcon((View)this, PointerIconCompat.getSystemIcon(this.getContext(), 1002));
        }

        private float approximateLineWidth(Layout layout2, int n, float f) {
            return layout2.getLineWidth(n) * (f / layout2.getPaint().getTextSize());
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void updateTextAndIcon(TextView object, ImageView imageView) {
            void var2_7;
            void var1_5;
            Object var9_8 = null;
            Drawable drawable2 = this.mTab != null ? this.mTab.getIcon() : null;
            CharSequence charSequence = this.mTab != null ? this.mTab.getText() : null;
            CharSequence charSequence2 = this.mTab != null ? this.mTab.getContentDescription() : null;
            if (var2_7 != null) {
                if (drawable2 != null) {
                    var2_7.setImageDrawable(drawable2);
                    var2_7.setVisibility(0);
                    this.setVisibility(0);
                } else {
                    var2_7.setVisibility(8);
                    var2_7.setImageDrawable(null);
                }
                var2_7.setContentDescription(charSequence2);
            }
            boolean bl = !TextUtils.isEmpty((CharSequence)charSequence);
            if (object != null) {
                if (bl) {
                    object.setText(charSequence);
                    object.setVisibility(0);
                    this.setVisibility(0);
                } else {
                    object.setVisibility(8);
                    object.setText(null);
                }
                object.setContentDescription(charSequence2);
            }
            if (var2_7 != null) {
                int n;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)var2_7.getLayoutParams();
                int n2 = n = 0;
                if (bl) {
                    n2 = n;
                    if (var2_7.getVisibility() == 0) {
                        n2 = TabLayout.this.dpToPx(8);
                    }
                }
                if (n2 != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = n2;
                    var2_7.requestLayout();
                }
            }
            if (bl) {
                Object var1_4 = var9_8;
            } else {
                CharSequence charSequence3 = charSequence2;
            }
            TooltipCompat.setTooltipText((View)this, (CharSequence)var1_5);
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onMeasure(int n, int n2) {
            float f;
            int n3 = View.MeasureSpec.getSize((int)n);
            int n4 = View.MeasureSpec.getMode((int)n);
            int n5 = TabLayout.this.getTabMaxWidth();
            if (n5 > 0 && (n4 == 0 || n3 > n5)) {
                n = View.MeasureSpec.makeMeasureSpec((int)TabLayout.this.mTabMaxWidth, (int)Integer.MIN_VALUE);
            }
            super.onMeasure(n, n2);
            if (this.mTextView == null) return;
            this.getResources();
            float f2 = TabLayout.this.mTabTextSize;
            n4 = this.mDefaultMaxLines;
            if (this.mIconView != null && this.mIconView.getVisibility() == 0) {
                n3 = 1;
                f = f2;
            } else {
                n3 = n4;
                f = f2;
                if (this.mTextView != null) {
                    n3 = n4;
                    f = f2;
                    if (this.mTextView.getLineCount() > 1) {
                        f = TabLayout.this.mTabTextMultiLineSize;
                        n3 = n4;
                    }
                }
            }
            f2 = this.mTextView.getTextSize();
            int n6 = this.mTextView.getLineCount();
            n4 = TextViewCompat.getMaxLines(this.mTextView);
            if (f == f2) {
                if (n4 < 0) return;
                if (n3 == n4) return;
            }
            n4 = n5 = 1;
            if (TabLayout.this.mMode == 1) {
                n4 = n5;
                if (f > f2) {
                    n4 = n5;
                    if (n6 == 1) {
                        Layout layout2 = this.mTextView.getLayout();
                        if (layout2 == null) return;
                        n4 = n5;
                        if (this.approximateLineWidth(layout2, 0, f) > (float)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight())) {
                            return;
                        }
                    }
                }
            }
            if (n4 == 0) return;
            this.mTextView.setTextSize(0, f);
            this.mTextView.setMaxLines(n3);
            super.onMeasure(n, n2);
        }

        public boolean performClick() {
            boolean bl;
            boolean bl2 = bl = super.performClick();
            if (this.mTab != null) {
                if (!bl) {
                    this.playSoundEffect(0);
                }
                this.mTab.select();
                bl2 = true;
            }
            return bl2;
        }

        void reset() {
            this.setTab(null);
            this.setSelected(false);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setSelected(boolean bl) {
            boolean bl2 = this.isSelected() != bl;
            super.setSelected(bl);
            if (bl2 && bl && Build.VERSION.SDK_INT < 16) {
                this.sendAccessibilityEvent(4);
            }
            if (this.mTextView != null) {
                this.mTextView.setSelected(bl);
            }
            if (this.mIconView != null) {
                this.mIconView.setSelected(bl);
            }
            if (this.mCustomView != null) {
                this.mCustomView.setSelected(bl);
            }
        }

        void setTab(Tab tab) {
            if (tab != this.mTab) {
                this.mTab = tab;
                this.update();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        final void update() {
            Tab tab = this.mTab;
            View view = tab != null ? tab.getCustomView() : null;
            if (view != null) {
                ViewParent viewParent = view.getParent();
                if (viewParent != this) {
                    if (viewParent != null) {
                        ((ViewGroup)viewParent).removeView(view);
                    }
                    this.addView(view);
                }
                this.mCustomView = view;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                this.mCustomTextView = (TextView)view.findViewById(16908308);
                if (this.mCustomTextView != null) {
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mCustomTextView);
                }
                this.mCustomIconView = (ImageView)view.findViewById(16908294);
            } else {
                if (this.mCustomView != null) {
                    this.removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            }
            if (this.mCustomView == null) {
                if (this.mIconView == null) {
                    view = (ImageView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup)this, false);
                    this.addView(view, 0);
                    this.mIconView = view;
                }
                if (this.mTextView == null) {
                    view = (TextView)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup)this, false);
                    this.addView(view);
                    this.mTextView = view;
                    this.mDefaultMaxLines = TextViewCompat.getMaxLines(this.mTextView);
                }
                TextViewCompat.setTextAppearance(this.mTextView, TabLayout.this.mTabTextAppearance);
                if (TabLayout.this.mTabTextColors != null) {
                    this.mTextView.setTextColor(TabLayout.this.mTabTextColors);
                }
                this.updateTextAndIcon(this.mTextView, this.mIconView);
            } else if (this.mCustomTextView != null || this.mCustomIconView != null) {
                this.updateTextAndIcon(this.mCustomTextView, this.mCustomIconView);
            }
            boolean bl = tab != null && tab.isSelected();
            this.setSelected(bl);
        }
    }

    public static class ViewPagerOnTabSelectedListener
    implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        @Override
        public void onTabReselected(Tab tab) {
        }

        @Override
        public void onTabSelected(Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(Tab tab) {
        }
    }

}

