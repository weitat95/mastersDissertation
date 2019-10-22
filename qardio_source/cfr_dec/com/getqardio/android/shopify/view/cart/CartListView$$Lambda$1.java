/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.cart.CartListView;
import java.lang.invoke.LambdaForm;

final class CartListView$$Lambda$1
implements Observer {
    private final CartListView arg$1;

    private CartListView$$Lambda$1(CartListView cartListView) {
        this.arg$1 = cartListView;
    }

    public static Observer lambdaFactory$(CartListView cartListView) {
        return new CartListView$$Lambda$1(cartListView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$bindViewModel$0((UserErrorCallback.Error)object);
    }
}

