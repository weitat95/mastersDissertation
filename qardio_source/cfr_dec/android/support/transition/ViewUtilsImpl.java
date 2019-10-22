/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 */
package android.support.transition;

import android.graphics.Matrix;
import android.support.transition.ViewOverlayImpl;
import android.support.transition.WindowIdImpl;
import android.view.View;

interface ViewUtilsImpl {
    public void clearNonTransitionAlpha(View var1);

    public ViewOverlayImpl getOverlay(View var1);

    public float getTransitionAlpha(View var1);

    public WindowIdImpl getWindowId(View var1);

    public void saveNonTransitionAlpha(View var1);

    public void setAnimationMatrix(View var1, Matrix var2);

    public void setLeftTopRightBottom(View var1, int var2, int var3, int var4, int var5);

    public void setTransitionAlpha(View var1, float var2);

    public void transformMatrixToGlobal(View var1, Matrix var2);

    public void transformMatrixToLocal(View var1, Matrix var2);
}

