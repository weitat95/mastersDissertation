/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$9
implements WeakSingleObserver.OnErrorDelegate {
    private static final RealCheckoutViewModel$$Lambda$9 instance = new RealCheckoutViewModel$$Lambda$9();

    private RealCheckoutViewModel$$Lambda$9() {
    }

    public static WeakSingleObserver.OnErrorDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onError(Object object, Throwable throwable) {
        RealCheckoutViewModel.lambda$invalidateShippingRates$6((RealCheckoutViewModel)object, throwable);
    }
}

