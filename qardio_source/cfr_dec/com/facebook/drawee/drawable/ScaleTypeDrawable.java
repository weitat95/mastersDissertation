/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.ScalingUtils;

public class ScaleTypeDrawable
extends ForwardingDrawable {
    Matrix mDrawMatrix;
    PointF mFocusPoint = null;
    ScalingUtils.ScaleType mScaleType;
    Object mScaleTypeState;
    private Matrix mTempMatrix = new Matrix();
    int mUnderlyingHeight = 0;
    int mUnderlyingWidth = 0;

    public ScaleTypeDrawable(Drawable drawable2, ScalingUtils.ScaleType scaleType) {
        super(Preconditions.checkNotNull(drawable2));
        this.mScaleType = scaleType;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void configureBoundsIfUnderlyingChanged() {
        boolean bl;
        boolean bl2 = false;
        if (this.mScaleType instanceof ScalingUtils.StatefulScaleType) {
            Object object = ((ScalingUtils.StatefulScaleType)((Object)this.mScaleType)).getState();
            bl2 = object == null || !object.equals(this.mScaleTypeState);
            this.mScaleTypeState = object;
        }
        if ((bl = this.mUnderlyingWidth != this.getCurrent().getIntrinsicWidth() || this.mUnderlyingHeight != this.getCurrent().getIntrinsicHeight()) || bl2) {
            this.configureBounds();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void configureBounds() {
        int n;
        int n2;
        float f = 0.5f;
        Object object = this.getCurrent();
        Rect rect = this.getBounds();
        int n3 = rect.width();
        int n4 = rect.height();
        this.mUnderlyingWidth = n = object.getIntrinsicWidth();
        this.mUnderlyingHeight = n2 = object.getIntrinsicHeight();
        if (n <= 0 || n2 <= 0) {
            object.setBounds(rect);
            this.mDrawMatrix = null;
            return;
        }
        if (n == n3 && n2 == n4) {
            object.setBounds(rect);
            this.mDrawMatrix = null;
            return;
        }
        if (this.mScaleType == ScalingUtils.ScaleType.FIT_XY) {
            object.setBounds(rect);
            this.mDrawMatrix = null;
            return;
        }
        object.setBounds(0, 0, n, n2);
        object = this.mScaleType;
        Matrix matrix = this.mTempMatrix;
        float f2 = this.mFocusPoint != null ? this.mFocusPoint.x : 0.5f;
        if (this.mFocusPoint != null) {
            f = this.mFocusPoint.y;
        }
        object.getTransform(matrix, rect, n, n2, f2, f);
        this.mDrawMatrix = this.mTempMatrix;
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
        this.getParentTransform(matrix);
        this.configureBoundsIfUnderlyingChanged();
        if (this.mDrawMatrix != null) {
            matrix.preConcat(this.mDrawMatrix);
        }
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        this.configureBounds();
    }

    @Override
    public Drawable setCurrent(Drawable drawable2) {
        drawable2 = super.setCurrent(drawable2);
        this.configureBounds();
        return drawable2;
    }

    public void setFocusPoint(PointF pointF) {
        if (Objects.equal((Object)this.mFocusPoint, (Object)pointF)) {
            return;
        }
        if (this.mFocusPoint == null) {
            this.mFocusPoint = new PointF();
        }
        this.mFocusPoint.set(pointF);
        this.configureBounds();
        this.invalidateSelf();
    }
}

