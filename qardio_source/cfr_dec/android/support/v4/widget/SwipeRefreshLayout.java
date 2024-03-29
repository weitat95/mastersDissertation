/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.Transformation
 *  android.widget.AbsListView
 *  android.widget.ListView
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CircleImageView;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListView;

public class SwipeRefreshLayout
extends ViewGroup
implements NestedScrollingChild,
NestedScrollingParent {
    private static final int ALPHA_ANIMATION_DURATION = 300;
    private static final int ANIMATE_TO_START_DURATION = 200;
    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
    private static final int CIRCLE_BG_LIGHT = -328966;
    static final int CIRCLE_DIAMETER = 40;
    static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    public static final int DEFAULT = 1;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int LARGE = 0;
    private static final int[] LAYOUT_ATTRS;
    private static final String LOG_TAG;
    private static final int MAX_ALPHA = 255;
    private static final float MAX_PROGRESS_ANGLE = 0.8f;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int STARTING_PROGRESS_ALPHA = 76;
    private int mActivePointerId = -1;
    private Animation mAlphaMaxAnimation;
    private Animation mAlphaStartAnimation;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    private int mCircleDiameter;
    CircleImageView mCircleView;
    private int mCircleViewIndex = -1;
    int mCurrentTargetOffsetTop;
    private final DecelerateInterpolator mDecelerateInterpolator;
    protected int mFrom;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    OnRefreshListener mListener;
    private int mMediumAnimationDuration;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    boolean mNotify;
    protected int mOriginalOffsetTop;
    private final int[] mParentOffsetInWindow;
    private final int[] mParentScrollConsumed = new int[2];
    CircularProgressDrawable mProgress;
    private Animation.AnimationListener mRefreshListener;
    boolean mRefreshing = false;
    private boolean mReturningToStart;
    boolean mScale;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private Animation mScaleDownToStartAnimation;
    int mSpinnerOffsetEnd;
    float mStartingScale;
    private View mTarget;
    private float mTotalDragDistance = -1.0f;
    private float mTotalUnconsumed;
    private int mTouchSlop;
    boolean mUsingCustomStart;

    static {
        LOG_TAG = SwipeRefreshLayout.class.getSimpleName();
        LAYOUT_ATTRS = new int[]{16842766};
    }

    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n;
        this.mParentOffsetInWindow = new int[2];
        this.mRefreshListener = new Animation.AnimationListener(){

            public void onAnimationEnd(Animation animation) {
                if (SwipeRefreshLayout.this.mRefreshing) {
                    SwipeRefreshLayout.this.mProgress.setAlpha(255);
                    SwipeRefreshLayout.this.mProgress.start();
                    if (SwipeRefreshLayout.this.mNotify && SwipeRefreshLayout.this.mListener != null) {
                        SwipeRefreshLayout.this.mListener.onRefresh();
                    }
                    SwipeRefreshLayout.this.mCurrentTargetOffsetTop = SwipeRefreshLayout.this.mCircleView.getTop();
                    return;
                }
                SwipeRefreshLayout.this.reset();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        };
        this.mAnimateToCorrectPosition = new Animation(){

            /*
             * Enabled aggressive block sorting
             */
            public void applyTransformation(float f, Transformation transformation) {
                int n = !SwipeRefreshLayout.this.mUsingCustomStart ? SwipeRefreshLayout.this.mSpinnerOffsetEnd - Math.abs(SwipeRefreshLayout.this.mOriginalOffsetTop) : SwipeRefreshLayout.this.mSpinnerOffsetEnd;
                int n2 = SwipeRefreshLayout.this.mFrom;
                n = (int)((float)(n - SwipeRefreshLayout.this.mFrom) * f);
                int n3 = SwipeRefreshLayout.this.mCircleView.getTop();
                SwipeRefreshLayout.this.setTargetOffsetTopAndBottom(n2 + n - n3);
                SwipeRefreshLayout.this.mProgress.setArrowScale(1.0f - f);
            }
        };
        this.mAnimateToStartPosition = new Animation(){

            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.moveToStart(f);
            }
        };
        this.mTouchSlop = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.mMediumAnimationDuration = this.getResources().getInteger(17694721);
        this.setWillNotDraw(false);
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.mCircleDiameter = (int)(40.0f * displayMetrics.density);
        this.createProgressView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        this.mSpinnerOffsetEnd = (int)(64.0f * displayMetrics.density);
        this.mTotalDragDistance = this.mSpinnerOffsetEnd;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper((View)this);
        this.setNestedScrollingEnabled(true);
        this.mCurrentTargetOffsetTop = n = -this.mCircleDiameter;
        this.mOriginalOffsetTop = n;
        this.moveToStart(1.0f);
        context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
        this.setEnabled(context.getBoolean(0, true));
        context.recycle();
    }

    private void animateOffsetToCorrectPosition(int n, Animation.AnimationListener animationListener) {
        this.mFrom = n;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration(200L);
        this.mAnimateToCorrectPosition.setInterpolator((Interpolator)this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToCorrectPosition);
    }

    private void animateOffsetToStartPosition(int n, Animation.AnimationListener animationListener) {
        if (this.mScale) {
            this.startScaleDownReturnToStartAnimation(n, animationListener);
            return;
        }
        this.mFrom = n;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration(200L);
        this.mAnimateToStartPosition.setInterpolator((Interpolator)this.mDecelerateInterpolator);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mAnimateToStartPosition);
    }

    private void createProgressView() {
        this.mCircleView = new CircleImageView(this.getContext(), -328966);
        this.mProgress = new CircularProgressDrawable(this.getContext());
        this.mProgress.setStyle(1);
        this.mCircleView.setImageDrawable((Drawable)this.mProgress);
        this.mCircleView.setVisibility(8);
        this.addView((View)this.mCircleView);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void ensureTarget() {
        if (this.mTarget != null) return;
        int n = 0;
        while (n < this.getChildCount()) {
            View view = this.getChildAt(n);
            if (!view.equals((Object)this.mCircleView)) {
                this.mTarget = view;
                return;
            }
            ++n;
        }
    }

    private void finishSpinner(float f) {
        if (f > this.mTotalDragDistance) {
            this.setRefreshing(true, true);
            return;
        }
        this.mRefreshing = false;
        this.mProgress.setStartEndTrim(0.0f, 0.0f);
        Animation.AnimationListener animationListener = null;
        if (!this.mScale) {
            animationListener = new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    if (!SwipeRefreshLayout.this.mScale) {
                        SwipeRefreshLayout.this.startScaleDownAnimation(null);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            };
        }
        this.animateOffsetToStartPosition(this.mCurrentTargetOffsetTop, animationListener);
        this.mProgress.setArrowEnabled(false);
    }

    private boolean isAnimationRunning(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void moveSpinner(float f) {
        this.mProgress.setArrowEnabled(true);
        float f2 = Math.min(1.0f, Math.abs(f / this.mTotalDragDistance));
        float f3 = (float)Math.max((double)f2 - 0.4, 0.0) * 5.0f / 3.0f;
        float f4 = Math.abs(f);
        float f5 = this.mTotalDragDistance;
        float f6 = this.mUsingCustomStart ? (float)(this.mSpinnerOffsetEnd - this.mOriginalOffsetTop) : (float)this.mSpinnerOffsetEnd;
        f4 = Math.max(0.0f, Math.min(f4 - f5, 2.0f * f6) / f6);
        f4 = (float)((double)(f4 / 4.0f) - Math.pow(f4 / 4.0f, 2.0)) * 2.0f;
        int n = this.mOriginalOffsetTop;
        int n2 = (int)(f6 * f2 + f6 * f4 * 2.0f);
        if (this.mCircleView.getVisibility() != 0) {
            this.mCircleView.setVisibility(0);
        }
        if (!this.mScale) {
            this.mCircleView.setScaleX(1.0f);
            this.mCircleView.setScaleY(1.0f);
        }
        if (this.mScale) {
            this.setAnimationProgress(Math.min(1.0f, f / this.mTotalDragDistance));
        }
        if (f < this.mTotalDragDistance) {
            if (this.mProgress.getAlpha() > 76 && !this.isAnimationRunning(this.mAlphaStartAnimation)) {
                this.startProgressAlphaStartAnimation();
            }
        } else if (this.mProgress.getAlpha() < 255 && !this.isAnimationRunning(this.mAlphaMaxAnimation)) {
            this.startProgressAlphaMaxAnimation();
        }
        this.mProgress.setStartEndTrim(0.0f, Math.min(0.8f, f3 * 0.8f));
        this.mProgress.setArrowScale(Math.min(1.0f, f3));
        this.mProgress.setProgressRotation((-0.25f + 0.4f * f3 + 2.0f * f4) * 0.5f);
        this.setTargetOffsetTopAndBottom(n + n2 - this.mCurrentTargetOffsetTop);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mActivePointerId = motionEvent.getPointerId(n);
        }
    }

    private void setColorViewAlpha(int n) {
        this.mCircleView.getBackground().setAlpha(n);
        this.mProgress.setAlpha(n);
    }

    private void setRefreshing(boolean bl, boolean bl2) {
        block3: {
            block2: {
                if (this.mRefreshing == bl) break block2;
                this.mNotify = bl2;
                this.ensureTarget();
                this.mRefreshing = bl;
                if (!this.mRefreshing) break block3;
                this.animateOffsetToCorrectPosition(this.mCurrentTargetOffsetTop, this.mRefreshListener);
            }
            return;
        }
        this.startScaleDownAnimation(this.mRefreshListener);
    }

    private Animation startAlphaAnimation(final int n, final int n2) {
        Animation animation = new Animation(){

            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.mProgress.setAlpha((int)((float)n + (float)(n2 - n) * f));
            }
        };
        animation.setDuration(300L);
        this.mCircleView.setAnimationListener(null);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(animation);
        return animation;
    }

    private void startDragging(float f) {
        if (f - this.mInitialDownY > (float)this.mTouchSlop && !this.mIsBeingDragged) {
            this.mInitialMotionY = this.mInitialDownY + (float)this.mTouchSlop;
            this.mIsBeingDragged = true;
            this.mProgress.setAlpha(76);
        }
    }

    private void startProgressAlphaMaxAnimation() {
        this.mAlphaMaxAnimation = this.startAlphaAnimation(this.mProgress.getAlpha(), 255);
    }

    private void startProgressAlphaStartAnimation() {
        this.mAlphaStartAnimation = this.startAlphaAnimation(this.mProgress.getAlpha(), 76);
    }

    private void startScaleDownReturnToStartAnimation(int n, Animation.AnimationListener animationListener) {
        this.mFrom = n;
        this.mStartingScale = this.mCircleView.getScaleX();
        this.mScaleDownToStartAnimation = new Animation(){

            public void applyTransformation(float f, Transformation transformation) {
                float f2 = SwipeRefreshLayout.this.mStartingScale;
                float f3 = -SwipeRefreshLayout.this.mStartingScale;
                SwipeRefreshLayout.this.setAnimationProgress(f2 + f3 * f);
                SwipeRefreshLayout.this.moveToStart(f);
            }
        };
        this.mScaleDownToStartAnimation.setDuration(150L);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownToStartAnimation);
    }

    private void startScaleUpAnimation(Animation.AnimationListener animationListener) {
        this.mCircleView.setVisibility(0);
        if (Build.VERSION.SDK_INT >= 11) {
            this.mProgress.setAlpha(255);
        }
        this.mScaleAnimation = new Animation(){

            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(f);
            }
        };
        this.mScaleAnimation.setDuration((long)this.mMediumAnimationDuration);
        if (animationListener != null) {
            this.mCircleView.setAnimationListener(animationListener);
        }
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleAnimation);
    }

    public boolean canChildScrollUp() {
        if (this.mChildScrollUpCallback != null) {
            return this.mChildScrollUpCallback.canChildScrollUp(this, this.mTarget);
        }
        if (this.mTarget instanceof ListView) {
            return ListViewCompat.canScrollList((ListView)this.mTarget, -1);
        }
        return this.mTarget.canScrollVertically(-1);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(f, f2, bl);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(f, f2);
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.mNestedScrollingChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int getChildDrawingOrder(int n, int n2) {
        block5: {
            block4: {
                if (this.mCircleViewIndex < 0) break block4;
                if (n2 == n - 1) {
                    return this.mCircleViewIndex;
                }
                if (n2 >= this.mCircleViewIndex) break block5;
            }
            return n2;
        }
        return n2 + 1;
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public int getProgressCircleDiameter() {
        return this.mCircleDiameter;
    }

    public int getProgressViewEndOffset() {
        return this.mSpinnerOffsetEnd;
    }

    public int getProgressViewStartOffset() {
        return this.mOriginalOffsetTop;
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    void moveToStart(float f) {
        this.setTargetOffsetTopAndBottom(this.mFrom + (int)((float)(this.mOriginalOffsetTop - this.mFrom) * f) - this.mCircleView.getTop());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.reset();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onInterceptTouchEvent(MotionEvent var1_1) {
        this.ensureTarget();
        var2_2 = var1_1.getActionMasked();
        if (this.mReturningToStart && var2_2 == 0) {
            this.mReturningToStart = false;
        }
        if (this.isEnabled() == false) return false;
        if (this.mReturningToStart != false) return false;
        if (this.canChildScrollUp() != false) return false;
        if (this.mRefreshing != false) return false;
        if (this.mNestedScrollInProgress) {
            return false;
        }
        switch (var2_2) {
            case 0: {
                this.setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCircleView.getTop());
                this.mActivePointerId = var1_1.getPointerId(0);
                this.mIsBeingDragged = false;
                var2_2 = var1_1.findPointerIndex(this.mActivePointerId);
                if (var2_2 < 0) return false;
                this.mInitialDownY = var1_1.getY(var2_2);
                ** break;
            }
            case 2: {
                if (this.mActivePointerId == -1) {
                    Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                var2_2 = var1_1.findPointerIndex(this.mActivePointerId);
                if (var2_2 < 0) return false;
                this.startDragging(var1_1.getY(var2_2));
                ** break;
            }
            case 6: {
                this.onSecondaryPointerUp(var1_1);
            }
lbl31:
            // 4 sources
            default: {
                return this.mIsBeingDragged;
            }
            case 1: 
            case 3: 
        }
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        block5: {
            block4: {
                n = this.getMeasuredWidth();
                n2 = this.getMeasuredHeight();
                if (this.getChildCount() == 0) break block4;
                if (this.mTarget == null) {
                    this.ensureTarget();
                }
                if (this.mTarget != null) break block5;
            }
            return;
        }
        View view = this.mTarget;
        n3 = this.getPaddingLeft();
        n4 = this.getPaddingTop();
        view.layout(n3, n4, n3 + (n - this.getPaddingLeft() - this.getPaddingRight()), n4 + (n2 - this.getPaddingTop() - this.getPaddingBottom()));
        n2 = this.mCircleView.getMeasuredWidth();
        n3 = this.mCircleView.getMeasuredHeight();
        this.mCircleView.layout(n / 2 - n2 / 2, this.mCurrentTargetOffsetTop, n / 2 + n2 / 2, this.mCurrentTargetOffsetTop + n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mTarget == null) {
            this.ensureTarget();
        }
        if (this.mTarget != null) {
            this.mTarget.measure(View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight()), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom()), (int)1073741824));
            this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec((int)this.mCircleDiameter, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)this.mCircleDiameter, (int)1073741824));
            this.mCircleViewIndex = -1;
            for (n = 0; n < this.getChildCount(); ++n) {
                if (this.getChildAt(n) != this.mCircleView) continue;
                this.mCircleViewIndex = n;
                return;
            }
        }
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        return this.dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return this.dispatchNestedPreFling(f, f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(View arrn, int n, int n2, int[] arrn2) {
        if (n2 > 0 && this.mTotalUnconsumed > 0.0f) {
            if ((float)n2 > this.mTotalUnconsumed) {
                arrn2[1] = n2 - (int)this.mTotalUnconsumed;
                this.mTotalUnconsumed = 0.0f;
            } else {
                this.mTotalUnconsumed -= (float)n2;
                arrn2[1] = n2;
            }
            this.moveSpinner(this.mTotalUnconsumed);
        }
        if (this.mUsingCustomStart && n2 > 0 && this.mTotalUnconsumed == 0.0f && Math.abs(n2 - arrn2[1]) > 0) {
            this.mCircleView.setVisibility(8);
        }
        if (this.dispatchNestedPreScroll(n - arrn2[0], n2 - arrn2[1], arrn = this.mParentScrollConsumed, null)) {
            arrn2[0] = arrn2[0] + arrn[0];
            arrn2[1] = arrn2[1] + arrn[1];
        }
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.dispatchNestedScroll(n, n2, n3, n4, this.mParentOffsetInWindow);
        n = n4 + this.mParentOffsetInWindow[1];
        if (n < 0 && !this.canChildScrollUp()) {
            this.mTotalUnconsumed += (float)Math.abs(n);
            this.moveSpinner(this.mTotalUnconsumed);
        }
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, n);
        this.startNestedScroll(n & 2);
        this.mTotalUnconsumed = 0.0f;
        this.mNestedScrollInProgress = true;
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return this.isEnabled() && !this.mReturningToStart && !this.mRefreshing && (n & 2) != 0;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view);
        this.mNestedScrollInProgress = false;
        if (this.mTotalUnconsumed > 0.0f) {
            this.finishSpinner(this.mTotalUnconsumed);
            this.mTotalUnconsumed = 0.0f;
        }
        this.stopNestedScroll();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        var4_2 = var1_1.getActionMasked();
        if (this.mReturningToStart && var4_2 == 0) {
            this.mReturningToStart = false;
        }
        if (this.isEnabled() == false) return false;
        if (this.mReturningToStart != false) return false;
        if (this.canChildScrollUp() != false) return false;
        if (this.mRefreshing != false) return false;
        if (this.mNestedScrollInProgress) {
            return false;
        }
        switch (var4_2) {
            case 3: {
                return false;
            }
            case 0: {
                this.mActivePointerId = var1_1.getPointerId(0);
                this.mIsBeingDragged = false;
                ** break;
            }
            case 2: {
                var4_2 = var1_1.findPointerIndex(this.mActivePointerId);
                if (var4_2 < 0) {
                    Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                var2_3 = var1_1.getY(var4_2);
                this.startDragging(var2_3);
                if (this.mIsBeingDragged == false) return true;
                if (!((var2_3 = (var2_3 - this.mInitialMotionY) * 0.5f) > 0.0f)) return false;
                this.moveSpinner(var2_3);
                ** break;
            }
            case 5: {
                var4_2 = var1_1.getActionIndex();
                if (var4_2 < 0) {
                    Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                this.mActivePointerId = var1_1.getPointerId(var4_2);
                ** break;
            }
            case 6: {
                this.onSecondaryPointerUp(var1_1);
            }
lbl39:
            // 5 sources
            default: {
                return true;
            }
            case 1: 
        }
        var4_2 = var1_1.findPointerIndex(this.mActivePointerId);
        if (var4_2 < 0) {
            Log.e((String)SwipeRefreshLayout.LOG_TAG, (String)"Got ACTION_UP event but don't have an active pointer id.");
            return false;
        }
        if (this.mIsBeingDragged) {
            var2_4 = var1_1.getY(var4_2);
            var3_5 = this.mInitialMotionY;
            this.mIsBeingDragged = false;
            this.finishSpinner((var2_4 - var3_5) * 0.5f);
        }
        this.mActivePointerId = -1;
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (Build.VERSION.SDK_INT < 21 && this.mTarget instanceof AbsListView || this.mTarget != null && !ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
            return;
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    void reset() {
        this.mCircleView.clearAnimation();
        this.mProgress.stop();
        this.mCircleView.setVisibility(8);
        this.setColorViewAlpha(255);
        if (this.mScale) {
            this.setAnimationProgress(0.0f);
        } else {
            this.setTargetOffsetTopAndBottom(this.mOriginalOffsetTop - this.mCurrentTargetOffsetTop);
        }
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    void setAnimationProgress(float f) {
        this.mCircleView.setScaleX(f);
        this.mCircleView.setScaleY(f);
    }

    @Deprecated
    public void setColorScheme(int ... arrn) {
        this.setColorSchemeResources(arrn);
    }

    public void setColorSchemeColors(int ... arrn) {
        this.ensureTarget();
        this.mProgress.setColorSchemeColors(arrn);
    }

    public void setColorSchemeResources(int ... arrn) {
        Context context = this.getContext();
        int[] arrn2 = new int[arrn.length];
        for (int i = 0; i < arrn.length; ++i) {
            arrn2[i] = ContextCompat.getColor(context, arrn[i]);
        }
        this.setColorSchemeColors(arrn2);
    }

    public void setDistanceToTriggerSync(int n) {
        this.mTotalDragDistance = n;
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        if (!bl) {
            this.reset();
        }
    }

    public void setNestedScrollingEnabled(boolean bl) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(bl);
    }

    public void setOnChildScrollUpCallback(OnChildScrollUpCallback onChildScrollUpCallback) {
        this.mChildScrollUpCallback = onChildScrollUpCallback;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener;
    }

    @Deprecated
    public void setProgressBackgroundColor(int n) {
        this.setProgressBackgroundColorSchemeResource(n);
    }

    public void setProgressBackgroundColorSchemeColor(int n) {
        this.mCircleView.setBackgroundColor(n);
    }

    public void setProgressBackgroundColorSchemeResource(int n) {
        this.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.getContext(), n));
    }

    public void setProgressViewEndTarget(boolean bl, int n) {
        this.mSpinnerOffsetEnd = n;
        this.mScale = bl;
        this.mCircleView.invalidate();
    }

    public void setProgressViewOffset(boolean bl, int n, int n2) {
        this.mScale = bl;
        this.mOriginalOffsetTop = n;
        this.mSpinnerOffsetEnd = n2;
        this.mUsingCustomStart = true;
        this.reset();
        this.mRefreshing = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setRefreshing(boolean bl) {
        if (bl && this.mRefreshing != bl) {
            this.mRefreshing = bl;
            int n = !this.mUsingCustomStart ? this.mSpinnerOffsetEnd + this.mOriginalOffsetTop : this.mSpinnerOffsetEnd;
            this.setTargetOffsetTopAndBottom(n - this.mCurrentTargetOffsetTop);
            this.mNotify = false;
            this.startScaleUpAnimation(this.mRefreshListener);
            return;
        }
        this.setRefreshing(bl, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSize(int n) {
        if (n != 0 && n != 1) {
            return;
        }
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.mCircleDiameter = n == 0 ? (int)(56.0f * displayMetrics.density) : (int)(40.0f * displayMetrics.density);
        this.mCircleView.setImageDrawable(null);
        this.mProgress.setStyle(n);
        this.mCircleView.setImageDrawable((Drawable)this.mProgress);
    }

    void setTargetOffsetTopAndBottom(int n) {
        this.mCircleView.bringToFront();
        ViewCompat.offsetTopAndBottom((View)this.mCircleView, n);
        this.mCurrentTargetOffsetTop = this.mCircleView.getTop();
    }

    public boolean startNestedScroll(int n) {
        return this.mNestedScrollingChildHelper.startNestedScroll(n);
    }

    void startScaleDownAnimation(Animation.AnimationListener animationListener) {
        this.mScaleDownAnimation = new Animation(){

            public void applyTransformation(float f, Transformation transformation) {
                SwipeRefreshLayout.this.setAnimationProgress(1.0f - f);
            }
        };
        this.mScaleDownAnimation.setDuration(150L);
        this.mCircleView.setAnimationListener(animationListener);
        this.mCircleView.clearAnimation();
        this.mCircleView.startAnimation(this.mScaleDownAnimation);
    }

    @Override
    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public static interface OnChildScrollUpCallback {
        public boolean canChildScrollUp(SwipeRefreshLayout var1, View var2);
    }

    public static interface OnRefreshListener {
        public void onRefresh();
    }

}

