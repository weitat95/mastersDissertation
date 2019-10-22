/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$4
implements Storefront.ProductQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$4 instance = new RealCollectionProductNextPageInteractor$$Lambda$4();

    private RealCollectionProductNextPageInteractor$$Lambda$4() {
    }

    public static Storefront.ProductQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductQuery productQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$5(productQuery);
    }
}

