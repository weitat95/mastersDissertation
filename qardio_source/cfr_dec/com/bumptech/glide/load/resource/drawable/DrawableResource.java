/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.engine.Resource;

public abstract class DrawableResource<T extends Drawable>
implements Resource<T> {
    protected final T drawable;

    public DrawableResource(T t) {
        if (t == null) {
            throw new NullPointerException("Drawable must not be null!");
        }
        this.drawable = t;
    }

    @Override
    public final T get() {
        return (T)this.drawable.getConstantState().newDrawable();
    }
}

