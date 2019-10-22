/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$8
implements Storefront.ProductQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$8 instance = new RealCollectionNextPageInteractor$$Lambda$8();

    private RealCollectionNextPageInteractor$$Lambda$8() {
    }

    public static Storefront.ProductQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductQuery productQuery) {
        RealCollectionNextPageInteractor.lambda$null$5(productQuery);
    }
}

