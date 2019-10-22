/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.getqardio.android.shopify.util;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class FlingBehavior
extends AppBarLayout.Behavior {
    private static final int TOP_CHILD_FLING_THRESHOLD = 2;
    private boolean isPositive;

    public FlingBehavior() {
    }

    public FlingBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean consumed(SwipeRefreshLayout swipeRefreshLayout, boolean bl) {
        boolean bl2 = false;
        if (swipeRefreshLayout.getChildCount() <= 0) return bl;
        if (swipeRefreshLayout.getChildAt(0) instanceof RecyclerView) {
            return this.consumed((RecyclerView)swipeRefreshLayout.getChildAt(0));
        }
        if (!(swipeRefreshLayout.getChildAt(0) instanceof ScrollingView)) return bl;
        bl = bl2;
        if (swipeRefreshLayout.getScrollY() >= 0) return bl;
        return true;
    }

    private boolean consumed(RecyclerView recyclerView) {
        boolean bl = false;
        if (recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0)) > 2) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, float f, float f2, boolean bl) {
        float f3;
        block7: {
            block6: {
                if (f2 > 0.0f && !this.isPositive) break block6;
                f3 = f2;
                if (!(f2 < 0.0f)) break block7;
                f3 = f2;
                if (!this.isPositive) break block7;
            }
            f3 = f2 * -1.0f;
        }
        if (view instanceof SwipeRefreshLayout) {
            bl = this.consumed((SwipeRefreshLayout)view, bl);
            return super.onNestedFling(coordinatorLayout, appBarLayout, view, f, f3, bl);
        }
        if (view instanceof RecyclerView) {
            bl = this.consumed((RecyclerView)view);
            return super.onNestedFling(coordinatorLayout, appBarLayout, view, f, f3, bl);
        }
        if (!(view instanceof ScrollingView)) return super.onNestedFling(coordinatorLayout, appBarLayout, view, f, f3, bl);
        if (view.getScrollY() < 0) {
            bl = true;
            return super.onNestedFling(coordinatorLayout, appBarLayout, view, f, f3, bl);
        }
        bl = false;
        return super.onNestedFling(coordinatorLayout, appBarLayout, view, f, f3, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n, int n2, int[] arrn) {
        super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, n, n2, arrn);
        boolean bl = n2 > 0;
        this.isPositive = bl;
    }
}

