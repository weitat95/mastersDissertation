/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 */
package android.support.design.widget;

import android.graphics.PorterDuff;

class ViewUtils {
    static PorterDuff.Mode parseTintMode(int n, PorterDuff.Mode mode) {
        switch (n) {
            default: {
                return mode;
            }
            case 3: {
                return PorterDuff.Mode.SRC_OVER;
            }
            case 5: {
                return PorterDuff.Mode.SRC_IN;
            }
            case 9: {
                return PorterDuff.Mode.SRC_ATOP;
            }
            case 14: {
                return PorterDuff.Mode.MULTIPLY;
            }
            case 15: 
        }
        return PorterDuff.Mode.SCREEN;
    }
}

