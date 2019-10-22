/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$1
implements Observer {
    private final RealCheckoutViewModel arg$1;

    private RealCheckoutViewModel$$Lambda$1(RealCheckoutViewModel realCheckoutViewModel) {
        this.arg$1 = realCheckoutViewModel;
    }

    public static Observer lambdaFactory$(RealCheckoutViewModel realCheckoutViewModel) {
        return new RealCheckoutViewModel$$Lambda$1(realCheckoutViewModel);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$new$0((Checkout.ShippingRate)object);
    }
}

