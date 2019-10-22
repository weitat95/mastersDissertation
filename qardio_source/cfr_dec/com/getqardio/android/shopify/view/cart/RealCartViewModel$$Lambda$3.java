/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.util.Function;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import java.lang.invoke.LambdaForm;

final class RealCartViewModel$$Lambda$3
implements Function {
    private static final RealCartViewModel$$Lambda$3 instance = new RealCartViewModel$$Lambda$3();

    private RealCartViewModel$$Lambda$3() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCartViewModel.lambda$createCheckout$1((CartItem)object);
    }
}

