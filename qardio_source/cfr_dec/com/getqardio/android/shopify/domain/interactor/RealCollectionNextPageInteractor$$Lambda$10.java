/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$10
implements Storefront.ProductVariantConnectionQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$10 instance = new RealCollectionNextPageInteractor$$Lambda$10();

    private RealCollectionNextPageInteractor$$Lambda$10() {
    }

    public static Storefront.ProductVariantConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        RealCollectionNextPageInteractor.lambda$null$4(productVariantConnectionQuery);
    }
}

