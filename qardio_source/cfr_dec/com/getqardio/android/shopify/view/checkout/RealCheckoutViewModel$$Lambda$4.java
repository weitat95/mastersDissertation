/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$4
implements WeakSingleObserver.OnSuccessDelegate {
    private static final RealCheckoutViewModel$$Lambda$4 instance = new RealCheckoutViewModel$$Lambda$4();

    private RealCheckoutViewModel$$Lambda$4() {
    }

    public static WeakSingleObserver.OnSuccessDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onSuccess(Object object, Object object2) {
        RealCheckoutViewModel.lambda$applyShippingRate$3((RealCheckoutViewModel)object, (Checkout)object2);
    }
}

