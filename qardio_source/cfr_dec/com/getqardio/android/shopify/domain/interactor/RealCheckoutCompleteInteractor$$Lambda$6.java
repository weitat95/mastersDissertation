/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$6
implements Storefront.NodeQueryDefinition {
    private static final RealCheckoutCompleteInteractor$$Lambda$6 instance = new RealCheckoutCompleteInteractor$$Lambda$6();

    private RealCheckoutCompleteInteractor$$Lambda$6() {
    }

    public static Storefront.NodeQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.NodeQuery nodeQuery) {
        RealCheckoutCompleteInteractor.lambda$null$3(nodeQuery);
    }
}

