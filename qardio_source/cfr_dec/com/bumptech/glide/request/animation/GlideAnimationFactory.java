/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.request.animation;

import com.bumptech.glide.request.animation.GlideAnimation;

public interface GlideAnimationFactory<R> {
    public GlideAnimation<R> build(boolean var1, boolean var2);
}

