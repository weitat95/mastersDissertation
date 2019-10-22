/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.nativecode;

import com.facebook.common.soloader.SoLoaderShim;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImagePipelineNativeLoader {
    public static final List<String> DEPENDENCIES = Collections.unmodifiableList(new ArrayList());

    public static void load() {
        SoLoaderShim.loadLibrary("imagepipeline");
    }
}

