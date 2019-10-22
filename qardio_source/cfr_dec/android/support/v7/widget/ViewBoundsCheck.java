/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.view.View;

class ViewBoundsCheck {
    BoundFlags mBoundFlags;
    final Callback mCallback;

    ViewBoundsCheck(Callback callback) {
        this.mCallback = callback;
        this.mBoundFlags = new BoundFlags();
    }

    /*
     * Enabled aggressive block sorting
     */
    View findOneViewWithinBoundFlags(int n, int n2, int n3, int n4) {
        int n5 = this.mCallback.getParentStart();
        int n6 = this.mCallback.getParentEnd();
        int n7 = n2 > n ? 1 : -1;
        View view = null;
        while (n != n2) {
            View view2 = this.mCallback.getChildAt(n);
            int n8 = this.mCallback.getChildStart(view2);
            int n9 = this.mCallback.getChildEnd(view2);
            this.mBoundFlags.setBounds(n5, n6, n8, n9);
            if (n3 != 0) {
                this.mBoundFlags.resetFlags();
                this.mBoundFlags.addFlags(n3);
                if (this.mBoundFlags.boundsMatch()) {
                    return view2;
                }
            }
            View view3 = view;
            if (n4 != 0) {
                this.mBoundFlags.resetFlags();
                this.mBoundFlags.addFlags(n4);
                view3 = view;
                if (this.mBoundFlags.boundsMatch()) {
                    view3 = view2;
                }
            }
            n += n7;
            view = view3;
        }
        return view;
    }

    boolean isViewWithinBoundFlags(View view, int n) {
        this.mBoundFlags.setBounds(this.mCallback.getParentStart(), this.mCallback.getParentEnd(), this.mCallback.getChildStart(view), this.mCallback.getChildEnd(view));
        if (n != 0) {
            this.mBoundFlags.resetFlags();
            this.mBoundFlags.addFlags(n);
            return this.mBoundFlags.boundsMatch();
        }
        return false;
    }

    static class BoundFlags {
        int mBoundFlags = 0;
        int mChildEnd;
        int mChildStart;
        int mRvEnd;
        int mRvStart;

        BoundFlags() {
        }

        void addFlags(int n) {
            this.mBoundFlags |= n;
        }

        /*
         * Enabled aggressive block sorting
         */
        boolean boundsMatch() {
            return !((this.mBoundFlags & 7) != 0 && (this.mBoundFlags & this.compare(this.mChildStart, this.mRvStart) << 0) == 0 || (this.mBoundFlags & 0x70) != 0 && (this.mBoundFlags & this.compare(this.mChildStart, this.mRvEnd) << 4) == 0 || (this.mBoundFlags & 0x700) != 0 && (this.mBoundFlags & this.compare(this.mChildEnd, this.mRvStart) << 8) == 0) && ((this.mBoundFlags & 0x7000) == 0 || (this.mBoundFlags & this.compare(this.mChildEnd, this.mRvEnd) << 12) != 0);
        }

        int compare(int n, int n2) {
            if (n > n2) {
                return 1;
            }
            if (n == n2) {
                return 2;
            }
            return 4;
        }

        void resetFlags() {
            this.mBoundFlags = 0;
        }

        void setBounds(int n, int n2, int n3, int n4) {
            this.mRvStart = n;
            this.mRvEnd = n2;
            this.mChildStart = n3;
            this.mChildEnd = n4;
        }
    }

    static interface Callback {
        public View getChildAt(int var1);

        public int getChildEnd(View var1);

        public int getChildStart(View var1);

        public int getParentEnd();

        public int getParentStart();
    }

}

