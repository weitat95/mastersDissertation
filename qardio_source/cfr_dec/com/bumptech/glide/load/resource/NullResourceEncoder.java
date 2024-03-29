/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.resource;

import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import java.io.OutputStream;

public class NullResourceEncoder<T>
implements ResourceEncoder<T> {
    private static final NullResourceEncoder<?> NULL_ENCODER = new NullResourceEncoder<T>();

    public static <T> NullResourceEncoder<T> get() {
        return NULL_ENCODER;
    }

    @Override
    public boolean encode(Resource<T> resource, OutputStream outputStream) {
        return false;
    }

    @Override
    public String getId() {
        return "";
    }
}

