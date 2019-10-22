/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$12
implements Storefront.ProductVariantQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$12 instance = new RealCollectionNextPageInteractor$$Lambda$12();

    private RealCollectionNextPageInteractor$$Lambda$12() {
    }

    public static Storefront.ProductVariantQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantQuery productVariantQuery) {
        RealCollectionNextPageInteractor.lambda$null$2(productVariantQuery);
    }
}

