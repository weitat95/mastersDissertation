/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package com.github.mikephil.charting.utils;

import android.graphics.Color;

public class ColorTemplate {
    public static final int[] COLORFUL_COLORS;
    public static final int[] JOYFUL_COLORS;
    public static final int[] LIBERTY_COLORS;
    public static final int[] MATERIAL_COLORS;
    public static final int[] PASTEL_COLORS;
    public static final int[] VORDIPLOM_COLORS;

    static {
        LIBERTY_COLORS = new int[]{Color.rgb((int)207, (int)248, (int)246), Color.rgb((int)148, (int)212, (int)212), Color.rgb((int)136, (int)180, (int)187), Color.rgb((int)118, (int)174, (int)175), Color.rgb((int)42, (int)109, (int)130)};
        JOYFUL_COLORS = new int[]{Color.rgb((int)217, (int)80, (int)138), Color.rgb((int)254, (int)149, (int)7), Color.rgb((int)254, (int)247, (int)120), Color.rgb((int)106, (int)167, (int)134), Color.rgb((int)53, (int)194, (int)209)};
        PASTEL_COLORS = new int[]{Color.rgb((int)64, (int)89, (int)128), Color.rgb((int)149, (int)165, (int)124), Color.rgb((int)217, (int)184, (int)162), Color.rgb((int)191, (int)134, (int)134), Color.rgb((int)179, (int)48, (int)80)};
        COLORFUL_COLORS = new int[]{Color.rgb((int)193, (int)37, (int)82), Color.rgb((int)255, (int)102, (int)0), Color.rgb((int)245, (int)199, (int)0), Color.rgb((int)106, (int)150, (int)31), Color.rgb((int)179, (int)100, (int)53)};
        VORDIPLOM_COLORS = new int[]{Color.rgb((int)192, (int)255, (int)140), Color.rgb((int)255, (int)247, (int)140), Color.rgb((int)255, (int)208, (int)140), Color.rgb((int)140, (int)234, (int)255), Color.rgb((int)255, (int)140, (int)157)};
        MATERIAL_COLORS = new int[]{ColorTemplate.rgb("#2ecc71"), ColorTemplate.rgb("#f1c40f"), ColorTemplate.rgb("#e74c3c"), ColorTemplate.rgb("#3498db")};
    }

    public static int colorWithAlpha(int n, int n2) {
        return 0xFFFFFF & n | (n2 & 0xFF) << 24;
    }

    public static int rgb(String string2) {
        int n = (int)Long.parseLong(string2.replace("#", ""), 16);
        return Color.rgb((int)(n >> 16 & 0xFF), (int)(n >> 8 & 0xFF), (int)(n >> 0 & 0xFF));
    }
}

