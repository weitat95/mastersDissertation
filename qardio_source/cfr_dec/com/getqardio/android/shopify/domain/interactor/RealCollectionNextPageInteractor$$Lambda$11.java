/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$11
implements Storefront.ProductVariantEdgeQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$11 instance = new RealCollectionNextPageInteractor$$Lambda$11();

    private RealCollectionNextPageInteractor$$Lambda$11() {
    }

    public static Storefront.ProductVariantEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        RealCollectionNextPageInteractor.lambda$null$3(productVariantEdgeQuery);
    }
}

