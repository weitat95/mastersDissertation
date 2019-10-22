/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$3
implements Storefront.ImageConnectionQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$3 instance = new RealProductByIdInteractor$$Lambda$3();

    private RealProductByIdInteractor$$Lambda$3() {
    }

    public static Storefront.ImageConnectionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
        RealProductByIdInteractor.lambda$null$1(imageConnectionQuery);
    }
}

