/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.ColorFilter
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.drawable;

import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class DrawableProperties {
    private int mAlpha = -1;
    private ColorFilter mColorFilter = null;
    private int mDither = -1;
    private int mFilterBitmap = -1;
    private boolean mIsSetColorFilter = false;

    /*
     * Enabled aggressive block sorting
     */
    public void applyTo(Drawable drawable2) {
        boolean bl;
        boolean bl2;
        block7: {
            block6: {
                bl2 = true;
                if (drawable2 == null) break block6;
                if (this.mAlpha != -1) {
                    drawable2.setAlpha(this.mAlpha);
                }
                if (this.mIsSetColorFilter) {
                    drawable2.setColorFilter(this.mColorFilter);
                }
                if (this.mDither != -1) {
                    bl = this.mDither != 0;
                    drawable2.setDither(bl);
                }
                if (this.mFilterBitmap != -1) break block7;
            }
            return;
        }
        bl = this.mFilterBitmap != 0 ? bl2 : false;
        drawable2.setFilterBitmap(bl);
    }

    public void setAlpha(int n) {
        this.mAlpha = n;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mIsSetColorFilter = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDither(boolean bl) {
        int n = bl ? 1 : 0;
        this.mDither = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFilterBitmap(boolean bl) {
        int n = bl ? 1 : 0;
        this.mFilterBitmap = n;
    }
}

