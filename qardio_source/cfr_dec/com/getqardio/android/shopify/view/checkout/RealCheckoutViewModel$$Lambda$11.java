/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$11
implements WeakSingleObserver.OnErrorDelegate {
    private static final RealCheckoutViewModel$$Lambda$11 instance = new RealCheckoutViewModel$$Lambda$11();

    private RealCheckoutViewModel$$Lambda$11() {
    }

    public static WeakSingleObserver.OnErrorDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onError(Object object, Throwable throwable) {
        RealCheckoutViewModel.lambda$completeCheckout$7((RealCheckoutViewModel)object, throwable);
    }
}

