/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$8
implements Storefront.ProductVariantQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$8 instance = new RealCollectionProductNextPageInteractor$$Lambda$8();

    private RealCollectionProductNextPageInteractor$$Lambda$8() {
    }

    public static Storefront.ProductVariantQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantQuery productVariantQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$2(productVariantQuery);
    }
}

