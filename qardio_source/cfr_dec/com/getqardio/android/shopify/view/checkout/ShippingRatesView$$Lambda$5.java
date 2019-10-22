/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.checkout.ShippingRateSelectDialog;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import java.lang.invoke.LambdaForm;

final class ShippingRatesView$$Lambda$5
implements ShippingRateSelectDialog.OnShippingRateSelectListener {
    private final ShippingRatesView arg$1;

    private ShippingRatesView$$Lambda$5(ShippingRatesView shippingRatesView) {
        this.arg$1 = shippingRatesView;
    }

    public static ShippingRateSelectDialog.OnShippingRateSelectListener lambdaFactory$(ShippingRatesView shippingRatesView) {
        return new ShippingRatesView$$Lambda$5(shippingRatesView);
    }

    @LambdaForm.Hidden
    @Override
    public void onShippingRateSelected(Checkout.ShippingRate shippingRate) {
        this.arg$1.lambda$onChangeClick$4(shippingRate);
    }
}

