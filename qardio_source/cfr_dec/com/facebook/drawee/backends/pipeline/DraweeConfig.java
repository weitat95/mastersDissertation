/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import com.facebook.common.internal.ImmutableList;
import com.facebook.drawee.backends.pipeline.DrawableFactory;
import javax.annotation.Nullable;

public class DraweeConfig {
    @Nullable
    private final ImmutableList<DrawableFactory> mCustomDrawableFactories;

    @Nullable
    public ImmutableList<DrawableFactory> getCustomDrawableFactories() {
        return this.mCustomDrawableFactories;
    }
}

