/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$9
implements Storefront.ImageConnectionQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$9 instance = new RealCollectionNextPageInteractor$$Lambda$9();

    private RealCollectionNextPageInteractor$$Lambda$9() {
    }

    public static Storefront.ImageConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
        RealCollectionNextPageInteractor.lambda$null$1(imageConnectionQuery);
    }
}

