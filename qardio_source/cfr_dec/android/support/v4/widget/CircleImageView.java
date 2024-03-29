/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RadialGradient
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.ImageView
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

class CircleImageView
extends ImageView {
    private Animation.AnimationListener mListener;
    int mShadowRadius;

    /*
     * Enabled aggressive block sorting
     */
    CircleImageView(Context context, int n) {
        super(context);
        float f = this.getContext().getResources().getDisplayMetrics().density;
        int n2 = (int)(1.75f * f);
        int n3 = (int)(0.0f * f);
        this.mShadowRadius = (int)(3.5f * f);
        if (this.elevationSupported()) {
            context = new ShapeDrawable((Shape)new OvalShape());
            ViewCompat.setElevation((View)this, 4.0f * f);
        } else {
            context = new ShapeDrawable((Shape)new OvalShadow(this.mShadowRadius));
            this.setLayerType(1, context.getPaint());
            context.getPaint().setShadowLayer((float)this.mShadowRadius, (float)n3, (float)n2, 503316480);
            n2 = this.mShadowRadius;
            this.setPadding(n2, n2, n2, n2);
        }
        context.getPaint().setColor(n);
        ViewCompat.setBackground((View)this, (Drawable)context);
    }

    private boolean elevationSupported() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (this.mListener != null) {
            this.mListener.onAnimationEnd(this.getAnimation());
        }
    }

    public void onAnimationStart() {
        super.onAnimationStart();
        if (this.mListener != null) {
            this.mListener.onAnimationStart(this.getAnimation());
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (!this.elevationSupported()) {
            this.setMeasuredDimension(this.getMeasuredWidth() + this.mShadowRadius * 2, this.getMeasuredHeight() + this.mShadowRadius * 2);
        }
    }

    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    public void setBackgroundColor(int n) {
        if (this.getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable)this.getBackground()).getPaint().setColor(n);
        }
    }

    private class OvalShadow
    extends OvalShape {
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();

        OvalShadow(int n) {
            CircleImageView.this.mShadowRadius = n;
            this.updateRadialGradient((int)this.rect().width());
        }

        private void updateRadialGradient(int n) {
            float f = n / 2;
            float f2 = n / 2;
            float f3 = CircleImageView.this.mShadowRadius;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mRadialGradient = new RadialGradient(f, f2, f3, new int[]{1023410176, 0}, null, tileMode);
            this.mShadowPaint.setShader((Shader)this.mRadialGradient);
        }

        public void draw(Canvas canvas, Paint paint) {
            int n = CircleImageView.this.getWidth();
            int n2 = CircleImageView.this.getHeight();
            canvas.drawCircle((float)(n / 2), (float)(n2 / 2), (float)(n / 2), this.mShadowPaint);
            canvas.drawCircle((float)(n / 2), (float)(n2 / 2), (float)(n / 2 - CircleImageView.this.mShadowRadius), paint);
        }

        protected void onResize(float f, float f2) {
            super.onResize(f, f2);
            this.updateRadialGradient((int)f);
        }
    }

}

