/*
 * Decompiled with CFR 0.147.
 */
package android.support.v7.widget;

class RtlSpacingHelper {
    private int mEnd = Integer.MIN_VALUE;
    private int mExplicitLeft = 0;
    private int mExplicitRight = 0;
    private boolean mIsRelative = false;
    private boolean mIsRtl = false;
    private int mLeft = 0;
    private int mRight = 0;
    private int mStart = Integer.MIN_VALUE;

    RtlSpacingHelper() {
    }

    public int getEnd() {
        if (this.mIsRtl) {
            return this.mLeft;
        }
        return this.mRight;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getStart() {
        if (this.mIsRtl) {
            return this.mRight;
        }
        return this.mLeft;
    }

    public void setAbsolute(int n, int n2) {
        this.mIsRelative = false;
        if (n != Integer.MIN_VALUE) {
            this.mExplicitLeft = n;
            this.mLeft = n;
        }
        if (n2 != Integer.MIN_VALUE) {
            this.mExplicitRight = n2;
            this.mRight = n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDirection(boolean bl) {
        if (bl == this.mIsRtl) {
            return;
        }
        this.mIsRtl = bl;
        if (!this.mIsRelative) {
            this.mLeft = this.mExplicitLeft;
            this.mRight = this.mExplicitRight;
            return;
        }
        if (bl) {
            int n = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitLeft;
            this.mLeft = n;
            n = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitRight;
            this.mRight = n;
            return;
        }
        int n = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitLeft;
        this.mLeft = n;
        n = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitRight;
        this.mRight = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setRelative(int n, int n2) {
        this.mStart = n;
        this.mEnd = n2;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if (n2 != Integer.MIN_VALUE) {
                this.mLeft = n2;
            }
            if (n == Integer.MIN_VALUE) return;
            {
                this.mRight = n;
                return;
            }
        } else {
            if (n != Integer.MIN_VALUE) {
                this.mLeft = n;
            }
            if (n2 == Integer.MIN_VALUE) return;
            {
                this.mRight = n2;
                return;
            }
        }
    }
}

