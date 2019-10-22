/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package android.support.v4.graphics;

import android.graphics.Color;

public final class ColorUtils {
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();

    private static int compositeAlpha(int n, int n2) {
        return 255 - (255 - n2) * (255 - n) / 255;
    }

    public static int compositeColors(int n, int n2) {
        int n3 = Color.alpha((int)n2);
        int n4 = Color.alpha((int)n);
        int n5 = ColorUtils.compositeAlpha(n4, n3);
        return Color.argb((int)n5, (int)ColorUtils.compositeComponent(Color.red((int)n), n4, Color.red((int)n2), n3, n5), (int)ColorUtils.compositeComponent(Color.green((int)n), n4, Color.green((int)n2), n3, n5), (int)ColorUtils.compositeComponent(Color.blue((int)n), n4, Color.blue((int)n2), n3, n5));
    }

    private static int compositeComponent(int n, int n2, int n3, int n4, int n5) {
        if (n5 == 0) {
            return 0;
        }
        return (n * 255 * n2 + n3 * n4 * (255 - n2)) / (n5 * 255);
    }

    public static int setAlphaComponent(int n, int n2) {
        if (n2 < 0 || n2 > 255) {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }
        return 0xFFFFFF & n | n2 << 24;
    }
}

