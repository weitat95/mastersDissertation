/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.view.cart.CartActivity;
import java.lang.invoke.LambdaForm;

final class CartActivity$$Lambda$7
implements Runnable {
    private final CartActivity arg$1;
    private final int arg$2;

    private CartActivity$$Lambda$7(CartActivity cartActivity, int n) {
        this.arg$1 = cartActivity;
        this.arg$2 = n;
    }

    public static Runnable lambdaFactory$(CartActivity cartActivity, int n) {
        return new CartActivity$$Lambda$7(cartActivity, n);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.lambda$showProgress$6(this.arg$2);
    }
}

