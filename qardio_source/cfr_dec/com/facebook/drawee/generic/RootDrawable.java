/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.generic;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.ForwardingDrawable;
import com.facebook.drawee.drawable.VisibilityAwareDrawable;
import com.facebook.drawee.drawable.VisibilityCallback;
import javax.annotation.Nullable;

public class RootDrawable
extends ForwardingDrawable
implements VisibilityAwareDrawable {
    @Nullable
    Drawable mControllerOverlay = null;
    @Nullable
    private VisibilityCallback mVisibilityCallback;

    public RootDrawable(Drawable drawable2) {
        super(drawable2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"WrongCall"})
    @Override
    public void draw(Canvas canvas) {
        block5: {
            block4: {
                if (!this.isVisible()) break block4;
                if (this.mVisibilityCallback != null) {
                    this.mVisibilityCallback.onDraw();
                }
                super.draw(canvas);
                if (this.mControllerOverlay != null) break block5;
            }
            return;
        }
        this.mControllerOverlay.setBounds(this.getBounds());
        this.mControllerOverlay.draw(canvas);
    }

    @Override
    public int getIntrinsicHeight() {
        return -1;
    }

    @Override
    public int getIntrinsicWidth() {
        return -1;
    }

    public void setControllerOverlay(@Nullable Drawable drawable2) {
        this.mControllerOverlay = drawable2;
        this.invalidateSelf();
    }

    @Override
    public void setVisibilityCallback(@Nullable VisibilityCallback visibilityCallback) {
        this.mVisibilityCallback = visibilityCallback;
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        if (this.mVisibilityCallback != null) {
            this.mVisibilityCallback.onVisibilityChange(bl);
        }
        return super.setVisible(bl, bl2);
    }
}

