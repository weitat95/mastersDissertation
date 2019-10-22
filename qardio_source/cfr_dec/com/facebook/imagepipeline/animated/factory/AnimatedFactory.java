/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.animated.factory;

import android.content.Context;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public interface AnimatedFactory {
    public AnimatedDrawableFactory getAnimatedDrawableFactory(Context var1);

    public AnimatedImageFactory getAnimatedImageFactory();
}

