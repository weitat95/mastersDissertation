/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.os.SystemClock
 */
package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.drawable.ArrayDrawable;
import java.util.Arrays;

public class FadeDrawable
extends ArrayDrawable {
    int mAlpha;
    int[] mAlphas;
    int mDurationMs;
    boolean[] mIsLayerOn;
    private final Drawable[] mLayers;
    int mPreventInvalidateCount;
    int[] mStartAlphas;
    long mStartTimeMs;
    int mTransitionState;

    /*
     * Enabled aggressive block sorting
     */
    public FadeDrawable(Drawable[] arrdrawable) {
        boolean bl = true;
        super(arrdrawable);
        if (arrdrawable.length < 1) {
            bl = false;
        }
        Preconditions.checkState(bl, "At least one layer required!");
        this.mLayers = arrdrawable;
        this.mStartAlphas = new int[arrdrawable.length];
        this.mAlphas = new int[arrdrawable.length];
        this.mAlpha = 255;
        this.mIsLayerOn = new boolean[arrdrawable.length];
        this.mPreventInvalidateCount = 0;
        this.resetInternal();
    }

    private void drawDrawableWithAlpha(Canvas canvas, Drawable drawable2, int n) {
        if (drawable2 != null && n > 0) {
            ++this.mPreventInvalidateCount;
            drawable2.mutate().setAlpha(n);
            --this.mPreventInvalidateCount;
            drawable2.draw(canvas);
        }
    }

    private void resetInternal() {
        this.mTransitionState = 2;
        Arrays.fill(this.mStartAlphas, 0);
        this.mStartAlphas[0] = 255;
        Arrays.fill(this.mAlphas, 0);
        this.mAlphas[0] = 255;
        Arrays.fill(this.mIsLayerOn, false);
        this.mIsLayerOn[0] = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateAlphas(float f) {
        boolean bl = true;
        int n = 0;
        while (n < this.mLayers.length) {
            int n2 = this.mIsLayerOn[n] ? 1 : -1;
            this.mAlphas[n] = (int)((float)this.mStartAlphas[n] + (float)(n2 * 255) * f);
            if (this.mAlphas[n] < 0) {
                this.mAlphas[n] = 0;
            }
            if (this.mAlphas[n] > 255) {
                this.mAlphas[n] = 255;
            }
            boolean bl2 = bl;
            if (this.mIsLayerOn[n]) {
                bl2 = bl;
                if (this.mAlphas[n] < 255) {
                    bl2 = false;
                }
            }
            bl = bl2;
            if (!this.mIsLayerOn[n]) {
                bl = bl2;
                if (this.mAlphas[n] > 0) {
                    bl = false;
                }
            }
            ++n;
        }
        return bl;
    }

    public void beginBatchMode() {
        ++this.mPreventInvalidateCount;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void draw(Canvas canvas) {
        int n = 2;
        boolean bl = false;
        boolean bl2 = true;
        switch (this.mTransitionState) {
            default: {
                bl = bl2;
                break;
            }
            case 0: {
                System.arraycopy(this.mAlphas, 0, this.mStartAlphas, 0, this.mLayers.length);
                this.mStartTimeMs = this.getCurrentTimeMs();
                float f = this.mDurationMs == 0 ? 1.0f : 0.0f;
                bl = this.updateAlphas(f);
                if (!bl) {
                    n = 1;
                }
                this.mTransitionState = n;
                break;
            }
            case 1: {
                if (this.mDurationMs > 0) {
                    bl = true;
                }
                Preconditions.checkState(bl);
                bl = this.updateAlphas((float)(this.getCurrentTimeMs() - this.mStartTimeMs) / (float)this.mDurationMs);
                if (!bl) {
                    n = 1;
                }
                this.mTransitionState = n;
                break;
            }
            case 2: {
                bl = true;
            }
        }
        for (n = 0; n < this.mLayers.length; ++n) {
            this.drawDrawableWithAlpha(canvas, this.mLayers[n], this.mAlphas[n] * this.mAlpha / 255);
        }
        if (!bl) {
            this.invalidateSelf();
        }
    }

    public void endBatchMode() {
        --this.mPreventInvalidateCount;
        this.invalidateSelf();
    }

    public void fadeInAllLayers() {
        this.mTransitionState = 0;
        Arrays.fill(this.mIsLayerOn, true);
        this.invalidateSelf();
    }

    public void fadeInLayer(int n) {
        this.mTransitionState = 0;
        this.mIsLayerOn[n] = true;
        this.invalidateSelf();
    }

    public void fadeOutLayer(int n) {
        this.mTransitionState = 0;
        this.mIsLayerOn[n] = false;
        this.invalidateSelf();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void finishTransitionImmediately() {
        this.mTransitionState = 2;
        int n = 0;
        do {
            if (n >= this.mLayers.length) {
                this.invalidateSelf();
                return;
            }
            int[] arrn = this.mAlphas;
            int n2 = this.mIsLayerOn[n] ? 255 : 0;
            arrn[n] = n2;
            ++n;
        } while (true);
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    protected long getCurrentTimeMs() {
        return SystemClock.uptimeMillis();
    }

    public void invalidateSelf() {
        if (this.mPreventInvalidateCount == 0) {
            super.invalidateSelf();
        }
    }

    @Override
    public void setAlpha(int n) {
        if (this.mAlpha != n) {
            this.mAlpha = n;
            this.invalidateSelf();
        }
    }

    public void setTransitionDuration(int n) {
        this.mDurationMs = n;
        if (this.mTransitionState == 1) {
            this.mTransitionState = 0;
        }
    }
}

