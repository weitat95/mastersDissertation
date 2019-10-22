/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package com.getqardio.shared.utils;

import android.content.Context;
import android.content.res.Resources;
import com.getqardio.shared.R;

public class BpLevelConstants {
    /*
     * Enabled aggressive block sorting
     */
    public static int calculateLevel(float f, float f2) {
        int n;
        int n2 = f2 < 120.0f ? 0 : (f2 < 130.0f ? 1 : (f2 < 140.0f ? 2 : (f2 < 160.0f ? 3 : (f2 < 180.0f ? 4 : 5))));
        if (f < 80.0f) {
            n = 0;
            return Math.max(n, n2);
        }
        if (f < 85.0f) {
            n = 1;
            return Math.max(n, n2);
        }
        if (f < 90.0f) {
            n = 2;
            return Math.max(n, n2);
        }
        if (f < 100.0f) {
            n = 3;
            return Math.max(n, n2);
        }
        if (f < 110.0f) {
            n = 4;
            return Math.max(n, n2);
        }
        n = 5;
        return Math.max(n, n2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Integer getColorForLevel(Context context, int n, int n2) {
        switch (BpLevelConstants.calculateLevel(n, n2)) {
            default: {
                n = 0;
                do {
                    return context.getResources().getColor(n);
                    break;
                } while (true);
            }
            case 0: {
                n = R.color.bp_level_optimal;
                return context.getResources().getColor(n);
            }
            case 1: {
                n = R.color.bp_level_normal;
                return context.getResources().getColor(n);
            }
            case 2: {
                n = R.color.bp_level_high_normal;
                return context.getResources().getColor(n);
            }
            case 3: {
                n = R.color.bp_level_hypertension_stage_1;
                return context.getResources().getColor(n);
            }
            case 4: {
                n = R.color.bp_level_hypertension_stage_2;
                return context.getResources().getColor(n);
            }
            case 5: 
        }
        n = R.color.bp_level_hypertension_stage_3;
        return context.getResources().getColor(n);
    }
}

