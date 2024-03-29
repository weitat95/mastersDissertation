/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 */
package com.bumptech.glide.request.animation;

import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.bumptech.glide.request.animation.DrawableCrossFadeViewAnimation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.GlideAnimationFactory;
import com.bumptech.glide.request.animation.NoAnimation;
import com.bumptech.glide.request.animation.ViewAnimation;
import com.bumptech.glide.request.animation.ViewAnimationFactory;

public class DrawableCrossFadeFactory<T extends Drawable>
implements GlideAnimationFactory<T> {
    private final ViewAnimationFactory<T> animationFactory;
    private final int duration;
    private DrawableCrossFadeViewAnimation<T> firstResourceAnimation;
    private DrawableCrossFadeViewAnimation<T> secondResourceAnimation;

    public DrawableCrossFadeFactory() {
        this(300);
    }

    public DrawableCrossFadeFactory(int n) {
        this(new ViewAnimationFactory(new DefaultAnimationFactory(n)), n);
    }

    DrawableCrossFadeFactory(ViewAnimationFactory<T> viewAnimationFactory, int n) {
        this.animationFactory = viewAnimationFactory;
        this.duration = n;
    }

    private GlideAnimation<T> getFirstResourceAnimation() {
        if (this.firstResourceAnimation == null) {
            this.firstResourceAnimation = new DrawableCrossFadeViewAnimation<T>(this.animationFactory.build(false, true), this.duration);
        }
        return this.firstResourceAnimation;
    }

    private GlideAnimation<T> getSecondResourceAnimation() {
        if (this.secondResourceAnimation == null) {
            this.secondResourceAnimation = new DrawableCrossFadeViewAnimation<T>(this.animationFactory.build(false, false), this.duration);
        }
        return this.secondResourceAnimation;
    }

    @Override
    public GlideAnimation<T> build(boolean bl, boolean bl2) {
        if (bl) {
            return NoAnimation.get();
        }
        if (bl2) {
            return this.getFirstResourceAnimation();
        }
        return this.getSecondResourceAnimation();
    }

    private static class DefaultAnimationFactory
    implements ViewAnimation.AnimationFactory {
        private final int duration;

        DefaultAnimationFactory(int n) {
            this.duration = n;
        }

        @Override
        public Animation build() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration((long)this.duration);
            return alphaAnimation;
        }
    }

}

