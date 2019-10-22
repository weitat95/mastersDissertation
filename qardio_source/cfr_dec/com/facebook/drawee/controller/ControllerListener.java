/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Animatable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import javax.annotation.Nullable;

public interface ControllerListener<INFO> {
    public void onFailure(String var1, Throwable var2);

    public void onFinalImageSet(String var1, @Nullable INFO var2, @Nullable Animatable var3);

    public void onIntermediateImageFailed(String var1, Throwable var2);

    public void onIntermediateImageSet(String var1, @Nullable INFO var2);

    public void onRelease(String var1);

    public void onSubmit(String var1, Object var2);
}

