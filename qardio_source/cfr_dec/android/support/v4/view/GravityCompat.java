/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.Gravity
 */
package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;

public final class GravityCompat {
    public static void apply(int n, int n2, int n3, Rect rect, Rect rect2, int n4) {
        if (Build.VERSION.SDK_INT >= 17) {
            Gravity.apply((int)n, (int)n2, (int)n3, (Rect)rect, (Rect)rect2, (int)n4);
            return;
        }
        Gravity.apply((int)n, (int)n2, (int)n3, (Rect)rect, (Rect)rect2);
    }

    public static int getAbsoluteGravity(int n, int n2) {
        if (Build.VERSION.SDK_INT >= 17) {
            return Gravity.getAbsoluteGravity((int)n, (int)n2);
        }
        return 0xFF7FFFFF & n;
    }
}

