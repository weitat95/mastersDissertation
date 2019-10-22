/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import java.lang.invoke.LambdaForm;

final class ShippingRatesView$$Lambda$4
implements Observer {
    private final ShippingRatesView arg$1;

    private ShippingRatesView$$Lambda$4(ShippingRatesView shippingRatesView) {
        this.arg$1 = shippingRatesView;
    }

    public static Observer lambdaFactory$(ShippingRatesView shippingRatesView) {
        return new ShippingRatesView$$Lambda$4(shippingRatesView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$bindViewModel$3((String)object);
    }
}

