/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.common;

import com.facebook.common.util.HashCodeUtil;
import java.util.Locale;

public class ResizeOptions {
    public final int height;
    public final float maxBitmapSize;
    public final float roundUpFraction;
    public final int width;

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof ResizeOptions)) {
                    return false;
                }
                object = (ResizeOptions)object;
                if (this.width != ((ResizeOptions)object).width || this.height != ((ResizeOptions)object).height) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return HashCodeUtil.hashCode(this.width, this.height);
    }

    public String toString() {
        return String.format((Locale)null, "%dx%d", this.width, this.height);
    }
}

