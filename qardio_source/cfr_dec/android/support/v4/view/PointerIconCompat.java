/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.PointerIcon
 */
package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.view.PointerIcon;

public final class PointerIconCompat {
    private Object mPointerIcon;

    private PointerIconCompat(Object object) {
        this.mPointerIcon = object;
    }

    public static PointerIconCompat getSystemIcon(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 24) {
            return new PointerIconCompat((Object)PointerIcon.getSystemIcon((Context)context, (int)n));
        }
        return new PointerIconCompat(null);
    }

    public Object getPointerIcon() {
        return this.mPointerIcon;
    }
}

