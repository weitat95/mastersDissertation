/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.util.DisplayMetrics
 */
package com.mixpanel.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;

public class ViewUtils {
    public static float dpToPx(float f, Context context) {
        return f * ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0f);
    }

    public static int mixColors(int n, int n2) {
        float f = Color.red((int)n) / 2 + Color.red((int)n2) / 2;
        float f2 = Color.green((int)n) / 2 + Color.green((int)n2) / 2;
        float f3 = Color.blue((int)n) / 2 + Color.blue((int)n2) / 2;
        return Color.rgb((int)((int)f), (int)((int)f2), (int)((int)f3));
    }
}

