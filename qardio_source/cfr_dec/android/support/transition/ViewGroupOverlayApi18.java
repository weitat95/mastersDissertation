/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroupOverlay
 */
package android.support.transition;

import android.graphics.drawable.Drawable;
import android.support.transition.ViewGroupOverlayImpl;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

class ViewGroupOverlayApi18
implements ViewGroupOverlayImpl {
    private final ViewGroupOverlay mViewGroupOverlay;

    ViewGroupOverlayApi18(ViewGroup viewGroup) {
        this.mViewGroupOverlay = viewGroup.getOverlay();
    }

    @Override
    public void add(Drawable drawable2) {
        this.mViewGroupOverlay.add(drawable2);
    }

    @Override
    public void add(View view) {
        this.mViewGroupOverlay.add(view);
    }

    @Override
    public void remove(Drawable drawable2) {
        this.mViewGroupOverlay.remove(drawable2);
    }

    @Override
    public void remove(View view) {
        this.mViewGroupOverlay.remove(view);
    }
}

