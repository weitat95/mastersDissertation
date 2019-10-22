/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.drawable;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.DrawableProperties;
import com.facebook.drawee.drawable.TransformAwareDrawable;
import com.facebook.drawee.drawable.TransformCallback;
import javax.annotation.Nullable;

public class DrawableUtils {
    public static void copyProperties(Drawable drawable2, Drawable drawable3) {
        if (drawable3 == null || drawable2 == null || drawable2 == drawable3) {
            return;
        }
        drawable2.setBounds(drawable3.getBounds());
        drawable2.setChangingConfigurations(drawable3.getChangingConfigurations());
        drawable2.setLevel(drawable3.getLevel());
        drawable2.setVisible(drawable3.isVisible(), false);
        drawable2.setState(drawable3.getState());
    }

    public static int getOpacityFromColor(int n) {
        if ((n >>>= 24) == 255) {
            return -1;
        }
        if (n == 0) {
            return -2;
        }
        return -3;
    }

    public static int multiplyColorAlpha(int n, int n2) {
        if (n2 == 255) {
            return n;
        }
        if (n2 == 0) {
            return n & 0xFFFFFF;
        }
        return (n >>> 24) * (n2 + (n2 >> 7)) >> 8 << 24 | 0xFFFFFF & n;
    }

    public static void setCallbacks(Drawable drawable2, @Nullable Drawable.Callback callback, @Nullable TransformCallback transformCallback) {
        if (drawable2 != null) {
            drawable2.setCallback(callback);
            if (drawable2 instanceof TransformAwareDrawable) {
                ((TransformAwareDrawable)drawable2).setTransformCallback(transformCallback);
            }
        }
    }

    public static void setDrawableProperties(Drawable drawable2, DrawableProperties drawableProperties) {
        if (drawable2 == null || drawableProperties == null) {
            return;
        }
        drawableProperties.applyTo(drawable2);
    }
}

