/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Path$FillType
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.Rounded;
import java.util.Arrays;

public class RoundedCornersDrawable
extends ForwardingDrawable
implements Rounded {
    private int mBorderColor = 0;
    private final Path mBorderPath;
    final float[] mBorderRadii;
    private float mBorderWidth = 0.0f;
    private boolean mIsCircle = false;
    private int mOverlayColor = 0;
    private float mPadding = 0.0f;
    final Paint mPaint;
    private final Path mPath;
    private final float[] mRadii;
    private final RectF mTempRectangle;
    Type mType = Type.OVERLAY_COLOR;

    public RoundedCornersDrawable(Drawable drawable2) {
        super(Preconditions.checkNotNull(drawable2));
        this.mRadii = new float[8];
        this.mBorderRadii = new float[8];
        this.mPaint = new Paint(1);
        this.mPath = new Path();
        this.mBorderPath = new Path();
        this.mTempRectangle = new RectF();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updatePath() {
        this.mPath.reset();
        this.mBorderPath.reset();
        this.mTempRectangle.set(this.getBounds());
        this.mTempRectangle.inset(this.mPadding, this.mPadding);
        if (this.mIsCircle) {
            this.mPath.addCircle(this.mTempRectangle.centerX(), this.mTempRectangle.centerY(), Math.min(this.mTempRectangle.width(), this.mTempRectangle.height()) / 2.0f, Path.Direction.CW);
        } else {
            this.mPath.addRoundRect(this.mTempRectangle, this.mRadii, Path.Direction.CW);
        }
        this.mTempRectangle.inset(-this.mPadding, -this.mPadding);
        this.mTempRectangle.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
        if (this.mIsCircle) {
            float f = Math.min(this.mTempRectangle.width(), this.mTempRectangle.height()) / 2.0f;
            this.mBorderPath.addCircle(this.mTempRectangle.centerX(), this.mTempRectangle.centerY(), f, Path.Direction.CW);
        } else {
            for (int i = 0; i < this.mBorderRadii.length; ++i) {
                this.mBorderRadii[i] = this.mRadii[i] + this.mPadding - this.mBorderWidth / 2.0f;
            }
            this.mBorderPath.addRoundRect(this.mTempRectangle, this.mBorderRadii, Path.Direction.CW);
        }
        this.mTempRectangle.inset(-this.mBorderWidth / 2.0f, -this.mBorderWidth / 2.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void draw(Canvas canvas) {
        Rect rect = this.getBounds();
        switch (1.$SwitchMap$com$facebook$drawee$drawable$RoundedCornersDrawable$Type[this.mType.ordinal()]) {
            case 1: {
                int n = canvas.save();
                this.mPath.setFillType(Path.FillType.EVEN_ODD);
                canvas.clipPath(this.mPath);
                super.draw(canvas);
                canvas.restoreToCount(n);
            }
            default: {
                break;
            }
            case 2: {
                super.draw(canvas);
                this.mPaint.setColor(this.mOverlayColor);
                this.mPaint.setStyle(Paint.Style.FILL);
                this.mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
                canvas.drawPath(this.mPath, this.mPaint);
                if (!this.mIsCircle) break;
                float f = ((float)(rect.width() - rect.height()) + this.mBorderWidth) / 2.0f;
                float f2 = ((float)(rect.height() - rect.width()) + this.mBorderWidth) / 2.0f;
                if (f > 0.0f) {
                    canvas.drawRect((float)rect.left, (float)rect.top, (float)rect.left + f, (float)rect.bottom, this.mPaint);
                    canvas.drawRect((float)rect.right - f, (float)rect.top, (float)rect.right, (float)rect.bottom, this.mPaint);
                }
                if (!(f2 > 0.0f)) break;
                canvas.drawRect((float)rect.left, (float)rect.top, (float)rect.right, (float)rect.top + f2, this.mPaint);
                canvas.drawRect((float)rect.left, (float)rect.bottom - f2, (float)rect.right, (float)rect.bottom, this.mPaint);
            }
        }
        if (this.mBorderColor != 0) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mBorderColor);
            this.mPaint.setStrokeWidth(this.mBorderWidth);
            this.mPath.setFillType(Path.FillType.EVEN_ODD);
            canvas.drawPath(this.mBorderPath, this.mPaint);
        }
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.updatePath();
    }

    @Override
    public void setBorder(int n, float f) {
        this.mBorderColor = n;
        this.mBorderWidth = f;
        this.updatePath();
        this.invalidateSelf();
    }

    @Override
    public void setCircle(boolean bl) {
        this.mIsCircle = bl;
        this.updatePath();
        this.invalidateSelf();
    }

    public void setOverlayColor(int n) {
        this.mOverlayColor = n;
        this.invalidateSelf();
    }

    @Override
    public void setPadding(float f) {
        this.mPadding = f;
        this.updatePath();
        this.invalidateSelf();
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

    public static enum Type {
        OVERLAY_COLOR,
        CLIPPING;

    }

}

