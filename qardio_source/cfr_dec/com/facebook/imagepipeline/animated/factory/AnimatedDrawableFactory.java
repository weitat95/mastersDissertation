/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.facebook.imagepipeline.animated.factory;

import android.graphics.drawable.Drawable;
import com.facebook.imagepipeline.image.CloseableImage;

public interface AnimatedDrawableFactory {
    public Drawable create(CloseableImage var1);
}

