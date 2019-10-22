/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ForwardingDrawable;

public class MatrixDrawable
extends ForwardingDrawable {
    private Matrix mDrawMatrix;
    private Matrix mMatrix;
    private int mUnderlyingHeight = 0;
    private int mUnderlyingWidth = 0;

    public MatrixDrawable(Drawable drawable2, Matrix matrix) {
        super(Preconditions.checkNotNull(drawable2));
        this.mMatrix = matrix;
    }

    private void configureBounds() {
        int n;
        int n2;
        Drawable drawable2 = this.getCurrent();
        Rect rect = this.getBounds();
        this.mUnderlyingWidth = n2 = drawable2.getIntrinsicWidth();
        this.mUnderlyingHeight = n = drawable2.getIntrinsicHeight();
        if (n2 <= 0 || n <= 0) {
            drawable2.setBounds(rect);
            this.mDrawMatrix = null;
            return;
        }
        drawable2.setBounds(0, 0, n2, n);
        this.mDrawMatrix = this.mMatrix;
    }

    private void configureBoundsIfUnderlyingChanged() {
        if (this.mUnderlyingWidth != this.getCurrent().getIntrinsicWidth() || this.mUnderlyingHeight != this.getCurrent().getIntrinsicHeight()) {
            this.configureBounds();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        this.configureBoundsIfUnderlyingChanged();
        if (this.mDrawMatrix != null) {
            int n = canvas.save();
            canvas.clipRect(this.getBounds());
            canvas.concat(this.mDrawMatrix);
            super.draw(canvas);
            canvas.restoreToCount(n);
            return;
        }
        super.draw(canvas);
    }

    @Override
    public void getTransform(Matrix matrix) {
        super.getTransform(matrix);
        if (this.mDrawMatrix != null) {
            matrix.preConcat(this.mDrawMatrix);
        }
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.configureBounds();
    }

    @Override
    public Drawable setCurrent(Drawable drawable2) {
        drawable2 = super.setCurrent(drawable2);
        this.configureBounds();
        return drawable2;
    }
}

