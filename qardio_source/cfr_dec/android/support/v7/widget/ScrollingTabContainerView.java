/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewPropertyAnimator
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.AbsListView
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.BaseAdapter
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class ScrollingTabContainerView
extends HorizontalScrollView
implements AdapterView.OnItemSelectedListener {
    private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private TabClickListener mTabClickListener;
    LinearLayoutCompat mTabLayout;
    Runnable mTabSelector;
    private Spinner mTabSpinner;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected ViewPropertyAnimator mVisibilityAnim;

    public ScrollingTabContainerView(Context object) {
        super((Context)object);
        this.setHorizontalScrollBarEnabled(false);
        object = ActionBarPolicy.get((Context)object);
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
        this.mTabLayout = this.createTabLayout();
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    private Spinner createSpinner() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(this.getContext(), null, R.attr.actionDropDownStyle);
        appCompatSpinner.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        appCompatSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);
        return appCompatSpinner;
    }

    private LinearLayoutCompat createTabLayout() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this.getContext(), null, R.attr.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        return linearLayoutCompat;
    }

    private boolean isCollapsed() {
        return this.mTabSpinner != null && this.mTabSpinner.getParent() == this;
    }

    private void performCollapse() {
        if (this.isCollapsed()) {
            return;
        }
        if (this.mTabSpinner == null) {
            this.mTabSpinner = this.createSpinner();
        }
        this.removeView((View)this.mTabLayout);
        this.addView((View)this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
        if (this.mTabSpinner.getAdapter() == null) {
            this.mTabSpinner.setAdapter((SpinnerAdapter)new TabAdapter());
        }
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
            this.mTabSelector = null;
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex);
    }

    private boolean performExpand() {
        if (!this.isCollapsed()) {
            return false;
        }
        this.removeView((View)this.mTabSpinner);
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
        this.setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void animateToTab(int n) {
        final View view = this.mTabLayout.getChildAt(n);
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
        }
        this.mTabSelector = new Runnable(){

            @Override
            public void run() {
                int n = view.getLeft();
                int n2 = (ScrollingTabContainerView.this.getWidth() - view.getWidth()) / 2;
                ScrollingTabContainerView.this.smoothScrollTo(n - n2, 0);
                ScrollingTabContainerView.this.mTabSelector = null;
            }
        };
        this.post(this.mTabSelector);
    }

    TabView createTabView(ActionBar.Tab object, boolean bl) {
        object = new TabView(this.getContext(), (ActionBar.Tab)object, bl);
        if (bl) {
            object.setBackgroundDrawable(null);
            object.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, this.mContentHeight));
            return object;
        }
        object.setFocusable(true);
        if (this.mTabClickListener == null) {
            this.mTabClickListener = new TabClickListener();
        }
        object.setOnClickListener((View.OnClickListener)this.mTabClickListener);
        return object;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            this.post(this.mTabSelector);
        }
    }

    protected void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = ActionBarPolicy.get(this.getContext());
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            this.removeCallbacks(this.mTabSelector);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
        ((TabView)view).getTab().select();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onMeasure(int n, int n2) {
        n2 = View.MeasureSpec.getMode((int)n);
        boolean bl = n2 == 1073741824;
        this.setFillViewport(bl);
        int n3 = this.mTabLayout.getChildCount();
        if (n3 > 1 && (n2 == 1073741824 || n2 == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = n3 > 2 ? (int)((float)View.MeasureSpec.getSize((int)n) * 0.4f) : View.MeasureSpec.getSize((int)n) / 2;
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        } else {
            this.mMaxTabWidth = -1;
        }
        n3 = View.MeasureSpec.makeMeasureSpec((int)this.mContentHeight, (int)1073741824);
        n2 = !bl && this.mAllowCollapse ? 1 : 0;
        if (n2 != 0) {
            this.mTabLayout.measure(0, n3);
            if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize((int)n)) {
                this.performCollapse();
            } else {
                this.performExpand();
            }
        } else {
            this.performExpand();
        }
        n2 = this.getMeasuredWidth();
        super.onMeasure(n, n3);
        n = this.getMeasuredWidth();
        if (bl && n2 != n) {
            this.setTabSelected(this.mSelectedTabIndex);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setAllowCollapse(boolean bl) {
        this.mAllowCollapse = bl;
    }

    public void setContentHeight(int n) {
        this.mContentHeight = n;
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTabSelected(int n) {
        this.mSelectedTabIndex = n;
        int n2 = this.mTabLayout.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.mTabLayout.getChildAt(i);
            boolean bl = i == n;
            view.setSelected(bl);
            if (!bl) continue;
            this.animateToTab(n);
        }
        if (this.mTabSpinner != null && n >= 0) {
            this.mTabSpinner.setSelection(n);
        }
    }

    private class TabAdapter
    extends BaseAdapter {
        TabAdapter() {
        }

        public int getCount() {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        public Object getItem(int n) {
            return ((TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(n)).getTab();
        }

        public long getItemId(int n) {
            return n;
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            if (view == null) {
                return ScrollingTabContainerView.this.createTabView((ActionBar.Tab)this.getItem(n), true);
            }
            ((TabView)view).bindTab((ActionBar.Tab)this.getItem(n));
            return view;
        }
    }

    private class TabClickListener
    implements View.OnClickListener {
        TabClickListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onClick(View view) {
            ((TabView)view).getTab().select();
            int n = ScrollingTabContainerView.this.mTabLayout.getChildCount();
            int n2 = 0;
            while (n2 < n) {
                View view2 = ScrollingTabContainerView.this.mTabLayout.getChildAt(n2);
                boolean bl = view2 == view;
                view2.setSelected(bl);
                ++n2;
            }
            return;
        }
    }

    private class TabView
    extends LinearLayoutCompat {
        private final int[] BG_ATTRS;
        private View mCustomView;
        private ImageView mIconView;
        private ActionBar.Tab mTab;
        private TextView mTextView;

        public TabView(Context context, ActionBar.Tab tab, boolean bl) {
            super(context, null, R.attr.actionBarTabStyle);
            this.BG_ATTRS = new int[]{16842964};
            this.mTab = tab;
            ScrollingTabContainerView.this = TintTypedArray.obtainStyledAttributes(context, null, this.BG_ATTRS, R.attr.actionBarTabStyle, 0);
            if (((TintTypedArray)ScrollingTabContainerView.this).hasValue(0)) {
                this.setBackgroundDrawable(((TintTypedArray)ScrollingTabContainerView.this).getDrawable(0));
            }
            ((TintTypedArray)ScrollingTabContainerView.this).recycle();
            if (bl) {
                this.setGravity(8388627);
            }
            this.update();
        }

        public void bindTab(ActionBar.Tab tab) {
            this.mTab = tab;
            this.update();
        }

        public ActionBar.Tab getTab() {
            return this.mTab;
        }

        @Override
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)ActionBar.Tab.class.getName());
        }

        @Override
        public void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            if (ScrollingTabContainerView.this.mMaxTabWidth > 0 && this.getMeasuredWidth() > ScrollingTabContainerView.this.mMaxTabWidth) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)ScrollingTabContainerView.this.mMaxTabWidth, (int)1073741824), n2);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void setSelected(boolean bl) {
            boolean bl2 = this.isSelected() != bl;
            super.setSelected(bl);
            if (bl2 && bl) {
                this.sendAccessibilityEvent(4);
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void update() {
            void var2_3;
            Object var2_1 = null;
            ActionBar.Tab tab = this.mTab;
            View view = tab.getCustomView();
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
                return;
            }
            if (this.mCustomView != null) {
                this.removeView(this.mCustomView);
                this.mCustomView = null;
            }
            Drawable drawable2 = tab.getIcon();
            CharSequence charSequence = tab.getText();
            if (drawable2 != null) {
                if (this.mIconView == null) {
                    AppCompatImageView appCompatImageView = new AppCompatImageView(this.getContext());
                    LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatImageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    this.addView((View)appCompatImageView, 0);
                    this.mIconView = appCompatImageView;
                }
                this.mIconView.setImageDrawable(drawable2);
                this.mIconView.setVisibility(0);
            } else if (this.mIconView != null) {
                this.mIconView.setVisibility(8);
                this.mIconView.setImageDrawable(null);
            }
            boolean bl = !TextUtils.isEmpty((CharSequence)charSequence);
            if (bl) {
                if (this.mTextView == null) {
                    AppCompatTextView appCompatTextView = new AppCompatTextView(this.getContext(), null, R.attr.actionBarTabTextStyle);
                    appCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
                    LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatTextView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    this.addView((View)appCompatTextView);
                    this.mTextView = appCompatTextView;
                }
                this.mTextView.setText(charSequence);
                this.mTextView.setVisibility(0);
            } else if (this.mTextView != null) {
                this.mTextView.setVisibility(8);
                this.mTextView.setText(null);
            }
            if (this.mIconView != null) {
                this.mIconView.setContentDescription(tab.getContentDescription());
            }
            if (!bl) {
                CharSequence charSequence2 = tab.getContentDescription();
            }
            TooltipCompat.setTooltipText((View)this, (CharSequence)var2_3);
        }
    }

    protected class VisibilityAnimListener
    extends AnimatorListenerAdapter {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator2) {
            if (this.mCanceled) {
                return;
            }
            ScrollingTabContainerView.this.mVisibilityAnim = null;
            ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
        }

        public void onAnimationStart(Animator animator2) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.mCanceled = false;
        }
    }

}

