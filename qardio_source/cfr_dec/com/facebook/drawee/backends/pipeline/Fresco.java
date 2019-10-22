/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import javax.annotation.Nullable;

public class Fresco {
    private static final Class<?> TAG = Fresco.class;
    private static PipelineDraweeControllerBuilderSupplier sDraweeControllerBuilderSupplier;
    private static volatile boolean sIsInitialized;

    static {
        sIsInitialized = false;
    }

    private Fresco() {
    }

    public static void initialize(Context context, @Nullable ImagePipelineConfig imagePipelineConfig) {
        Fresco.initialize(context, imagePipelineConfig, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void initialize(Context context, @Nullable ImagePipelineConfig imagePipelineConfig, @Nullable DraweeConfig draweeConfig) {
        if (sIsInitialized) {
            FLog.w(TAG, "Fresco has already been initialized! `Fresco.initialize(...)` should only be called 1 single time to avoid memory leaks!");
        } else {
            sIsInitialized = true;
        }
        context = context.getApplicationContext();
        if (imagePipelineConfig == null) {
            ImagePipelineFactory.initialize(context);
        } else {
            ImagePipelineFactory.initialize(imagePipelineConfig);
        }
        Fresco.initializeDrawee(context, draweeConfig);
    }

    private static void initializeDrawee(Context context, @Nullable DraweeConfig draweeConfig) {
        sDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(context, draweeConfig);
        SimpleDraweeView.initialize(sDraweeControllerBuilderSupplier);
    }

    public static PipelineDraweeControllerBuilder newDraweeControllerBuilder() {
        return sDraweeControllerBuilderSupplier.get();
    }
}

