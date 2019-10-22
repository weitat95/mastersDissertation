/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Matrix$ScaleToFit
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Path$FillType
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.BitmapDrawable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.DrawableUtils;
import com.facebook.drawee.drawable.Rounded;
import com.facebook.drawee.drawable.TransformAwareDrawable;
import com.facebook.drawee.drawable.TransformCallback;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import javax.annotation.Nullable;

public class RoundedBitmapDrawable
extends BitmapDrawable
implements Rounded,
TransformAwareDrawable {
    final RectF mBitmapBounds;
    private int mBorderColor = 0;
    private final Paint mBorderPaint;
    private final Path mBorderPath;
    final float[] mBorderRadii;
    private float mBorderWidth = 0.0f;
    final Matrix mBoundsTransform;
    private final float[] mCornerRadii = new float[8];
    final RectF mDrawableBounds;
    final Matrix mInverseParentTransform;
    private boolean mIsCircle = false;
    private boolean mIsPathDirty = true;
    private boolean mIsShaderTransformDirty = true;
    private WeakReference<Bitmap> mLastBitmap;
    private float mPadding = 0.0f;
    private final Paint mPaint;
    final Matrix mParentTransform;
    private final Path mPath;
    final Matrix mPrevBoundsTransform;
    final Matrix mPrevParentTransform;
    final RectF mPrevRootBounds;
    private boolean mRadiiNonZero = false;
    final RectF mRootBounds;
    final Matrix mTransform;
    @Nullable
    private TransformCallback mTransformCallback;

    public RoundedBitmapDrawable(Resources resources, Bitmap bitmap, @Nullable Paint paint) {
        super(resources, bitmap);
        this.mBorderRadii = new float[8];
        this.mRootBounds = new RectF();
        this.mPrevRootBounds = new RectF();
        this.mBitmapBounds = new RectF();
        this.mDrawableBounds = new RectF();
        this.mBoundsTransform = new Matrix();
        this.mPrevBoundsTransform = new Matrix();
        this.mParentTransform = new Matrix();
        this.mPrevParentTransform = new Matrix();
        this.mInverseParentTransform = new Matrix();
        this.mTransform = new Matrix();
        this.mPath = new Path();
        this.mBorderPath = new Path();
        this.mPaint = new Paint();
        this.mBorderPaint = new Paint(1);
        if (paint != null) {
            this.mPaint.set(paint);
        }
        this.mPaint.setFlags(1);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    private void updatePaint() {
        Bitmap bitmap = this.getBitmap();
        if (this.mLastBitmap == null || this.mLastBitmap.get() != bitmap) {
            this.mLastBitmap = new WeakReference<Bitmap>(bitmap);
            this.mPaint.setShader((Shader)new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            this.mIsShaderTransformDirty = true;
        }
        if (this.mIsShaderTransformDirty) {
            this.mPaint.getShader().setLocalMatrix(this.mTransform);
            this.mIsShaderTransformDirty = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updatePath() {
        if (this.mIsPathDirty) {
            this.mBorderPath.reset();
            this.mRootBounds.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
            if (this.mIsCircle) {
                float f = Math.min(this.mRootBounds.width(), this.mRootBounds.height()) / 2.0f;
                this.mBorderPath.addCircle(this.mRootBounds.centerX(), this.mRootBounds.centerY(), f, Path.Direction.CW);
            } else {
                for (int i = 0; i < this.mBorderRadii.length; ++i) {
                    this.mBorderRadii[i] = this.mCornerRadii[i] + this.mPadding - this.mBorderWidth / 2.0f;
                }
                this.mBorderPath.addRoundRect(this.mRootBounds, this.mBorderRadii, Path.Direction.CW);
            }
            this.mRootBounds.inset(-this.mBorderWidth / 2.0f, -this.mBorderWidth / 2.0f);
            this.mPath.reset();
            this.mRootBounds.inset(this.mPadding, this.mPadding);
            if (this.mIsCircle) {
                this.mPath.addCircle(this.mRootBounds.centerX(), this.mRootBounds.centerY(), Math.min(this.mRootBounds.width(), this.mRootBounds.height()) / 2.0f, Path.Direction.CW);
            } else {
                this.mPath.addRoundRect(this.mRootBounds, this.mCornerRadii, Path.Direction.CW);
            }
            this.mRootBounds.inset(-this.mPadding, -this.mPadding);
            this.mPath.setFillType(Path.FillType.WINDING);
            this.mIsPathDirty = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateTransform() {
        if (this.mTransformCallback != null) {
            this.mTransformCallback.getTransform(this.mParentTransform);
            this.mTransformCallback.getRootBounds(this.mRootBounds);
        } else {
            this.mParentTransform.reset();
            this.mRootBounds.set(this.getBounds());
        }
        this.mBitmapBounds.set(0.0f, 0.0f, (float)this.getBitmap().getWidth(), (float)this.getBitmap().getHeight());
        this.mDrawableBounds.set(this.getBounds());
        this.mBoundsTransform.setRectToRect(this.mBitmapBounds, this.mDrawableBounds, Matrix.ScaleToFit.FILL);
        if (!this.mParentTransform.equals((Object)this.mPrevParentTransform) || !this.mBoundsTransform.equals((Object)this.mPrevBoundsTransform)) {
            this.mIsShaderTransformDirty = true;
            this.mParentTransform.invert(this.mInverseParentTransform);
            this.mTransform.set(this.mParentTransform);
            this.mTransform.preConcat(this.mBoundsTransform);
            this.mPrevParentTransform.set(this.mParentTransform);
            this.mPrevBoundsTransform.set(this.mBoundsTransform);
        }
        if (!this.mRootBounds.equals((Object)this.mPrevRootBounds)) {
            this.mIsPathDirty = true;
            this.mPrevRootBounds.set(this.mRootBounds);
        }
    }

    public void draw(Canvas canvas) {
        if (!this.shouldRound()) {
            super.draw(canvas);
            return;
        }
        this.updateTransform();
        this.updatePath();
        this.updatePaint();
        int n = canvas.save();
        canvas.concat(this.mInverseParentTransform);
        canvas.drawPath(this.mPath, this.mPaint);
        if (this.mBorderWidth > 0.0f) {
            this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
            this.mBorderPaint.setColor(DrawableUtils.multiplyColorAlpha(this.mBorderColor, this.mPaint.getAlpha()));
            canvas.drawPath(this.mBorderPath, this.mBorderPaint);
        }
        canvas.restoreToCount(n);
    }

    public void setAlpha(int n) {
        if (n != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(n);
            super.setAlpha(n);
            this.invalidateSelf();
        }
    }

    @Override
    public void setBorder(int n, float f) {
        if (this.mBorderColor != n || this.mBorderWidth != f) {
            this.mBorderColor = n;
            this.mBorderWidth = f;
            this.mIsPathDirty = true;
            this.invalidateSelf();
        }
    }

    @Override
    public void setCircle(boolean bl) {
        this.mIsCircle = bl;
        this.mIsPathDirty = true;
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        super.setColorFilter(colorFilter);
    }

    @Override
    public void setPadding(float f) {
        if (this.mPadding != f) {
            this.mPadding = f;
            this.mIsPathDirty = true;
            this.invalidateSelf();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void setRadii(float[] var1_1) {
        block3: {
            if (var1_1 != null) break block3;
            Arrays.fill(this.mCornerRadii, 0.0f);
            this.mRadiiNonZero = false;
            ** GOTO lbl13
        }
        var4_2 = var1_1.length == 8;
        Preconditions.checkArgument(var4_2, "radii should have exactly 8 values");
        System.arraycopy(var1_1, 0, this.mCornerRadii, 0, 8);
        this.mRadiiNonZero = false;
        var2_3 = 0;
        do {
            block4: {
                if (var2_3 < 8) break block4;
lbl13:
                // 2 sources
                this.mIsPathDirty = true;
                this.invalidateSelf();
                return;
            }
            var4_2 = this.mRadiiNonZero;
            var3_4 = var1_1[var2_3] > 0.0f;
            this.mRadiiNonZero = var3_4 | var4_2;
            ++var2_3;
        } while (true);
    }

    @Override
    public void setTransformCallback(@Nullable TransformCallback transformCallback) {
        this.mTransformCallback = transformCallback;
    }

    boolean shouldRound() {
        return this.mIsCircle || this.mRadiiNonZero || this.mBorderWidth > 0.0f;
    }
}

