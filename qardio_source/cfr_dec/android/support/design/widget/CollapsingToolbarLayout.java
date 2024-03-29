/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package android.support.design.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingTextHelper;
import android.support.design.widget.ThemeUtils;
import android.support.design.widget.ViewGroupUtils;
import android.support.design.widget.ViewOffsetHelper;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

public class CollapsingToolbarLayout
extends FrameLayout {
    final CollapsingTextHelper mCollapsingTextHelper;
    private boolean mCollapsingTitleEnabled;
    private Drawable mContentScrim;
    int mCurrentOffset;
    private boolean mDrawCollapsingTitle;
    private View mDummyView;
    private int mExpandedMarginBottom;
    private int mExpandedMarginEnd;
    private int mExpandedMarginStart;
    private int mExpandedMarginTop;
    WindowInsetsCompat mLastInsets;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private boolean mRefreshToolbar = true;
    private int mScrimAlpha;
    private long mScrimAnimationDuration;
    private ValueAnimator mScrimAnimator;
    private int mScrimVisibleHeightTrigger = -1;
    private boolean mScrimsAreShown;
    Drawable mStatusBarScrim;
    private final Rect mTmpRect = new Rect();
    private Toolbar mToolbar;
    private View mToolbarDirectChild;
    private int mToolbarId;

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        ThemeUtils.checkAppCompatTheme(context);
        this.mCollapsingTextHelper = new CollapsingTextHelper((View)this);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout, n, R.style.Widget_Design_CollapsingToolbar);
        this.mCollapsingTextHelper.setExpandedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
        this.mCollapsingTextHelper.setCollapsedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        this.mExpandedMarginBottom = n = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.mExpandedMarginEnd = n;
        this.mExpandedMarginTop = n;
        this.mExpandedMarginStart = n;
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.mExpandedMarginStart = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.mExpandedMarginEnd = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.mExpandedMarginTop = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.mExpandedMarginBottom = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.mCollapsingTitleEnabled = context.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        this.setTitle(context.getText(R.styleable.CollapsingToolbarLayout_title));
        this.mCollapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.mCollapsingTextHelper.setCollapsedTextAppearance(R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setExpandedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setCollapsedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.mScrimVisibleHeightTrigger = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        this.mScrimAnimationDuration = context.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        this.setContentScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        this.setStatusBarScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        this.mToolbarId = context.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        context.recycle();
        this.setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return CollapsingToolbarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void animateScrim(int n) {
        this.ensureToolbar();
        if (this.mScrimAnimator == null) {
            this.mScrimAnimator = new ValueAnimator();
            this.mScrimAnimator.setDuration(this.mScrimAnimationDuration);
            ValueAnimator valueAnimator = this.mScrimAnimator;
            Interpolator interpolator = n > this.mScrimAlpha ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
            valueAnimator.setInterpolator((TimeInterpolator)interpolator);
            this.mScrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CollapsingToolbarLayout.this.setScrimAlpha((Integer)valueAnimator.getAnimatedValue());
                }
            });
        } else if (this.mScrimAnimator.isRunning()) {
            this.mScrimAnimator.cancel();
        }
        this.mScrimAnimator.setIntValues(new int[]{this.mScrimAlpha, n});
        this.mScrimAnimator.start();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void ensureToolbar() {
        block7: {
            if (!this.mRefreshToolbar) {
                return;
            }
            this.mToolbar = null;
            this.mToolbarDirectChild = null;
            if (this.mToolbarId != -1) {
                this.mToolbar = (Toolbar)this.findViewById(this.mToolbarId);
                if (this.mToolbar != null) {
                    this.mToolbarDirectChild = this.findDirectChild((View)this.mToolbar);
                }
            }
            if (this.mToolbar != null) break block7;
            Object var4_1 = null;
            int n = 0;
            int n2 = this.getChildCount();
            do {
                block9: {
                    void var3_7;
                    block8: {
                        Object var3_5 = var4_1;
                        if (n >= n2) break block8;
                        View view = this.getChildAt(n);
                        if (!(view instanceof Toolbar)) break block9;
                        Toolbar toolbar = (Toolbar)view;
                    }
                    this.mToolbar = var3_7;
                    break;
                }
                ++n;
            } while (true);
        }
        this.updateDummyView();
        this.mRefreshToolbar = false;
    }

    private View findDirectChild(View view) {
        View view2 = view;
        for (view = view.getParent(); view != this && view != null; view = view.getParent()) {
            if (!(view instanceof View)) continue;
            view2 = view;
        }
        return view2;
    }

    private static int getHeightWithMargins(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            return view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
        return view.getHeight();
    }

    static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper;
        ViewOffsetHelper viewOffsetHelper2 = viewOffsetHelper = (ViewOffsetHelper)view.getTag(R.id.view_offset_helper);
        if (viewOffsetHelper == null) {
            viewOffsetHelper2 = new ViewOffsetHelper(view);
            view.setTag(R.id.view_offset_helper, (Object)viewOffsetHelper2);
        }
        return viewOffsetHelper2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isToolbarChild(View view) {
        if (this.mToolbarDirectChild == null || this.mToolbarDirectChild == this) {
            if (view == this.mToolbar) return true;
            return false;
        }
        if (view != this.mToolbarDirectChild) return false;
        return true;
    }

    private void updateDummyView() {
        ViewParent viewParent;
        if (!this.mCollapsingTitleEnabled && this.mDummyView != null && (viewParent = this.mDummyView.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(this.mDummyView);
        }
        if (this.mCollapsingTitleEnabled && this.mToolbar != null) {
            if (this.mDummyView == null) {
                this.mDummyView = new View(this.getContext());
            }
            if (this.mDummyView.getParent() == null) {
                this.mToolbar.addView(this.mDummyView, -1, -1);
            }
        }
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n;
        super.draw(canvas);
        this.ensureToolbar();
        if (this.mToolbar == null && this.mContentScrim != null && this.mScrimAlpha > 0) {
            this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas);
        }
        if (this.mCollapsingTitleEnabled && this.mDrawCollapsingTitle) {
            this.mCollapsingTextHelper.draw(canvas);
        }
        if (this.mStatusBarScrim != null && this.mScrimAlpha > 0 && (n = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0) > 0) {
            this.mStatusBarScrim.setBounds(0, -this.mCurrentOffset, this.getWidth(), n - this.mCurrentOffset);
            this.mStatusBarScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mStatusBarScrim.draw(canvas);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.mContentScrim != null) {
            bl2 = bl;
            if (this.mScrimAlpha > 0) {
                bl2 = bl;
                if (this.isToolbarChild(view)) {
                    this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
                    this.mContentScrim.draw(canvas);
                    bl2 = true;
                }
            }
        }
        return super.drawChild(canvas, view, l) || bl2;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        boolean bl = false;
        Drawable drawable2 = this.mStatusBarScrim;
        boolean bl2 = bl;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = false | drawable2.setState(arrn);
            }
        }
        drawable2 = this.mContentScrim;
        bl = bl2;
        if (drawable2 != null) {
            bl = bl2;
            if (drawable2.isStateful()) {
                bl = bl2 | drawable2.setState(arrn);
            }
        }
        bl2 = bl;
        if (this.mCollapsingTextHelper != null) {
            bl2 = bl | this.mCollapsingTextHelper.setState(arrn);
        }
        if (bl2) {
            this.invalidate();
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getCollapsedTitleGravity() {
        return this.mCollapsingTextHelper.getCollapsedTextGravity();
    }

    public Typeface getCollapsedTitleTypeface() {
        return this.mCollapsingTextHelper.getCollapsedTypeface();
    }

    public Drawable getContentScrim() {
        return this.mContentScrim;
    }

    public int getExpandedTitleGravity() {
        return this.mCollapsingTextHelper.getExpandedTextGravity();
    }

    public int getExpandedTitleMarginBottom() {
        return this.mExpandedMarginBottom;
    }

    public int getExpandedTitleMarginEnd() {
        return this.mExpandedMarginEnd;
    }

    public int getExpandedTitleMarginStart() {
        return this.mExpandedMarginStart;
    }

    public int getExpandedTitleMarginTop() {
        return this.mExpandedMarginTop;
    }

    public Typeface getExpandedTitleTypeface() {
        return this.mCollapsingTextHelper.getExpandedTypeface();
    }

    final int getMaxOffsetForPinChild(View view) {
        ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(view);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        return this.getHeight() - viewOffsetHelper.getLayoutTop() - view.getHeight() - layoutParams.bottomMargin;
    }

    int getScrimAlpha() {
        return this.mScrimAlpha;
    }

    public long getScrimAnimationDuration() {
        return this.mScrimAnimationDuration;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getScrimVisibleHeightTrigger() {
        if (this.mScrimVisibleHeightTrigger >= 0) {
            return this.mScrimVisibleHeightTrigger;
        }
        int n = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0;
        int n2 = ViewCompat.getMinimumHeight((View)this);
        if (n2 > 0) {
            return Math.min(n2 * 2 + n, this.getHeight());
        }
        return this.getHeight() / 3;
    }

    public Drawable getStatusBarScrim() {
        return this.mStatusBarScrim;
    }

    public CharSequence getTitle() {
        if (this.mCollapsingTitleEnabled) {
            return this.mCollapsingTextHelper.getText();
        }
        return null;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent viewParent = this.getParent();
        if (viewParent instanceof AppBarLayout) {
            ViewCompat.setFitsSystemWindows((View)this, ViewCompat.getFitsSystemWindows((View)viewParent));
            if (this.mOnOffsetChangedListener == null) {
                this.mOnOffsetChangedListener = new OffsetUpdateListener();
            }
            ((AppBarLayout)viewParent).addOnOffsetChangedListener(this.mOnOffsetChangedListener);
            ViewCompat.requestApplyInsets((View)this);
        }
    }

    protected void onDetachedFromWindow() {
        ViewParent viewParent = this.getParent();
        if (this.mOnOffsetChangedListener != null && viewParent instanceof AppBarLayout) {
            ((AppBarLayout)viewParent).removeOnOffsetChangedListener(this.mOnOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Object object;
        int n5;
        int n6;
        int n7;
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mLastInsets != null) {
            n6 = this.mLastInsets.getSystemWindowInsetTop();
            n5 = this.getChildCount();
            for (n7 = 0; n7 < n5; ++n7) {
                object = this.getChildAt(n7);
                if (ViewCompat.getFitsSystemWindows((View)object) || object.getTop() >= n6) continue;
                ViewCompat.offsetTopAndBottom((View)object, n6);
            }
        }
        if (this.mCollapsingTitleEnabled && this.mDummyView != null) {
            bl = ViewCompat.isAttachedToWindow(this.mDummyView) && this.mDummyView.getVisibility() == 0;
            this.mDrawCollapsingTitle = bl;
            if (this.mDrawCollapsingTitle) {
                n7 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                object = this.mToolbarDirectChild != null ? this.mToolbarDirectChild : this.mToolbar;
                int n8 = this.getMaxOffsetForPinChild((View)object);
                ViewGroupUtils.getDescendantRect((ViewGroup)this, this.mDummyView, this.mTmpRect);
                object = this.mCollapsingTextHelper;
                int n9 = this.mTmpRect.left;
                n6 = n7 != 0 ? this.mToolbar.getTitleMarginEnd() : this.mToolbar.getTitleMarginStart();
                int n10 = this.mTmpRect.top;
                int n11 = this.mToolbar.getTitleMarginTop();
                int n12 = this.mTmpRect.right;
                n5 = n7 != 0 ? this.mToolbar.getTitleMarginStart() : this.mToolbar.getTitleMarginEnd();
                ((CollapsingTextHelper)object).setCollapsedBounds(n9 + n6, n11 + (n10 + n8), n5 + n12, this.mTmpRect.bottom + n8 - this.mToolbar.getTitleMarginBottom());
                object = this.mCollapsingTextHelper;
                n6 = n7 != 0 ? this.mExpandedMarginEnd : this.mExpandedMarginStart;
                n5 = this.mTmpRect.top;
                n8 = this.mExpandedMarginTop;
                n7 = n7 != 0 ? this.mExpandedMarginStart : this.mExpandedMarginEnd;
                ((CollapsingTextHelper)object).setExpandedBounds(n6, n8 + n5, n3 - n - n7, n4 - n2 - this.mExpandedMarginBottom);
                this.mCollapsingTextHelper.recalculate();
            }
        }
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            CollapsingToolbarLayout.getViewOffsetHelper(this.getChildAt(n)).onViewLayout();
        }
        if (this.mToolbar != null) {
            if (this.mCollapsingTitleEnabled && TextUtils.isEmpty((CharSequence)this.mCollapsingTextHelper.getText())) {
                this.mCollapsingTextHelper.setText(this.mToolbar.getTitle());
            }
            if (this.mToolbarDirectChild == null || this.mToolbarDirectChild == this) {
                this.setMinimumHeight(CollapsingToolbarLayout.getHeightWithMargins((View)this.mToolbar));
            } else {
                this.setMinimumHeight(CollapsingToolbarLayout.getHeightWithMargins(this.mToolbarDirectChild));
            }
        }
        this.updateScrimVisibility();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        this.ensureToolbar();
        super.onMeasure(n, n2);
        int n3 = View.MeasureSpec.getMode((int)n2);
        n2 = this.mLastInsets != null ? this.mLastInsets.getSystemWindowInsetTop() : 0;
        if (n3 == 0 && n2 > 0) {
            super.onMeasure(n, View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredHeight() + n2), (int)1073741824));
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (this.mContentScrim != null) {
            this.mContentScrim.setBounds(0, 0, n, n2);
        }
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = null;
        if (ViewCompat.getFitsSystemWindows((View)this)) {
            windowInsetsCompat2 = windowInsetsCompat;
        }
        if (!ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat2)) {
            this.mLastInsets = windowInsetsCompat2;
            this.requestLayout();
        }
        return windowInsetsCompat.consumeSystemWindowInsets();
    }

    public void setCollapsedTitleGravity(int n) {
        this.mCollapsingTextHelper.setCollapsedTextGravity(n);
    }

    public void setCollapsedTitleTextAppearance(int n) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(n);
    }

    public void setCollapsedTitleTextColor(int n) {
        this.setCollapsedTitleTextColor(ColorStateList.valueOf((int)n));
    }

    public void setCollapsedTitleTextColor(ColorStateList colorStateList) {
        this.mCollapsingTextHelper.setCollapsedTextColor(colorStateList);
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        this.mCollapsingTextHelper.setCollapsedTypeface(typeface);
    }

    public void setContentScrim(Drawable drawable2) {
        Drawable drawable3 = null;
        if (this.mContentScrim != drawable2) {
            if (this.mContentScrim != null) {
                this.mContentScrim.setCallback(null);
            }
            if (drawable2 != null) {
                drawable3 = drawable2.mutate();
            }
            this.mContentScrim = drawable3;
            if (this.mContentScrim != null) {
                this.mContentScrim.setBounds(0, 0, this.getWidth(), this.getHeight());
                this.mContentScrim.setCallback((Drawable.Callback)this);
                this.mContentScrim.setAlpha(this.mScrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void setContentScrimColor(int n) {
        this.setContentScrim((Drawable)new ColorDrawable(n));
    }

    public void setContentScrimResource(int n) {
        this.setContentScrim(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setExpandedTitleColor(int n) {
        this.setExpandedTitleTextColor(ColorStateList.valueOf((int)n));
    }

    public void setExpandedTitleGravity(int n) {
        this.mCollapsingTextHelper.setExpandedTextGravity(n);
    }

    public void setExpandedTitleMargin(int n, int n2, int n3, int n4) {
        this.mExpandedMarginStart = n;
        this.mExpandedMarginTop = n2;
        this.mExpandedMarginEnd = n3;
        this.mExpandedMarginBottom = n4;
        this.requestLayout();
    }

    public void setExpandedTitleMarginBottom(int n) {
        this.mExpandedMarginBottom = n;
        this.requestLayout();
    }

    public void setExpandedTitleMarginEnd(int n) {
        this.mExpandedMarginEnd = n;
        this.requestLayout();
    }

    public void setExpandedTitleMarginStart(int n) {
        this.mExpandedMarginStart = n;
        this.requestLayout();
    }

    public void setExpandedTitleMarginTop(int n) {
        this.mExpandedMarginTop = n;
        this.requestLayout();
    }

    public void setExpandedTitleTextAppearance(int n) {
        this.mCollapsingTextHelper.setExpandedTextAppearance(n);
    }

    public void setExpandedTitleTextColor(ColorStateList colorStateList) {
        this.mCollapsingTextHelper.setExpandedTextColor(colorStateList);
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        this.mCollapsingTextHelper.setExpandedTypeface(typeface);
    }

    void setScrimAlpha(int n) {
        if (n != this.mScrimAlpha) {
            if (this.mContentScrim != null && this.mToolbar != null) {
                ViewCompat.postInvalidateOnAnimation((View)this.mToolbar);
            }
            this.mScrimAlpha = n;
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public void setScrimAnimationDuration(long l) {
        this.mScrimAnimationDuration = l;
    }

    public void setScrimVisibleHeightTrigger(int n) {
        if (this.mScrimVisibleHeightTrigger != n) {
            this.mScrimVisibleHeightTrigger = n;
            this.updateScrimVisibility();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setScrimsShown(boolean bl) {
        boolean bl2 = ViewCompat.isLaidOut((View)this) && !this.isInEditMode();
        this.setScrimsShown(bl, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setScrimsShown(boolean bl, boolean bl2) {
        int n = 255;
        if (this.mScrimsAreShown == bl) return;
        if (bl2) {
            if (!bl) {
                n = 0;
            }
            this.animateScrim(n);
        } else {
            if (!bl) {
                n = 0;
            }
            this.setScrimAlpha(n);
        }
        this.mScrimsAreShown = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatusBarScrim(Drawable drawable2) {
        Drawable drawable3 = null;
        if (this.mStatusBarScrim == drawable2) return;
        if (this.mStatusBarScrim != null) {
            this.mStatusBarScrim.setCallback(null);
        }
        if (drawable2 != null) {
            drawable3 = drawable2.mutate();
        }
        this.mStatusBarScrim = drawable3;
        if (this.mStatusBarScrim != null) {
            if (this.mStatusBarScrim.isStateful()) {
                this.mStatusBarScrim.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection(this.mStatusBarScrim, ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.mStatusBarScrim;
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
            this.mStatusBarScrim.setCallback((Drawable.Callback)this);
            this.mStatusBarScrim.setAlpha(this.mScrimAlpha);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setStatusBarScrimColor(int n) {
        this.setStatusBarScrim((Drawable)new ColorDrawable(n));
    }

    public void setStatusBarScrimResource(int n) {
        this.setStatusBarScrim(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setTitle(CharSequence charSequence) {
        this.mCollapsingTextHelper.setText(charSequence);
    }

    public void setTitleEnabled(boolean bl) {
        if (bl != this.mCollapsingTitleEnabled) {
            this.mCollapsingTitleEnabled = bl;
            this.updateDummyView();
            this.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        if (this.mStatusBarScrim != null && this.mStatusBarScrim.isVisible() != bl) {
            this.mStatusBarScrim.setVisible(bl, false);
        }
        if (this.mContentScrim != null && this.mContentScrim.isVisible() != bl) {
            this.mContentScrim.setVisible(bl, false);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final void updateScrimVisibility() {
        if (this.mContentScrim != null || this.mStatusBarScrim != null) {
            boolean bl = this.getHeight() + this.mCurrentOffset < this.getScrimVisibleHeightTrigger();
            this.setScrimsShown(bl);
        }
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mContentScrim || drawable2 == this.mStatusBarScrim;
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        int mCollapseMode = 0;
        float mParallaxMult = 0.5f;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout_Layout);
            this.mCollapseMode = context.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            this.setParallaxMultiplier(context.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void setParallaxMultiplier(float f) {
            this.mParallaxMult = f;
        }
    }

    private class OffsetUpdateListener
    implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int n) {
            int n2;
            CollapsingToolbarLayout.this.mCurrentOffset = n;
            int n3 = CollapsingToolbarLayout.this.mLastInsets != null ? CollapsingToolbarLayout.this.mLastInsets.getSystemWindowInsetTop() : 0;
            int n4 = CollapsingToolbarLayout.this.getChildCount();
            block4: for (n2 = 0; n2 < n4; ++n2) {
                appBarLayout = CollapsingToolbarLayout.this.getChildAt(n2);
                LayoutParams layoutParams = (LayoutParams)appBarLayout.getLayoutParams();
                ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper((View)appBarLayout);
                switch (layoutParams.mCollapseMode) {
                    case 1: {
                        viewOffsetHelper.setTopAndBottomOffset(MathUtils.clamp(-n, 0, CollapsingToolbarLayout.this.getMaxOffsetForPinChild((View)appBarLayout)));
                    }
                    default: {
                        continue block4;
                    }
                    case 2: {
                        viewOffsetHelper.setTopAndBottomOffset(Math.round((float)(-n) * layoutParams.mParallaxMult));
                    }
                }
            }
            CollapsingToolbarLayout.this.updateScrimVisibility();
            if (CollapsingToolbarLayout.this.mStatusBarScrim != null && n3 > 0) {
                ViewCompat.postInvalidateOnAnimation((View)CollapsingToolbarLayout.this);
            }
            n2 = CollapsingToolbarLayout.this.getHeight();
            n4 = ViewCompat.getMinimumHeight((View)CollapsingToolbarLayout.this);
            CollapsingToolbarLayout.this.mCollapsingTextHelper.setExpansionFraction((float)Math.abs(n) / (float)(n2 - n4 - n3));
        }
    }

}

