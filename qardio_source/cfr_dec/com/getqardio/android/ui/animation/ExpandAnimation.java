/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package com.getqardio.android.ui.animation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExpandAnimation
extends Animation {
    private View content;
    private final int deltaHeight;
    private final int startHeight;

    public ExpandAnimation(View view, int n, int n2) {
        this.content = view;
        this.startHeight = n;
        this.deltaHeight = n2 - n;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        transformation = this.content.getLayoutParams();
        transformation.height = (int)((float)this.startHeight + (float)this.deltaHeight * f);
        this.content.setLayoutParams((ViewGroup.LayoutParams)transformation);
    }

    public boolean willChangeBounds() {
        return true;
    }
}

