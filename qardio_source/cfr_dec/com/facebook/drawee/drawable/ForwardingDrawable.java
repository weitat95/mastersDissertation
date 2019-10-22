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
 */
package com.facebook.drawee.drawable;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.DrawableParent;
import com.facebook.drawee.drawable.DrawableProperties;
import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.TransformAwareDrawable;
import com.facebook.drawee.drawable.TransformCallback;

public class ForwardingDrawable
extends Drawable
implements Drawable.Callback,
DrawableParent,
TransformAwareDrawable,
TransformCallback {
    private static final Matrix sTempTransform = new Matrix();
    private Drawable mCurrentDelegate;
    private final DrawableProperties mDrawableProperties = new DrawableProperties();
    protected TransformCallback mTransformCallback;

    public ForwardingDrawable(Drawable drawable2) {
        this.mCurrentDelegate = drawable2;
        DrawableUtils.setCallbacks(this.mCurrentDelegate, this, this);
    }

    public void draw(Canvas canvas) {
        this.mCurrentDelegate.draw(canvas);
    }

    public Drawable getCurrent() {
        return this.mCurrentDelegate;
    }

    @Override
    public Drawable getDrawable() {
        return this.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.mCurrentDelegate.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.mCurrentDelegate.getIntrinsicWidth();
    }

    public int getOpacity() {
        return this.mCurrentDelegate.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.mCurrentDelegate.getPadding(rect);
    }

    protected void getParentTransform(Matrix matrix) {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getTransform(matrix);
            return;
        }
        matrix.reset();
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
        this.getParentTransform(matrix);
    }

    public void invalidateDrawable(Drawable drawable2) {
        this.invalidateSelf();
    }

    public boolean isStateful() {
        return this.mCurrentDelegate.isStateful();
    }

    public Drawable mutate() {
        this.mCurrentDelegate.mutate();
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        this.mCurrentDelegate.setBounds(rect);
    }

    protected boolean onLevelChange(int n) {
        return this.mCurrentDelegate.setLevel(n);
    }

    protected boolean onStateChange(int[] arrn) {
        return this.mCurrentDelegate.setState(arrn);
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    public void setAlpha(int n) {
        this.mDrawableProperties.setAlpha(n);
        this.mCurrentDelegate.setAlpha(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawableProperties.setColorFilter(colorFilter);
        this.mCurrentDelegate.setColorFilter(colorFilter);
    }

    public Drawable setCurrent(Drawable drawable2) {
        drawable2 = this.setCurrentWithoutInvalidate(drawable2);
        this.invalidateSelf();
        return drawable2;
    }

    protected Drawable setCurrentWithoutInvalidate(Drawable drawable2) {
        Drawable drawable3 = this.mCurrentDelegate;
        DrawableUtils.setCallbacks(drawable3, null, null);
        DrawableUtils.setCallbacks(drawable2, null, null);
        DrawableUtils.setDrawableProperties(drawable2, this.mDrawableProperties);
        DrawableUtils.copyProperties(drawable2, this);
        DrawableUtils.setCallbacks(drawable2, this, this);
        this.mCurrentDelegate = drawable2;
        return drawable3;
    }

    public void setDither(boolean bl) {
        this.mDrawableProperties.setDither(bl);
        this.mCurrentDelegate.setDither(bl);
    }

    @Override
    public Drawable setDrawable(Drawable drawable2) {
        return this.setCurrent(drawable2);
    }

    public void setFilterBitmap(boolean bl) {
        this.mDrawableProperties.setFilterBitmap(bl);
        this.mCurrentDelegate.setFilterBitmap(bl);
    }

    @TargetApi(value=21)
    public void setHotspot(float f, float f2) {
        this.mCurrentDelegate.setHotspot(f, f2);
    }

    @Override
    public void setTransformCallback(TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        super.setVisible(bl, bl2);
        return this.mCurrentDelegate.setVisible(bl, bl2);
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        this.unscheduleSelf(runnable);
    }
}

