/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.drawable;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.DrawableProperties;
import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.TransformAwareDrawable;
import com.facebook.drawee.drawable.TransformCallback;
import javax.annotation.Nullable;

public class ArrayDrawable
extends Drawable
implements Drawable.Callback,
TransformAwareDrawable,
TransformCallback {
    private final DrawableParent[] mDrawableParents;
    private final DrawableProperties mDrawableProperties = new DrawableProperties();
    private boolean mIsMutated = false;
    private boolean mIsStateful = false;
    private boolean mIsStatefulCalculated = false;
    private final Drawable[] mLayers;
    private final Rect mTmpRect = new Rect();
    private TransformCallback mTransformCallback;

    public ArrayDrawable(Drawable[] arrdrawable) {
        Preconditions.checkNotNull(arrdrawable);
        this.mLayers = arrdrawable;
        for (int i = 0; i < this.mLayers.length; ++i) {
            DrawableUtils.setCallbacks(this.mLayers[i], this, this);
        }
        this.mDrawableParents = new DrawableParent[this.mLayers.length];
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.draw(canvas);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Nullable
    public Drawable getDrawable(int n) {
        boolean bl = true;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2);
        bl2 = n < this.mLayers.length ? bl : false;
        Preconditions.checkArgument(bl2);
        return this.mLayers[n];
    }

    public int getIntrinsicHeight() {
        int n = -1;
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            int n2 = n;
            if (drawable2 != null) {
                n2 = Math.max(n, drawable2.getIntrinsicHeight());
            }
            n = n2;
        }
        if (n > 0) {
            return n;
        }
        return -1;
    }

    public int getIntrinsicWidth() {
        int n = -1;
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            int n2 = n;
            if (drawable2 != null) {
                n2 = Math.max(n, drawable2.getIntrinsicWidth());
            }
            n = n2;
        }
        if (n > 0) {
            return n;
        }
        return -1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getOpacity() {
        if (this.mLayers.length == 0) {
            return -2;
        }
        int n = -1;
        int n2 = 1;
        do {
            int n3 = n;
            if (n2 >= this.mLayers.length) return n3;
            Drawable drawable2 = this.mLayers[n2];
            n3 = n;
            if (drawable2 != null) {
                n3 = Drawable.resolveOpacity((int)n, (int)drawable2.getOpacity());
            }
            ++n2;
            n = n3;
        } while (true);
    }

    public boolean getPadding(Rect rect) {
        rect.left = 0;
        rect.top = 0;
        rect.right = 0;
        rect.bottom = 0;
        Rect rect2 = this.mTmpRect;
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.getPadding(rect2);
            rect.left = Math.max(rect.left, rect2.left);
            rect.top = Math.max(rect.top, rect2.top);
            rect.right = Math.max(rect.right, rect2.right);
            rect.bottom = Math.max(rect.bottom, rect2.bottom);
        }
        return true;
    }

    @Override
    public void getRootBounds(RectF rectF) {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getRootBounds(rectF);
            return;
        }
        rectF.set(this.getBounds());
    }

    @Override
    public void getTransform(Matrix matrix) {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getTransform(matrix);
            return;
        }
        matrix.reset();
    }

    public void invalidateDrawable(Drawable drawable2) {
        this.invalidateSelf();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isStateful() {
        if (!this.mIsStatefulCalculated) {
            this.mIsStateful = false;
            for (int i = 0; i < this.mLayers.length; ++i) {
                Drawable drawable2 = this.mLayers[i];
                boolean bl = this.mIsStateful;
                boolean bl2 = drawable2 != null && drawable2.isStateful();
                this.mIsStateful = bl2 | bl;
            }
            this.mIsStatefulCalculated = true;
        }
        return this.mIsStateful;
    }

    public Drawable mutate() {
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.mutate();
        }
        this.mIsMutated = true;
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setBounds(rect);
        }
    }

    protected boolean onLevelChange(int n) {
        boolean bl = false;
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            boolean bl2 = bl;
            if (drawable2 != null) {
                bl2 = bl;
                if (drawable2.setLevel(n)) {
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        return bl;
    }

    protected boolean onStateChange(int[] arrn) {
        boolean bl = false;
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            boolean bl2 = bl;
            if (drawable2 != null) {
                bl2 = bl;
                if (drawable2.setState(arrn)) {
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        return bl;
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    public void setAlpha(int n) {
        this.mDrawableProperties.setAlpha(n);
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setAlpha(n);
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawableProperties.setColorFilter(colorFilter);
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setColorFilter(colorFilter);
        }
    }

    public void setDither(boolean bl) {
        this.mDrawableProperties.setDither(bl);
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setDither(bl);
        }
    }

    public void setFilterBitmap(boolean bl) {
        this.mDrawableProperties.setFilterBitmap(bl);
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setFilterBitmap(bl);
        }
    }

    @TargetApi(value=21)
    public void setHotspot(float f, float f2) {
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    public void setTransformCallback(TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        for (int i = 0; i < this.mLayers.length; ++i) {
            Drawable drawable2 = this.mLayers[i];
            if (drawable2 == null) continue;
            drawable2.setVisible(bl, bl2);
        }
        return bl3;
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }
}

