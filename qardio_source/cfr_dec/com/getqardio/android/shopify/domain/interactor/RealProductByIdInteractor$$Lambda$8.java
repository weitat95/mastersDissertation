/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealProductByIdInteractor$$Lambda$8
implements Storefront.SelectedOptionQueryDefinition {
    private static final RealProductByIdInteractor$$Lambda$8 instance = new RealProductByIdInteractor$$Lambda$8();

    private RealProductByIdInteractor$$Lambda$8() {
    }

    public static Storefront.SelectedOptionQueryDefinition lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
        RealProductByIdInteractor.lambda$null$3(selectedOptionQuery);
    }
}

