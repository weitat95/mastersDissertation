/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

public class BottomSheetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback(){

        @Override
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            return view.getLeft();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            int n3 = BottomSheetBehavior.this.mMinOffset;
            if (BottomSheetBehavior.this.mHideable) {
                n2 = BottomSheetBehavior.this.mParentHeight;
                do {
                    return MathUtils.clamp(n, n3, n2);
                    break;
                } while (true);
            }
            n2 = BottomSheetBehavior.this.mMaxOffset;
            return MathUtils.clamp(n, n3, n2);
        }

        @Override
        public int getViewVerticalDragRange(View view) {
            if (BottomSheetBehavior.this.mHideable) {
                return BottomSheetBehavior.this.mParentHeight - BottomSheetBehavior.this.mMinOffset;
            }
            return BottomSheetBehavior.this.mMaxOffset - BottomSheetBehavior.this.mMinOffset;
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (n == 1) {
                BottomSheetBehavior.this.setStateInternal(1);
            }
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            BottomSheetBehavior.this.dispatchOnSlide(n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onViewReleased(View view, float f, float f2) {
            int n;
            int n2;
            if (f2 < 0.0f) {
                n2 = BottomSheetBehavior.this.mMinOffset;
                n = 3;
            } else if (BottomSheetBehavior.this.mHideable && BottomSheetBehavior.this.shouldHide(view, f2)) {
                n2 = BottomSheetBehavior.this.mParentHeight;
                n = 5;
            } else if (f2 == 0.0f) {
                n = view.getTop();
                if (Math.abs(n - BottomSheetBehavior.this.mMinOffset) < Math.abs(n - BottomSheetBehavior.this.mMaxOffset)) {
                    n2 = BottomSheetBehavior.this.mMinOffset;
                    n = 3;
                } else {
                    n2 = BottomSheetBehavior.this.mMaxOffset;
                    n = 4;
                }
            } else {
                n2 = BottomSheetBehavior.this.mMaxOffset;
                n = 4;
            }
            if (BottomSheetBehavior.this.mViewDragHelper.settleCapturedViewAt(view.getLeft(), n2)) {
                BottomSheetBehavior.this.setStateInternal(2);
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, n));
                return;
            }
            BottomSheetBehavior.this.setStateInternal(n);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean tryCaptureView(View view, int n) {
            View view2;
            boolean bl = true;
            if (BottomSheetBehavior.this.mState == 1) {
                return false;
            }
            if (BottomSheetBehavior.this.mTouchingScrollingChild) return false;
            if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == n && (view2 = (View)BottomSheetBehavior.this.mNestedScrollingChildRef.get()) != null) {
                if (view2.canScrollVertically(-1)) return false;
            }
            if (BottomSheetBehavior.this.mViewRef == null) return false;
            if (BottomSheetBehavior.this.mViewRef.get() != view) return false;
            return bl;
        }
    };
    boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    int mMaxOffset;
    private float mMaximumVelocity;
    int mMinOffset;
    private boolean mNestedScrolled;
    WeakReference<View> mNestedScrollingChildRef;
    int mParentHeight;
    private int mPeekHeight;
    private boolean mPeekHeightAuto;
    private int mPeekHeightMin;
    private boolean mSkipCollapsed;
    int mState = 4;
    boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    ViewDragHelper mViewDragHelper;
    WeakReference<V> mViewRef;

    public BottomSheetBehavior() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        TypedValue typedValue = attributeSet.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (typedValue != null && typedValue.data == -1) {
            this.setPeekHeight(typedValue.data);
        } else {
            this.setPeekHeight(attributeSet.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        this.setHideable(attributeSet.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        this.setSkipCollapsed(attributeSet.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        attributeSet.recycle();
        this.mMaximumVelocity = ViewConfiguration.get((Context)context).getScaledMaximumFlingVelocity();
    }

    public static <V extends View> BottomSheetBehavior<V> from(V object) {
        if (!((object = object.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        if (!((object = ((CoordinatorLayout.LayoutParams)((Object)object)).getBehavior()) instanceof BottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        return (BottomSheetBehavior)object;
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        return this.mVelocityTracker.getYVelocity(this.mActivePointerId);
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    void dispatchOnSlide(int n) {
        View view;
        block3: {
            block2: {
                view = (View)this.mViewRef.get();
                if (view == null || this.mCallback == null) break block2;
                if (n <= this.mMaxOffset) break block3;
                this.mCallback.onSlide(view, (float)(this.mMaxOffset - n) / (float)(this.mParentHeight - this.mMaxOffset));
            }
            return;
        }
        this.mCallback.onSlide(view, (float)(this.mMaxOffset - n) / (float)(this.mMaxOffset - this.mMinOffset));
    }

    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n = view.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view2 = this.findScrollingChild(view.getChildAt(i));
                if (view2 == null) continue;
                return view2;
            }
        }
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V object, MotionEvent motionEvent) {
        void var3_4;
        boolean bl = true;
        if (!object.isShown()) {
            this.mIgnoreEvents = true;
            return false;
        }
        int n = var3_4.getActionMasked();
        if (n == 0) {
            this.reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)var3_4);
        switch (n) {
            case 1: 
            case 3: {
                this.mTouchingScrollingChild = false;
                this.mActivePointerId = -1;
                if (!this.mIgnoreEvents) break;
                this.mIgnoreEvents = false;
                return false;
            }
            case 0: {
                int n2 = (int)var3_4.getX();
                this.mInitialY = (int)var3_4.getY();
                View view = this.mNestedScrollingChildRef != null ? (View)this.mNestedScrollingChildRef.get() : null;
                if (view != null && coordinatorLayout.isPointInChildBounds(view, n2, this.mInitialY)) {
                    this.mActivePointerId = var3_4.getPointerId(var3_4.getActionIndex());
                    this.mTouchingScrollingChild = true;
                }
                boolean bl2 = this.mActivePointerId == -1 && !coordinatorLayout.isPointInChildBounds((View)object, n2, this.mInitialY);
                this.mIgnoreEvents = bl2;
            }
        }
        if (!this.mIgnoreEvents && this.mViewDragHelper.shouldInterceptTouchEvent((MotionEvent)var3_4)) {
            return true;
        }
        View view = (View)this.mNestedScrollingChildRef.get();
        if (n != 2) return false;
        if (view == null) return false;
        if (this.mIgnoreEvents) return false;
        if (this.mState == 1) return false;
        if (coordinatorLayout.isPointInChildBounds(view, (int)var3_4.getX(), (int)var3_4.getY())) return false;
        if (!(Math.abs((float)this.mInitialY - var3_4.getY()) > (float)this.mViewDragHelper.getTouchSlop())) return false;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        if (ViewCompat.getFitsSystemWindows((View)coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            ViewCompat.setFitsSystemWindows(v, true);
        }
        int n2 = v.getTop();
        coordinatorLayout.onLayoutChild((View)v, n);
        this.mParentHeight = coordinatorLayout.getHeight();
        if (this.mPeekHeightAuto) {
            if (this.mPeekHeightMin == 0) {
                this.mPeekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            n = Math.max(this.mPeekHeightMin, this.mParentHeight - coordinatorLayout.getWidth() * 9 / 16);
        } else {
            n = this.mPeekHeight;
        }
        this.mMinOffset = Math.max(0, this.mParentHeight - v.getHeight());
        this.mMaxOffset = Math.max(this.mParentHeight - n, this.mMinOffset);
        if (this.mState == 3) {
            ViewCompat.offsetTopAndBottom(v, this.mMinOffset);
        } else if (this.mHideable && this.mState == 5) {
            ViewCompat.offsetTopAndBottom(v, this.mParentHeight);
        } else if (this.mState == 4) {
            ViewCompat.offsetTopAndBottom(v, this.mMaxOffset);
        } else if (this.mState == 1 || this.mState == 2) {
            ViewCompat.offsetTopAndBottom(v, n2 - v.getTop());
        }
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(coordinatorLayout, this.mDragCallback);
        }
        this.mViewRef = new WeakReference<V>(v);
        this.mNestedScrollingChildRef = new WeakReference<View>(this.findScrollingChild((View)v));
        return true;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        return view == this.mNestedScrollingChildRef.get() && (this.mState != 3 || super.onNestedPreFling(coordinatorLayout, v, view, f, f2));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int[] arrn) {
        if (view != (View)this.mNestedScrollingChildRef.get()) {
            return;
        }
        n = v.getTop();
        int n3 = n - n2;
        if (n2 > 0) {
            if (n3 < this.mMinOffset) {
                arrn[1] = n - this.mMinOffset;
                ViewCompat.offsetTopAndBottom(v, -arrn[1]);
                this.setStateInternal(3);
            } else {
                arrn[1] = n2;
                ViewCompat.offsetTopAndBottom(v, -n2);
                this.setStateInternal(1);
            }
        } else if (n2 < 0 && !view.canScrollVertically(-1)) {
            if (n3 <= this.mMaxOffset || this.mHideable) {
                arrn[1] = n2;
                ViewCompat.offsetTopAndBottom(v, -n2);
                this.setStateInternal(1);
            } else {
                arrn[1] = n - this.mMaxOffset;
                ViewCompat.offsetTopAndBottom(v, -arrn[1]);
                this.setStateInternal(4);
            }
        }
        this.dispatchOnSlide(v.getTop());
        this.mLastNestedScrollDy = n2;
        this.mNestedScrolled = true;
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, parcelable.getSuperState());
        if (parcelable.state == 1 || parcelable.state == 2) {
            this.mState = 4;
            return;
        }
        this.mState = parcelable.state;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), this.mState);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n) {
        boolean bl = false;
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        if ((n & 2) != 0) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view) {
        int n;
        int n2;
        if (v.getTop() == this.mMinOffset) {
            this.setStateInternal(3);
            return;
        }
        if (this.mNestedScrollingChildRef == null || view != this.mNestedScrollingChildRef.get() || !this.mNestedScrolled) return;
        if (this.mLastNestedScrollDy > 0) {
            n2 = this.mMinOffset;
            n = 3;
        } else if (this.mHideable && this.shouldHide((View)v, this.getYVelocity())) {
            n2 = this.mParentHeight;
            n = 5;
        } else if (this.mLastNestedScrollDy == 0) {
            n = v.getTop();
            if (Math.abs(n - this.mMinOffset) < Math.abs(n - this.mMaxOffset)) {
                n2 = this.mMinOffset;
                n = 3;
            } else {
                n2 = this.mMaxOffset;
                n = 4;
            }
        } else {
            n2 = this.mMaxOffset;
            n = 4;
        }
        if (this.mViewDragHelper.smoothSlideViewTo((View)v, v.getLeft(), n2)) {
            this.setStateInternal(2);
            ViewCompat.postOnAnimation(v, new SettleRunnable((View)v, n));
        } else {
            this.setStateInternal(n);
        }
        this.mNestedScrolled = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean bl;
        boolean bl2 = true;
        if (!v.isShown()) {
            return false;
        }
        int n = motionEvent.getActionMasked();
        if (this.mState == 1) {
            bl = bl2;
            if (n == 0) return bl;
        }
        this.mViewDragHelper.processTouchEvent(motionEvent);
        if (n == 0) {
            this.reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (n == 2 && !this.mIgnoreEvents && Math.abs((float)this.mInitialY - motionEvent.getY()) > (float)this.mViewDragHelper.getTouchSlop()) {
            this.mViewDragHelper.captureChildView((View)v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        bl = bl2;
        if (!this.mIgnoreEvents) return bl;
        return false;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.mCallback = bottomSheetCallback;
    }

    public void setHideable(boolean bl) {
        this.mHideable = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setPeekHeight(int n) {
        View view;
        boolean bl = false;
        if (n == -1) {
            if (!this.mPeekHeightAuto) {
                this.mPeekHeightAuto = true;
                bl = true;
            }
        } else if (this.mPeekHeightAuto || this.mPeekHeight != n) {
            this.mPeekHeightAuto = false;
            this.mPeekHeight = Math.max(0, n);
            this.mMaxOffset = this.mParentHeight - n;
            bl = true;
        }
        if (bl && this.mState == 4 && this.mViewRef != null && (view = (View)this.mViewRef.get()) != null) {
            view.requestLayout();
        }
    }

    public void setSkipCollapsed(boolean bl) {
        this.mSkipCollapsed = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setState(final int n) {
        if (n == this.mState) return;
        if (this.mViewRef == null) {
            if (n != 4 && n != 3 && (!this.mHideable || n != 5)) return;
            {
                this.mState = n;
                return;
            }
        }
        final View view = (View)this.mViewRef.get();
        if (view == null) {
            return;
        }
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent.isLayoutRequested() && ViewCompat.isAttachedToWindow(view)) {
            view.post(new Runnable(){

                @Override
                public void run() {
                    BottomSheetBehavior.this.startSettlingAnimation(view, n);
                }
            });
            return;
        }
        this.startSettlingAnimation(view, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setStateInternal(int n) {
        View view;
        block3: {
            block2: {
                if (this.mState == n) break block2;
                this.mState = n;
                view = (View)this.mViewRef.get();
                if (view != null && this.mCallback != null) break block3;
            }
            return;
        }
        this.mCallback.onStateChanged(view, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean shouldHide(View view, float f) {
        block5: {
            block4: {
                if (this.mSkipCollapsed) break block4;
                if (view.getTop() < this.mMaxOffset) {
                    return false;
                }
                if (!(Math.abs((float)view.getTop() + 0.1f * f - (float)this.mMaxOffset) / (float)this.mPeekHeight > 0.5f)) break block5;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    void startSettlingAnimation(View view, int n) {
        int n2;
        if (n == 4) {
            n2 = this.mMaxOffset;
        } else if (n == 3) {
            n2 = this.mMinOffset;
        } else {
            if (!this.mHideable || n != 5) {
                throw new IllegalArgumentException("Illegal state argument: " + n);
            }
            n2 = this.mParentHeight;
        }
        if (this.mViewDragHelper.smoothSlideViewTo(view, view.getLeft(), n2)) {
            this.setStateInternal(2);
            ViewCompat.postOnAnimation(view, new SettleRunnable(view, n));
            return;
        }
        this.setStateInternal(n);
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(View var1, float var2);

        public abstract void onStateChanged(View var1, int var2);
    }

    protected static class SavedState
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
        final int state;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int n) {
            super(parcelable);
            this.state = n;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.state);
        }

    }

    private class SettleRunnable
    implements Runnable {
        private final int mTargetState;
        private final View mView;

        SettleRunnable(View view, int n) {
            this.mView = view;
            this.mTargetState = n;
        }

        @Override
        public void run() {
            if (BottomSheetBehavior.this.mViewDragHelper != null && BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.mView, this);
                return;
            }
            BottomSheetBehavior.this.setStateInternal(this.mTargetState);
        }
    }

}

