/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public abstract class SnapHelper
extends RecyclerView.OnFlingListener {
    private Scroller mGravityScroller;
    RecyclerView mRecyclerView;
    private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener(){
        boolean mScrolled = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int n) {
            super.onScrollStateChanged(recyclerView, n);
            if (n == 0 && this.mScrolled) {
                this.mScrolled = false;
                SnapHelper.this.snapToTargetExistingView();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
            if (n != 0 || n2 != 0) {
                this.mScrolled = true;
            }
        }
    };

    private void destroyCallbacks() {
        this.mRecyclerView.removeOnScrollListener(this.mScrollListener);
        this.mRecyclerView.setOnFlingListener(null);
    }

    private void setupCallbacks() throws IllegalStateException {
        if (this.mRecyclerView.getOnFlingListener() != null) {
            throw new IllegalStateException("An instance of OnFlingListener already set.");
        }
        this.mRecyclerView.addOnScrollListener(this.mScrollListener);
        this.mRecyclerView.setOnFlingListener(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean snapFromFling(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        RecyclerView.SmoothScroller smoothScroller;
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) || (smoothScroller = this.createScroller(layoutManager)) == null || (n = this.findTargetSnapPosition(layoutManager, n, n2)) == -1) {
            return false;
        }
        smoothScroller.setTargetPosition(n);
        layoutManager.startSmoothScroll(smoothScroller);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void attachToRecyclerView(RecyclerView recyclerView) throws IllegalStateException {
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
        this.mGravityScroller = new Scroller(this.mRecyclerView.getContext(), (Interpolator)new DecelerateInterpolator());
        this.snapToTargetExistingView();
    }

    public abstract int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager var1, View var2);

    protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        return this.createSnapScroller(layoutManager);
    }

    @Deprecated
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
            protected void onTargetFound(View arrn, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                arrn = SnapHelper.this.calculateDistanceToFinalSnap(SnapHelper.this.mRecyclerView.getLayoutManager(), (View)arrn);
                int n = arrn[0];
                int n2 = arrn[1];
                int n3 = this.calculateTimeForDeceleration(Math.max(Math.abs(n), Math.abs(n2)));
                if (n3 > 0) {
                    action.update(n, n2, n3, (Interpolator)this.mDecelerateInterpolator);
                }
            }
        };
    }

    public abstract View findSnapView(RecyclerView.LayoutManager var1);

    public abstract int findTargetSnapPosition(RecyclerView.LayoutManager var1, int var2, int var3);

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onFling(int n, int n2) {
        block3: {
            block2: {
                RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
                if (layoutManager == null || this.mRecyclerView.getAdapter() == null) break block2;
                int n3 = this.mRecyclerView.getMinFlingVelocity();
                if ((Math.abs(n2) > n3 || Math.abs(n) > n3) && this.snapFromFling(layoutManager, n, n2)) break block3;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void snapToTargetExistingView() {
        View view;
        int[] arrn;
        if (this.mRecyclerView == null || (arrn = this.mRecyclerView.getLayoutManager()) == null || (view = this.findSnapView((RecyclerView.LayoutManager)arrn)) == null || (arrn = this.calculateDistanceToFinalSnap((RecyclerView.LayoutManager)arrn, view))[0] == 0 && arrn[1] == 0) {
            return;
        }
        this.mRecyclerView.smoothScrollBy(arrn[0], arrn[1]);
    }

}

