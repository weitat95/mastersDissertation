/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class PagerSnapHelper
extends SnapHelper {
    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int distanceToCenter(RecyclerView.LayoutManager layoutManager, View view, OrientationHelper orientationHelper) {
        int n;
        int n2 = orientationHelper.getDecoratedStart(view);
        int n3 = orientationHelper.getDecoratedMeasurement(view) / 2;
        if (layoutManager.getClipToPadding()) {
            n = orientationHelper.getStartAfterPadding() + orientationHelper.getTotalSpace() / 2;
            do {
                return n2 + n3 - n;
                break;
            } while (true);
        }
        n = orientationHelper.getEnd() / 2;
        return n2 + n3 - n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View findCenterView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int n = layoutManager.getChildCount();
        if (n == 0) {
            return null;
        }
        View view = null;
        int n2 = layoutManager.getClipToPadding() ? orientationHelper.getStartAfterPadding() + orientationHelper.getTotalSpace() / 2 : orientationHelper.getEnd() / 2;
        int n3 = Integer.MAX_VALUE;
        int n4 = 0;
        do {
            View view2 = view;
            if (n4 >= n) return view2;
            view2 = layoutManager.getChildAt(n4);
            int n5 = Math.abs(orientationHelper.getDecoratedStart(view2) + orientationHelper.getDecoratedMeasurement(view2) / 2 - n2);
            int n6 = n3;
            if (n5 < n3) {
                n6 = n5;
                view = view2;
            }
            ++n4;
            n3 = n6;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int n = layoutManager.getChildCount();
        if (n == 0) {
            return null;
        }
        View view = null;
        int n2 = Integer.MAX_VALUE;
        int n3 = 0;
        do {
            View view2 = view;
            if (n3 >= n) return view2;
            view2 = layoutManager.getChildAt(n3);
            int n4 = orientationHelper.getDecoratedStart(view2);
            int n5 = n2;
            if (n4 < n2) {
                n5 = n4;
                view = view2;
            }
            ++n3;
            n2 = n5;
        } while (true);
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (this.mHorizontalHelper == null || this.mHorizontalHelper.mLayoutManager != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (this.mVerticalHelper == null || this.mVerticalHelper.mLayoutManager != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager layoutManager, View view) {
        int[] arrn = new int[2];
        arrn[0] = layoutManager.canScrollHorizontally() ? this.distanceToCenter(layoutManager, view, this.getHorizontalHelper(layoutManager)) : 0;
        if (layoutManager.canScrollVertically()) {
            arrn[1] = this.distanceToCenter(layoutManager, view, this.getVerticalHelper(layoutManager));
            return arrn;
        }
        arrn[1] = 0;
        return arrn;
    }

    @Override
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(this.mRecyclerView.getContext()){

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / (float)displayMetrics.densityDpi;
            }

            @Override
            protected int calculateTimeForScrolling(int n) {
                return Math.min(100, super.calculateTimeForScrolling(n));
            }

            @Override
            protected void onTargetFound(View arrn, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                arrn = PagerSnapHelper.this.calculateDistanceToFinalSnap(PagerSnapHelper.this.mRecyclerView.getLayoutManager(), (View)arrn);
                int n = arrn[0];
                int n2 = arrn[1];
                int n3 = this.calculateTimeForDeceleration(Math.max(Math.abs(n), Math.abs(n2)));
                if (n3 > 0) {
                    action.update(n, n2, n3, (Interpolator)this.mDecelerateInterpolator);
                }
            }
        };
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return this.findCenterView(layoutManager, this.getVerticalHelper(layoutManager));
        }
        if (layoutManager.canScrollHorizontally()) {
            return this.findCenterView(layoutManager, this.getHorizontalHelper(layoutManager));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        int n3;
        int n4 = layoutManager.getItemCount();
        if (n4 == 0) {
            return -1;
        }
        View view = null;
        if (layoutManager.canScrollVertically()) {
            view = this.findStartView(layoutManager, this.getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            view = this.findStartView(layoutManager, this.getHorizontalHelper(layoutManager));
        }
        if (view == null) {
            return -1;
        }
        int n5 = layoutManager.getPosition(view);
        if (n5 == -1) {
            return -1;
        }
        n = layoutManager.canScrollHorizontally() ? (n > 0 ? 1 : 0) : (n2 > 0 ? 1 : 0);
        n2 = n3 = 0;
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) {
            layoutManager = ((RecyclerView.SmoothScroller.ScrollVectorProvider)((Object)layoutManager)).computeScrollVectorForPosition(n4 - 1);
            n2 = n3;
            if (layoutManager != null) {
                n2 = ((PointF)layoutManager).x < 0.0f || ((PointF)layoutManager).y < 0.0f ? 1 : 0;
            }
        }
        if (n2 != 0) {
            n2 = n5;
            if (n == 0) return n2;
            return n5 - 1;
        }
        n2 = n5;
        if (n == 0) return n2;
        return n5 + 1;
    }

}

