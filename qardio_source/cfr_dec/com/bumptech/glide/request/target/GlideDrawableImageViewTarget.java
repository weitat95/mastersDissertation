/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.widget.ImageView
 */
package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SquaringDrawable;

public class GlideDrawableImageViewTarget
extends ImageViewTarget<GlideDrawable> {
    private int maxLoopCount;
    private GlideDrawable resource;

    public GlideDrawableImageViewTarget(ImageView imageView) {
        this(imageView, -1);
    }

    public GlideDrawableImageViewTarget(ImageView imageView, int n) {
        super(imageView);
        this.maxLoopCount = n;
    }

    @Override
    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
        GlideDrawable glideDrawable2 = glideDrawable;
        if (!glideDrawable.isAnimated()) {
            float f = (float)((ImageView)this.view).getWidth() / (float)((ImageView)this.view).getHeight();
            float f2 = (float)glideDrawable.getIntrinsicWidth() / (float)glideDrawable.getIntrinsicHeight();
            glideDrawable2 = glideDrawable;
            if (Math.abs(f - 1.0f) <= 0.05f) {
                glideDrawable2 = glideDrawable;
                if (Math.abs(f2 - 1.0f) <= 0.05f) {
                    glideDrawable2 = new SquaringDrawable(glideDrawable, ((ImageView)this.view).getWidth());
                }
            }
        }
        super.onResourceReady(glideDrawable2, glideAnimation);
        this.resource = glideDrawable2;
        glideDrawable2.setLoopCount(this.maxLoopCount);
        glideDrawable2.start();
    }

    @Override
    public void onStart() {
        if (this.resource != null) {
            this.resource.start();
        }
    }

    @Override
    public void onStop() {
        if (this.resource != null) {
            this.resource.stop();
        }
    }

    @Override
    protected void setResource(GlideDrawable glideDrawable) {
        ((ImageView)this.view).setImageDrawable((Drawable)glideDrawable);
    }
}

