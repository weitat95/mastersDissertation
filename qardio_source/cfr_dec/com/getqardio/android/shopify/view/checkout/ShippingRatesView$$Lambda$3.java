/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.core.util.Function;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import java.lang.invoke.LambdaForm;

final class ShippingRatesView$$Lambda$3
implements Function {
    private final ShippingRatesView arg$1;

    private ShippingRatesView$$Lambda$3(ShippingRatesView shippingRatesView) {
        this.arg$1 = shippingRatesView;
    }

    public static Function lambdaFactory$(ShippingRatesView shippingRatesView) {
        return new ShippingRatesView$$Lambda$3(shippingRatesView);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$bindViewModel$2((Checkout.ShippingRate)object);
    }
}

