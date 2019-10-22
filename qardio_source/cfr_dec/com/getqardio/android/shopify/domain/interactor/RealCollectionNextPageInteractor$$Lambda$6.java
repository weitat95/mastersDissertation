/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$6
implements Storefront.ProductConnectionQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$6 instance = new RealCollectionNextPageInteractor$$Lambda$6();

    private RealCollectionNextPageInteractor$$Lambda$6() {
    }

    public static Storefront.ProductConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductConnectionQuery productConnectionQuery) {
        RealCollectionNextPageInteractor.lambda$null$7(productConnectionQuery);
    }
}

