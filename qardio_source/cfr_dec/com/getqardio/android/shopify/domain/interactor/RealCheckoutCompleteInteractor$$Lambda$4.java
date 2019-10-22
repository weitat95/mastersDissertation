/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.Converters;
import com.shopify.buy3.Storefront;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class RealCheckoutCompleteInteractor$$Lambda$4
implements Function {
    private static final RealCheckoutCompleteInteractor$$Lambda$4 instance = new RealCheckoutCompleteInteractor$$Lambda$4();

    private RealCheckoutCompleteInteractor$$Lambda$4() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return Converters.convertToPayment((Storefront.Payment)object);
    }
}

