/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.google.gson.JsonObject;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.RealGraphCall;
import com.shopify.buy3.RealQueryGraphCall$$Lambda$1;
import com.shopify.buy3.Storefront;
import com.shopify.buy3.Utils;
import com.shopify.buy3.cache.HttpCache;
import com.shopify.graphql.support.Query;
import com.shopify.graphql.support.SchemaViolationError;
import com.shopify.graphql.support.TopLevelResponse;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.Call;
import okhttp3.HttpUrl;

final class RealQueryGraphCall
extends RealGraphCall<Storefront.QueryRoot>
implements QueryGraphCall {
    private RealQueryGraphCall(RealGraphCall<Storefront.QueryRoot> realGraphCall) {
        super(realGraphCall);
    }

    RealQueryGraphCall(Storefront.QueryRootQuery queryRootQuery, HttpUrl httpUrl, Call.Factory factory, ScheduledExecutorService scheduledExecutorService, HttpCachePolicy.Policy policy, HttpCache httpCache) {
        super(queryRootQuery, httpUrl, factory, RealQueryGraphCall$$Lambda$1.lambdaFactory$(), scheduledExecutorService, policy, httpCache);
    }

    static /* synthetic */ Storefront.QueryRoot lambda$new$0(TopLevelResponse topLevelResponse) throws SchemaViolationError {
        return new Storefront.QueryRoot(topLevelResponse.getData());
    }

    @Override
    public QueryGraphCall cachePolicy(HttpCachePolicy.Policy policy) {
        if (this.executed.get()) {
            throw new IllegalStateException("Already Executed");
        }
        return new RealQueryGraphCall((Storefront.QueryRootQuery)this.query, this.serverUrl, this.httpCallFactory, this.dispatcher, Utils.checkNotNull(policy, "cachePolicy == null"), this.httpCache);
    }

    @Override
    public RealQueryGraphCall clone() {
        return new RealQueryGraphCall(this);
    }
}

