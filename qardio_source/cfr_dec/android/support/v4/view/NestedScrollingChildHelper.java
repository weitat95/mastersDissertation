/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v4.view;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

public class NestedScrollingChildHelper {
    private boolean mIsNestedScrollingEnabled;
    private ViewParent mNestedScrollingParentNonTouch;
    private ViewParent mNestedScrollingParentTouch;
    private int[] mTempNestedScrollConsumed;
    private final View mView;

    public NestedScrollingChildHelper(View view) {
        this.mView = view;
    }

    private ViewParent getNestedScrollingParentForType(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 0: {
                return this.mNestedScrollingParentTouch;
            }
            case 1: 
        }
        return this.mNestedScrollingParentNonTouch;
    }

    private void setNestedScrollingParentForType(int n, ViewParent viewParent) {
        switch (n) {
            default: {
                return;
            }
            case 0: {
                this.mNestedScrollingParentTouch = viewParent;
                return;
            }
            case 1: 
        }
        this.mNestedScrollingParentNonTouch = viewParent;
    }

    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.isNestedScrollingEnabled()) {
            ViewParent viewParent = this.getNestedScrollingParentForType(0);
            bl3 = bl2;
            if (viewParent != null) {
                bl3 = ViewParentCompat.onNestedFling(viewParent, this.mView, f, f2, bl);
            }
        }
        return bl3;
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.isNestedScrollingEnabled()) {
            ViewParent viewParent = this.getNestedScrollingParentForType(0);
            bl2 = bl;
            if (viewParent != null) {
                bl2 = ViewParentCompat.onNestedPreFling(viewParent, this.mView, f, f2);
            }
        }
        return bl2;
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.dispatchNestedPreScroll(n, n2, arrn, arrn2, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        if (!this.isNestedScrollingEnabled()) return false;
        ViewParent viewParent = this.getNestedScrollingParentForType(n3);
        if (viewParent == null) {
            return false;
        }
        if (n != 0 || n2 != 0) {
            int n4 = 0;
            int n5 = 0;
            if (arrn2 != null) {
                this.mView.getLocationInWindow(arrn2);
                n4 = arrn2[0];
                n5 = arrn2[1];
            }
            int[] arrn3 = arrn;
            if (arrn == null) {
                if (this.mTempNestedScrollConsumed == null) {
                    this.mTempNestedScrollConsumed = new int[2];
                }
                arrn3 = this.mTempNestedScrollConsumed;
            }
            arrn3[0] = 0;
            arrn3[1] = 0;
            ViewParentCompat.onNestedPreScroll(viewParent, this.mView, n, n2, arrn3, n3);
            if (arrn2 != null) {
                this.mView.getLocationInWindow(arrn2);
                arrn2[0] = arrn2[0] - n4;
                arrn2[1] = arrn2[1] - n5;
            }
            if (arrn3[0] != 0) return true;
            if (arrn3[1] == 0) return false;
            return true;
        }
        if (arrn2 == null) return false;
        arrn2[0] = 0;
        arrn2[1] = 0;
        return false;
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.dispatchNestedScroll(n, n2, n3, n4, arrn, 0);
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        if (this.isNestedScrollingEnabled()) {
            ViewParent viewParent = this.getNestedScrollingParentForType(n5);
            if (viewParent == null) {
                return false;
            }
            if (n != 0 || n2 != 0 || n3 != 0 || n4 != 0) {
                int n6 = 0;
                int n7 = 0;
                if (arrn != null) {
                    this.mView.getLocationInWindow(arrn);
                    n6 = arrn[0];
                    n7 = arrn[1];
                }
                ViewParentCompat.onNestedScroll(viewParent, this.mView, n, n2, n3, n4, n5);
                if (arrn != null) {
                    this.mView.getLocationInWindow(arrn);
                    arrn[0] = arrn[0] - n6;
                    arrn[1] = arrn[1] - n7;
                }
                return true;
            }
            if (arrn != null) {
                arrn[0] = 0;
                arrn[1] = 0;
            }
        }
        return false;
    }

    public boolean hasNestedScrollingParent() {
        return this.hasNestedScrollingParent(0);
    }

    public boolean hasNestedScrollingParent(int n) {
        return this.getNestedScrollingParentForType(n) != null;
    }

    public boolean isNestedScrollingEnabled() {
        return this.mIsNestedScrollingEnabled;
    }

    public void setNestedScrollingEnabled(boolean bl) {
        if (this.mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(this.mView);
        }
        this.mIsNestedScrollingEnabled = bl;
    }

    public boolean startNestedScroll(int n) {
        return this.startNestedScroll(n, 0);
    }

    public boolean startNestedScroll(int n, int n2) {
        if (this.hasNestedScrollingParent(n2)) {
            return true;
        }
        if (this.isNestedScrollingEnabled()) {
            View view = this.mView;
            for (ViewParent viewParent = this.mView.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
                if (ViewParentCompat.onStartNestedScroll(viewParent, view, this.mView, n, n2)) {
                    this.setNestedScrollingParentForType(n2, viewParent);
                    ViewParentCompat.onNestedScrollAccepted(viewParent, view, this.mView, n, n2);
                    return true;
                }
                if (!(viewParent instanceof View)) continue;
                view = (View)viewParent;
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        this.stopNestedScroll(0);
    }

    public void stopNestedScroll(int n) {
        ViewParent viewParent = this.getNestedScrollingParentForType(n);
        if (viewParent != null) {
            ViewParentCompat.onStopNestedScroll(viewParent, this.mView, n);
            this.setNestedScrollingParentForType(n, null);
        }
    }
}

