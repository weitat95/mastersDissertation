/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.view.cart.CartActivity;
import com.shopify.buy3.pay.PayHelper;
import java.lang.invoke.LambdaForm;

final class CartActivity$$Lambda$1
implements PayHelper.AndroidPayReadyCallback {
    private final CartActivity arg$1;

    private CartActivity$$Lambda$1(CartActivity cartActivity) {
        this.arg$1 = cartActivity;
    }

    public static PayHelper.AndroidPayReadyCallback lambdaFactory$(CartActivity cartActivity) {
        return new CartActivity$$Lambda$1(cartActivity);
    }

    @LambdaForm.Hidden
    @Override
    public void onResult(boolean bl) {
        this.arg$1.lambda$onConnected$0(bl);
    }
}

