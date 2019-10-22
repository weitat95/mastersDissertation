/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import android.graphics.drawable.Drawable;
import com.facebook.imagepipeline.image.CloseableImage;
import javax.annotation.Nullable;

public interface DrawableFactory {
    @Nullable
    public Drawable createDrawable(CloseableImage var1);

    public boolean supportsImageType(CloseableImage var1);
}

