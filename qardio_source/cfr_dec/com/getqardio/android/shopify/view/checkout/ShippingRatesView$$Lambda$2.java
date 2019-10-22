/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import java.lang.invoke.LambdaForm;

final class ShippingRatesView$$Lambda$2
implements Observer {
    private final ShippingRatesView arg$1;

    private ShippingRatesView$$Lambda$2(ShippingRatesView shippingRatesView) {
        this.arg$1 = shippingRatesView;
    }

    public static Observer lambdaFactory$(ShippingRatesView shippingRatesView) {
        return new ShippingRatesView$$Lambda$2(shippingRatesView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$bindViewModel$1((String)object);
    }
}

