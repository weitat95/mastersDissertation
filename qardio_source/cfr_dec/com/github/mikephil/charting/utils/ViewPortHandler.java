/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.view.View
 */
package com.github.mikephil.charting.utils;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class ViewPortHandler {
    protected Matrix mCenterViewPortMatrixBuffer;
    protected float mChartHeight = 0.0f;
    protected float mChartWidth = 0.0f;
    protected RectF mContentRect;
    protected final Matrix mMatrixTouch = new Matrix();
    private float mMaxScaleX = Float.MAX_VALUE;
    private float mMaxScaleY = Float.MAX_VALUE;
    private float mMinScaleX = 1.0f;
    private float mMinScaleY = 1.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mTransOffsetX = 0.0f;
    private float mTransOffsetY = 0.0f;
    private float mTransX = 0.0f;
    private float mTransY = 0.0f;
    protected final float[] matrixBuffer;
    protected float[] valsBufferForFitScreen;

    public ViewPortHandler() {
        this.mContentRect = new RectF();
        this.valsBufferForFitScreen = new float[9];
        this.mCenterViewPortMatrixBuffer = new Matrix();
        this.matrixBuffer = new float[9];
    }

    public boolean canZoomInMoreX() {
        return this.mScaleX < this.mMaxScaleX;
    }

    public boolean canZoomInMoreY() {
        return this.mScaleY < this.mMaxScaleY;
    }

    public boolean canZoomOutMoreX() {
        return this.mScaleX > this.mMinScaleX;
    }

    public boolean canZoomOutMoreY() {
        return this.mScaleY > this.mMinScaleY;
    }

    public void centerViewPort(float[] arrf, View view) {
        Matrix matrix = this.mCenterViewPortMatrixBuffer;
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        float f = arrf[0];
        float f2 = this.offsetLeft();
        float f3 = arrf[1];
        float f4 = this.offsetTop();
        matrix.postTranslate(-(f - f2), -(f3 - f4));
        this.refresh(matrix, view, true);
    }

    public float contentBottom() {
        return this.mContentRect.bottom;
    }

    public float contentHeight() {
        return this.mContentRect.height();
    }

    public float contentLeft() {
        return this.mContentRect.left;
    }

    public float contentRight() {
        return this.mContentRect.right;
    }

    public float contentTop() {
        return this.mContentRect.top;
    }

    public float contentWidth() {
        return this.mContentRect.width();
    }

    public float getChartHeight() {
        return this.mChartHeight;
    }

    public float getChartWidth() {
        return this.mChartWidth;
    }

    public MPPointF getContentCenter() {
        return MPPointF.getInstance(this.mContentRect.centerX(), this.mContentRect.centerY());
    }

    public RectF getContentRect() {
        return this.mContentRect;
    }

    public Matrix getMatrixTouch() {
        return this.mMatrixTouch;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public float getSmallestContentExtension() {
        return Math.min(this.mContentRect.width(), this.mContentRect.height());
    }

    public boolean hasNoDragOffset() {
        return this.mTransOffsetX <= 0.0f && this.mTransOffsetY <= 0.0f;
    }

    public boolean isFullyZoomedOut() {
        return this.isFullyZoomedOutX() && this.isFullyZoomedOutY();
    }

    public boolean isFullyZoomedOutX() {
        return !(this.mScaleX > this.mMinScaleX) && !(this.mMinScaleX > 1.0f);
    }

    public boolean isFullyZoomedOutY() {
        return !(this.mScaleY > this.mMinScaleY) && !(this.mMinScaleY > 1.0f);
    }

    public boolean isInBounds(float f, float f2) {
        return this.isInBoundsX(f) && this.isInBoundsY(f2);
    }

    public boolean isInBoundsBottom(float f) {
        return this.mContentRect.bottom >= (f = (float)((int)(f * 100.0f)) / 100.0f);
    }

    public boolean isInBoundsLeft(float f) {
        return this.mContentRect.left <= 1.0f + f;
    }

    public boolean isInBoundsRight(float f) {
        return this.mContentRect.right >= (f = (float)((int)(f * 100.0f)) / 100.0f) - 1.0f;
    }

    public boolean isInBoundsTop(float f) {
        return this.mContentRect.top <= f;
    }

    public boolean isInBoundsX(float f) {
        return this.isInBoundsLeft(f) && this.isInBoundsRight(f);
    }

    public boolean isInBoundsY(float f) {
        return this.isInBoundsTop(f) && this.isInBoundsBottom(f);
    }

    public void limitTransAndScale(Matrix matrix, RectF rectF) {
        matrix.getValues(this.matrixBuffer);
        float f = this.matrixBuffer[2];
        float f2 = this.matrixBuffer[0];
        float f3 = this.matrixBuffer[5];
        float f4 = this.matrixBuffer[4];
        this.mScaleX = Math.min(Math.max(this.mMinScaleX, f2), this.mMaxScaleX);
        this.mScaleY = Math.min(Math.max(this.mMinScaleY, f4), this.mMaxScaleY);
        f4 = 0.0f;
        f2 = 0.0f;
        if (rectF != null) {
            f4 = rectF.width();
            f2 = rectF.height();
        }
        this.mTransX = Math.min(Math.max(f, -f4 * (this.mScaleX - 1.0f) - this.mTransOffsetX), this.mTransOffsetX);
        f4 = this.mScaleY;
        this.mTransY = Math.max(Math.min(f3, this.mTransOffsetY + f2 * (f4 - 1.0f)), -this.mTransOffsetY);
        this.matrixBuffer[2] = this.mTransX;
        this.matrixBuffer[0] = this.mScaleX;
        this.matrixBuffer[5] = this.mTransY;
        this.matrixBuffer[4] = this.mScaleY;
        matrix.setValues(this.matrixBuffer);
    }

    public float offsetBottom() {
        return this.mChartHeight - this.mContentRect.bottom;
    }

    public float offsetLeft() {
        return this.mContentRect.left;
    }

    public float offsetRight() {
        return this.mChartWidth - this.mContentRect.right;
    }

    public float offsetTop() {
        return this.mContentRect.top;
    }

    public Matrix refresh(Matrix matrix, View view, boolean bl) {
        this.mMatrixTouch.set(matrix);
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
        if (bl) {
            view.invalidate();
        }
        matrix.set(this.mMatrixTouch);
        return matrix;
    }

    public void restrainViewPort(float f, float f2, float f3, float f4) {
        this.mContentRect.set(f, f2, this.mChartWidth - f3, this.mChartHeight - f4);
    }

    public void setChartDimens(float f, float f2) {
        float f3 = this.offsetLeft();
        float f4 = this.offsetTop();
        float f5 = this.offsetRight();
        float f6 = this.offsetBottom();
        this.mChartHeight = f2;
        this.mChartWidth = f;
        this.restrainViewPort(f3, f4, f5, f6);
    }

    public void setDragOffsetX(float f) {
        this.mTransOffsetX = Utils.convertDpToPixel(f);
    }

    public void setDragOffsetY(float f) {
        this.mTransOffsetY = Utils.convertDpToPixel(f);
    }

    public void setMaximumScaleX(float f) {
        float f2 = f;
        if (f == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        this.mMaxScaleX = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMaximumScaleY(float f) {
        float f2 = f;
        if (f == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        this.mMaxScaleY = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinMaxScaleX(float f, float f2) {
        float f3 = f;
        if (f < 1.0f) {
            f3 = 1.0f;
        }
        f = f2;
        if (f2 == 0.0f) {
            f = Float.MAX_VALUE;
        }
        this.mMinScaleX = f3;
        this.mMaxScaleX = f;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinMaxScaleY(float f, float f2) {
        float f3 = f;
        if (f < 1.0f) {
            f3 = 1.0f;
        }
        f = f2;
        if (f2 == 0.0f) {
            f = Float.MAX_VALUE;
        }
        this.mMinScaleY = f3;
        this.mMaxScaleY = f;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinimumScaleX(float f) {
        float f2 = f;
        if (f < 1.0f) {
            f2 = 1.0f;
        }
        this.mMinScaleX = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinimumScaleY(float f) {
        float f2 = f;
        if (f < 1.0f) {
            f2 = 1.0f;
        }
        this.mMinScaleY = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void zoom(float f, float f2, float f3, float f4, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.postScale(f, f2, f3, f4);
    }
}

