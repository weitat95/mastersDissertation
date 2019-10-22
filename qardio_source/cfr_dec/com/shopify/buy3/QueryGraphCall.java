/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphCall;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.Storefront;

public interface QueryGraphCall
extends GraphCall<Storefront.QueryRoot> {
    public QueryGraphCall cachePolicy(HttpCachePolicy.Policy var1);
}

