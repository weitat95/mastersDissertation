/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityRecord
 *  android.view.animation.AnimationUtils
 *  android.widget.EdgeEffect
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.OverScroller
 *  android.widget.ScrollView
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import java.util.ArrayList;

public class NestedScrollView
extends FrameLayout
implements NestedScrollingChild2,
NestedScrollingParent,
ScrollingView {
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int[] SCROLLVIEW_STYLEABLE = new int[]{16843130};
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId = -1;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo = null;
    private EdgeEffect mEdgeGlowBottom;
    private EdgeEffect mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged = false;
    private boolean mIsLaidOut = false;
    private boolean mIsLayoutDirty = true;
    private int mLastMotionY;
    private long mLastScroll;
    private int mLastScrollerY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled = true;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    public NestedScrollView(Context context) {
        this(context, null);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.initScrollView();
        context = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, n, 0);
        this.setFillViewport(context.getBoolean(0, false));
        context.recycle();
        this.mParentHelper = new NestedScrollingParentHelper((ViewGroup)this);
        this.mChildHelper = new NestedScrollingChildHelper((View)this);
        this.setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate((View)this, ACCESSIBILITY_DELEGATE);
    }

    private boolean canScroll() {
        boolean bl = false;
        View view = this.getChildAt(0);
        boolean bl2 = bl;
        if (view != null) {
            int n = view.getHeight();
            bl2 = bl;
            if (this.getHeight() < this.getPaddingTop() + n + this.getPaddingBottom()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int clamp(int n, int n2, int n3) {
        if (n2 >= n3) return 0;
        if (n < 0) {
            return 0;
        }
        int n4 = n;
        if (n2 + n <= n3) return n4;
        return n3 - n2;
    }

    private void doScrollY(int n) {
        block3: {
            block2: {
                if (n == 0) break block2;
                if (!this.mSmoothScrollingEnabled) break block3;
                this.smoothScrollBy(0, n);
            }
            return;
        }
        this.scrollBy(0, n);
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.recycleVelocityTracker();
        this.stopNestedScroll(0);
        if (this.mEdgeGlowTop != null) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
    }

    private void ensureGlows() {
        if (this.getOverScrollMode() != 2) {
            if (this.mEdgeGlowTop == null) {
                Context context = this.getContext();
                this.mEdgeGlowTop = new EdgeEffect(context);
                this.mEdgeGlowBottom = new EdgeEffect(context);
            }
            return;
        }
        this.mEdgeGlowTop = null;
        this.mEdgeGlowBottom = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View findFocusableViewInBounds(boolean bl, int n, int n2) {
        ArrayList arrayList = this.getFocusables(2);
        View view = null;
        boolean bl2 = false;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            View view2 = (View)arrayList.get(n4);
            int n5 = view2.getTop();
            int n6 = view2.getBottom();
            View view3 = view;
            boolean bl3 = bl2;
            if (n < n6) {
                view3 = view;
                bl3 = bl2;
                if (n5 < n2) {
                    boolean bl4 = n < n5 && n6 < n2;
                    if (view == null) {
                        view3 = view2;
                        bl3 = bl4;
                    } else {
                        n5 = bl && n5 < view.getTop() || !bl && n6 > view.getBottom() ? 1 : 0;
                        if (bl2) {
                            view3 = view;
                            bl3 = bl2;
                            if (bl4) {
                                view3 = view;
                                bl3 = bl2;
                                if (n5 != 0) {
                                    view3 = view2;
                                    bl3 = bl2;
                                }
                            }
                        } else if (bl4) {
                            view3 = view2;
                            bl3 = true;
                        } else {
                            view3 = view;
                            bl3 = bl2;
                            if (n5 != 0) {
                                view3 = view2;
                                bl3 = bl2;
                            }
                        }
                    }
                }
            }
            ++n4;
            view = view3;
            bl2 = bl3;
        }
        return view;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void flingWithNestedDispatch(int n) {
        int n2 = this.getScrollY();
        boolean bl = !(n2 <= 0 && n <= 0 || n2 >= this.getScrollRange() && n >= 0);
        if (!this.dispatchNestedPreFling(0.0f, n)) {
            this.dispatchNestedFling(0.0f, n, bl);
            this.fling(n);
        }
    }

    private float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = this.getContext();
            if (!context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    private boolean inChild(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.getChildCount() > 0) {
            int n3 = this.getScrollY();
            View view = this.getChildAt(0);
            bl2 = bl;
            if (n2 >= view.getTop() - n3) {
                bl2 = bl;
                if (n2 < view.getBottom() - n3) {
                    bl2 = bl;
                    if (n >= view.getLeft()) {
                        bl2 = bl;
                        if (n < view.getRight()) {
                            bl2 = true;
                        }
                    }
                }
            }
        }
        return bl2;
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
            return;
        }
        this.mVelocityTracker.clear();
    }

    private void initScrollView() {
        this.mScroller = new OverScroller(this.getContext());
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private boolean isOffScreen(View view) {
        boolean bl = false;
        if (!this.isWithinDeltaOfScreen(view, 0, this.getHeight())) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isViewDescendantOf(View view, View view2) {
        return view == view2 || (view = view.getParent()) instanceof ViewGroup && NestedScrollView.isViewDescendantOf(view, view2);
    }

    private boolean isWithinDeltaOfScreen(View view, int n, int n2) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + n >= this.getScrollY() && this.mTempRect.top - n <= this.getScrollY() + n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionY = (int)motionEvent.getY(n);
            this.mActivePointerId = motionEvent.getPointerId(n);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private boolean scrollAndFocus(int n, int n2, int n3) {
        View view;
        void var8_11;
        boolean bl = true;
        int n4 = this.getHeight();
        int n5 = this.getScrollY();
        n4 = n5 + n4;
        boolean bl2 = n == 33;
        View view2 = view = this.findFocusableViewInBounds(bl2, n2, n3);
        if (view == null) {
            NestedScrollView nestedScrollView = this;
        }
        if (n2 >= n5 && n3 <= n4) {
            bl2 = false;
        } else {
            n2 = bl2 ? (n2 -= n5) : n3 - n4;
            this.doScrollY(n2);
            bl2 = bl;
        }
        if (var8_11 != this.findFocus()) {
            var8_11.requestFocus(n);
        }
        return bl2;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n = this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (n != 0) {
            this.scrollBy(0, n);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean scrollToChildRect(Rect rect, boolean bl) {
        int n = this.computeScrollDeltaToGetChildRectOnScreen(rect);
        if (n == 0) return false;
        boolean bl2 = true;
        if (!bl2) return bl2;
        if (bl) {
            this.scrollBy(0, n);
            return bl2;
        }
        this.smoothScrollBy(0, n);
        return bl2;
    }

    public void addView(View view) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int n) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, n);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, n, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean arrowScroll(int n) {
        View view;
        boolean bl = false;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, n);
        int n2 = this.getMaxScrollAmount();
        if (view != null && this.isWithinDeltaOfScreen(view, n2, this.getHeight())) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            view.requestFocus(n);
        } else {
            int n3;
            int n4 = n2;
            if (n == 33 && this.getScrollY() < n4) {
                n3 = this.getScrollY();
            } else {
                n3 = n4;
                if (n == 130) {
                    n3 = n4;
                    if (this.getChildCount() > 0) {
                        int n5 = this.getChildAt(0).getBottom();
                        int n6 = this.getScrollY() + this.getHeight() - this.getPaddingBottom();
                        n3 = n4;
                        if (n5 - n6 < n2) {
                            n3 = n5 - n6;
                        }
                    }
                }
            }
            if (n3 == 0) return bl;
            if (n != 130) {
                n3 = -n3;
            }
            this.doScrollY(n3);
        }
        if (view2 == null) return true;
        if (!view2.isFocused()) return true;
        if (!this.isOffScreen(view2)) return true;
        n = this.getDescendantFocusability();
        this.setDescendantFocusability(131072);
        this.requestFocus();
        this.setDescendantFocusability(n);
        return true;
    }

    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int n;
            this.mScroller.getCurrX();
            int n2 = this.mScroller.getCurrY();
            int n3 = n = n2 - this.mLastScrollerY;
            if (this.dispatchNestedPreScroll(0, n, this.mScrollConsumed, null, 1)) {
                n3 = n - this.mScrollConsumed[1];
            }
            if (n3 != 0) {
                n = this.getScrollRange();
                int n4 = this.getScrollY();
                this.overScrollByCompat(0, n3, this.getScrollX(), n4, 0, n, 0, 0, false);
                int n5 = this.getScrollY() - n4;
                if (!this.dispatchNestedScroll(0, n5, 0, n3 - n5, null, 1) && (n3 = (n3 = this.getOverScrollMode()) == 0 || n3 == 1 && n > 0 ? 1 : 0) != 0) {
                    this.ensureGlows();
                    if (n2 <= 0 && n4 > 0) {
                        this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
                    } else if (n2 >= n && n4 < n) {
                        this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
                    }
                }
            }
            this.mLastScrollerY = n2;
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        if (this.hasNestedScrollingParent(1)) {
            this.stopNestedScroll(1);
        }
        this.mLastScrollerY = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        int n = this.getHeight();
        int n2 = this.getScrollY();
        int n3 = n2 + n;
        int n4 = this.getVerticalFadingEdgeLength();
        int n5 = n2;
        if (rect.top > 0) {
            n5 = n2 + n4;
        }
        n2 = n3;
        if (rect.bottom < this.getChildAt(0).getHeight()) {
            n2 = n3 - n4;
        }
        if (rect.bottom > n2 && rect.top > n5) {
            if (rect.height() > n) {
                n5 = 0 + (rect.top - n5);
                return Math.min(n5, this.getChildAt(0).getBottom() - n2);
            }
            n5 = 0 + (rect.bottom - n2);
            return Math.min(n5, this.getChildAt(0).getBottom() - n2);
        }
        if (rect.top >= n5) return 0;
        if (rect.bottom >= n2) return 0;
        if (rect.height() > n) {
            n2 = 0 - (n2 - rect.bottom);
            return Math.max(n2, -this.getScrollY());
        }
        n2 = 0 - (n5 - rect.top);
        return Math.max(n2, -this.getScrollY());
    }

    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int computeVerticalScrollRange() {
        int n = this.getChildCount();
        int n2 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        if (n == 0) {
            return n2;
        }
        n = this.getChildAt(0).getBottom();
        int n3 = this.getScrollY();
        int n4 = Math.max(0, n - n2);
        if (n3 < 0) {
            return n - n3;
        }
        n2 = n;
        if (n3 <= n4) return n2;
        return n + (n3 - n4);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.mChildHelper.dispatchNestedFling(f, f2, bl);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mChildHelper.dispatchNestedPreFling(f, f2);
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.mChildHelper.dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        return this.mChildHelper.dispatchNestedPreScroll(n, n2, arrn, arrn2, n3);
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        return this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn, n5);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mEdgeGlowTop != null) {
            int n;
            int n2;
            int n3;
            int n4 = this.getScrollY();
            if (!this.mEdgeGlowTop.isFinished()) {
                n = canvas.save();
                n3 = this.getWidth();
                n2 = this.getPaddingLeft();
                int n5 = this.getPaddingRight();
                canvas.translate((float)this.getPaddingLeft(), (float)Math.min(0, n4));
                this.mEdgeGlowTop.setSize(n3 - n2 - n5, this.getHeight());
                if (this.mEdgeGlowTop.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                canvas.restoreToCount(n);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                n = canvas.save();
                n3 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                n2 = this.getHeight();
                canvas.translate((float)(-n3 + this.getPaddingLeft()), (float)(Math.max(this.getScrollRange(), n4) + n2));
                canvas.rotate(180.0f, (float)n3, 0.0f);
                this.mEdgeGlowBottom.setSize(n3, n2);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                canvas.restoreToCount(n);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean bl = false;
        this.mTempRect.setEmpty();
        if (!this.canScroll()) {
            boolean bl2 = bl;
            if (!this.isFocused()) return bl2;
            bl2 = bl;
            if (keyEvent.getKeyCode() == 4) return bl2;
            View view = this.findFocus();
            keyEvent = view;
            if (view == this) {
                keyEvent = null;
            }
            keyEvent = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)keyEvent, 130);
            bl2 = bl;
            if (keyEvent == null) return bl2;
            bl2 = bl;
            if (keyEvent == this) return bl2;
            bl2 = bl;
            if (!keyEvent.requestFocus(130)) return bl2;
            return true;
        }
        boolean bl3 = bl = false;
        if (keyEvent.getAction() != 0) return bl3;
        switch (keyEvent.getKeyCode()) {
            default: {
                return bl;
            }
            case 19: {
                if (keyEvent.isAltPressed()) return this.fullScroll(33);
                return this.arrowScroll(33);
            }
            case 20: {
                if (keyEvent.isAltPressed()) return this.fullScroll(130);
                return this.arrowScroll(130);
            }
            case 62: 
        }
        int n = keyEvent.isShiftPressed() ? 33 : 130;
        this.pageScroll(n);
        return bl;
    }

    public void fling(int n) {
        if (this.getChildCount() > 0) {
            this.startNestedScroll(2, 1);
            this.mScroller.fling(this.getScrollX(), this.getScrollY(), 0, n, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.mLastScrollerY = this.getScrollY();
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean fullScroll(int n) {
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = n3;
        if (n2 != 0 && (n2 = this.getChildCount()) > 0) {
            View view = this.getChildAt(n2 - 1);
            this.mTempRect.bottom = view.getBottom() + this.getPaddingBottom();
            this.mTempRect.top = this.mTempRect.bottom - n3;
        }
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    protected float getBottomFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getHeight();
        int n3 = this.getPaddingBottom();
        n2 = this.getChildAt(0).getBottom() - this.getScrollY() - (n2 - n3);
        if (n2 < n) {
            return (float)n2 / (float)n;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int)(0.5f * (float)this.getHeight());
    }

    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    int getScrollRange() {
        int n = 0;
        if (this.getChildCount() > 0) {
            n = Math.max(0, this.getChildAt(0).getHeight() - (this.getHeight() - this.getPaddingBottom() - this.getPaddingTop()));
        }
        return n;
    }

    protected float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getScrollY();
        if (n2 < n) {
            return (float)n2 / (float)n;
        }
        return 1.0f;
    }

    public boolean hasNestedScrollingParent() {
        return this.mChildHelper.hasNestedScrollingParent();
    }

    public boolean hasNestedScrollingParent(int n) {
        return this.mChildHelper.hasNestedScrollingParent(n);
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    protected void measureChild(View view, int n, int n2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
    }

    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2), (int)marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin), (int)0));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0) return false;
        {
            switch (motionEvent.getAction()) {
                default: {
                    return false;
                }
                case 8: {
                    float f;
                    if (this.mIsBeingDragged || (f = motionEvent.getAxisValue(9)) == 0.0f) return false;
                    int n = (int)(this.getVerticalScrollFactorCompat() * f);
                    int n2 = this.getScrollRange();
                    int n3 = this.getScrollY();
                    int n4 = n3 - n;
                    if (n4 < 0) {
                        n = 0;
                    } else {
                        n = n4;
                        if (n4 > n2) {
                            n = n2;
                        }
                    }
                    if (n == n3) return false;
                    super.scrollTo(this.getScrollX(), n);
                    return true;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl = true;
        int n = motionEvent.getAction();
        if (n == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (n & 0xFF) {
            case 2: {
                n = this.mActivePointerId;
                if (n == -1) return this.mIsBeingDragged;
                int n2 = motionEvent.findPointerIndex(n);
                if (n2 == -1) {
                    Log.e((String)TAG, (String)("Invalid pointerId=" + n + " in onInterceptTouchEvent"));
                    return this.mIsBeingDragged;
                }
                n = (int)motionEvent.getY(n2);
                if (Math.abs(n - this.mLastMotionY) <= this.mTouchSlop) return this.mIsBeingDragged;
                if ((this.getNestedScrollAxes() & 2) != 0) return this.mIsBeingDragged;
                this.mIsBeingDragged = true;
                this.mLastMotionY = n;
                this.initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mNestedYOffset = 0;
                motionEvent = this.getParent();
                if (motionEvent == null) return this.mIsBeingDragged;
                motionEvent.requestDisallowInterceptTouchEvent(true);
                return this.mIsBeingDragged;
            }
            case 0: {
                n = (int)motionEvent.getY();
                if (!this.inChild((int)motionEvent.getX(), n)) {
                    this.mIsBeingDragged = false;
                    this.recycleVelocityTracker();
                    return this.mIsBeingDragged;
                }
                this.mLastMotionY = n;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mScroller.computeScrollOffset();
                if (this.mScroller.isFinished()) {
                    bl = false;
                }
                this.mIsBeingDragged = bl;
                this.startNestedScroll(2, 0);
                return this.mIsBeingDragged;
            }
            case 1: 
            case 3: {
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                this.recycleVelocityTracker();
                if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.stopNestedScroll(0);
                return this.mIsBeingDragged;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                return this.mIsBeingDragged;
            }
        }
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && NestedScrollView.isViewDescendantOf(this.mChildToScrollTo, (View)this)) {
            this.scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                this.scrollTo(this.getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            n = this.getChildCount() > 0 ? this.getChildAt(0).getMeasuredHeight() : 0;
            n = Math.max(0, n - (n4 - n2 - this.getPaddingBottom() - this.getPaddingTop()));
            if (this.getScrollY() > n) {
                this.scrollTo(this.getScrollX(), n);
            } else if (this.getScrollY() < 0) {
                this.scrollTo(this.getScrollX(), 0);
            }
        }
        this.scrollTo(this.getScrollX(), this.getScrollY());
        this.mIsLaidOut = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        View view;
        block3: {
            block2: {
                super.onMeasure(n, n2);
                if (!this.mFillViewport || View.MeasureSpec.getMode((int)n2) == 0 || this.getChildCount() <= 0) break block2;
                view = this.getChildAt(0);
                n2 = this.getMeasuredHeight();
                if (view.getMeasuredHeight() < n2) break block3;
            }
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(n2 - this.getPaddingTop() - this.getPaddingBottom()), (int)1073741824));
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (!bl) {
            this.flingWithNestedDispatch((int)f2);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return this.dispatchNestedPreFling(f, f2);
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.dispatchNestedPreScroll(n, n2, arrn, null);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        n = this.getScrollY();
        this.scrollBy(0, n4);
        n = this.getScrollY() - n;
        this.dispatchNestedScroll(0, n, 0, n4 - n, null);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, n);
        this.startNestedScroll(2);
    }

    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        super.scrollTo(n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        if (n == 2) {
            n2 = 130;
        } else {
            n2 = n;
            if (n == 1) {
                n2 = 33;
            }
        }
        View view = rect == null ? FocusFinder.getInstance().findNextFocus((ViewGroup)this, null, n2) : FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, rect, n2);
        if (view == null || this.isOffScreen(view)) {
            return false;
        }
        return view.requestFocus(n2, rect);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mSavedState = object;
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = this.getScrollY();
        return savedState;
    }

    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        if (this.mOnScrollChangeListener != null) {
            this.mOnScrollChangeListener.onScrollChange(this, n, n2, n3, n4);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        View view = this.findFocus();
        if (view == null || this == view || !this.isWithinDeltaOfScreen(view, 0, n4)) {
            return;
        }
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return (n & 2) != 0;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.mParentHelper.onStopNestedScroll(view);
        this.stopNestedScroll();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.initVelocityTrackerIfNotExists();
        MotionEvent motionEvent2 = MotionEvent.obtain((MotionEvent)motionEvent);
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.mNestedYOffset = 0;
        }
        motionEvent2.offsetLocation(0.0f, (float)this.mNestedYOffset);
        switch (n) {
            case 0: {
                ViewParent viewParent;
                if (this.getChildCount() == 0) {
                    return false;
                }
                boolean bl = !this.mScroller.isFinished();
                this.mIsBeingDragged = bl;
                if (bl && (viewParent = this.getParent()) != null) {
                    viewParent.requestDisallowInterceptTouchEvent(true);
                }
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionY = (int)motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.startNestedScroll(2, 0);
                break;
            }
            case 2: {
                int n2;
                int n3 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (n3 == -1) {
                    Log.e((String)TAG, (String)("Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent"));
                    break;
                }
                int n4 = (int)motionEvent.getY(n3);
                int n5 = n = this.mLastMotionY - n4;
                if (this.dispatchNestedPreScroll(0, n, this.mScrollConsumed, this.mScrollOffset, 0)) {
                    n5 = n - this.mScrollConsumed[1];
                    motionEvent2.offsetLocation(0.0f, (float)this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                }
                n = n5;
                if (!this.mIsBeingDragged) {
                    n = n5;
                    if (Math.abs(n5) > this.mTouchSlop) {
                        ViewParent viewParent = this.getParent();
                        if (viewParent != null) {
                            viewParent.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        n = n5 > 0 ? n5 - this.mTouchSlop : n5 + this.mTouchSlop;
                    }
                }
                if (!this.mIsBeingDragged) break;
                this.mLastMotionY = n4 - this.mScrollOffset[1];
                int n6 = this.getScrollY();
                n4 = this.getScrollRange();
                n5 = this.getOverScrollMode();
                n5 = n5 == 0 || n5 == 1 && n4 > 0 ? 1 : 0;
                if (this.overScrollByCompat(0, n, 0, this.getScrollY(), 0, n4, 0, 0, true) && !this.hasNestedScrollingParent(0)) {
                    this.mVelocityTracker.clear();
                }
                if (this.dispatchNestedScroll(0, n2 = this.getScrollY() - n6, 0, n - n2, this.mScrollOffset, 0)) {
                    this.mLastMotionY -= this.mScrollOffset[1];
                    motionEvent2.offsetLocation(0.0f, (float)this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                    break;
                }
                if (n5 == 0) break;
                this.ensureGlows();
                n5 = n6 + n;
                if (n5 < 0) {
                    EdgeEffectCompat.onPull(this.mEdgeGlowTop, (float)n / (float)this.getHeight(), motionEvent.getX(n3) / (float)this.getWidth());
                    if (!this.mEdgeGlowBottom.isFinished()) {
                        this.mEdgeGlowBottom.onRelease();
                    }
                } else if (n5 > n4) {
                    EdgeEffectCompat.onPull(this.mEdgeGlowBottom, (float)n / (float)this.getHeight(), 1.0f - motionEvent.getX(n3) / (float)this.getWidth());
                    if (!this.mEdgeGlowTop.isFinished()) {
                        this.mEdgeGlowTop.onRelease();
                    }
                }
                if (this.mEdgeGlowTop == null || this.mEdgeGlowTop.isFinished() && this.mEdgeGlowBottom.isFinished()) break;
                ViewCompat.postInvalidateOnAnimation((View)this);
                break;
            }
            case 1: {
                motionEvent = this.mVelocityTracker;
                motionEvent.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                n = (int)motionEvent.getYVelocity(this.mActivePointerId);
                if (Math.abs(n) > this.mMinimumVelocity) {
                    this.flingWithNestedDispatch(-n);
                } else if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.mActivePointerId = -1;
                this.endDrag();
                break;
            }
            case 3: {
                if (this.mIsBeingDragged && this.getChildCount() > 0 && this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.mActivePointerId = -1;
                this.endDrag();
                break;
            }
            case 5: {
                n = motionEvent.getActionIndex();
                this.mLastMotionY = (int)motionEvent.getY(n);
                this.mActivePointerId = motionEvent.getPointerId(n);
                break;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                this.mLastMotionY = (int)motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                break;
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent2);
        }
        motionEvent2.recycle();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean overScrollByCompat(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        int n9 = this.getOverScrollMode();
        boolean bl2 = this.computeHorizontalScrollRange() > this.computeHorizontalScrollExtent();
        boolean bl3 = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent();
        bl2 = n9 == 0 || n9 == 1 && bl2;
        bl3 = n9 == 0 || n9 == 1 && bl3;
        n3 += n;
        if (!bl2) {
            n7 = 0;
        }
        n4 += n2;
        if (!bl3) {
            n8 = 0;
        }
        n2 = -n7;
        n = n7 + n5;
        n5 = -n8;
        n6 = n8 + n6;
        bl = false;
        if (n3 > n) {
            bl = true;
        } else {
            n = n3;
            if (n3 < n2) {
                n = n2;
                bl = true;
            }
        }
        boolean bl4 = false;
        if (n4 > n6) {
            n2 = n6;
            bl4 = true;
        } else {
            n2 = n4;
            if (n4 < n5) {
                n2 = n5;
                bl4 = true;
            }
        }
        if (bl4 && !this.hasNestedScrollingParent(1)) {
            this.mScroller.springBack(n, n2, 0, 0, 0, this.getScrollRange());
        }
        this.onOverScrolled(n, n2, bl, bl4);
        return bl || bl4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean pageScroll(int n) {
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        if (n2 != 0) {
            View view;
            this.mTempRect.top = this.getScrollY() + n3;
            n2 = this.getChildCount();
            if (n2 > 0 && this.mTempRect.top + n3 > (view = this.getChildAt(n2 - 1)).getBottom()) {
                this.mTempRect.top = view.getBottom() - n3;
            }
        } else {
            this.mTempRect.top = this.getScrollY() - n3;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + n3;
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void requestChildFocus(View view, View view2) {
        if (!this.mIsLayoutDirty) {
            this.scrollToChild(view2);
        } else {
            this.mChildToScrollTo = view2;
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.scrollToChildRect(rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (bl) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public void scrollTo(int n, int n2) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n = NestedScrollView.clamp(n, this.getWidth() - this.getPaddingRight() - this.getPaddingLeft(), view.getWidth());
            n2 = NestedScrollView.clamp(n2, this.getHeight() - this.getPaddingBottom() - this.getPaddingTop(), view.getHeight());
            if (n != this.getScrollX() || n2 != this.getScrollY()) {
                super.scrollTo(n, n2);
            }
        }
    }

    public void setFillViewport(boolean bl) {
        if (bl != this.mFillViewport) {
            this.mFillViewport = bl;
            this.requestLayout();
        }
    }

    public void setNestedScrollingEnabled(boolean bl) {
        this.mChildHelper.setNestedScrollingEnabled(bl);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setSmoothScrollingEnabled(boolean bl) {
        this.mSmoothScrollingEnabled = bl;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void smoothScrollBy(int n, int n2) {
        if (this.getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
            n = this.getHeight();
            int n3 = this.getPaddingBottom();
            int n4 = this.getPaddingTop();
            n3 = Math.max(0, this.getChildAt(0).getHeight() - (n - n3 - n4));
            n = this.getScrollY();
            n2 = Math.max(0, Math.min(n + n2, n3));
            this.mScroller.startScroll(this.getScrollX(), n, 0, n2 - n);
            ViewCompat.postInvalidateOnAnimation((View)this);
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.scrollBy(n, n2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int n, int n2) {
        this.smoothScrollBy(n - this.getScrollX(), n2 - this.getScrollY());
    }

    public boolean startNestedScroll(int n) {
        return this.mChildHelper.startNestedScroll(n);
    }

    public boolean startNestedScroll(int n, int n2) {
        return this.mChildHelper.startNestedScroll(n, n2);
    }

    @Override
    public void stopNestedScroll() {
        this.mChildHelper.stopNestedScroll();
    }

    public void stopNestedScroll(int n) {
        this.mChildHelper.stopNestedScroll(n);
    }

    static class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onInitializeAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent((View)object, accessibilityEvent);
            object = (NestedScrollView)object;
            accessibilityEvent.setClassName((CharSequence)ScrollView.class.getName());
            boolean bl = ((NestedScrollView)object).getScrollRange() > 0;
            accessibilityEvent.setScrollable(bl);
            accessibilityEvent.setScrollX(object.getScrollX());
            accessibilityEvent.setScrollY(object.getScrollY());
            AccessibilityRecordCompat.setMaxScrollX((AccessibilityRecord)accessibilityEvent, object.getScrollX());
            AccessibilityRecordCompat.setMaxScrollY((AccessibilityRecord)accessibilityEvent, ((NestedScrollView)object).getScrollRange());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int n;
            super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
            object = (NestedScrollView)object;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (object.isEnabled() && (n = ((NestedScrollView)object).getScrollRange()) > 0) {
                accessibilityNodeInfoCompat.setScrollable(true);
                if (object.getScrollY() > 0) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (object.getScrollY() < n) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
        }

        @Override
        public boolean performAccessibilityAction(View object, int n, Bundle bundle) {
            if (super.performAccessibilityAction((View)object, n, bundle)) {
                return true;
            }
            if (!(object = (NestedScrollView)object).isEnabled()) {
                return false;
            }
            switch (n) {
                default: {
                    return false;
                }
                case 4096: {
                    n = object.getHeight();
                    int n2 = object.getPaddingBottom();
                    int n3 = object.getPaddingTop();
                    n = Math.min(object.getScrollY() + (n - n2 - n3), ((NestedScrollView)object).getScrollRange());
                    if (n != object.getScrollY()) {
                        ((NestedScrollView)object).smoothScrollTo(0, n);
                        return true;
                    }
                    return false;
                }
                case 8192: 
            }
            n = object.getHeight();
            int n4 = object.getPaddingBottom();
            int n5 = object.getPaddingTop();
            n = Math.max(object.getScrollY() - (n - n4 - n5), 0);
            if (n != object.getScrollY()) {
                ((NestedScrollView)object).smoothScrollTo(0, n);
                return true;
            }
            return false;
        }
    }

    public static interface OnScrollChangeListener {
        public void onScrollChange(NestedScrollView var1, int var2, int var3, int var4, int var5);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int scrollPosition;

        SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode((Object)this)) + " scrollPosition=" + this.scrollPosition + "}";
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.scrollPosition);
        }

    }

}

