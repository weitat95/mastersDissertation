/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.drawee.generic;

import com.facebook.common.internal.Preconditions;
import java.util.Arrays;

public class RoundingParams {
    private int mBorderColor = 0;
    private float mBorderWidth = 0.0f;
    private float[] mCornersRadii = null;
    private int mOverlayColor = 0;
    private float mPadding = 0.0f;
    private boolean mRoundAsCircle = false;
    private RoundingMethod mRoundingMethod = RoundingMethod.BITMAP_ONLY;

    private float[] getOrCreateRoundedCornersRadii() {
        if (this.mCornersRadii == null) {
            this.mCornersRadii = new float[8];
        }
        return this.mCornersRadii;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) {
            return true;
        }
        boolean bl2 = bl;
        if (object == null) return bl2;
        bl2 = bl;
        if (this.getClass() != object.getClass()) return bl2;
        object = (RoundingParams)object;
        bl2 = bl;
        if (this.mRoundAsCircle != ((RoundingParams)object).mRoundAsCircle) return bl2;
        bl2 = bl;
        if (this.mOverlayColor != ((RoundingParams)object).mOverlayColor) return bl2;
        bl2 = bl;
        if (Float.compare(((RoundingParams)object).mBorderWidth, this.mBorderWidth) != 0) return bl2;
        bl2 = bl;
        if (this.mBorderColor != ((RoundingParams)object).mBorderColor) return bl2;
        bl2 = bl;
        if (Float.compare(((RoundingParams)object).mPadding, this.mPadding) != 0) return bl2;
        bl2 = bl;
        if (this.mRoundingMethod != ((RoundingParams)object).mRoundingMethod) return bl2;
        return Arrays.equals(this.mCornersRadii, ((RoundingParams)object).mCornersRadii);
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public float[] getCornersRadii() {
        return this.mCornersRadii;
    }

    public int getOverlayColor() {
        return this.mOverlayColor;
    }

    public float getPadding() {
        return this.mPadding;
    }

    public boolean getRoundAsCircle() {
        return this.mRoundAsCircle;
    }

    public RoundingMethod getRoundingMethod() {
        return this.mRoundingMethod;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.mRoundingMethod != null ? this.mRoundingMethod.hashCode() : 0;
        int n3 = this.mRoundAsCircle ? 1 : 0;
        int n4 = this.mCornersRadii != null ? Arrays.hashCode(this.mCornersRadii) : 0;
        int n5 = this.mOverlayColor;
        int n6 = this.mBorderWidth != 0.0f ? Float.floatToIntBits(this.mBorderWidth) : 0;
        int n7 = this.mBorderColor;
        if (this.mPadding != 0.0f) {
            n = Float.floatToIntBits(this.mPadding);
        }
        return (((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n;
    }

    public RoundingParams setBorderColor(int n) {
        this.mBorderColor = n;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public RoundingParams setBorderWidth(float f) {
        boolean bl = f >= 0.0f;
        Preconditions.checkArgument(bl, "the border width cannot be < 0");
        this.mBorderWidth = f;
        return this;
    }

    public RoundingParams setCornersRadii(float f, float f2, float f3, float f4) {
        float[] arrf = this.getOrCreateRoundedCornersRadii();
        arrf[1] = f;
        arrf[0] = f;
        arrf[3] = f2;
        arrf[2] = f2;
        arrf[5] = f3;
        arrf[4] = f3;
        arrf[7] = f4;
        arrf[6] = f4;
        return this;
    }

    public RoundingParams setOverlayColor(int n) {
        this.mOverlayColor = n;
        this.mRoundingMethod = RoundingMethod.OVERLAY_COLOR;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public RoundingParams setPadding(float f) {
        boolean bl = f >= 0.0f;
        Preconditions.checkArgument(bl, "the padding cannot be < 0");
        this.mPadding = f;
        return this;
    }

    public RoundingParams setRoundAsCircle(boolean bl) {
        this.mRoundAsCircle = bl;
        return this;
    }

    public static enum RoundingMethod {
        OVERLAY_COLOR,
        BITMAP_ONLY;

    }

}

