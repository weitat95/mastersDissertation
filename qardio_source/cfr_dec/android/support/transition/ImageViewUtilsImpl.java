/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.graphics.Matrix
 *  android.widget.ImageView
 */
package android.support.transition;

import android.animation.Animator;
import android.graphics.Matrix;
import android.widget.ImageView;

interface ImageViewUtilsImpl {
    public void animateTransform(ImageView var1, Matrix var2);

    public void reserveEndAnimateTransform(ImageView var1, Animator var2);

    public void startAnimateTransform(ImageView var1);
}

