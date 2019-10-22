/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$6
implements Storefront.ProductVariantConnectionQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$6 instance = new RealCollectionProductNextPageInteractor$$Lambda$6();

    private RealCollectionProductNextPageInteractor$$Lambda$6() {
    }

    public static Storefront.ProductVariantConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$4(productVariantConnectionQuery);
    }
}

