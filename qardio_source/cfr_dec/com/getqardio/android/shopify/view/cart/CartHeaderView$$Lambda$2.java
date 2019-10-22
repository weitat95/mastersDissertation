/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.cart.CartHeaderView;
import java.lang.invoke.LambdaForm;

final class CartHeaderView$$Lambda$2
implements Observer {
    private final CartHeaderView arg$1;

    private CartHeaderView$$Lambda$2(CartHeaderView cartHeaderView) {
        this.arg$1 = cartHeaderView;
    }

    public static Observer lambdaFactory$(CartHeaderView cartHeaderView) {
        return new CartHeaderView$$Lambda$2(cartHeaderView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$bindViewModel$0((String)object);
    }
}

