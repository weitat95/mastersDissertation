/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.cart.CartActivity;
import com.shopify.buy3.pay.PayCart;
import java.lang.invoke.LambdaForm;

final class CartActivity$$Lambda$3
implements Observer {
    private final CartActivity arg$1;

    private CartActivity$$Lambda$3(CartActivity cartActivity) {
        this.arg$1 = cartActivity;
    }

    public static Observer lambdaFactory$(CartActivity cartActivity) {
        return new CartActivity$$Lambda$3(cartActivity);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$initViewModels$2((PayCart)object);
    }
}

