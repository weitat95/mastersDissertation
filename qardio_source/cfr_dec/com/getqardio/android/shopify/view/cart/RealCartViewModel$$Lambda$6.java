/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.util.BiFunction;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import com.shopify.buy3.pay.PayCart;
import java.lang.invoke.LambdaForm;

final class RealCartViewModel$$Lambda$6
implements BiFunction {
    private static final RealCartViewModel$$Lambda$6 instance = new RealCartViewModel$$Lambda$6();

    private RealCartViewModel$$Lambda$6() {
    }

    public static BiFunction lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object, Object object2) {
        return RealCartViewModel.lambda$checkoutPayCart$4((PayCart.Builder)object, (Checkout.LineItem)object2);
    }
}

