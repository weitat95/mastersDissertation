/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.binaryresource;

import java.io.IOException;
import java.io.InputStream;

public interface BinaryResource {
    public InputStream openStream() throws IOException;

    public long size();
}

