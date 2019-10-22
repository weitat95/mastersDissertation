/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class SwipeDismissBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    float mAlphaEndSwipeDistance = 0.5f;
    float mAlphaStartSwipeDistance = 0.0f;
    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback(){
        private int mActivePointerId = -1;
        private int mOriginalCapturedViewLeft;

        /*
         * Enabled aggressive block sorting
         */
        private boolean shouldDismiss(View view, float f) {
            if (f != 0.0f) {
                boolean bl = ViewCompat.getLayoutDirection(view) == 1;
                if (SwipeDismissBehavior.this.mSwipeDirection == 2) return true;
                {
                    if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                        if (!(bl ? !(f < 0.0f) : !(f > 0.0f))) return true;
                        return false;
                    } else {
                        if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                            return false;
                        }
                        if (!(bl ? !(f > 0.0f) : !(f < 0.0f))) return true;
                        return false;
                    }
                }
            }
            int n = view.getLeft();
            int n2 = this.mOriginalCapturedViewLeft;
            int n3 = Math.round((float)view.getWidth() * SwipeDismissBehavior.this.mDragDismissThreshold);
            if (Math.abs(n - n2) < n3) return false;
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            int n3;
            n2 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0;
            if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                if (n2 != 0) {
                    n3 = this.mOriginalCapturedViewLeft - view.getWidth();
                    n2 = this.mOriginalCapturedViewLeft;
                    return SwipeDismissBehavior.clamp(n3, n, n2);
                }
                n3 = this.mOriginalCapturedViewLeft;
                n2 = this.mOriginalCapturedViewLeft + view.getWidth();
                return SwipeDismissBehavior.clamp(n3, n, n2);
            }
            if (SwipeDismissBehavior.this.mSwipeDirection != 1) {
                n3 = this.mOriginalCapturedViewLeft - view.getWidth();
                n2 = this.mOriginalCapturedViewLeft + view.getWidth();
                return SwipeDismissBehavior.clamp(n3, n, n2);
            }
            if (n2 != 0) {
                n3 = this.mOriginalCapturedViewLeft;
                n2 = this.mOriginalCapturedViewLeft + view.getWidth();
                return SwipeDismissBehavior.clamp(n3, n, n2);
            }
            n3 = this.mOriginalCapturedViewLeft - view.getWidth();
            n2 = this.mOriginalCapturedViewLeft;
            return SwipeDismissBehavior.clamp(n3, n, n2);
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return view.getWidth();
        }

        @Override
        public void onViewCaptured(View view, int n) {
            this.mActivePointerId = n;
            this.mOriginalCapturedViewLeft = view.getLeft();
            if ((view = view.getParent()) != null) {
                view.requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (SwipeDismissBehavior.this.mListener != null) {
                SwipeDismissBehavior.this.mListener.onDragStateChanged(n);
            }
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            float f = (float)this.mOriginalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.mAlphaStartSwipeDistance;
            float f2 = (float)this.mOriginalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.mAlphaEndSwipeDistance;
            if ((float)n <= f) {
                view.setAlpha(1.0f);
                return;
            }
            if ((float)n >= f2) {
                view.setAlpha(0.0f);
                return;
            }
            view.setAlpha(SwipeDismissBehavior.clamp(0.0f, 1.0f - SwipeDismissBehavior.fraction(f, f2, n), 1.0f));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f, float f2) {
            this.mActivePointerId = -1;
            int n = view.getWidth();
            boolean bl = false;
            if (this.shouldDismiss(view, f)) {
                n = view.getLeft() < this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft - n : this.mOriginalCapturedViewLeft + n;
                bl = true;
            } else {
                n = this.mOriginalCapturedViewLeft;
            }
            if (SwipeDismissBehavior.this.mViewDragHelper.settleCapturedViewAt(n, view.getTop())) {
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, bl));
                return;
            } else {
                if (!bl || SwipeDismissBehavior.this.mListener == null) return;
                {
                    SwipeDismissBehavior.this.mListener.onDismiss(view);
                    return;
                }
            }
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            return this.mActivePointerId == -1 && SwipeDismissBehavior.this.canSwipeDismissView(view);
        }
    };
    float mDragDismissThreshold = 0.5f;
    private boolean mInterceptingEvents;
    OnDismissListener mListener;
    private float mSensitivity = 0.0f;
    private boolean mSensitivitySet;
    int mSwipeDirection = 2;
    ViewDragHelper mViewDragHelper;

    static float clamp(float f, float f2, float f3) {
        return Math.min(Math.max(f, f2), f3);
    }

    static int clamp(int n, int n2, int n3) {
        return Math.min(Math.max(n, n2), n3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void ensureViewDragHelper(ViewGroup object) {
        if (this.mViewDragHelper == null) {
            void var1_3;
            if (this.mSensitivitySet) {
                ViewDragHelper viewDragHelper = ViewDragHelper.create(object, this.mSensitivity, this.mDragCallback);
            } else {
                ViewDragHelper viewDragHelper = ViewDragHelper.create(object, this.mDragCallback);
            }
            this.mViewDragHelper = var1_3;
        }
    }

    static float fraction(float f, float f2, float f3) {
        return (f3 - f) / (f2 - f);
    }

    public boolean canSwipeDismissView(View view) {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = bl = this.mInterceptingEvents;
        switch (motionEvent.getActionMasked()) {
            default: {
                bl3 = bl;
                break;
            }
            case 0: {
                bl3 = this.mInterceptingEvents = coordinatorLayout.isPointInChildBounds((View)v, (int)motionEvent.getX(), (int)motionEvent.getY());
            }
            case 2: {
                break;
            }
            case 1: 
            case 3: {
                this.mInterceptingEvents = false;
                bl3 = bl;
            }
        }
        if (!bl3) return bl2;
        this.ensureViewDragHelper(coordinatorLayout);
        return this.mViewDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.mViewDragHelper != null) {
            this.mViewDragHelper.processTouchEvent(motionEvent);
            return true;
        }
        return false;
    }

    public void setEndAlphaSwipeDistance(float f) {
        this.mAlphaEndSwipeDistance = SwipeDismissBehavior.clamp(0.0f, f, 1.0f);
    }

    public void setListener(OnDismissListener onDismissListener) {
        this.mListener = onDismissListener;
    }

    public void setStartAlphaSwipeDistance(float f) {
        this.mAlphaStartSwipeDistance = SwipeDismissBehavior.clamp(0.0f, f, 1.0f);
    }

    public void setSwipeDirection(int n) {
        this.mSwipeDirection = n;
    }

    public static interface OnDismissListener {
        public void onDismiss(View var1);

        public void onDragStateChanged(int var1);
    }

    private class SettleRunnable
    implements Runnable {
        private final boolean mDismiss;
        private final View mView;

        SettleRunnable(View view, boolean bl) {
            this.mView = view;
            this.mDismiss = bl;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void run() {
            if (SwipeDismissBehavior.this.mViewDragHelper != null && SwipeDismissBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
                return;
            } else {
                if (!this.mDismiss || SwipeDismissBehavior.this.mListener == null) return;
                {
                    SwipeDismissBehavior.this.mListener.onDismiss(this.mView);
                    return;
                }
            }
        }
    }

}

