/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine;

public interface Resource<Z> {
    public Z get();

    public int getSize();

    public void recycle();
}

