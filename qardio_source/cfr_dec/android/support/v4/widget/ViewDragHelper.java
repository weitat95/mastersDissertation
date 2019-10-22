/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 *  android.widget.OverScroller
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;

public class ViewDragHelper {
    private static final Interpolator sInterpolator = new Interpolator(){

        public float getInterpolation(float f) {
            return (f -= 1.0f) * f * f * f * f + 1.0f;
        }
    };
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private OverScroller mScroller;
    private final Runnable mSetIdleRunnable = new Runnable(){

        @Override
        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.mParentView = viewGroup;
        this.mCallback = callback;
        viewGroup = ViewConfiguration.get((Context)context);
        this.mEdgeSize = (int)(20.0f * context.getResources().getDisplayMetrics().density + 0.5f);
        this.mTouchSlop = viewGroup.getScaledTouchSlop();
        this.mMaxVelocity = viewGroup.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewGroup.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(context, sInterpolator);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkNewEdgeDrag(float f, float f2, int n, int n2) {
        block5: {
            block4: {
                f = Math.abs(f);
                f2 = Math.abs(f2);
                if ((this.mInitialEdgesTouched[n] & n2) != n2 || (this.mTrackingEdges & n2) == 0 || (this.mEdgeDragsLocked[n] & n2) == n2 || (this.mEdgeDragsInProgress[n] & n2) == n2 || f <= (float)this.mTouchSlop && f2 <= (float)this.mTouchSlop) break block4;
                if (f < 0.5f * f2 && this.mCallback.onEdgeLock(n2)) {
                    int[] arrn = this.mEdgeDragsLocked;
                    arrn[n] = arrn[n] | n2;
                    return false;
                }
                if ((this.mEdgeDragsInProgress[n] & n2) == 0 && f > (float)this.mTouchSlop) break block5;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkTouchSlop(View view, float f, float f2) {
        boolean bl = true;
        if (view == null) {
            return false;
        }
        boolean bl2 = this.mCallback.getViewHorizontalDragRange(view) > 0;
        boolean bl3 = this.mCallback.getViewVerticalDragRange(view) > 0;
        if (bl2 && bl3) {
            if (f * f + f2 * f2 > (float)(this.mTouchSlop * this.mTouchSlop)) return bl;
            return false;
        }
        if (bl2) {
            if (Math.abs(f) > (float)this.mTouchSlop) return bl;
            return false;
        }
        if (!bl3) {
            return false;
        }
        if (Math.abs(f2) > (float)this.mTouchSlop) return bl;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private float clampMag(float f, float f2, float f3) {
        float f4 = Math.abs(f);
        if (f4 < f2) {
            return 0.0f;
        }
        if (!(f4 > f3)) return f;
        f2 = f3;
        if (f > 0.0f) return f2;
        return -f3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int clampMag(int n, int n2, int n3) {
        int n4 = Math.abs(n);
        if (n4 < n2) {
            return 0;
        }
        if (n4 <= n3) return n;
        n2 = n3;
        if (n > 0) return n2;
        return -n3;
    }

    private void clearMotionHistory() {
        if (this.mInitialMotionX == null) {
            return;
        }
        Arrays.fill(this.mInitialMotionX, 0.0f);
        Arrays.fill(this.mInitialMotionY, 0.0f);
        Arrays.fill(this.mLastMotionX, 0.0f);
        Arrays.fill(this.mLastMotionY, 0.0f);
        Arrays.fill(this.mInitialEdgesTouched, 0);
        Arrays.fill(this.mEdgeDragsInProgress, 0);
        Arrays.fill(this.mEdgeDragsLocked, 0);
        this.mPointersDown = 0;
    }

    private void clearMotionHistory(int n) {
        if (this.mInitialMotionX == null || !this.isPointerDown(n)) {
            return;
        }
        this.mInitialMotionX[n] = 0.0f;
        this.mInitialMotionY[n] = 0.0f;
        this.mLastMotionX[n] = 0.0f;
        this.mLastMotionY[n] = 0.0f;
        this.mInitialEdgesTouched[n] = 0;
        this.mEdgeDragsInProgress[n] = 0;
        this.mEdgeDragsLocked[n] = 0;
        this.mPointersDown &= ~(1 << n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int computeAxisDuration(int n, int n2, int n3) {
        if (n == 0) {
            return 0;
        }
        int n4 = this.mParentView.getWidth();
        int n5 = n4 / 2;
        float f = Math.min(1.0f, (float)Math.abs(n) / (float)n4);
        float f2 = n5;
        float f3 = n5;
        f = this.distanceInfluenceForSnapDuration(f);
        if ((n2 = Math.abs(n2)) > 0) {
            n = Math.round(1000.0f * Math.abs((f2 + f3 * f) / (float)n2)) * 4;
            do {
                return Math.min(n, 600);
                break;
            } while (true);
        }
        n = (int)(((float)Math.abs(n) / (float)n3 + 1.0f) * 256.0f);
        return Math.min(n, 600);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int computeSettleDuration(View view, int n, int n2, int n3, int n4) {
        n3 = this.clampMag(n3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
        n4 = this.clampMag(n4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
        int n5 = Math.abs(n);
        int n6 = Math.abs(n2);
        int n7 = Math.abs(n3);
        int n8 = Math.abs(n4);
        int n9 = n7 + n8;
        int n10 = n5 + n6;
        float f = n3 != 0 ? (float)n7 / (float)n9 : (float)n5 / (float)n10;
        float f2 = n4 != 0 ? (float)n8 / (float)n9 : (float)n6 / (float)n10;
        n = this.computeAxisDuration(n, n3, this.mCallback.getViewHorizontalDragRange(view));
        n2 = this.computeAxisDuration(n2, n4, this.mCallback.getViewVerticalDragRange(view));
        return (int)((float)n * f + (float)n2 * f2);
    }

    public static ViewDragHelper create(ViewGroup object, float f, Callback callback) {
        object = ViewDragHelper.create(object, callback);
        object.mTouchSlop = (int)((float)object.mTouchSlop * (1.0f / f));
        return object;
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    private void dispatchViewReleased(float f, float f2) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, f, f2);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            this.setDragState(0);
        }
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    private void dragTo(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = this.mCapturedView.getLeft();
        int n8 = this.mCapturedView.getTop();
        if (n3 != 0) {
            n5 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, n, n3);
            ViewCompat.offsetLeftAndRight(this.mCapturedView, n5 - n7);
        }
        if (n4 != 0) {
            n6 = this.mCallback.clampViewPositionVertical(this.mCapturedView, n2, n4);
            ViewCompat.offsetTopAndBottom(this.mCapturedView, n6 - n8);
        }
        if (n3 != 0 || n4 != 0) {
            this.mCallback.onViewPositionChanged(this.mCapturedView, n5, n6, n5 - n7, n6 - n8);
        }
    }

    private void ensureMotionHistorySizeForId(int n) {
        if (this.mInitialMotionX == null || this.mInitialMotionX.length <= n) {
            float[] arrf = new float[n + 1];
            float[] arrf2 = new float[n + 1];
            float[] arrf3 = new float[n + 1];
            float[] arrf4 = new float[n + 1];
            int[] arrn = new int[n + 1];
            int[] arrn2 = new int[n + 1];
            int[] arrn3 = new int[n + 1];
            if (this.mInitialMotionX != null) {
                System.arraycopy(this.mInitialMotionX, 0, arrf, 0, this.mInitialMotionX.length);
                System.arraycopy(this.mInitialMotionY, 0, arrf2, 0, this.mInitialMotionY.length);
                System.arraycopy(this.mLastMotionX, 0, arrf3, 0, this.mLastMotionX.length);
                System.arraycopy(this.mLastMotionY, 0, arrf4, 0, this.mLastMotionY.length);
                System.arraycopy(this.mInitialEdgesTouched, 0, arrn, 0, this.mInitialEdgesTouched.length);
                System.arraycopy(this.mEdgeDragsInProgress, 0, arrn2, 0, this.mEdgeDragsInProgress.length);
                System.arraycopy(this.mEdgeDragsLocked, 0, arrn3, 0, this.mEdgeDragsLocked.length);
            }
            this.mInitialMotionX = arrf;
            this.mInitialMotionY = arrf2;
            this.mLastMotionX = arrf3;
            this.mLastMotionY = arrf4;
            this.mInitialEdgesTouched = arrn;
            this.mEdgeDragsInProgress = arrn2;
            this.mEdgeDragsLocked = arrn3;
        }
    }

    private boolean forceSettleCapturedViewAt(int n, int n2, int n3, int n4) {
        int n5 = this.mCapturedView.getLeft();
        int n6 = this.mCapturedView.getTop();
        if ((n -= n5) == 0 && (n2 -= n6) == 0) {
            this.mScroller.abortAnimation();
            this.setDragState(0);
            return false;
        }
        n3 = this.computeSettleDuration(this.mCapturedView, n, n2, n3, n4);
        this.mScroller.startScroll(n5, n6, n, n2, n3);
        this.setDragState(2);
        return true;
    }

    private int getEdgesTouched(int n, int n2) {
        int n3 = 0;
        if (n < this.mParentView.getLeft() + this.mEdgeSize) {
            n3 = false | true;
        }
        int n4 = n3;
        if (n2 < this.mParentView.getTop() + this.mEdgeSize) {
            n4 = n3 | 4;
        }
        n3 = n4;
        if (n > this.mParentView.getRight() - this.mEdgeSize) {
            n3 = n4 | 2;
        }
        n = n3;
        if (n2 > this.mParentView.getBottom() - this.mEdgeSize) {
            n = n3 | 8;
        }
        return n;
    }

    private boolean isValidPointerForActionMove(int n) {
        if (!this.isPointerDown(n)) {
            Log.e((String)"ViewDragHelper", (String)("Ignoring pointerId=" + n + " because ACTION_DOWN was not received " + "for this pointer before ACTION_MOVE. It likely happened because " + " ViewDragHelper did not receive all the events in the event stream."));
            return false;
        }
        return true;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        this.dispatchViewReleased(this.clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), this.clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void reportNewEdgeDrags(float f, float f2, int n) {
        int n2 = 0;
        if (this.checkNewEdgeDrag(f, f2, n, 1)) {
            n2 = false | true;
        }
        int n3 = n2;
        if (this.checkNewEdgeDrag(f2, f, n, 4)) {
            n3 = n2 | 4;
        }
        n2 = n3;
        if (this.checkNewEdgeDrag(f, f2, n, 2)) {
            n2 = n3 | 2;
        }
        n3 = n2;
        if (this.checkNewEdgeDrag(f2, f, n, 8)) {
            n3 = n2 | 8;
        }
        if (n3 != 0) {
            int[] arrn = this.mEdgeDragsInProgress;
            arrn[n] = arrn[n] | n3;
            this.mCallback.onEdgeDragStarted(n3, n);
        }
    }

    private void saveInitialMotion(float f, float f2, int n) {
        this.ensureMotionHistorySizeForId(n);
        float[] arrf = this.mInitialMotionX;
        this.mLastMotionX[n] = f;
        arrf[n] = f;
        arrf = this.mInitialMotionY;
        this.mLastMotionY[n] = f2;
        arrf[n] = f2;
        this.mInitialEdgesTouched[n] = this.getEdgesTouched((int)f, (int)f2);
        this.mPointersDown |= 1 << n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void saveLastMotion(MotionEvent motionEvent) {
        int n = motionEvent.getPointerCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = motionEvent.getPointerId(n2);
            if (this.isValidPointerForActionMove(n3)) {
                float f = motionEvent.getX(n2);
                float f2 = motionEvent.getY(n2);
                this.mLastMotionX[n3] = f;
                this.mLastMotionY[n3] = f2;
            }
            ++n2;
        }
        return;
    }

    public void abort() {
        this.cancel();
        if (this.mDragState == 2) {
            int n = this.mScroller.getCurrX();
            int n2 = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, n3, n4, n3 - n, n4 - n2);
        }
        this.setDragState(0);
    }

    public void cancel() {
        this.mActivePointerId = -1;
        this.clearMotionHistory();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void captureChildView(View view, int n) {
        if (view.getParent() != this.mParentView) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + (Object)this.mParentView + ")");
        }
        this.mCapturedView = view;
        this.mActivePointerId = n;
        this.mCallback.onViewCaptured(view, n);
        this.setDragState(1);
    }

    public boolean checkTouchSlop(int n) {
        int n2 = this.mInitialMotionX.length;
        for (int i = 0; i < n2; ++i) {
            if (!this.checkTouchSlop(n, i)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean checkTouchSlop(int n, int n2) {
        boolean bl = true;
        if (!this.isPointerDown(n2)) {
            return false;
        }
        boolean bl2 = (n & 1) == 1;
        n = (n & 2) == 2 ? 1 : 0;
        float f = this.mLastMotionX[n2] - this.mInitialMotionX[n2];
        float f2 = this.mLastMotionY[n2] - this.mInitialMotionY[n2];
        if (bl2 && n != 0) {
            if (f * f + f2 * f2 > (float)(this.mTouchSlop * this.mTouchSlop)) return bl;
            return false;
        }
        if (bl2) {
            if (Math.abs(f) > (float)this.mTouchSlop) return bl;
            return false;
        }
        if (n == 0) {
            return false;
        }
        if (Math.abs(f2) > (float)this.mTouchSlop) return bl;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean continueSettling(boolean bl) {
        block11: {
            block10: {
                if (this.mDragState != 2) break block10;
                boolean bl2 = this.mScroller.computeScrollOffset();
                int n = this.mScroller.getCurrX();
                int n2 = this.mScroller.getCurrY();
                int n3 = n - this.mCapturedView.getLeft();
                int n4 = n2 - this.mCapturedView.getTop();
                if (n3 != 0) {
                    ViewCompat.offsetLeftAndRight(this.mCapturedView, n3);
                }
                if (n4 != 0) {
                    ViewCompat.offsetTopAndBottom(this.mCapturedView, n4);
                }
                if (n3 != 0 || n4 != 0) {
                    this.mCallback.onViewPositionChanged(this.mCapturedView, n, n2, n3, n4);
                }
                boolean bl3 = bl2;
                if (bl2) {
                    bl3 = bl2;
                    if (n == this.mScroller.getFinalX()) {
                        bl3 = bl2;
                        if (n2 == this.mScroller.getFinalY()) {
                            this.mScroller.abortAnimation();
                            bl3 = false;
                        }
                    }
                }
                if (bl3) break block10;
                if (!bl) break block11;
                this.mParentView.post(this.mSetIdleRunnable);
            }
            do {
                return this.mDragState == 2;
                break;
            } while (true);
        }
        this.setDragState(0);
        return this.mDragState == 2;
    }

    public View findTopChildUnder(int n, int n2) {
        for (int i = this.mParentView.getChildCount() - 1; i >= 0; --i) {
            View view = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
            if (n < view.getLeft() || n >= view.getRight() || n2 < view.getTop() || n2 >= view.getBottom()) continue;
            return view;
        }
        return null;
    }

    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public boolean isCapturedViewUnder(int n, int n2) {
        return this.isViewUnder(this.mCapturedView, n, n2);
    }

    public boolean isPointerDown(int n) {
        return (this.mPointersDown & 1 << n) != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isViewUnder(View view, int n, int n2) {
        return view != null && n >= view.getLeft() && n < view.getRight() && n2 >= view.getTop() && n2 < view.getBottom();
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void processTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        int n2 = motionEvent.getActionIndex();
        if (n == 0) {
            this.cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (n) {
            default: {
                return;
            }
            case 0: {
                float f = motionEvent.getX();
                float f2 = motionEvent.getY();
                n2 = motionEvent.getPointerId(0);
                motionEvent = this.findTopChildUnder((int)f, (int)f2);
                this.saveInitialMotion(f, f2, n2);
                this.tryCaptureViewForDrag((View)motionEvent, n2);
                n = this.mInitialEdgesTouched[n2];
                if ((this.mTrackingEdges & n) == 0) return;
                this.mCallback.onEdgeTouched(this.mTrackingEdges & n, n2);
                return;
            }
            case 5: {
                n = motionEvent.getPointerId(n2);
                float f = motionEvent.getX(n2);
                float f3 = motionEvent.getY(n2);
                this.saveInitialMotion(f, f3, n);
                if (this.mDragState == 0) {
                    this.tryCaptureViewForDrag(this.findTopChildUnder((int)f, (int)f3), n);
                    n2 = this.mInitialEdgesTouched[n];
                    if ((this.mTrackingEdges & n2) == 0) return;
                    this.mCallback.onEdgeTouched(this.mTrackingEdges & n2, n);
                    return;
                }
                if (!this.isCapturedViewUnder((int)f, (int)f3)) return;
                this.tryCaptureViewForDrag(this.mCapturedView, n);
                return;
            }
            case 2: {
                if (this.mDragState == 1) {
                    if (!this.isValidPointerForActionMove(this.mActivePointerId)) return;
                    n2 = motionEvent.findPointerIndex(this.mActivePointerId);
                    float f = motionEvent.getX(n2);
                    float f4 = motionEvent.getY(n2);
                    n2 = (int)(f - this.mLastMotionX[this.mActivePointerId]);
                    n = (int)(f4 - this.mLastMotionY[this.mActivePointerId]);
                    this.dragTo(this.mCapturedView.getLeft() + n2, this.mCapturedView.getTop() + n, n2, n);
                    this.saveLastMotion(motionEvent);
                    return;
                }
                n = motionEvent.getPointerCount();
                for (n2 = 0; n2 < n; ++n2) {
                    View view;
                    int n3 = motionEvent.getPointerId(n2);
                    if (!this.isValidPointerForActionMove(n3)) continue;
                    float f = motionEvent.getX(n2);
                    float f5 = motionEvent.getY(n2);
                    float f6 = f - this.mInitialMotionX[n3];
                    float f7 = f5 - this.mInitialMotionY[n3];
                    this.reportNewEdgeDrags(f6, f7, n3);
                    if (this.mDragState == 1 || this.checkTouchSlop(view = this.findTopChildUnder((int)f, (int)f5), f6, f7) && this.tryCaptureViewForDrag(view, n3)) break;
                }
                this.saveLastMotion(motionEvent);
                return;
            }
            case 6: {
                int n4 = motionEvent.getPointerId(n2);
                if (this.mDragState == 1 && n4 == this.mActivePointerId) {
                    int n5 = -1;
                    int n6 = motionEvent.getPointerCount();
                    n2 = 0;
                    do {
                        float f;
                        float f8;
                        n = n5;
                        if (n2 >= n6) break;
                        n = motionEvent.getPointerId(n2);
                        if (n != this.mActivePointerId && this.findTopChildUnder((int)(f = motionEvent.getX(n2)), (int)(f8 = motionEvent.getY(n2))) == this.mCapturedView && this.tryCaptureViewForDrag(this.mCapturedView, n)) {
                            n = this.mActivePointerId;
                            break;
                        }
                        ++n2;
                    } while (true);
                    if (n == -1) {
                        this.releaseViewForPointerUp();
                    }
                }
                this.clearMotionHistory(n4);
                return;
            }
            case 1: {
                if (this.mDragState == 1) {
                    this.releaseViewForPointerUp();
                }
                this.cancel();
                return;
            }
            case 3: 
        }
        if (this.mDragState == 1) {
            this.dispatchViewReleased(0.0f, 0.0f);
        }
        this.cancel();
    }

    void setDragState(int n) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != n) {
            this.mDragState = n;
            this.mCallback.onViewDragStateChanged(n);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public void setEdgeTrackingEnabled(int n) {
        this.mTrackingEdges = n;
    }

    public void setMinVelocity(float f) {
        this.mMinVelocity = f;
    }

    public boolean settleCapturedViewAt(int n, int n2) {
        if (!this.mReleaseInProgress) {
            throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
        }
        return this.forceSettleCapturedViewAt(n, n2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId));
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        int n2 = motionEvent.getActionIndex();
        if (n == 0) {
            this.cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (n) {
            case 0: {
                float f = motionEvent.getX();
                float f2 = motionEvent.getY();
                n2 = motionEvent.getPointerId(0);
                this.saveInitialMotion(f, f2, n2);
                motionEvent = this.findTopChildUnder((int)f, (int)f2);
                if (motionEvent == this.mCapturedView && this.mDragState == 2) {
                    this.tryCaptureViewForDrag((View)motionEvent, n2);
                }
                if ((this.mTrackingEdges & (n = this.mInitialEdgesTouched[n2])) == 0) break;
                this.mCallback.onEdgeTouched(this.mTrackingEdges & n, n2);
                break;
            }
            case 5: {
                n = motionEvent.getPointerId(n2);
                float f = motionEvent.getX(n2);
                float f3 = motionEvent.getY(n2);
                this.saveInitialMotion(f, f3, n);
                if (this.mDragState == 0) {
                    n2 = this.mInitialEdgesTouched[n];
                    if ((this.mTrackingEdges & n2) == 0) break;
                    this.mCallback.onEdgeTouched(this.mTrackingEdges & n2, n);
                    break;
                }
                if (this.mDragState != 2 || (motionEvent = this.findTopChildUnder((int)f, (int)f3)) != this.mCapturedView) break;
                this.tryCaptureViewForDrag((View)motionEvent, n);
                break;
            }
            case 2: {
                if (this.mInitialMotionX == null || this.mInitialMotionY == null) break;
                int n3 = motionEvent.getPointerCount();
                for (n2 = 0; n2 < n3; ++n2) {
                    int n4 = motionEvent.getPointerId(n2);
                    if (!this.isValidPointerForActionMove(n4)) continue;
                    float f = motionEvent.getX(n2);
                    float f4 = motionEvent.getY(n2);
                    float f5 = f - this.mInitialMotionX[n4];
                    float f6 = f4 - this.mInitialMotionY[n4];
                    View view = this.findTopChildUnder((int)f, (int)f4);
                    n = view != null && this.checkTouchSlop(view, f5, f6) ? 1 : 0;
                    if (n != 0) {
                        int n5 = view.getLeft();
                        int n6 = (int)f5;
                        n6 = this.mCallback.clampViewPositionHorizontal(view, n5 + n6, (int)f5);
                        int n7 = view.getTop();
                        int n8 = (int)f6;
                        n8 = this.mCallback.clampViewPositionVertical(view, n7 + n8, (int)f6);
                        int n9 = this.mCallback.getViewHorizontalDragRange(view);
                        int n10 = this.mCallback.getViewVerticalDragRange(view);
                        if ((n9 == 0 || n9 > 0 && n6 == n5) && (n10 == 0 || n10 > 0 && n8 == n7)) break;
                    }
                    this.reportNewEdgeDrags(f5, f6, n4);
                    if (this.mDragState == 1 || n != 0 && this.tryCaptureViewForDrag(view, n4)) break;
                }
                this.saveLastMotion(motionEvent);
                break;
            }
            case 6: {
                this.clearMotionHistory(motionEvent.getPointerId(n2));
                break;
            }
            case 1: 
            case 3: {
                this.cancel();
            }
        }
        return this.mDragState == 1;
    }

    public boolean smoothSlideViewTo(View view, int n, int n2) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        boolean bl = this.forceSettleCapturedViewAt(n, n2, 0, 0);
        if (!bl && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return bl;
    }

    boolean tryCaptureViewForDrag(View view, int n) {
        if (view == this.mCapturedView && this.mActivePointerId == n) {
            return true;
        }
        if (view != null && this.mCallback.tryCaptureView(view, n)) {
            this.mActivePointerId = n;
            this.captureChildView(view, n);
            return true;
        }
        return false;
    }

    public static abstract class Callback {
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int n, int n2) {
            return 0;
        }

        public int getOrderedChildIndex(int n) {
            return n;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange(View view) {
            return 0;
        }

        public void onEdgeDragStarted(int n, int n2) {
        }

        public boolean onEdgeLock(int n) {
            return false;
        }

        public void onEdgeTouched(int n, int n2) {
        }

        public void onViewCaptured(View view, int n) {
        }

        public void onViewDragStateChanged(int n) {
        }

        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
        }

        public void onViewReleased(View view, float f, float f2) {
        }

        public abstract boolean tryCaptureView(View var1, int var2);
    }

}

