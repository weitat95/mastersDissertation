/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.animation.LinearInterpolator
 */
package com.getqardio.android.ui.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.getqardio.android.R;

public class AnimatedCheckView
extends View {
    private int animDuration;
    private int circleColor;
    private Paint circlePaint;
    private float currentProgress;
    private int cx;
    private int cy;
    private int lineColor;
    private Paint linePaint;
    private int lineWidth;
    private float[] points = new float[6];
    private int radius;

    public AnimatedCheckView(Context context) {
        this(context, null);
    }

    public AnimatedCheckView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnimatedCheckView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.AnimatedCheckView, n, 0);
        this.circleColor = context.getColor(1, -16711936);
        this.lineColor = context.getColor(2, -1);
        this.lineWidth = context.getDimensionPixelSize(3, 4);
        this.animDuration = context.getInteger(4, 150);
        this.radius = context.getDimensionPixelSize(0, 65);
        context.recycle();
        this.init();
    }

    private void init() {
        this.circlePaint = new Paint(1);
        this.circlePaint.setStyle(Paint.Style.FILL);
        this.circlePaint.setColor(this.circleColor);
        this.linePaint = new Paint(1);
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setColor(this.lineColor);
        this.linePaint.setStrokeWidth((float)this.lineWidth);
    }

    private void showCheck() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f}).setDuration((long)this.animDuration);
        valueAnimator.setInterpolator((TimeInterpolator)new LinearInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimatedCheckView.this.currentProgress = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                AnimatedCheckView.this.invalidate();
            }
        });
    }

    protected void onDraw(Canvas canvas) {
        block3: {
            block2: {
                this.circlePaint.setColor(this.circleColor);
                canvas.drawCircle((float)this.cx, (float)this.cy, (float)this.radius, this.circlePaint);
                if (!(this.currentProgress > 0.0f)) break block2;
                if (!(this.currentProgress < 0.33333334f)) break block3;
                float f = this.points[0];
                float f2 = this.points[2];
                float f3 = this.points[0];
                float f4 = this.currentProgress;
                float f5 = this.points[1];
                float f6 = this.points[3];
                float f7 = this.points[1];
                float f8 = this.currentProgress;
                canvas.drawLine(this.points[0], this.points[1], f + (f2 - f3) * f4, f5 + (f6 - f7) * f8, this.linePaint);
            }
            return;
        }
        float f = this.points[2];
        float f9 = this.points[4];
        float f10 = this.points[2];
        float f11 = this.currentProgress;
        float f12 = this.points[3];
        float f13 = this.points[5];
        float f14 = this.points[3];
        float f15 = this.currentProgress;
        canvas.drawLine(this.points[0], this.points[1], this.points[2], this.points[3], this.linePaint);
        canvas.drawLine(this.points[2], this.points[3], f + (f9 - f10) * f11, f12 + (f13 - f14) * f15, this.linePaint);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        n3 = Math.min(n - this.getPaddingLeft() - this.getPaddingRight(), n2 - this.getPaddingBottom() - this.getPaddingTop());
        this.cx = n / 2;
        this.cy = n2 / 2;
        float f = (float)n3 / 2.0f;
        this.points[0] = f / 2.0f + (float)this.getPaddingLeft();
        this.points[1] = (float)this.getPaddingTop() + f;
        this.points[2] = 5.0f * f / 6.0f + (float)this.getPaddingLeft();
        this.points[3] = f / 3.0f + f + (float)this.getPaddingTop();
        this.points[4] = 1.5f * f + (float)this.getPaddingLeft();
        this.points[5] = f - f / 3.0f + (float)this.getPaddingTop();
        this.radius = 65;
    }

    public void setChecked() {
        this.showCheck();
    }

}

