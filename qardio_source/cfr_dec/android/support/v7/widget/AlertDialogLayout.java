/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AlertDialogLayout
extends LinearLayoutCompat {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n4 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n3, 0, n2, 0);
            layoutParams.height = n4;
        }
    }

    private static int resolveMinimumHeight(View view) {
        int n = ViewCompat.getMinimumHeight(view);
        if (n > 0) {
            return n;
        }
        if (view instanceof ViewGroup && (view = (ViewGroup)view).getChildCount() == 1) {
            return AlertDialogLayout.resolveMinimumHeight(view.getChildAt(0));
        }
        return 0;
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n + n3, n2 + n4);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean tryOnMeasure(int n, int n2) {
        int n3;
        int n4;
        View view;
        View view2 = null;
        View view3 = null;
        View view4 = null;
        int n5 = this.getChildCount();
        for (n4 = 0; n4 < n5; ++n4) {
            view = this.getChildAt(n4);
            if (view.getVisibility() == 8) continue;
            n3 = view.getId();
            if (n3 == R.id.topPanel) {
                view2 = view;
                continue;
            }
            if (n3 == R.id.buttonPanel) {
                view3 = view;
                continue;
            }
            if (n3 != R.id.contentPanel && n3 != R.id.customPanel) {
                return false;
            }
            if (view4 != null) {
                return false;
            }
            view4 = view;
        }
        int n6 = View.MeasureSpec.getMode((int)n2);
        int n7 = View.MeasureSpec.getSize((int)n2);
        int n8 = View.MeasureSpec.getMode((int)n);
        n3 = 0;
        int n9 = n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (view2 != null) {
            view2.measure(n, 0);
            n9 = n4 + view2.getMeasuredHeight();
            n3 = View.combineMeasuredStates((int)0, (int)view2.getMeasuredState());
        }
        n4 = 0;
        int n10 = 0;
        int n11 = n3;
        int n12 = n9;
        if (view3 != null) {
            view3.measure(n, 0);
            n4 = AlertDialogLayout.resolveMinimumHeight(view3);
            n10 = view3.getMeasuredHeight() - n4;
            n12 = n9 + n4;
            n11 = View.combineMeasuredStates((int)n3, (int)view3.getMeasuredState());
        }
        int n13 = 0;
        n3 = n11;
        n9 = n12;
        if (view4 != null) {
            n3 = n6 == 0 ? 0 : View.MeasureSpec.makeMeasureSpec((int)Math.max(0, n7 - n12), (int)n6);
            view4.measure(n, n3);
            n13 = view4.getMeasuredHeight();
            n9 = n12 + n13;
            n3 = View.combineMeasuredStates((int)n11, (int)view4.getMeasuredState());
        }
        n11 = n3;
        int n14 = n7 -= n9;
        n12 = n9;
        if (view3 != null) {
            n10 = Math.min(n7, n10);
            n12 = n4;
            n11 = n7;
            if (n10 > 0) {
                n11 = n7 - n10;
                n12 = n4 + n10;
            }
            view3.measure(n, View.MeasureSpec.makeMeasureSpec((int)n12, (int)1073741824));
            n12 = n9 - n4 + view3.getMeasuredHeight();
            n4 = View.combineMeasuredStates((int)n3, (int)view3.getMeasuredState());
            n14 = n11;
            n11 = n4;
        }
        n3 = n11;
        n4 = n12;
        if (view4 != null) {
            n3 = n11;
            n4 = n12;
            if (n14 > 0) {
                view4.measure(n, View.MeasureSpec.makeMeasureSpec((int)(n13 + n14), (int)n6));
                n4 = n12 - n13 + view4.getMeasuredHeight();
                n3 = View.combineMeasuredStates((int)n11, (int)view4.getMeasuredState());
            }
        }
        n11 = 0;
        for (n9 = 0; n9 < n5; ++n9) {
            view = this.getChildAt(n9);
            n12 = n11;
            if (view.getVisibility() != 8) {
                n12 = Math.max(n11, view.getMeasuredWidth());
            }
            n11 = n12;
        }
        this.setMeasuredDimension(View.resolveSizeAndState((int)(n11 + (this.getPaddingLeft() + this.getPaddingRight())), (int)n, (int)n3), View.resolveSizeAndState((int)n4, (int)n2, (int)0));
        if (n8 != 1073741824) {
            this.forceUniformWidth(n5, n2);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Drawable drawable2;
        int n5 = this.getPaddingLeft();
        int n6 = n3 - n;
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingRight();
        n = this.getMeasuredHeight();
        int n9 = this.getChildCount();
        int n10 = this.getGravity();
        switch (n10 & 0x70) {
            default: {
                n = this.getPaddingTop();
                break;
            }
            case 80: {
                n = this.getPaddingTop() + n4 - n2 - n;
                break;
            }
            case 16: {
                n = this.getPaddingTop() + (n4 - n2 - n) / 2;
            }
        }
        n3 = (drawable2 = this.getDividerDrawable()) == null ? 0 : drawable2.getIntrinsicHeight();
        for (n4 = 0; n4 < n9; ++n4) {
            drawable2 = this.getChildAt(n4);
            n2 = n;
            if (drawable2 != null) {
                n2 = n;
                if (drawable2.getVisibility() != 8) {
                    int n11;
                    int n12 = drawable2.getMeasuredWidth();
                    int n13 = drawable2.getMeasuredHeight();
                    LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)drawable2.getLayoutParams();
                    n2 = n11 = layoutParams.gravity;
                    if (n11 < 0) {
                        n2 = n10 & 0x800007;
                    }
                    switch (GravityCompat.getAbsoluteGravity(n2, ViewCompat.getLayoutDirection((View)this)) & 7) {
                        default: {
                            n2 = n5 + layoutParams.leftMargin;
                            break;
                        }
                        case 1: {
                            n2 = (n6 - n5 - n8 - n12) / 2 + n5 + layoutParams.leftMargin - layoutParams.rightMargin;
                            break;
                        }
                        case 5: {
                            n2 = n6 - n7 - n12 - layoutParams.rightMargin;
                        }
                    }
                    n11 = n;
                    if (this.hasDividerBeforeChildAt(n4)) {
                        n11 = n + n3;
                    }
                    n = n11 + layoutParams.topMargin;
                    this.setChildFrame((View)drawable2, n2, n, n12, n13);
                    n2 = n + (layoutParams.bottomMargin + n13);
                }
            }
            n = n2;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (!this.tryOnMeasure(n, n2)) {
            super.onMeasure(n, n2);
        }
    }
}

