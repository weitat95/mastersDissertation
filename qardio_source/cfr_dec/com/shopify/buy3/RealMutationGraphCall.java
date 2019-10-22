/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.google.gson.JsonObject;
import com.shopify.buy3.GraphCall;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.MutationGraphCall;
import com.shopify.buy3.RealGraphCall;
import com.shopify.buy3.RealMutationGraphCall$$Lambda$1;
import com.shopify.buy3.Storefront;
import com.shopify.buy3.cache.HttpCache;
import com.shopify.graphql.support.Query;
import com.shopify.graphql.support.SchemaViolationError;
import com.shopify.graphql.support.TopLevelResponse;
import java.util.concurrent.ScheduledExecutorService;
import okhttp3.Call;
import okhttp3.HttpUrl;

final class RealMutationGraphCall
extends RealGraphCall<Storefront.Mutation>
implements MutationGraphCall {
    private RealMutationGraphCall(RealGraphCall<Storefront.Mutation> realGraphCall) {
        super(realGraphCall);
    }

    RealMutationGraphCall(Storefront.MutationQuery mutationQuery, HttpUrl httpUrl, Call.Factory factory, ScheduledExecutorService scheduledExecutorService, HttpCachePolicy.Policy policy, HttpCache httpCache) {
        super(mutationQuery, httpUrl, factory, RealMutationGraphCall$$Lambda$1.lambdaFactory$(), scheduledExecutorService, policy, httpCache);
    }

    static /* synthetic */ Storefront.Mutation lambda$new$0(TopLevelResponse topLevelResponse) throws SchemaViolationError {
        return new Storefront.Mutation(topLevelResponse.getData());
    }

    @Override
    public GraphCall<Storefront.Mutation> clone() {
        return new RealMutationGraphCall(this);
    }
}

