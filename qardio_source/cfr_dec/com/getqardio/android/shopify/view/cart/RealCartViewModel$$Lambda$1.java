/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import java.lang.invoke.LambdaForm;

final class RealCartViewModel$$Lambda$1
implements WeakObserver.OnNextDelegate {
    private static final RealCartViewModel$$Lambda$1 instance = new RealCartViewModel$$Lambda$1();

    private RealCartViewModel$$Lambda$1() {
    }

    public static WeakObserver.OnNextDelegate lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onNext(Object object, Object object2) {
        RealCartViewModel.access$lambda$0((RealCartViewModel)object, (Cart)object2);
    }
}

