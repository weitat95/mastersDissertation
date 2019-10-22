/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.Converters;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCreateInteractor$$Lambda$3
implements Function {
    private static final RealCheckoutCreateInteractor$$Lambda$3 instance = new RealCheckoutCreateInteractor$$Lambda$3();

    private RealCheckoutCreateInteractor$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return Converters.convertToCheckout((Storefront.Checkout)object);
    }
}

