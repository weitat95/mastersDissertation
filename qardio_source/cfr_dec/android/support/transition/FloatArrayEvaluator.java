/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 */
package android.support.transition;

import android.animation.TypeEvaluator;

class FloatArrayEvaluator
implements TypeEvaluator<float[]> {
    private float[] mArray;

    FloatArrayEvaluator(float[] arrf) {
        this.mArray = arrf;
    }

    public float[] evaluate(float f, float[] arrf, float[] arrf2) {
        float[] arrf3;
        float[] arrf4 = arrf3 = this.mArray;
        if (arrf3 == null) {
            arrf4 = new float[arrf.length];
        }
        for (int i = 0; i < arrf4.length; ++i) {
            float f2 = arrf[i];
            arrf4[i] = (arrf2[i] - f2) * f + f2;
        }
        return arrf4;
    }
}

