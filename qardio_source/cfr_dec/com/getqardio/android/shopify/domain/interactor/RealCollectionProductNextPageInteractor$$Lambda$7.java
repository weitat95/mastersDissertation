/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$7
implements Storefront.ProductVariantEdgeQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$7 instance = new RealCollectionProductNextPageInteractor$$Lambda$7();

    private RealCollectionProductNextPageInteractor$$Lambda$7() {
    }

    public static Storefront.ProductVariantEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$3(productVariantEdgeQuery);
    }
}

