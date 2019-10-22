/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity;
import com.google.android.gms.wallet.MaskedWallet;
import java.lang.invoke.LambdaForm;

final class CheckoutActivity$$Lambda$4
implements Observer {
    private final CheckoutActivity arg$1;

    private CheckoutActivity$$Lambda$4(CheckoutActivity checkoutActivity) {
        this.arg$1 = checkoutActivity;
    }

    public static Observer lambdaFactory$(CheckoutActivity checkoutActivity) {
        return new CheckoutActivity$$Lambda$4(checkoutActivity);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$initViewModels$3((MaskedWallet)object);
    }
}

