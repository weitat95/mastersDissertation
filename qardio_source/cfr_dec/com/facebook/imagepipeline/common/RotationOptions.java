/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.common;

import com.facebook.common.util.HashCodeUtil;
import java.util.Locale;

public class RotationOptions {
    private final boolean mDeferUntilRendered;
    private final int mRotation;

    private RotationOptions(int n, boolean bl) {
        this.mRotation = n;
        this.mDeferUntilRendered = bl;
    }

    public static RotationOptions autoRotate() {
        return new RotationOptions(-1, false);
    }

    public static RotationOptions autoRotateAtRenderTime() {
        return new RotationOptions(-1, true);
    }

    public boolean canDeferUntilRendered() {
        return this.mDeferUntilRendered;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof RotationOptions)) {
                    return false;
                }
                object = (RotationOptions)object;
                if (this.mRotation != ((RotationOptions)object).mRotation || this.mDeferUntilRendered != ((RotationOptions)object).mDeferUntilRendered) break block5;
            }
            return true;
        }
        return false;
    }

    public int getForcedAngle() {
        if (this.useImageMetadata()) {
            throw new IllegalStateException("Rotation is set to use EXIF");
        }
        return this.mRotation;
    }

    public int hashCode() {
        return HashCodeUtil.hashCode((Object)this.mRotation, this.mDeferUntilRendered);
    }

    public String toString() {
        return String.format((Locale)null, "%d defer:%b", this.mRotation, this.mDeferUntilRendered);
    }

    public boolean useImageMetadata() {
        return this.mRotation == -1;
    }
}

