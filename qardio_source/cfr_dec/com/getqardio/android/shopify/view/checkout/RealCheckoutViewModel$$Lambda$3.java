/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.domain.model.Payment;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$3
implements Observer {
    private final RealCheckoutViewModel arg$1;

    private RealCheckoutViewModel$$Lambda$3(RealCheckoutViewModel realCheckoutViewModel) {
        this.arg$1 = realCheckoutViewModel;
    }

    public static Observer lambdaFactory$(RealCheckoutViewModel realCheckoutViewModel) {
        return new RealCheckoutViewModel$$Lambda$3(realCheckoutViewModel);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$new$2((Payment)object);
    }
}

