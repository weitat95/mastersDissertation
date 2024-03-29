/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import java.io.File;

public interface DiskCache {
    public void delete(Key var1);

    public File get(Key var1);

    public void put(Key var1, Writer var2);

    public static interface Factory {
        public DiskCache build();
    }

    public static interface Writer {
        public boolean write(File var1);
    }

}

