/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.view.MotionEvent
 *  android.view.View
 */
package android.support.v7.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

class FastScroller
extends RecyclerView.ItemDecoration
implements RecyclerView.OnItemTouchListener {
    private static final int[] EMPTY_STATE_SET;
    private static final int[] PRESSED_STATE_SET;
    private int mAnimationState = 0;
    private int mDragState = 0;
    private final Runnable mHideRunnable;
    float mHorizontalDragX;
    private final int[] mHorizontalRange;
    int mHorizontalThumbCenterX;
    private final StateListDrawable mHorizontalThumbDrawable;
    private final int mHorizontalThumbHeight;
    int mHorizontalThumbWidth;
    private final Drawable mHorizontalTrackDrawable;
    private final int mHorizontalTrackHeight;
    private final int mMargin;
    private boolean mNeedHorizontalScrollbar = false;
    private boolean mNeedVerticalScrollbar = false;
    private final RecyclerView.OnScrollListener mOnScrollListener;
    private RecyclerView mRecyclerView;
    private int mRecyclerViewHeight = 0;
    private int mRecyclerViewWidth = 0;
    private final int mScrollbarMinimumRange;
    private final ValueAnimator mShowHideAnimator;
    private int mState = 0;
    float mVerticalDragY;
    private final int[] mVerticalRange = new int[2];
    int mVerticalThumbCenterY;
    private final StateListDrawable mVerticalThumbDrawable;
    int mVerticalThumbHeight;
    private final int mVerticalThumbWidth;
    private final Drawable mVerticalTrackDrawable;
    private final int mVerticalTrackWidth;

    static {
        PRESSED_STATE_SET = new int[]{16842919};
        EMPTY_STATE_SET = new int[0];
    }

    FastScroller(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable2, StateListDrawable stateListDrawable2, Drawable drawable3, int n, int n2, int n3) {
        this.mHorizontalRange = new int[2];
        this.mShowHideAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        this.mHideRunnable = new Runnable(){

            @Override
            public void run() {
                FastScroller.this.hide(500);
            }
        };
        this.mOnScrollListener = new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(RecyclerView recyclerView, int n, int n2) {
                FastScroller.this.updateScrollPosition(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
            }
        };
        this.mVerticalThumbDrawable = stateListDrawable;
        this.mVerticalTrackDrawable = drawable2;
        this.mHorizontalThumbDrawable = stateListDrawable2;
        this.mHorizontalTrackDrawable = drawable3;
        this.mVerticalThumbWidth = Math.max(n, stateListDrawable.getIntrinsicWidth());
        this.mVerticalTrackWidth = Math.max(n, drawable2.getIntrinsicWidth());
        this.mHorizontalThumbHeight = Math.max(n, stateListDrawable2.getIntrinsicWidth());
        this.mHorizontalTrackHeight = Math.max(n, drawable3.getIntrinsicWidth());
        this.mScrollbarMinimumRange = n2;
        this.mMargin = n3;
        this.mVerticalThumbDrawable.setAlpha(255);
        this.mVerticalTrackDrawable.setAlpha(255);
        this.mShowHideAnimator.addListener((Animator.AnimatorListener)new AnimatorListener());
        this.mShowHideAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new AnimatorUpdater());
        this.attachToRecyclerView(recyclerView);
    }

    private void cancelHide() {
        this.mRecyclerView.removeCallbacks(this.mHideRunnable);
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this);
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
        this.cancelHide();
    }

    private void drawHorizontalScrollbar(Canvas canvas) {
        int n = this.mRecyclerViewHeight - this.mHorizontalThumbHeight;
        int n2 = this.mHorizontalThumbCenterX - this.mHorizontalThumbWidth / 2;
        this.mHorizontalThumbDrawable.setBounds(0, 0, this.mHorizontalThumbWidth, this.mHorizontalThumbHeight);
        this.mHorizontalTrackDrawable.setBounds(0, 0, this.mRecyclerViewWidth, this.mHorizontalTrackHeight);
        canvas.translate(0.0f, (float)n);
        this.mHorizontalTrackDrawable.draw(canvas);
        canvas.translate((float)n2, 0.0f);
        this.mHorizontalThumbDrawable.draw(canvas);
        canvas.translate((float)(-n2), (float)(-n));
    }

    private void drawVerticalScrollbar(Canvas canvas) {
        int n = this.mRecyclerViewWidth - this.mVerticalThumbWidth;
        int n2 = this.mVerticalThumbCenterY - this.mVerticalThumbHeight / 2;
        this.mVerticalThumbDrawable.setBounds(0, 0, this.mVerticalThumbWidth, this.mVerticalThumbHeight);
        this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, this.mRecyclerViewHeight);
        if (this.isLayoutRTL()) {
            this.mVerticalTrackDrawable.draw(canvas);
            canvas.translate((float)this.mVerticalThumbWidth, (float)n2);
            canvas.scale(-1.0f, 1.0f);
            this.mVerticalThumbDrawable.draw(canvas);
            canvas.scale(1.0f, 1.0f);
            canvas.translate((float)(-this.mVerticalThumbWidth), (float)(-n2));
            return;
        }
        canvas.translate((float)n, 0.0f);
        this.mVerticalTrackDrawable.draw(canvas);
        canvas.translate(0.0f, (float)n2);
        this.mVerticalThumbDrawable.draw(canvas);
        canvas.translate((float)(-n), (float)(-n2));
    }

    private int[] getHorizontalRange() {
        this.mHorizontalRange[0] = this.mMargin;
        this.mHorizontalRange[1] = this.mRecyclerViewWidth - this.mMargin;
        return this.mHorizontalRange;
    }

    private int[] getVerticalRange() {
        this.mVerticalRange[0] = this.mMargin;
        this.mVerticalRange[1] = this.mRecyclerViewHeight - this.mMargin;
        return this.mVerticalRange;
    }

    private void horizontalScrollTo(float f) {
        int[] arrn = this.getHorizontalRange();
        f = Math.max((float)arrn[0], Math.min((float)arrn[1], f));
        if (Math.abs((float)this.mHorizontalThumbCenterX - f) < 2.0f) {
            return;
        }
        int n = this.scrollTo(this.mHorizontalDragX, f, arrn, this.mRecyclerView.computeHorizontalScrollRange(), this.mRecyclerView.computeHorizontalScrollOffset(), this.mRecyclerViewWidth);
        if (n != 0) {
            this.mRecyclerView.scrollBy(n, 0);
        }
        this.mHorizontalDragX = f;
    }

    private boolean isLayoutRTL() {
        return ViewCompat.getLayoutDirection((View)this.mRecyclerView) == 1;
    }

    private void requestRedraw() {
        this.mRecyclerView.invalidate();
    }

    private void resetHideDelay(int n) {
        this.cancelHide();
        this.mRecyclerView.postDelayed(this.mHideRunnable, (long)n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int scrollTo(float f, float f2, int[] arrn, int n, int n2, int n3) {
        int n4 = arrn[1] - arrn[0];
        if (n4 == 0) {
            return 0;
        }
        f = (f2 - f) / (float)n4;
        n3 = n - n3;
        n = (int)((float)n3 * f);
        if ((n2 += n) >= n3) return 0;
        if (n2 >= 0) return n;
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setState(int n) {
        if (n == 2 && this.mState != 2) {
            this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
            this.cancelHide();
        }
        if (n == 0) {
            this.requestRedraw();
        } else {
            this.show();
        }
        if (this.mState == 2 && n != 2) {
            this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
            this.resetHideDelay(1200);
        } else if (n == 1) {
            this.resetHideDelay(1500);
        }
        this.mState = n;
    }

    private void setupCallbacks() {
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this);
        this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
    }

    private void verticalScrollTo(float f) {
        int[] arrn = this.getVerticalRange();
        f = Math.max((float)arrn[0], Math.min((float)arrn[1], f));
        if (Math.abs((float)this.mVerticalThumbCenterY - f) < 2.0f) {
            return;
        }
        int n = this.scrollTo(this.mVerticalDragY, f, arrn, this.mRecyclerView.computeVerticalScrollRange(), this.mRecyclerView.computeVerticalScrollOffset(), this.mRecyclerViewHeight);
        if (n != 0) {
            this.mRecyclerView.scrollBy(0, n);
        }
        this.mVerticalDragY = f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        block5: {
            block4: {
                if (this.mRecyclerView == recyclerView) break block4;
                if (this.mRecyclerView != null) {
                    this.destroyCallbacks();
                }
                this.mRecyclerView = recyclerView;
                if (this.mRecyclerView != null) break block5;
            }
            return;
        }
        this.setupCallbacks();
    }

    void hide(int n) {
        switch (this.mAnimationState) {
            default: {
                return;
            }
            case 1: {
                this.mShowHideAnimator.cancel();
            }
            case 2: 
        }
        this.mAnimationState = 3;
        this.mShowHideAnimator.setFloatValues(new float[]{((Float)this.mShowHideAnimator.getAnimatedValue()).floatValue(), 0.0f});
        this.mShowHideAnimator.setDuration((long)n);
        this.mShowHideAnimator.start();
    }

    boolean isPointInsideHorizontalThumb(float f, float f2) {
        return f2 >= (float)(this.mRecyclerViewHeight - this.mHorizontalThumbHeight) && f >= (float)(this.mHorizontalThumbCenterX - this.mHorizontalThumbWidth / 2) && f <= (float)(this.mHorizontalThumbCenterX + this.mHorizontalThumbWidth / 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean isPointInsideVerticalThumb(float f, float f2) {
        return (this.isLayoutRTL() ? f <= (float)(this.mVerticalThumbWidth / 2) : f >= (float)(this.mRecyclerViewWidth - this.mVerticalThumbWidth)) && f2 >= (float)(this.mVerticalThumbCenterY - this.mVerticalThumbHeight / 2) && f2 <= (float)(this.mVerticalThumbCenterY + this.mVerticalThumbHeight / 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.mRecyclerViewWidth != this.mRecyclerView.getWidth() || this.mRecyclerViewHeight != this.mRecyclerView.getHeight()) {
            this.mRecyclerViewWidth = this.mRecyclerView.getWidth();
            this.mRecyclerViewHeight = this.mRecyclerView.getHeight();
            this.setState(0);
            return;
        } else {
            if (this.mAnimationState == 0) return;
            {
                if (this.mNeedVerticalScrollbar) {
                    this.drawVerticalScrollbar(canvas);
                }
                if (!this.mNeedHorizontalScrollbar) return;
                {
                    this.drawHorizontalScrollbar(canvas);
                    return;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.mState == 1) {
            boolean bl = this.isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean bl2 = this.isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() == 0 && (bl || bl2)) {
                if (bl2) {
                    this.mDragState = 1;
                    this.mHorizontalDragX = (int)motionEvent.getX();
                } else if (bl) {
                    this.mDragState = 2;
                    this.mVerticalDragY = (int)motionEvent.getY();
                }
                this.setState(2);
                return true;
            }
            return false;
        }
        return this.mState == 2;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.mState == 0) return;
        if (motionEvent.getAction() == 0) {
            boolean bl = this.isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean bl2 = this.isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (!bl && !bl2) return;
            {
                if (bl2) {
                    this.mDragState = 1;
                    this.mHorizontalDragX = (int)motionEvent.getX();
                } else if (bl) {
                    this.mDragState = 2;
                    this.mVerticalDragY = (int)motionEvent.getY();
                }
                this.setState(2);
                return;
            }
        }
        if (motionEvent.getAction() == 1 && this.mState == 2) {
            this.mVerticalDragY = 0.0f;
            this.mHorizontalDragX = 0.0f;
            this.setState(1);
            this.mDragState = 0;
            return;
        }
        if (motionEvent.getAction() != 2 || this.mState != 2) return;
        this.show();
        if (this.mDragState == 1) {
            this.horizontalScrollTo(motionEvent.getX());
        }
        if (this.mDragState != 2) {
            return;
        }
        this.verticalScrollTo(motionEvent.getY());
    }

    public void show() {
        switch (this.mAnimationState) {
            default: {
                return;
            }
            case 3: {
                this.mShowHideAnimator.cancel();
            }
            case 0: 
        }
        this.mAnimationState = 1;
        this.mShowHideAnimator.setFloatValues(new float[]{((Float)this.mShowHideAnimator.getAnimatedValue()).floatValue(), 1.0f});
        this.mShowHideAnimator.setDuration(500L);
        this.mShowHideAnimator.setStartDelay(0L);
        this.mShowHideAnimator.start();
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateScrollPosition(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.mRecyclerView.computeVerticalScrollRange();
        boolean bl = n5 - (n3 = this.mRecyclerViewHeight) > 0 && this.mRecyclerViewHeight >= this.mScrollbarMinimumRange;
        this.mNeedVerticalScrollbar = bl;
        int n6 = this.mRecyclerView.computeHorizontalScrollRange();
        bl = n6 - (n4 = this.mRecyclerViewWidth) > 0 && this.mRecyclerViewWidth >= this.mScrollbarMinimumRange;
        this.mNeedHorizontalScrollbar = bl;
        if (!this.mNeedVerticalScrollbar && !this.mNeedHorizontalScrollbar) {
            if (this.mState == 0) return;
            {
                this.setState(0);
                return;
            }
        } else {
            float f;
            float f2;
            if (this.mNeedVerticalScrollbar) {
                f2 = n2;
                f = (float)n3 / 2.0f;
                this.mVerticalThumbCenterY = (int)((float)n3 * (f2 + f) / (float)n5);
                this.mVerticalThumbHeight = Math.min(n3, n3 * n3 / n5);
            }
            if (this.mNeedHorizontalScrollbar) {
                f2 = n;
                f = (float)n4 / 2.0f;
                this.mHorizontalThumbCenterX = (int)((float)n4 * (f2 + f) / (float)n6);
                this.mHorizontalThumbWidth = Math.min(n4, n4 * n4 / n6);
            }
            if (this.mState != 0 && this.mState != 1) return;
            {
                this.setState(1);
                return;
            }
        }
    }

    private class AnimatorListener
    extends AnimatorListenerAdapter {
        private boolean mCanceled = false;

        private AnimatorListener() {
        }

        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator2) {
            if (this.mCanceled) {
                this.mCanceled = false;
                return;
            }
            if (((Float)FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0f) {
                FastScroller.this.mAnimationState = 0;
                FastScroller.this.setState(0);
                return;
            }
            FastScroller.this.mAnimationState = 2;
            FastScroller.this.requestRedraw();
        }
    }

    private class AnimatorUpdater
    implements ValueAnimator.AnimatorUpdateListener {
        private AnimatorUpdater() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int n = (int)(((Float)valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScroller.this.mVerticalThumbDrawable.setAlpha(n);
            FastScroller.this.mVerticalTrackDrawable.setAlpha(n);
            FastScroller.this.requestRedraw();
        }
    }

}

