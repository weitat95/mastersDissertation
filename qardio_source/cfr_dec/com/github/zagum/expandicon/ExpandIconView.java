/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ArgbEvaluator
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Point
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 */
package com.github.zagum.expandicon;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.github.zagum.expandicon.R;

public class ExpandIconView
extends View {
    private float alpha = -45.0f;
    private final float animationSpeed;
    private ValueAnimator arrowAnimator;
    private final Point center;
    private float centerTranslation = 0.0f;
    private int color = -16777216;
    private final int colorLess;
    private final int colorMore;
    private float fraction = 0.0f;
    private final Point left = new Point();
    private int padding;
    private final Paint paint;
    private final Path path;
    private final Point right = new Point();
    private int state;
    private boolean switchColor = false;
    private final Point tempLeft;
    private final Point tempRight;
    private final boolean useDefaultPadding;

    public ExpandIconView(Context context) {
        this(context, null);
    }

    public ExpandIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ExpandIconView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        boolean bl;
        long l;
        this.center = new Point();
        this.tempLeft = new Point();
        this.tempRight = new Point();
        this.path = new Path();
        context = this.getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.ExpandIconView, 0, 0);
        try {
            bl = context.getBoolean(R.styleable.ExpandIconView_eiv_roundedCorners, false);
            this.switchColor = context.getBoolean(R.styleable.ExpandIconView_eiv_switchColor, false);
            this.color = context.getColor(R.styleable.ExpandIconView_eiv_color, -16777216);
            this.colorMore = context.getColor(R.styleable.ExpandIconView_eiv_colorMore, -16777216);
            this.colorLess = context.getColor(R.styleable.ExpandIconView_eiv_colorLess, -16777216);
            l = context.getInteger(R.styleable.ExpandIconView_eiv_animationDuration, 150);
            this.padding = context.getDimensionPixelSize(R.styleable.ExpandIconView_eiv_padding, -1);
            boolean bl2 = this.padding == -1;
            this.useDefaultPadding = bl2;
        }
        finally {
            context.recycle();
        }
        this.paint = new Paint(1);
        this.paint.setColor(this.color);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setDither(true);
        if (bl) {
            this.paint.setStrokeJoin(Paint.Join.ROUND);
            this.paint.setStrokeCap(Paint.Cap.ROUND);
        }
        this.animationSpeed = 90.0f / (float)l;
        this.setState(0, false);
    }

    private void animateArrow(float f) {
        this.cancelAnimation();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.alpha, f});
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            private final ArgbEvaluator colorEvaluator = new ArgbEvaluator();

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ExpandIconView.this.alpha = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                ExpandIconView.this.updateArrowPath();
                if (ExpandIconView.this.switchColor) {
                    ExpandIconView.this.updateColor(this.colorEvaluator);
                }
                ExpandIconView.this.postInvalidateOnAnimationCompat();
            }
        });
        valueAnimator.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
        valueAnimator.setDuration(this.calculateAnimationDuration(f));
        valueAnimator.start();
        this.arrowAnimator = valueAnimator;
    }

    private long calculateAnimationDuration(float f) {
        return (long)(Math.abs(f - this.alpha) / this.animationSpeed);
    }

    private void calculateArrowMetrics() {
        int n;
        int n2 = this.getMeasuredWidth();
        int n3 = this.getMeasuredHeight();
        int n4 = n3 - this.padding * 2;
        if (n4 >= (n = n2 - this.padding * 2)) {
            n4 = n;
        }
        if (this.useDefaultPadding) {
            this.padding = (int)(0.16666667f * (float)n2);
        }
        float f = (int)((float)n4 * 0.1388889f);
        this.paint.setStrokeWidth(f);
        this.center.set(n2 / 2, n3 / 2);
        this.left.set(this.center.x - n4 / 2, this.center.y);
        this.right.set(this.center.x + n4 / 2, this.center.y);
    }

    private void cancelAnimation() {
        if (this.arrowAnimator != null && this.arrowAnimator.isRunning()) {
            this.arrowAnimator.cancel();
        }
    }

    private int getFinalStateByFraction() {
        if (this.fraction <= 0.5f) {
            return 0;
        }
        return 1;
    }

    private void postInvalidateOnAnimationCompat() {
        if (Build.VERSION.SDK_INT > 15) {
            this.postInvalidateOnAnimation();
            return;
        }
        this.postInvalidateDelayed(10L);
    }

    private void rotate(Point point, double d, Point point2) {
        d = Math.toRadians(d);
        point2.set((int)((double)this.center.x + (double)(point.x - this.center.x) * Math.cos(d) - (double)(point.y - this.center.y) * Math.sin(d)), (int)((double)this.center.y + (double)(point.x - this.center.x) * Math.sin(d) + (double)(point.y - this.center.y) * Math.cos(d)));
    }

    private void updateArrow(boolean bl) {
        float f = -45.0f + this.fraction * 90.0f;
        if (bl) {
            this.animateArrow(f);
            return;
        }
        this.cancelAnimation();
        this.alpha = f;
        if (this.switchColor) {
            this.updateColor(new ArgbEvaluator());
        }
        this.updateArrowPath();
        this.invalidate();
    }

    private void updateArrowPath() {
        this.path.reset();
        if (this.left != null && this.right != null) {
            this.rotate(this.left, -this.alpha, this.tempLeft);
            this.rotate(this.right, this.alpha, this.tempRight);
            this.centerTranslation = (this.center.y - this.tempLeft.y) / 2;
            this.path.moveTo((float)this.tempLeft.x, (float)this.tempLeft.y);
            this.path.lineTo((float)this.center.x, (float)this.center.y);
            this.path.lineTo((float)this.tempRight.x, (float)this.tempRight.y);
        }
    }

    private void updateColor(ArgbEvaluator argbEvaluator) {
        this.color = (Integer)argbEvaluator.evaluate((this.alpha + 45.0f) / 90.0f, (Object)this.colorMore, (Object)this.colorLess);
        this.paint.setColor(this.color);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0.0f, this.centerTranslation);
        canvas.drawPath(this.path, this.paint);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.calculateArrowMetrics();
        this.updateArrowPath();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFraction(float f, boolean bl) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Fraction value must be from 0 to 1f, fraction=" + f);
        }
        if (this.fraction == f) {
            return;
        }
        this.fraction = f;
        this.state = f == 0.0f ? 0 : (f == 1.0f ? 1 : 2);
        this.updateArrow(bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setState(int n, boolean bl) {
        this.state = n;
        if (n == 0) {
            this.fraction = 0.0f;
        } else {
            if (n != 1) {
                throw new IllegalArgumentException("Unknown state, must be one of STATE_MORE = 0,  STATE_LESS = 1");
            }
            this.fraction = 1.0f;
        }
        this.updateArrow(bl);
    }

}

