/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.resource;

import com.bumptech.glide.load.Encoder;
import java.io.OutputStream;

public class NullEncoder<T>
implements Encoder<T> {
    private static final NullEncoder<?> NULL_ENCODER = new NullEncoder<T>();

    public static <T> Encoder<T> get() {
        return NULL_ENCODER;
    }

    @Override
    public boolean encode(T t, OutputStream outputStream) {
        return false;
    }

    @Override
    public String getId() {
        return "";
    }
}

