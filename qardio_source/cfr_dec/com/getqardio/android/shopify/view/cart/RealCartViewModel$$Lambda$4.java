/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import java.lang.invoke.LambdaForm;

final class RealCartViewModel$$Lambda$4
implements WeakObserver.OnNextDelegate {
    private final int arg$1;

    private RealCartViewModel$$Lambda$4(int n) {
        this.arg$1 = n;
    }

    public static WeakObserver.OnNextDelegate lambdaFactory$(int n) {
        return new RealCartViewModel$$Lambda$4(n);
    }

    @LambdaForm.Hidden
    public void onNext(Object object, Object object2) {
        RealCartViewModel.lambda$createCheckout$2(this.arg$1, (RealCartViewModel)object, (Checkout)object2);
    }
}

