/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.Rounded;
import java.util.Arrays;

public class RoundedColorDrawable
extends Drawable
implements Rounded {
    private int mAlpha = 255;
    private int mBorderColor = 0;
    final Path mBorderPath;
    final float[] mBorderRadii;
    private float mBorderWidth = 0.0f;
    private int mColor = 0;
    private boolean mIsCircle = false;
    private float mPadding = 0.0f;
    final Paint mPaint;
    final Path mPath;
    private final float[] mRadii = new float[8];
    private final RectF mTempRect;

    public RoundedColorDrawable(int n) {
        this.mBorderRadii = new float[8];
        this.mPaint = new Paint(1);
        this.mPath = new Path();
        this.mBorderPath = new Path();
        this.mTempRect = new RectF();
        this.setColor(n);
    }

    public static RoundedColorDrawable fromColorDrawable(ColorDrawable colorDrawable) {
        return new RoundedColorDrawable(colorDrawable.getColor());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updatePath() {
        float f;
        this.mPath.reset();
        this.mBorderPath.reset();
        this.mTempRect.set(this.getBounds());
        this.mTempRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
        if (this.mIsCircle) {
            f = Math.min(this.mTempRect.width(), this.mTempRect.height()) / 2.0f;
            this.mBorderPath.addCircle(this.mTempRect.centerX(), this.mTempRect.centerY(), f, Path.Direction.CW);
        } else {
            for (int i = 0; i < this.mBorderRadii.length; ++i) {
                this.mBorderRadii[i] = this.mRadii[i] + this.mPadding - this.mBorderWidth / 2.0f;
            }
            this.mBorderPath.addRoundRect(this.mTempRect, this.mBorderRadii, Path.Direction.CW);
        }
        this.mTempRect.inset(-this.mBorderWidth / 2.0f, -this.mBorderWidth / 2.0f);
        this.mTempRect.inset(this.mPadding, this.mPadding);
        if (this.mIsCircle) {
            f = Math.min(this.mTempRect.width(), this.mTempRect.height()) / 2.0f;
            this.mPath.addCircle(this.mTempRect.centerX(), this.mTempRect.centerY(), f, Path.Direction.CW);
        } else {
            this.mPath.addRoundRect(this.mTempRect, this.mRadii, Path.Direction.CW);
        }
        this.mTempRect.inset(-this.mPadding, -this.mPadding);
    }

    public void draw(Canvas canvas) {
        this.mPaint.setColor(DrawableUtils.multiplyColorAlpha(this.mColor, this.mAlpha));
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mBorderWidth != 0.0f) {
            this.mPaint.setColor(DrawableUtils.multiplyColorAlpha(this.mBorderColor, this.mAlpha));
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(this.mBorderWidth);
            canvas.drawPath(this.mBorderPath, this.mPaint);
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(DrawableUtils.multiplyColorAlpha(this.mColor, this.mAlpha));
    }

    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.updatePath();
    }

    public void setAlpha(int n) {
        if (n != this.mAlpha) {
            this.mAlpha = n;
            this.invalidateSelf();
        }
    }

    @Override
    public void setBorder(int n, float f) {
        if (this.mBorderColor != n) {
            this.mBorderColor = n;
            this.invalidateSelf();
        }
        if (this.mBorderWidth != f) {
            this.mBorderWidth = f;
            this.updatePath();
            this.invalidateSelf();
        }
    }

    @Override
    public void setCircle(boolean bl) {
        this.mIsCircle = bl;
        this.updatePath();
        this.invalidateSelf();
    }

    public void setColor(int n) {
        if (this.mColor != n) {
            this.mColor = n;
            this.invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public void setPadding(float f) {
        if (this.mPadding != f) {
            this.mPadding = f;
            this.updatePath();
            this.invalidateSelf();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setRadii(float[] arrf) {
        if (arrf == null) {
            Arrays.fill(this.mRadii, 0.0f);
        } else {
            boolean bl = arrf.length == 8;
            Preconditions.checkArgument(bl, "radii should have exactly 8 values");
            System.arraycopy(arrf, 0, this.mRadii, 0, 8);
        }
        this.updatePath();
        this.invalidateSelf();
    }
}

