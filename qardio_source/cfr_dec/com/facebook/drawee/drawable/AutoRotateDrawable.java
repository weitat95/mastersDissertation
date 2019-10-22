/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.SystemClock
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ForwardingDrawable;

public class AutoRotateDrawable
extends ForwardingDrawable
implements Runnable {
    private boolean mClockwise;
    private int mInterval;
    private boolean mIsScheduled = false;
    float mRotationAngle = 0.0f;

    public AutoRotateDrawable(Drawable drawable2, int n) {
        this(drawable2, n, true);
    }

    public AutoRotateDrawable(Drawable drawable2, int n, boolean bl) {
        super(Preconditions.checkNotNull(drawable2));
        this.mInterval = n;
        this.mClockwise = bl;
    }

    private int getIncrement() {
        return (int)(20.0f / (float)this.mInterval * 360.0f);
    }

    private void scheduleNextFrame() {
        if (!this.mIsScheduled) {
            this.mIsScheduled = true;
            this.scheduleSelf((Runnable)this, SystemClock.uptimeMillis() + 20L);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int n = canvas.save();
        Rect rect = this.getBounds();
        int n2 = rect.right;
        int n3 = rect.left;
        int n4 = rect.bottom;
        int n5 = rect.top;
        float f = this.mRotationAngle;
        if (!this.mClockwise) {
            f = 360.0f - this.mRotationAngle;
        }
        canvas.rotate(f, (float)(rect.left + (n2 - n3) / 2), (float)(rect.top + (n4 - n5) / 2));
        super.draw(canvas);
        canvas.restoreToCount(n);
        this.scheduleNextFrame();
    }

    @Override
    public void run() {
        this.mIsScheduled = false;
        this.mRotationAngle += (float)this.getIncrement();
        this.invalidateSelf();
    }
}

