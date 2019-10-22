/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Animatable
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import com.facebook.drawee.controller.ControllerListener;
import javax.annotation.Nullable;

public class BaseControllerListener<INFO>
implements ControllerListener<INFO> {
    private static final ControllerListener<Object> NO_OP_LISTENER = new BaseControllerListener<Object>();

    public static <INFO> ControllerListener<INFO> getNoOpListener() {
        return NO_OP_LISTENER;
    }

    @Override
    public void onFailure(String string2, Throwable throwable) {
    }

    @Override
    public void onFinalImageSet(String string2, @Nullable INFO INFO, @Nullable Animatable animatable) {
    }

    @Override
    public void onIntermediateImageFailed(String string2, Throwable throwable) {
    }

    @Override
    public void onIntermediateImageSet(String string2, @Nullable INFO INFO) {
    }

    @Override
    public void onRelease(String string2) {
    }

    @Override
    public void onSubmit(String string2, Object object) {
    }
}

