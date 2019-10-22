/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$3
implements Storefront.ProductEdgeQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$3 instance = new RealCollectionProductNextPageInteractor$$Lambda$3();

    private RealCollectionProductNextPageInteractor$$Lambda$3() {
    }

    public static Storefront.ProductEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductEdgeQuery productEdgeQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$6(productEdgeQuery);
    }
}

