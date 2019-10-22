/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.request;

public interface Request {
    public void begin();

    public void clear();

    public boolean isCancelled();

    public boolean isComplete();

    public boolean isResourceSet();

    public boolean isRunning();

    public void pause();

    public void recycle();
}

