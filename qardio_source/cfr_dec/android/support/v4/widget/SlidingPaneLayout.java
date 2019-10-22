/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout
extends ViewGroup {
    static final SlidingPanelLayoutImpl IMPL = Build.VERSION.SDK_INT >= 17 ? new SlidingPanelLayoutImplJBMR1() : (Build.VERSION.SDK_INT >= 16 ? new SlidingPanelLayoutImplJB() : new SlidingPanelLayoutImplBase());
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout = true;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<DisableLayerRunnable> mPostedRunnables;
    boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor = -858993460;
    private final Rect mTmpRect = new Rect();

    public SlidingPaneLayout(Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mPostedRunnables = new ArrayList();
        float f = context.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int)(32.0f * f + 0.5f);
        this.setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility((View)this, 1);
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.mDragHelper.setMinVelocity(400.0f * f);
    }

    private boolean closePane(View view, int n) {
        boolean bl = false;
        if (this.mFirstLayout || this.smoothSlideTo(0.0f, n)) {
            this.mPreservedOpenState = false;
            bl = true;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void dimChildView(View object, float f, int n) {
        void var3_4;
        void var2_3;
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (var2_3 > 0.0f && var3_4 != false) {
            int n2 = (int)((float)((0xFF000000 & var3_4) >>> 24) * var2_3);
            if (layoutParams.dimPaint == null) {
                layoutParams.dimPaint = new Paint();
            }
            layoutParams.dimPaint.setColorFilter((ColorFilter)new PorterDuffColorFilter(n2 << 24 | 0xFFFFFF & var3_4, PorterDuff.Mode.SRC_OVER));
            if (object.getLayerType() != 2) {
                object.setLayerType(2, layoutParams.dimPaint);
            }
            this.invalidateChildRegion((View)object);
            return;
        } else {
            if (object.getLayerType() == 0) return;
            {
                if (layoutParams.dimPaint != null) {
                    layoutParams.dimPaint.setColorFilter(null);
                }
                DisableLayerRunnable disableLayerRunnable = new DisableLayerRunnable((View)object);
                this.mPostedRunnables.add(disableLayerRunnable);
                ViewCompat.postOnAnimation((View)this, disableLayerRunnable);
                return;
            }
        }
    }

    private boolean openPane(View view, int n) {
        if (this.mFirstLayout || this.smoothSlideTo(1.0f, n)) {
            this.mPreservedOpenState = true;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void parallaxOtherViews(float f) {
        int n;
        boolean bl = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        n = layoutParams.dimWhenOffset && (n = bl ? layoutParams.rightMargin : layoutParams.leftMargin) <= 0 ? 1 : 0;
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            layoutParams = this.getChildAt(n3);
            if (layoutParams != this.mSlideableView) {
                int n4;
                int n5 = (int)((1.0f - this.mParallaxOffset) * (float)this.mParallaxBy);
                this.mParallaxOffset = f;
                n5 = n4 = n5 - (int)((1.0f - f) * (float)this.mParallaxBy);
                if (bl) {
                    n5 = -n4;
                }
                layoutParams.offsetLeftAndRight(n5);
                if (n != 0) {
                    float f2 = bl ? this.mParallaxOffset - 1.0f : 1.0f - this.mParallaxOffset;
                    this.dimChildView((View)layoutParams, f2, this.mCoveredFadeColor);
                }
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean viewIsOpaque(View view) {
        block6: {
            block5: {
                if (view.isOpaque()) break block5;
                if (Build.VERSION.SDK_INT >= 18) {
                    return false;
                }
                if ((view = view.getBackground()) == null) {
                    return false;
                }
                if (view.getOpacity() != -1) break block6;
            }
            return true;
        }
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public boolean closePane() {
        return this.closePane(this.mSlideableView, 0);
    }

    public void computeScroll() {
        block3: {
            block2: {
                if (!this.mDragHelper.continueSettling(true)) break block2;
                if (this.mCanSlide) break block3;
                this.mDragHelper.abort();
            }
            return;
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    void dispatchOnPanelClosed(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelOpened(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelSlide(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(view, this.mSlideOffset);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n;
        int n2;
        super.draw(canvas);
        Drawable drawable2 = this.isLayoutRtlSupport() ? this.mShadowDrawableRight : this.mShadowDrawableLeft;
        if (this.getChildCount() <= 1) return;
        View view = this.getChildAt(1);
        if (view == null) return;
        if (drawable2 == null) {
            return;
        }
        int n3 = view.getTop();
        int n4 = view.getBottom();
        int n5 = drawable2.getIntrinsicWidth();
        if (this.isLayoutRtlSupport()) {
            n2 = view.getRight();
            n = n2 + n5;
        } else {
            n = view.getLeft();
            n2 = n - n5;
        }
        drawable2.setBounds(n2, n3, n, n4);
        drawable2.draw(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean drawChild(Canvas canvas, View view, long l) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n = canvas.save(2);
        if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean bl = super.drawChild(canvas, view, l);
        canvas.restoreToCount(n);
        return bl;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    void invalidateChildRegion(View view) {
        IMPL.invalidateChildRegion(this, view);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean isDimmed(View object) {
        block3: {
            block2: {
                if (object == null) break block2;
                LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
                if (this.mCanSlide && layoutParams.dimWhenOffset && this.mSlideOffset > 0.0f) break block3;
            }
            return false;
        }
        return true;
    }

    boolean isLayoutRtlSupport() {
        return ViewCompat.getLayoutDirection((View)this) == 1;
    }

    public boolean isOpen() {
        return !this.mCanSlide || this.mSlideOffset == 1.0f;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int n = this.mPostedRunnables.size();
        for (int i = 0; i < n; ++i) {
            this.mPostedRunnables.get(i).run();
        }
        this.mPostedRunnables.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        View view;
        int n = motionEvent.getActionMasked();
        if (!this.mCanSlide && n == 0 && this.getChildCount() > 1 && (view = this.getChildAt(1)) != null) {
            boolean bl2 = !this.mDragHelper.isViewUnder(view, (int)motionEvent.getX(), (int)motionEvent.getY());
            this.mPreservedOpenState = bl2;
        }
        if (!this.mCanSlide || this.mIsUnableToDrag && n != 0) {
            this.mDragHelper.cancel();
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (n == 3 || n == 1) {
            this.mDragHelper.cancel();
            return false;
        }
        boolean bl3 = bl = false;
        switch (n) {
            default: {
                bl3 = bl;
                return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
            }
            case 0: {
                this.mIsUnableToDrag = false;
                float f = motionEvent.getX();
                float f2 = motionEvent.getY();
                this.mInitialMotionX = f;
                this.mInitialMotionY = f2;
                bl3 = bl;
                if (!this.mDragHelper.isViewUnder(this.mSlideableView, (int)f, (int)f2)) return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
                {
                    bl3 = bl;
                    if (!this.isDimmed(this.mSlideableView)) return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
                    {
                        bl3 = true;
                        return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
                    }
                }
            }
            case 2: {
                float f = motionEvent.getX();
                float f3 = motionEvent.getY();
                f = Math.abs(f - this.mInitialMotionX);
                f3 = Math.abs(f3 - this.mInitialMotionY);
                bl3 = bl;
                if (!(f > (float)this.mDragHelper.getTouchSlop())) return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
                {
                    bl3 = bl;
                    if (!(f3 > f)) return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
                    {
                        this.mDragHelper.cancel();
                        this.mIsUnableToDrag = true;
                        return false;
                    }
                }
            }
            case 1: 
        }
        return this.mDragHelper.shouldInterceptTouchEvent(motionEvent) || bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        boolean bl2 = this.isLayoutRtlSupport();
        if (bl2) {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        }
        int n5 = n3 - n;
        n = bl2 ? this.getPaddingRight() : this.getPaddingLeft();
        n3 = bl2 ? this.getPaddingLeft() : this.getPaddingRight();
        int n6 = this.getPaddingTop();
        int n7 = this.getChildCount();
        n4 = n;
        if (this.mFirstLayout) {
            float f = this.mCanSlide && this.mPreservedOpenState ? 1.0f : 0.0f;
            this.mSlideOffset = f;
        }
        int n8 = 0;
        n2 = n;
        n = n4;
        for (n4 = n8; n4 < n7; ++n4) {
            View view = this.getChildAt(n4);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n9 = view.getMeasuredWidth();
            int n10 = 0;
            if (layoutParams.slideable) {
                n8 = layoutParams.leftMargin;
                int n11 = layoutParams.rightMargin;
                this.mSlideRange = n11 = Math.min(n, n5 - n3 - this.mOverhangSize) - n2 - (n8 + n11);
                n8 = bl2 ? layoutParams.rightMargin : layoutParams.leftMargin;
                bl = n2 + n8 + n11 + n9 / 2 > n5 - n3;
                layoutParams.dimWhenOffset = bl;
                n11 = (int)((float)n11 * this.mSlideOffset);
                n2 += n11 + n8;
                this.mSlideOffset = (float)n11 / (float)this.mSlideRange;
                n8 = n10;
            } else if (this.mCanSlide && this.mParallaxBy != 0) {
                n8 = (int)((1.0f - this.mSlideOffset) * (float)this.mParallaxBy);
                n2 = n;
            } else {
                n2 = n;
                n8 = n10;
            }
            if (bl2) {
                n10 = n5 - n2 + n8;
                n8 = n10 - n9;
            } else {
                n8 = n2 - n8;
                n10 = n8 + n9;
            }
            view.layout(n8, n6, n10, n6 + view.getMeasuredHeight());
            n += view.getWidth();
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    this.parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams)this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            } else {
                for (n = 0; n < n7; ++n) {
                    this.dimChildView(this.getChildAt(n), 0.0f, this.mSliderFadeColor);
                }
            }
            this.updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        LayoutParams layoutParams;
        View view;
        int n5;
        int n6;
        int n7;
        int n8 = View.MeasureSpec.getMode((int)n);
        int n9 = View.MeasureSpec.getSize((int)n);
        int n10 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        if (n8 != 1073741824) {
            if (!this.isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            }
            if (n8 == Integer.MIN_VALUE) {
                n7 = n9;
                n = n2;
                n5 = n10;
            } else {
                n5 = n10;
                n = n2;
                n7 = n9;
                if (n8 == 0) {
                    n7 = 300;
                    n5 = n10;
                    n = n2;
                }
            }
        } else {
            n5 = n10;
            n = n2;
            n7 = n9;
            if (n10 == 0) {
                if (!this.isInEditMode()) {
                    throw new IllegalStateException("Height must not be UNSPECIFIED");
                }
                n5 = n10;
                n = n2;
                n7 = n9;
                if (n10 == 0) {
                    n5 = Integer.MIN_VALUE;
                    n = 300;
                    n7 = n9;
                }
            }
        }
        n9 = 0;
        n2 = 0;
        switch (n5) {
            case 1073741824: {
                n9 = n2 = n - this.getPaddingTop() - this.getPaddingBottom();
                break;
            }
            case Integer.MIN_VALUE: {
                n2 = n - this.getPaddingTop() - this.getPaddingBottom();
                break;
            }
        }
        float f = 0.0f;
        boolean bl = false;
        n8 = n6 = n7 - this.getPaddingLeft() - this.getPaddingRight();
        int n11 = this.getChildCount();
        if (n11 > 2) {
            Log.e((String)"SlidingPaneLayout", (String)"onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        for (n3 = 0; n3 < n11; ++n3) {
            boolean bl2;
            block28: {
                float f2;
                block29: {
                    block27: {
                        view = this.getChildAt(n3);
                        layoutParams = (LayoutParams)view.getLayoutParams();
                        if (view.getVisibility() != 8) break block27;
                        layoutParams.dimWhenOffset = false;
                        n4 = n8;
                        n10 = n9;
                        bl2 = bl;
                        break block28;
                    }
                    f2 = f;
                    if (!(layoutParams.weight > 0.0f)) break block29;
                    f2 = f + layoutParams.weight;
                    bl2 = bl;
                    n10 = n9;
                    f = f2;
                    n4 = n8;
                    if (layoutParams.width == 0) break block28;
                }
                n = layoutParams.leftMargin + layoutParams.rightMargin;
                n = layoutParams.width == -2 ? View.MeasureSpec.makeMeasureSpec((int)(n6 - n), (int)Integer.MIN_VALUE) : (layoutParams.width == -1 ? View.MeasureSpec.makeMeasureSpec((int)(n6 - n), (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.width, (int)1073741824));
                n10 = layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824));
                view.measure(n, n10);
                n10 = view.getMeasuredWidth();
                n4 = view.getMeasuredHeight();
                n = n9;
                if (n5 == Integer.MIN_VALUE) {
                    n = n9;
                    if (n4 > n9) {
                        n = Math.min(n4, n2);
                    }
                }
                bl2 = (n9 = n8 - n10) < 0;
                layoutParams.slideable = bl2;
                bl |= bl2;
                bl2 = bl;
                n10 = n;
                f = f2;
                n4 = n9;
                if (layoutParams.slideable) {
                    this.mSlideableView = view;
                    bl2 = bl;
                    n10 = n;
                    f = f2;
                    n4 = n9;
                }
            }
            bl = bl2;
            n9 = n10;
            n8 = n4;
        }
        if (bl || f > 0.0f) {
            n3 = n6 - this.mOverhangSize;
            for (n10 = 0; n10 < n11; ++n10) {
                view = this.getChildAt(n10);
                if (view.getVisibility() == 8) continue;
                layoutParams = (LayoutParams)view.getLayoutParams();
                if (view.getVisibility() == 8) continue;
                n = layoutParams.width == 0 && layoutParams.weight > 0.0f ? 1 : 0;
                n5 = n != 0 ? 0 : view.getMeasuredWidth();
                if (bl && view != this.mSlideableView) {
                    if (layoutParams.width >= 0 || n5 <= n3 && !(layoutParams.weight > 0.0f)) continue;
                    n = n != 0 ? (layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824))) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824);
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824), n);
                    continue;
                }
                if (!(layoutParams.weight > 0.0f)) continue;
                n = layoutParams.width == 0 ? (layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824))) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824);
                if (bl) {
                    n4 = n6 - (layoutParams.leftMargin + layoutParams.rightMargin);
                    int n12 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824);
                    if (n5 == n4) continue;
                    view.measure(n12, n);
                    continue;
                }
                n4 = Math.max(0, n8);
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n5 + (int)(layoutParams.weight * (float)n4 / f)), (int)1073741824), n);
            }
        }
        this.setMeasuredDimension(n7, this.getPaddingTop() + n9 + this.getPaddingBottom());
        this.mCanSlide = bl;
        if (this.mDragHelper.getViewDragState() != 0 && !bl) {
            this.mDragHelper.abort();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void onPanelDragged(int n) {
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean bl = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        int n2 = this.mSlideableView.getWidth();
        if (bl) {
            n = this.getWidth() - n - n2;
        }
        n2 = bl ? this.getPaddingRight() : this.getPaddingLeft();
        int n3 = bl ? layoutParams.rightMargin : layoutParams.leftMargin;
        this.mSlideOffset = (float)(n - (n2 + n3)) / (float)this.mSlideRange;
        if (this.mParallaxBy != 0) {
            this.parallaxOtherViews(this.mSlideOffset);
        }
        if (layoutParams.dimWhenOffset) {
            this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        this.dispatchOnPanelSlide(this.mSlideableView);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (parcelable.isOpen) {
            this.openPane();
        } else {
            this.closePane();
        }
        this.mPreservedOpenState = parcelable.isOpen;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        boolean bl = this.isSlideable() ? this.isOpen() : this.mPreservedOpenState;
        savedState.isOpen = bl;
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3) {
            this.mFirstLayout = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCanSlide) {
            return super.onTouchEvent(motionEvent);
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        boolean bl = true;
        switch (motionEvent.getActionMasked()) {
            default: {
                return true;
            }
            case 0: {
                float f = motionEvent.getX();
                float f2 = motionEvent.getY();
                this.mInitialMotionX = f;
                this.mInitialMotionY = f2;
                return true;
            }
            case 1: 
        }
        boolean bl2 = bl;
        if (!this.isDimmed(this.mSlideableView)) return bl2;
        float f = motionEvent.getX();
        float f3 = motionEvent.getY();
        float f4 = f - this.mInitialMotionX;
        float f5 = f3 - this.mInitialMotionY;
        int n = this.mDragHelper.getTouchSlop();
        bl2 = bl;
        if (!(f4 * f4 + f5 * f5 < (float)(n * n))) return bl2;
        bl2 = bl;
        if (!this.mDragHelper.isViewUnder(this.mSlideableView, (int)f, (int)f3)) return bl2;
        this.closePane(this.mSlideableView, 0);
        return true;
    }

    public boolean openPane() {
        return this.openPane(this.mSlideableView, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!this.isInTouchMode() && !this.mCanSlide) {
            boolean bl = view == this.mSlideableView;
            this.mPreservedOpenState = bl;
        }
    }

    void setAllChildrenVisible() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() != 4) continue;
            view.setVisibility(0);
        }
    }

    public void setCoveredFadeColor(int n) {
        this.mCoveredFadeColor = n;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.mPanelSlideListener = panelSlideListener;
    }

    public void setParallaxDistance(int n) {
        this.mParallaxBy = n;
        this.requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable2) {
        this.setShadowDrawableLeft(drawable2);
    }

    public void setShadowDrawableLeft(Drawable drawable2) {
        this.mShadowDrawableLeft = drawable2;
    }

    public void setShadowDrawableRight(Drawable drawable2) {
        this.mShadowDrawableRight = drawable2;
    }

    @Deprecated
    public void setShadowResource(int n) {
        this.setShadowDrawable(this.getResources().getDrawable(n));
    }

    public void setShadowResourceLeft(int n) {
        this.setShadowDrawableLeft(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setShadowResourceRight(int n) {
        this.setShadowDrawableRight(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setSliderFadeColor(int n) {
        this.mSliderFadeColor = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean smoothSlideTo(float f, int n) {
        block6: {
            block5: {
                if (!this.mCanSlide) break block5;
                boolean bl = this.isLayoutRtlSupport();
                LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
                if (bl) {
                    n = this.getPaddingRight();
                    int n2 = layoutParams.rightMargin;
                    int n3 = this.mSlideableView.getWidth();
                    n = (int)((float)this.getWidth() - ((float)(n + n2) + (float)this.mSlideRange * f + (float)n3));
                } else {
                    n = (int)((float)(this.getPaddingLeft() + layoutParams.leftMargin) + (float)this.mSlideRange * f);
                }
                if (this.mDragHelper.smoothSlideViewTo(this.mSlideableView, n, this.mSlideableView.getTop())) break block6;
            }
            return false;
        }
        this.setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateObscuredViewsVisibility(View view) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl = this.isLayoutRtlSupport();
        int n5 = bl ? this.getWidth() - this.getPaddingRight() : this.getPaddingLeft();
        int n6 = bl ? this.getPaddingLeft() : this.getWidth() - this.getPaddingRight();
        int n7 = this.getPaddingTop();
        int n8 = this.getHeight();
        int n9 = this.getPaddingBottom();
        if (view != null && SlidingPaneLayout.viewIsOpaque(view)) {
            n3 = view.getLeft();
            n = view.getRight();
            n2 = view.getTop();
            n4 = view.getBottom();
        } else {
            n4 = 0;
            n2 = 0;
            n = 0;
            n3 = 0;
        }
        int n10 = 0;
        int n11 = this.getChildCount();
        View view2;
        while (n10 < n11 && (view2 = this.getChildAt(n10)) != view) {
            if (view2.getVisibility() != 8) {
                int n12 = bl ? n6 : n5;
                int n13 = Math.max(n12, view2.getLeft());
                int n14 = Math.max(n7, view2.getTop());
                n12 = bl ? n5 : n6;
                n12 = Math.min(n12, view2.getRight());
                int n15 = Math.min(n8 - n9, view2.getBottom());
                n12 = n13 >= n3 && n14 >= n2 && n12 <= n && n15 <= n4 ? 4 : 0;
                view2.setVisibility(n12);
            }
            ++n10;
        }
        return;
    }

    class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }

        public boolean filter(View view) {
            return SlidingPaneLayout.this.isDimmed(view);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)SlidingPaneLayout.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat2);
            this.copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompat2);
            accessibilityNodeInfoCompat2.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            view = ViewCompat.getParentForAccessibility(view);
            if (view instanceof View) {
                accessibilityNodeInfoCompat.setParent(view);
            }
            int n = SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                view = SlidingPaneLayout.this.getChildAt(i);
                if (this.filter(view) || view.getVisibility() != 0) continue;
                ViewCompat.setImportantForAccessibility(view, 1);
                accessibilityNodeInfoCompat.addChild(view);
            }
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!this.filter(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }
    }

    private class DisableLayerRunnable
    implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View view) {
            this.mChildView = view;
        }

        @Override
        public void run() {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                this.mChildView.setLayerType(0, null);
                SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    private class DragHelperCallback
    extends ViewDragHelper.Callback {
        DragHelperCallback() {
        }

        @Override
        public int clampViewPositionHorizontal(View object, int n, int n2) {
            object = (LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                n2 = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.getPaddingRight() + object.rightMargin + SlidingPaneLayout.this.mSlideableView.getWidth());
                int n3 = SlidingPaneLayout.this.mSlideRange;
                return Math.max(Math.min(n, n2), n2 - n3);
            }
            n2 = SlidingPaneLayout.this.getPaddingLeft() + object.leftMargin;
            int n4 = SlidingPaneLayout.this.mSlideRange;
            return Math.min(Math.max(n, n2), n2 + n4);
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.mSlideRange;
        }

        @Override
        public void onEdgeDragStarted(int n, int n2) {
            SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, n2);
        }

        @Override
        public void onViewCaptured(View view, int n) {
            SlidingPaneLayout.this.setAllChildrenVisible();
        }

        @Override
        public void onViewDragStateChanged(int n) {
            block3: {
                block2: {
                    if (SlidingPaneLayout.this.mDragHelper.getViewDragState() != 0) break block2;
                    if (SlidingPaneLayout.this.mSlideOffset != 0.0f) break block3;
                    SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
                    SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
                    SlidingPaneLayout.this.mPreservedOpenState = false;
                }
                return;
            }
            SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
            SlidingPaneLayout.this.mPreservedOpenState = true;
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            SlidingPaneLayout.this.onPanelDragged(n);
            SlidingPaneLayout.this.invalidate();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f, float f2) {
            int n;
            block7: {
                int n2;
                block8: {
                    LayoutParams layoutParams;
                    block4: {
                        int n3;
                        block6: {
                            block5: {
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (!SlidingPaneLayout.this.isLayoutRtlSupport()) break block4;
                                n3 = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                                if (f < 0.0f) break block5;
                                n = n3;
                                if (f != 0.0f) break block6;
                                n = n3;
                                if (!(SlidingPaneLayout.this.mSlideOffset > 0.5f)) break block6;
                            }
                            n = n3 + SlidingPaneLayout.this.mSlideRange;
                        }
                        n3 = SlidingPaneLayout.this.mSlideableView.getWidth();
                        n = SlidingPaneLayout.this.getWidth() - n - n3;
                        break block7;
                    }
                    n2 = SlidingPaneLayout.this.getPaddingLeft() + layoutParams.leftMargin;
                    if (f > 0.0f) break block8;
                    n = n2;
                    if (f != 0.0f) break block7;
                    n = n2;
                    if (!(SlidingPaneLayout.this.mSlideOffset > 0.5f)) break block7;
                }
                n = n2 + SlidingPaneLayout.this.mSlideRange;
            }
            SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(n, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            if (SlidingPaneLayout.this.mIsUnableToDrag) {
                return false;
            }
            return ((LayoutParams)view.getLayoutParams()).slideable;
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        private static final int[] ATTRS = new int[]{16843137};
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, ATTRS);
            this.weight = context.getFloat(0, 0.0f);
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public static interface PanelSlideListener {
        public void onPanelClosed(View var1);

        public void onPanelOpened(View var1);

        public void onPanelSlide(View var1, float var2);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, null);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean isOpen;

        /*
         * Enabled aggressive block sorting
         */
        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            boolean bl = parcel.readInt() != 0;
            this.isOpen = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            n = this.isOpen ? 1 : 0;
            parcel.writeInt(n);
        }

    }

    static interface SlidingPanelLayoutImpl {
        public void invalidateChildRegion(SlidingPaneLayout var1, View var2);
    }

    static class SlidingPanelLayoutImplBase
    implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() {
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.postInvalidateOnAnimation((View)slidingPaneLayout, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    static class SlidingPanelLayoutImplJB
    extends SlidingPanelLayoutImplBase {
        private Method mGetDisplayList;
        private Field mRecreateDisplayList;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        SlidingPanelLayoutImplJB() {
            try {
                this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)"SlidingPaneLayout", (String)"Couldn't fetch getDisplayList method; dimming won't work right.", (Throwable)noSuchMethodException);
            }
            try {
                this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
                this.mRecreateDisplayList.setAccessible(true);
                return;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)"SlidingPaneLayout", (String)"Couldn't fetch mRecreateDisplayList field; dimming will be slow.", (Throwable)noSuchFieldException);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            if (this.mGetDisplayList != null && this.mRecreateDisplayList != null) {
                try {
                    this.mRecreateDisplayList.setBoolean((Object)view, true);
                    this.mGetDisplayList.invoke((Object)view, null);
                }
                catch (Exception exception) {
                    Log.e((String)"SlidingPaneLayout", (String)"Error refreshing display list state", (Throwable)exception);
                }
                super.invalidateChildRegion(slidingPaneLayout, view);
                return;
            }
            view.invalidate();
        }
    }

    static class SlidingPanelLayoutImplJBMR1
    extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() {
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.setLayerPaint(view, ((LayoutParams)view.getLayoutParams()).dimPaint);
        }
    }

}

