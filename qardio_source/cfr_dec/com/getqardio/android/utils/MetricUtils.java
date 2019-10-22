/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import com.getqardio.android.utils.Utils;

public class MetricUtils {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float convertHeight(int n, int n2, float f) {
        if (n == n2) {
            return f;
        }
        if (n != 0) return (float)(30.48 * (double)f);
        if (n2 != 1) return (float)(30.48 * (double)f);
        return (float)((int)(0.0328084 * (double)f * 1000000.0)) / 1000000.0f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float convertWeight(int n, int n2, float f) {
        if (n == n2) {
            return f;
        }
        float f2 = 0.0f;
        if (n == 0 && n2 == 2) {
            return f / 6.35f;
        }
        if (n == 2 && n2 == 0) {
            return Utils.round(Float.valueOf((float)(6.35029 * (double)f)), 1);
        }
        if (n == 0 && n2 == 1) {
            return (float)((int)(2.2045856f * f * 10.0f)) / 10.0f;
        }
        if (n == 1 && n2 == 0) {
            return Utils.round(Float.valueOf((float)(0.453592 * (double)f)), 1);
        }
        if (n == 2 && n2 == 1) {
            String string2 = Float.valueOf(14.0f * f).toString();
            return Float.valueOf(string2.substring(0, string2.indexOf(".") + 2)).floatValue();
        }
        float f3 = f2;
        if (n != 1) return f3;
        f3 = f2;
        if (n2 != 2) return f3;
        return (float)((int)(0.0714286 * (double)f * 1000000.0)) / 1000000.0f;
    }

    public static float convertWeightFromHectograms(float f) {
        return f / 10.0f;
    }

    public static float convertWeightToHectograms(float f) {
        return 10.0f * f;
    }

    public static int getWeightUnitNameRes(int n) {
        switch (n) {
            default: {
                return 2131362267;
            }
            case 1: {
                return 2131362269;
            }
            case 2: 
        }
        return 2131362366;
    }

    public static int getWeightUnitPositionInStringsArray(int n) {
        switch (n) {
            default: {
                return 2;
            }
            case 1: {
                return 0;
            }
            case 2: 
        }
        return 1;
    }

    public static boolean isPercentageValid(float f) {
        return f < 100.0f && f > 0.0f;
    }
}

