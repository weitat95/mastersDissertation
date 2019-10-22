/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphResponse;
import com.shopify.graphql.support.AbstractResponse;

public interface GraphCall<T extends AbstractResponse<T>> {
    public void cancel();

    public GraphCall<T> clone();

    public GraphResponse<T> execute() throws GraphError;

    public static interface Callback<T extends AbstractResponse<T>> {
        public void onFailure(GraphError var1);

        public void onResponse(GraphResponse<T> var1);
    }

}

