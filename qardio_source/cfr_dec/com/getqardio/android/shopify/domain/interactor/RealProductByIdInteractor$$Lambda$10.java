/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$10
implements Storefront.ImageQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$10 instance = new RealProductByIdInteractor$$Lambda$10();

    private RealProductByIdInteractor$$Lambda$10() {
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

