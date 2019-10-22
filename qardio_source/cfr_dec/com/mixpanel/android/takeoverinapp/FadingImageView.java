/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.LinearGradient
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.Xfermode
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.ImageView
 */
package com.mixpanel.android.takeoverinapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class FadingImageView
extends ImageView {
    private Paint mAlphaGradientPaint;
    private Shader mAlphaGradientShader;
    private Paint mDarkenGradientPaint;
    private Shader mDarkenGradientShader;
    private Matrix mGradientMatrix;
    private int mHeight;
    private boolean mShouldShowShadow;
    private int mWidth;

    public FadingImageView(Context context) {
        super(context);
        this.initFadingImageView();
    }

    public FadingImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initFadingImageView();
    }

    public FadingImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initFadingImageView();
    }

    private void initFadingImageView() {
        this.mGradientMatrix = new Matrix();
        this.mAlphaGradientPaint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.mAlphaGradientShader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, new int[]{-16777216, -16777216, -452984832, 0}, new float[]{0.0f, 0.2f, 0.4f, 1.0f}, tileMode);
        this.mAlphaGradientPaint.setShader(this.mAlphaGradientShader);
        this.mAlphaGradientPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.mDarkenGradientPaint = new Paint();
        tileMode = Shader.TileMode.CLAMP;
        this.mDarkenGradientShader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, new int[]{0, 0, -16777216, -16777216}, new float[]{0.0f, 0.85f, 0.98f, 1.0f}, tileMode);
        this.mDarkenGradientPaint.setShader(this.mDarkenGradientShader);
        this.mAlphaGradientPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public void draw(Canvas canvas) {
        if (this.mShouldShowShadow) {
            Rect rect = canvas.getClipBounds();
            int n = canvas.saveLayer(0.0f, 0.0f, (float)rect.width(), (float)rect.height(), null, 31);
            super.draw(canvas);
            canvas.drawRect(0.0f, 0.0f, (float)this.mWidth, (float)this.mHeight, this.mAlphaGradientPaint);
            canvas.restoreToCount(n);
            return;
        }
        super.draw(canvas);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mShouldShowShadow) {
            this.mHeight = this.getHeight();
            this.mWidth = this.getWidth();
            n = View.MeasureSpec.getSize((int)n2);
            this.mGradientMatrix.setScale(1.0f, (float)n);
            this.mAlphaGradientShader.setLocalMatrix(this.mGradientMatrix);
            this.mDarkenGradientShader.setLocalMatrix(this.mGradientMatrix);
        }
    }

    public void showShadow(boolean bl) {
        this.mShouldShowShadow = bl;
    }
}

