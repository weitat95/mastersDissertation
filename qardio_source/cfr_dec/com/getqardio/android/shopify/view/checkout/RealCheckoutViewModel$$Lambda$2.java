/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import com.google.android.gms.wallet.MaskedWallet;
import java.lang.invoke.LambdaForm;

final class RealCheckoutViewModel$$Lambda$2
implements Observer {
    private final RealCheckoutViewModel arg$1;

    private RealCheckoutViewModel$$Lambda$2(RealCheckoutViewModel realCheckoutViewModel) {
        this.arg$1 = realCheckoutViewModel;
    }

    public static Observer lambdaFactory$(RealCheckoutViewModel realCheckoutViewModel) {
        return new RealCheckoutViewModel$$Lambda$2(realCheckoutViewModel);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$new$1((MaskedWallet)object);
    }
}

