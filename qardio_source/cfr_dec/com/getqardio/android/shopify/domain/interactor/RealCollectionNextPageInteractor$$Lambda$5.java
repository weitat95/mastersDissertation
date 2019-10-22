/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$5
implements Storefront.ImageQueryDefinition {
    private static final RealCollectionNextPageInteractor$$Lambda$5 instance = new RealCollectionNextPageInteractor$$Lambda$5();

    private RealCollectionNextPageInteractor$$Lambda$5() {
    }

    public static Storefront.ImageQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageQuery imageQuery) {
        imageQuery.src();
    }
}

