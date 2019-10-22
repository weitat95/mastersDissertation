/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.util.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCreateInteractor$$Lambda$1
implements Function {
    private static final RealCheckoutCreateInteractor$$Lambda$1 instance = new RealCheckoutCreateInteractor$$Lambda$1();

    private RealCheckoutCreateInteractor$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCheckoutCreateInteractor.lambda$execute$0((Checkout.LineItem)object);
    }
}

