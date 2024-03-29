/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.request.target;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public final class PreloadTarget<Z>
extends SimpleTarget<Z> {
    private PreloadTarget(int n, int n2) {
        super(n, n2);
    }

    public static <Z> PreloadTarget<Z> obtain(int n, int n2) {
        return new PreloadTarget<Z>(n, n2);
    }

    @Override
    public void onResourceReady(Z z, GlideAnimation<? super Z> glideAnimation) {
        Glide.clear(this);
    }
}

