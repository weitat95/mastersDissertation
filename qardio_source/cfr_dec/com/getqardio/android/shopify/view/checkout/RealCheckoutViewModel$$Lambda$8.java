/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$8
implements WeakSingleObserver.OnSuccessDelegate {
    private static final RealCheckoutViewModel$$Lambda$8 instance = new RealCheckoutViewModel$$Lambda$8();

    private RealCheckoutViewModel$$Lambda$8() {
    }

    public static WeakSingleObserver.OnSuccessDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onSuccess(Object object, Object object2) {
        RealCheckoutViewModel.access$lambda$1((RealCheckoutViewModel)object, (Checkout.ShippingRates)object2);
    }
}

