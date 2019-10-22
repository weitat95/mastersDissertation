/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.RealGraphCall;
import com.shopify.buy3.RealQueryGraphCall;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.TopLevelResponse;

final class RealQueryGraphCall$$Lambda$1
implements RealGraphCall.ResponseDataConverter {
    private static final RealQueryGraphCall$$Lambda$1 instance = new RealQueryGraphCall$$Lambda$1();

    private RealQueryGraphCall$$Lambda$1() {
    }

    public static RealGraphCall.ResponseDataConverter lambdaFactory$() {
        return instance;
    }

    public AbstractResponse convert(TopLevelResponse topLevelResponse) {
        return RealQueryGraphCall.lambda$new$0(topLevelResponse);
    }
}

