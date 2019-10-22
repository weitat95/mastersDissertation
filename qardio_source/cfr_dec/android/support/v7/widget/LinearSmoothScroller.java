/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.PointF
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class LinearSmoothScroller
extends RecyclerView.SmoothScroller {
    private final float MILLISECONDS_PER_PX;
    protected final DecelerateInterpolator mDecelerateInterpolator;
    protected int mInterimTargetDx = 0;
    protected int mInterimTargetDy = 0;
    protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    protected PointF mTargetVector;

    public LinearSmoothScroller(Context context) {
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.MILLISECONDS_PER_PX = this.calculateSpeedPerPixel(context.getResources().getDisplayMetrics());
    }

    private int clampApplyScroll(int n, int n2) {
        int n3;
        n2 = n3 = n - n2;
        if (n * n3 <= 0) {
            n2 = 0;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int calculateDtToFit(int n, int n2, int n3, int n4, int n5) {
        switch (n5) {
            default: {
                throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
            }
            case -1: {
                return n3 - n;
            }
            case 1: {
                return n4 - n2;
            }
            case 0: 
        }
        n3 -= n;
        n = n3;
        if (n3 > 0) return n;
        n = n4 - n2;
        if (n >= 0) return 0;
        return n;
    }

    public int calculateDxToMakeVisible(View view, int n) {
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.calculateDtToFit(layoutManager.getDecoratedLeft(view) - layoutParams.leftMargin, layoutManager.getDecoratedRight(view) + layoutParams.rightMargin, layoutManager.getPaddingLeft(), layoutManager.getWidth() - layoutManager.getPaddingRight(), n);
    }

    public int calculateDyToMakeVisible(View view, int n) {
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        if (layoutManager == null || !layoutManager.canScrollVertically()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        return this.calculateDtToFit(layoutManager.getDecoratedTop(view) - layoutParams.topMargin, layoutManager.getDecoratedBottom(view) + layoutParams.bottomMargin, layoutManager.getPaddingTop(), layoutManager.getHeight() - layoutManager.getPaddingBottom(), n);
    }

    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 25.0f / (float)displayMetrics.densityDpi;
    }

    protected int calculateTimeForDeceleration(int n) {
        return (int)Math.ceil((double)this.calculateTimeForScrolling(n) / 0.3356);
    }

    protected int calculateTimeForScrolling(int n) {
        return (int)Math.ceil((float)Math.abs(n) * this.MILLISECONDS_PER_PX);
    }

    public PointF computeScrollVectorForPosition(int n) {
        RecyclerView.LayoutManager layoutManager = this.getLayoutManager();
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return ((RecyclerView.SmoothScroller.ScrollVectorProvider)((Object)layoutManager)).computeScrollVectorForPosition(n);
        }
        Log.w((String)"LinearSmoothScroller", (String)("You should override computeScrollVectorForPosition when the LayoutManager does not implement " + RecyclerView.SmoothScroller.ScrollVectorProvider.class.getCanonicalName()));
        return null;
    }

    protected int getHorizontalSnapPreference() {
        if (this.mTargetVector == null || this.mTargetVector.x == 0.0f) {
            return 0;
        }
        if (this.mTargetVector.x > 0.0f) {
            return 1;
        }
        return -1;
    }

    protected int getVerticalSnapPreference() {
        if (this.mTargetVector == null || this.mTargetVector.y == 0.0f) {
            return 0;
        }
        if (this.mTargetVector.y > 0.0f) {
            return 1;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onSeekTargetStep(int n, int n2, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        if (this.getChildCount() == 0) {
            this.stop();
            return;
        } else {
            this.mInterimTargetDx = this.clampApplyScroll(this.mInterimTargetDx, n);
            this.mInterimTargetDy = this.clampApplyScroll(this.mInterimTargetDy, n2);
            if (this.mInterimTargetDx != 0 || this.mInterimTargetDy != 0) return;
            {
                this.updateActionForInterimTarget(action);
                return;
            }
        }
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
        this.mInterimTargetDy = 0;
        this.mInterimTargetDx = 0;
        this.mTargetVector = null;
    }

    @Override
    protected void onTargetFound(View view, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        int n;
        int n2 = this.calculateDxToMakeVisible(view, this.getHorizontalSnapPreference());
        int n3 = this.calculateTimeForDeceleration((int)Math.sqrt(n2 * n2 + (n = this.calculateDyToMakeVisible(view, this.getVerticalSnapPreference())) * n));
        if (n3 > 0) {
            action.update(-n2, -n, n3, (Interpolator)this.mDecelerateInterpolator);
        }
    }

    protected void updateActionForInterimTarget(RecyclerView.SmoothScroller.Action action) {
        PointF pointF = this.computeScrollVectorForPosition(this.getTargetPosition());
        if (pointF == null || pointF.x == 0.0f && pointF.y == 0.0f) {
            action.jumpTo(this.getTargetPosition());
            this.stop();
            return;
        }
        this.normalize(pointF);
        this.mTargetVector = pointF;
        this.mInterimTargetDx = (int)(pointF.x * 10000.0f);
        this.mInterimTargetDy = (int)(pointF.y * 10000.0f);
        int n = this.calculateTimeForScrolling(10000);
        action.update((int)((float)this.mInterimTargetDx * 1.2f), (int)((float)this.mInterimTargetDy * 1.2f), (int)((float)n * 1.2f), (Interpolator)this.mLinearInterpolator);
    }
}

