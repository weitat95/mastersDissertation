/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewOverlay
 */
package android.support.transition;

import android.graphics.drawable.Drawable;
import android.support.transition.ViewOverlayImpl;
import android.view.View;
import android.view.ViewOverlay;

class ViewOverlayApi18
implements ViewOverlayImpl {
    private final ViewOverlay mViewOverlay;

    ViewOverlayApi18(View view) {
        this.mViewOverlay = view.getOverlay();
    }

    @Override
    public void add(Drawable drawable2) {
        this.mViewOverlay.add(drawable2);
    }

    @Override
    public void remove(Drawable drawable2) {
        this.mViewOverlay.remove(drawable2);
    }
}

