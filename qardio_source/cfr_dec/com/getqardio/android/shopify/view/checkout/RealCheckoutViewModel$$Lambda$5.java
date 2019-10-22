/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$5
implements WeakSingleObserver.OnErrorDelegate {
    private static final RealCheckoutViewModel$$Lambda$5 instance = new RealCheckoutViewModel$$Lambda$5();

    private RealCheckoutViewModel$$Lambda$5() {
    }

    public static WeakSingleObserver.OnErrorDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onError(Object object, Throwable throwable) {
        RealCheckoutViewModel.lambda$applyShippingRate$4((RealCheckoutViewModel)object, throwable);
    }
}

