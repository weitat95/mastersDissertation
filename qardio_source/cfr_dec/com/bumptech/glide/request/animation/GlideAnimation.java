/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package com.bumptech.glide.request.animation;

import android.graphics.drawable.Drawable;
import android.view.View;

public interface GlideAnimation<R> {
    public boolean animate(R var1, ViewAdapter var2);

    public static interface ViewAdapter {
        public Drawable getCurrentDrawable();

        public View getView();

        public void setDrawable(Drawable var1);
    }

}

