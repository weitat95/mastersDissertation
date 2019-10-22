/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.RealGraphCall;
import com.shopify.buy3.RealMutationGraphCall;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.TopLevelResponse;

final class RealMutationGraphCall$$Lambda$1
implements RealGraphCall.ResponseDataConverter {
    private static final RealMutationGraphCall$$Lambda$1 instance = new RealMutationGraphCall$$Lambda$1();

    private RealMutationGraphCall$$Lambda$1() {
    }

    public static RealGraphCall.ResponseDataConverter lambdaFactory$() {
        return instance;
    }

    public AbstractResponse convert(TopLevelResponse topLevelResponse) {
        return RealMutationGraphCall.lambda$new$0(topLevelResponse);
    }
}

