/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import java.io.IOException;
import okio.Sink;

interface ResponseCacheRecordEditor {
    public void abort() throws IOException;

    public Sink bodySink();

    public void commit() throws IOException;

    public Sink headerSink();
}

