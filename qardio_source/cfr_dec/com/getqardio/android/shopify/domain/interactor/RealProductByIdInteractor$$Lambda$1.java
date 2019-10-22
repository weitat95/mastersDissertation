/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$1
implements Storefront.ProductQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$1 instance = new RealProductByIdInteractor$$Lambda$1();

    private RealProductByIdInteractor$$Lambda$1() {
    }

    public static Storefront.ProductQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ProductQuery productQuery) {
        RealProductByIdInteractor.lambda$execute$7(productQuery);
    }
}

