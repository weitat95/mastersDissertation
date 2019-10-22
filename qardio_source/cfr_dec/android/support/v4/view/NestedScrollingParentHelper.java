/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.v4.view;

import android.view.View;
import android.view.ViewGroup;

public class NestedScrollingParentHelper {
    private int mNestedScrollAxes;
    private final ViewGroup mViewGroup;

    public NestedScrollingParentHelper(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollAxes;
    }

    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.onNestedScrollAccepted(view, view2, n, 0);
    }

    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
        this.mNestedScrollAxes = n;
    }

    public void onStopNestedScroll(View view) {
        this.onStopNestedScroll(view, 0);
    }

    public void onStopNestedScroll(View view, int n) {
        this.mNestedScrollAxes = 0;
    }
}

