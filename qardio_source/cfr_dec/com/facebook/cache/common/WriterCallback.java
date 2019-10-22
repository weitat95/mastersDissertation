/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.common;

import java.io.IOException;
import java.io.OutputStream;

public interface WriterCallback {
    public void write(OutputStream var1) throws IOException;
}

