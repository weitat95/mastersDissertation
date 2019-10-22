/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import java.lang.invoke.LambdaForm;

final class RealCartViewModel$$Lambda$5
implements WeakObserver.OnErrorDelegate {
    private final int arg$1;

    private RealCartViewModel$$Lambda$5(int n) {
        this.arg$1 = n;
    }

    public static WeakObserver.OnErrorDelegate lambdaFactory$(int n) {
        return new RealCartViewModel$$Lambda$5(n);
    }

    @LambdaForm.Hidden
    public void onError(Object object, Throwable throwable) {
        RealCartViewModel.lambda$createCheckout$3(this.arg$1, (RealCartViewModel)object, throwable);
    }
}

