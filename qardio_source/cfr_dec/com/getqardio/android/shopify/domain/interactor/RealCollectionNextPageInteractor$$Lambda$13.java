/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$13
implements Storefront.ImageEdgeQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$13 instance = new RealCollectionNextPageInteractor$$Lambda$13();

    private RealCollectionNextPageInteractor$$Lambda$13() {
    }

    public static Storefront.ImageEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
        RealCollectionNextPageInteractor.lambda$null$0(imageEdgeQuery);
    }
}

