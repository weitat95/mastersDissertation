/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.cache.ResponseCacheRecord;
import com.shopify.buy3.cache.ResponseCacheRecordEditor;
import java.io.IOException;

interface ResponseCacheStore {
    public ResponseCacheRecord cacheRecord(String var1) throws IOException;

    public ResponseCacheRecordEditor cacheRecordEditor(String var1) throws IOException;

    public void remove(String var1) throws IOException;
}

