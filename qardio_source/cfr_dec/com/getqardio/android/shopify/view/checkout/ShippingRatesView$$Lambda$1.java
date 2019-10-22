/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.core.util.Function;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import java.lang.invoke.LambdaForm;

final class ShippingRatesView$$Lambda$1
implements Function {
    private static final ShippingRatesView$$Lambda$1 instance = new ShippingRatesView$$Lambda$1();

    private ShippingRatesView$$Lambda$1() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return ShippingRatesView.lambda$bindViewModel$0((Checkout.ShippingRate)object);
    }
}

