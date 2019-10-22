/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.RectF
 */
package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.RectF;

public interface TransformCallback {
    public void getRootBounds(RectF var1);

    public void getTransform(Matrix var1);
}

