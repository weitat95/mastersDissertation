/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$7
implements WeakSingleObserver.OnErrorDelegate {
    private static final RealCheckoutViewModel$$Lambda$7 instance = new RealCheckoutViewModel$$Lambda$7();

    private RealCheckoutViewModel$$Lambda$7() {
    }

    public static WeakSingleObserver.OnErrorDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onError(Object object, Throwable throwable) {
        RealCheckoutViewModel.lambda$updateShippingAddress$5((RealCheckoutViewModel)object, throwable);
    }
}

