/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.cart.CartActivity;
import java.lang.invoke.LambdaForm;

final class CartActivity$$Lambda$5
implements Observer {
    private final CartActivity arg$1;

    private CartActivity$$Lambda$5(CartActivity cartActivity) {
        this.arg$1 = cartActivity;
    }

    public static Observer lambdaFactory$(CartActivity cartActivity) {
        return new CartActivity$$Lambda$5(cartActivity);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$initViewModels$4((ProgressLiveData.Progress)object);
    }
}

