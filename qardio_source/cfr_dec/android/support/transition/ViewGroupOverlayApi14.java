/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.content.Context;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewOverlayApi14;
import android.view.View;
import android.view.ViewGroup;

class ViewGroupOverlayApi14
extends ViewOverlayApi14
implements ViewGroupOverlayImpl {
    ViewGroupOverlayApi14(Context context, ViewGroup viewGroup, View view) {
        super(context, viewGroup, view);
    }

    static ViewGroupOverlayApi14 createFrom(ViewGroup viewGroup) {
        return (ViewGroupOverlayApi14)ViewOverlayApi14.createFrom((View)viewGroup);
    }

    @Override
    public void add(View view) {
        this.mOverlayViewGroup.add(view);
    }

    @Override
    public void remove(View view) {
        this.mOverlayViewGroup.remove(view);
    }
}

