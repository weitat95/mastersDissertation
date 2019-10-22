/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.ViewOverlayApi18;
import android.support.transition.ViewOverlayImpl;
import android.support.transition.ViewUtilsApi14;
import android.support.transition.WindowIdApi18;
import android.support.transition.WindowIdImpl;
import android.view.View;

class ViewUtilsApi18
extends ViewUtilsApi14 {
    ViewUtilsApi18() {
    }

    @Override
    public ViewOverlayImpl getOverlay(View view) {
        return new ViewOverlayApi18(view);
    }

    @Override
    public WindowIdImpl getWindowId(View view) {
        return new WindowIdApi18(view);
    }
}

