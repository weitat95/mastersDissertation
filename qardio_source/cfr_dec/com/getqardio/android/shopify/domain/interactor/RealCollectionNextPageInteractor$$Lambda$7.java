/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$7
implements Storefront.ProductEdgeQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$7 instance = new RealCollectionNextPageInteractor$$Lambda$7();

    private RealCollectionNextPageInteractor$$Lambda$7() {
    }

    public static Storefront.ProductEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductEdgeQuery productEdgeQuery) {
        RealCollectionNextPageInteractor.lambda$null$6(productEdgeQuery);
    }
}

