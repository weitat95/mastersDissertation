/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.widget.OverScroller
 */
package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.ViewOffsetBehavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

abstract class HeaderBehavior<V extends View>
extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    OverScroller mScroller;
    private int mTouchSlop = -1;
    private VelocityTracker mVelocityTracker;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    boolean canDragView(V v) {
        return false;
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V v, int n, int n2, float f) {
        if (this.mFlingRunnable != null) {
            v.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = new OverScroller(v.getContext());
        }
        this.mScroller.fling(0, this.getTopAndBottomOffset(), 0, Math.round(f), 0, 0, n, n2);
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingRunnable = new FlingRunnable(this, coordinatorLayout, v);
            ViewCompat.postOnAnimation(v, this.mFlingRunnable);
            return true;
        }
        this.onFlingFinished(coordinatorLayout, v);
        return false;
    }

    int getMaxDragOffset(V v) {
        return -v.getHeight();
    }

    int getScrollRangeForDragFling(V v) {
        return v.getHeight();
    }

    int getTopBottomOffsetForScrollingSibling() {
        return this.getTopAndBottomOffset();
    }

    void onFlingFinished(CoordinatorLayout coordinatorLayout, V v) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get((Context)coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getAction() == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (motionEvent.getActionMasked()) {
            case 0: {
                this.mIsBeingDragged = false;
                int n = (int)motionEvent.getX();
                int n2 = (int)motionEvent.getY();
                if (!this.canDragView(v) || !coordinatorLayout.isPointInChildBounds((View)v, n, n2)) break;
                this.mLastMotionY = n2;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.ensureVelocityTracker();
                break;
            }
            case 2: {
                int n = this.mActivePointerId;
                if (n == -1 || (n = motionEvent.findPointerIndex(n)) == -1 || Math.abs((n = (int)motionEvent.getY(n)) - this.mLastMotionY) <= this.mTouchSlop) break;
                this.mIsBeingDragged = true;
                this.mLastMotionY = n;
                break;
            }
            case 1: 
            case 3: {
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker == null) break;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return this.mIsBeingDragged;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout var1_1, V var2_2, MotionEvent var3_3) {
        block12: {
            if (this.mTouchSlop < 0) {
                this.mTouchSlop = ViewConfiguration.get((Context)var1_1.getContext()).getScaledTouchSlop();
            }
            switch (var3_3.getActionMasked()) {
                case 0: {
                    var5_4 = (int)var3_3.getX();
                    var6_6 = (int)var3_3.getY();
                    if (var1_1.isPointInChildBounds((View)var2_2, var5_4, var6_6) == false) return false;
                    if (this.canDragView(var2_2) == false) return false;
                    this.mLastMotionY = var6_6;
                    this.mActivePointerId = var3_3.getPointerId(0);
                    this.ensureVelocityTracker();
                    ** break;
                }
                case 2: {
                    var5_5 = var3_3.findPointerIndex(this.mActivePointerId);
                    if (var5_5 == -1) {
                        return false;
                    }
                    var7_8 = (int)var3_3.getY(var5_5);
                    var5_5 = var6_7 = this.mLastMotionY - var7_8;
                    if (!this.mIsBeingDragged) {
                        var5_5 = var6_7;
                        if (Math.abs(var6_7) > this.mTouchSlop) {
                            this.mIsBeingDragged = true;
                            var5_5 = var6_7 > 0 ? var6_7 - this.mTouchSlop : var6_7 + this.mTouchSlop;
                        }
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = var7_8;
                        this.scroll(var1_1, var2_2, var5_5, this.getMaxDragOffset(var2_2), 0);
                    }
                }
lbl28:
                // 5 sources
                default: {
                    break block12;
                }
                case 1: {
                    if (this.mVelocityTracker == null) break;
                    this.mVelocityTracker.addMovement(var3_3);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    var4_9 = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                    this.fling(var1_1, var2_2, -this.getScrollRangeForDragFling(var2_2), 0, var4_9);
                    break;
                }
                case 3: 
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }
        if (this.mVelocityTracker == null) return true;
        this.mVelocityTracker.addMovement(var3_3);
        return true;
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v, this.getTopBottomOffsetForScrollingSibling() - n, n2, n3);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int n) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v, n, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3) {
        int n4;
        int n5 = this.getTopAndBottomOffset();
        int n6 = n4 = 0;
        if (n2 != 0) {
            n6 = n4;
            if (n5 >= n2) {
                n6 = n4;
                if (n5 <= n3) {
                    n = MathUtils.clamp(n, n2, n3);
                    n6 = n4;
                    if (n5 != n) {
                        this.setTopAndBottomOffset(n);
                        n6 = n5 - n;
                    }
                }
            }
        }
        return n6;
    }

    private class FlingRunnable
    implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;

        FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
            this.mParent = coordinatorLayout;
            this.mLayout = v;
        }

        @Override
        public void run() {
            block3: {
                block2: {
                    if (this.mLayout == null || this$0.mScroller == null) break block2;
                    if (!this$0.mScroller.computeScrollOffset()) break block3;
                    this$0.setHeaderTopBottomOffset(this.mParent, this.mLayout, this$0.mScroller.getCurrY());
                    ViewCompat.postOnAnimation(this.mLayout, this);
                }
                return;
            }
            this$0.onFlingFinished(this.mParent, this.mLayout);
        }
    }

}

