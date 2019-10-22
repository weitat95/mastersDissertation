/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$4
implements Storefront.ProductOptionQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$4 instance = new RealProductByIdInteractor$$Lambda$4();

    private RealProductByIdInteractor$$Lambda$4() {
    }

    public static Storefront.ProductOptionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductOptionQuery productOptionQuery) {
        RealProductByIdInteractor.lambda$null$2(productOptionQuery);
    }
}

