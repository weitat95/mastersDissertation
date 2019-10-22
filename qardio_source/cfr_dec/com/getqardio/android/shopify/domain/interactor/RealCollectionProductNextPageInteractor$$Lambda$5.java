/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$5
implements Storefront.ImageConnectionQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$5 instance = new RealCollectionProductNextPageInteractor$$Lambda$5();

    private RealCollectionProductNextPageInteractor$$Lambda$5() {
    }

    public static Storefront.ImageConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
        RealCollectionProductNextPageInteractor.lambda$null$1(imageConnectionQuery);
    }
}

