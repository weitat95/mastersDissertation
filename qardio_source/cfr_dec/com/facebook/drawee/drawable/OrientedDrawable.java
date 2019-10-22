/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ForwardingDrawable;

public class OrientedDrawable
extends ForwardingDrawable {
    private int mRotationAngle;
    final Matrix mRotationMatrix;
    private final Matrix mTempMatrix = new Matrix();
    private final RectF mTempRectF = new RectF();

    /*
     * Enabled aggressive block sorting
     */
    public OrientedDrawable(Drawable drawable2, int n) {
        super(drawable2);
        boolean bl = n % 90 == 0;
        Preconditions.checkArgument(bl);
        this.mRotationMatrix = new Matrix();
        this.mRotationAngle = n;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.mRotationAngle <= 0) {
            super.draw(canvas);
            return;
        }
        int n = canvas.save();
        canvas.concat(this.mRotationMatrix);
        super.draw(canvas);
        canvas.restoreToCount(n);
    }

    @Override
    public int getIntrinsicHeight() {
        if (this.mRotationAngle % 180 == 0) {
            return super.getIntrinsicHeight();
        }
        return super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicWidth() {
        if (this.mRotationAngle % 180 == 0) {
            return super.getIntrinsicWidth();
        }
        return super.getIntrinsicHeight();
    }

    @Override
    public void getTransform(Matrix matrix) {
        this.getParentTransform(matrix);
        if (!this.mRotationMatrix.isIdentity()) {
            matrix.preConcat(this.mRotationMatrix);
        }
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        Drawable drawable2 = this.getCurrent();
        if (this.mRotationAngle > 0) {
            this.mRotationMatrix.setRotate((float)this.mRotationAngle, (float)rect.centerX(), (float)rect.centerY());
            this.mTempMatrix.reset();
            this.mRotationMatrix.invert(this.mTempMatrix);
            this.mTempRectF.set(rect);
            this.mTempMatrix.mapRect(this.mTempRectF);
            drawable2.setBounds((int)this.mTempRectF.left, (int)this.mTempRectF.top, (int)this.mTempRectF.right, (int)this.mTempRectF.bottom);
            return;
        }
        drawable2.setBounds(rect);
    }
}

