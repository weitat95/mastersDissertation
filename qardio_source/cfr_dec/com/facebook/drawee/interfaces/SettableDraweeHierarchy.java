/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.facebook.drawee.interfaces;

import android.graphics.drawable.Drawable;
import com.facebook.drawee.interfaces.DraweeHierarchy;

public interface SettableDraweeHierarchy
extends DraweeHierarchy {
    public void reset();

    public void setControllerOverlay(Drawable var1);

    public void setFailure(Throwable var1);

    public void setImage(Drawable var1, float var2, boolean var3);

    public void setProgress(float var1, boolean var2);

    public void setRetry(Throwable var1);
}

