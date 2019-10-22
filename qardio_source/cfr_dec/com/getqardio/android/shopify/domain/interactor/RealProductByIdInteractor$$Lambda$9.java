/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$9
implements Storefront.ImageEdgeQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$9 instance = new RealProductByIdInteractor$$Lambda$9();

    private RealProductByIdInteractor$$Lambda$9() {
    }

    public static Storefront.ImageEdgeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
        RealProductByIdInteractor.lambda$null$0(imageEdgeQuery);
    }
}

