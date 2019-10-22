/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import okio.Source;

interface ResponseCacheRecord {
    public Source bodySource();

    public void close();

    public Source headerSource();
}

