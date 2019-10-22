/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.request.animation;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.GlideAnimationFactory;
import com.bumptech.glide.request.animation.NoAnimation;
import com.bumptech.glide.request.animation.ViewAnimation;

public class ViewAnimationFactory<R>
implements GlideAnimationFactory<R> {
    private final ViewAnimation.AnimationFactory animationFactory;
    private GlideAnimation<R> glideAnimation;

    ViewAnimationFactory(ViewAnimation.AnimationFactory animationFactory) {
        this.animationFactory = animationFactory;
    }

    @Override
    public GlideAnimation<R> build(boolean bl, boolean bl2) {
        if (bl || !bl2) {
            return NoAnimation.get();
        }
        if (this.glideAnimation == null) {
            this.glideAnimation = new ViewAnimation(this.animationFactory);
        }
        return this.glideAnimation;
    }
}

