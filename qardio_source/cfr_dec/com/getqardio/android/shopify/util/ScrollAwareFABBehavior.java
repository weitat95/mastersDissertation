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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

public class ScrollAwareFABBehavior
extends FloatingActionButton.Behavior {
    public ScrollAwareFABBehavior(Context context, AttributeSet attributeSet) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view, int n, int n2, int n3, int n4) {
        super.onNestedScroll(coordinatorLayout, floatingActionButton, view, n, n2, n3, n4);
        if (n2 > 0 && floatingActionButton.getVisibility() == 0) {
            floatingActionButton.hide();
            return;
        } else {
            if (n2 >= 0 || floatingActionButton.getVisibility() == 0) return;
            {
                floatingActionButton.show();
                return;
            }
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view, View view2, int n) {
        return n == 2 || super.onStartNestedScroll(coordinatorLayout, floatingActionButton, view, view2, n);
    }
}

