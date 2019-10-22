/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.shopify.view.checkout;

import android.view.View;
import com.getqardio.android.shopify.view.checkout.ShippingRateSelectDialog;
import java.lang.invoke.LambdaForm;

final class ShippingRateSelectDialog$$Lambda$1
implements View.OnClickListener {
    private final ShippingRateSelectDialog arg$1;

    private ShippingRateSelectDialog$$Lambda$1(ShippingRateSelectDialog shippingRateSelectDialog) {
        this.arg$1 = shippingRateSelectDialog;
    }

    public static View.OnClickListener lambdaFactory$(ShippingRateSelectDialog shippingRateSelectDialog) {
        return new ShippingRateSelectDialog$$Lambda$1(shippingRateSelectDialog);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}

