/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.view;

import android.view.View;
import android.view.ViewGroup;
import javax.annotation.Nullable;

public class AspectRatioMeasure {
    private static boolean shouldAdjust(int n) {
        return n == 0 || n == -2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void updateMeasureSpec(Spec spec, float f, @Nullable ViewGroup.LayoutParams layoutParams, int n, int n2) {
        block5: {
            block4: {
                if (f <= 0.0f || layoutParams == null) break block4;
                if (AspectRatioMeasure.shouldAdjust(layoutParams.height)) {
                    spec.height = View.MeasureSpec.makeMeasureSpec((int)View.resolveSize((int)((int)((float)(View.MeasureSpec.getSize((int)spec.width) - n) / f + (float)n2)), (int)spec.height), (int)1073741824);
                    return;
                }
                if (AspectRatioMeasure.shouldAdjust(layoutParams.width)) break block5;
            }
            return;
        }
        spec.width = View.MeasureSpec.makeMeasureSpec((int)View.resolveSize((int)((int)((float)(View.MeasureSpec.getSize((int)spec.height) - n2) * f + (float)n)), (int)spec.width), (int)1073741824);
    }

    public static class Spec {
        public int height;
        public int width;
    }

}

