/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.os.Build;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtilsApi14;
import android.support.transition.ViewGroupUtilsApi18;
import android.support.transition.ViewGroupUtilsImpl;
import android.view.ViewGroup;

class ViewGroupUtils {
    private static final ViewGroupUtilsImpl IMPL = Build.VERSION.SDK_INT >= 18 ? new ViewGroupUtilsApi18() : new ViewGroupUtilsApi14();

    static ViewGroupOverlayImpl getOverlay(ViewGroup viewGroup) {
        return IMPL.getOverlay(viewGroup);
    }

    static void suppressLayout(ViewGroup viewGroup, boolean bl) {
        IMPL.suppressLayout(viewGroup, bl);
    }
}

