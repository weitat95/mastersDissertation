/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$9
implements Storefront.ImageEdgeQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$9 instance = new RealCollectionProductNextPageInteractor$$Lambda$9();

    private RealCollectionProductNextPageInteractor$$Lambda$9() {
    }

    public static Storefront.ImageEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$0(imageEdgeQuery);
    }
}

