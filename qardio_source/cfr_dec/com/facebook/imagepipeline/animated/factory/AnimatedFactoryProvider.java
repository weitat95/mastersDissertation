/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.animated.factory;

import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import java.lang.reflect.Constructor;

public class AnimatedFactoryProvider {
    private static AnimatedFactory sImpl = null;
    private static boolean sImplLoaded;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AnimatedFactory getAnimatedFactory(PlatformBitmapFactory platformBitmapFactory, ExecutorSupplier executorSupplier) {
        if (!sImplLoaded) {
            try {
                sImpl = (AnimatedFactory)Class.forName("com.facebook.imagepipeline.animated.factory.AnimatedFactoryImplSupport").getConstructor(PlatformBitmapFactory.class, ExecutorSupplier.class).newInstance(platformBitmapFactory, executorSupplier);
            }
            catch (Throwable throwable) {}
            if (sImpl != null) {
                sImplLoaded = true;
                return sImpl;
            }
            try {
                sImpl = (AnimatedFactory)Class.forName("com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl").getConstructor(PlatformBitmapFactory.class, ExecutorSupplier.class).newInstance(platformBitmapFactory, executorSupplier);
            }
            catch (Throwable throwable) {}
            sImplLoaded = true;
        }
        return sImpl;
    }
}

