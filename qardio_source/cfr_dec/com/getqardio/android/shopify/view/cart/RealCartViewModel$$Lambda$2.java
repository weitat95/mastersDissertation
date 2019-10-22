/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.core.util.Function;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import java.lang.invoke.LambdaForm;

final class RealCartViewModel$$Lambda$2
implements Function {
    private static final RealCartViewModel$$Lambda$2 instance = new RealCartViewModel$$Lambda$2();

    private RealCartViewModel$$Lambda$2() {
    }

    public static Function lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return RealCartViewModel.lambda$cartTotalLiveData$0((Cart)object);
    }
}

