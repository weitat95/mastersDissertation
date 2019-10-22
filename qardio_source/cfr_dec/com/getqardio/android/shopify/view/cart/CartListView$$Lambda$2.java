/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.cart.CartListView;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class CartListView$$Lambda$2
implements Observer {
    private final CartListView arg$1;

    private CartListView$$Lambda$2(CartListView cartListView) {
        this.arg$1 = cartListView;
    }

    public static Observer lambdaFactory$(CartListView cartListView) {
        return new CartListView$$Lambda$2(cartListView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        CartListView.access$lambda$0(this.arg$1, (List)object);
    }
}

