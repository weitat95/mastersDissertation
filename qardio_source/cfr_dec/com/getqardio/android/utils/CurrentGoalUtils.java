/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import com.getqardio.android.datamodel.Goal;

public class CurrentGoalUtils {
    private static int calculatePercentage(float f, float f2) {
        return (int)(100.0f * f / f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int getFacialResource(Float f, Goal goal) {
        if (f == null || goal == null) {
            return 2130837987;
        }
        int n = goal.positiveBoundary != null ? goal.positiveBoundary : 20;
        int n2 = goal.neutralBoundary != null ? goal.neutralBoundary : 0;
        int n3 = goal.excellentBoundary != null ? goal.excellentBoundary : 105;
        int n4 = CurrentGoalUtils.calculatePercentage(f.floatValue(), goal.targetPerWeek.floatValue());
        if (n4 >= n2 && n4 < n) {
            return 2130837902;
        }
        if (n4 >= n && n4 < n3) {
            return 2130837987;
        }
        if (n4 >= n3) {
            return 2130837988;
        }
        return 2130837718;
    }
}

