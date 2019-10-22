/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.design.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout
extends ViewGroup {
    private int mBaseline = -1;

    public BaselineLayout(Context context) {
        super(context, null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public int getBaseline() {
        return this.mBaseline;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeft();
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingTop();
        n2 = 0;
        while (n2 < n5) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() != 8) {
                int n9 = view.getMeasuredWidth();
                int n10 = view.getMeasuredHeight();
                int n11 = n6 + (n3 - n - n7 - n6 - n9) / 2;
                n4 = this.mBaseline != -1 && view.getBaseline() != -1 ? this.mBaseline + n8 - view.getBaseline() : n8;
                view.layout(n11, n4, n11 + n9, n4 + n10);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4 = this.getChildCount();
        int n5 = 0;
        int n6 = 0;
        int n7 = -1;
        int n8 = -1;
        int n9 = 0;
        for (int i = 0; i < n4; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            this.measureChild(view, n, n2);
            int n10 = view.getBaseline();
            int n11 = n7;
            n3 = n8;
            if (n10 != -1) {
                n11 = Math.max(n7, n10);
                n3 = Math.max(n8, view.getMeasuredHeight() - n10);
            }
            n5 = Math.max(n5, view.getMeasuredWidth());
            n6 = Math.max(n6, view.getMeasuredHeight());
            n9 = View.combineMeasuredStates((int)n9, (int)view.getMeasuredState());
            n7 = n11;
            n8 = n3;
        }
        n3 = n6;
        if (n7 != -1) {
            n3 = Math.max(n6, n7 + Math.max(n8, this.getPaddingBottom()));
            this.mBaseline = n7;
        }
        n6 = Math.max(n3, this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n5, this.getSuggestedMinimumWidth()), (int)n, (int)n9), View.resolveSizeAndState((int)n6, (int)n2, (int)(n9 << 16)));
    }
}

