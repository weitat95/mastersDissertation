/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionProductNextPageInteractor$$Lambda$1
implements Storefront.ProductConnectionQueryDefinition {
    private static final RealCollectionProductNextPageInteractor$$Lambda$1 instance = new RealCollectionProductNextPageInteractor$$Lambda$1();

    private RealCollectionProductNextPageInteractor$$Lambda$1() {
    }

    public static Storefront.ProductConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductConnectionQuery productConnectionQuery) {
        RealCollectionProductNextPageInteractor.lambda$execute$7(productConnectionQuery);
    }
}

