/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.checkout;

import com.getqardio.android.shopify.view.checkout.CheckoutActivity;
import java.lang.invoke.LambdaForm;

final class CheckoutActivity$$Lambda$6
implements Runnable {
    private final CheckoutActivity arg$1;

    private CheckoutActivity$$Lambda$6(CheckoutActivity checkoutActivity) {
        this.arg$1 = checkoutActivity;
    }

    public static Runnable lambdaFactory$(CheckoutActivity checkoutActivity) {
        return new CheckoutActivity$$Lambda$6(checkoutActivity);
    }

    @LambdaForm.Hidden
    @Override
    public void run() {
        this.arg$1.finish();
    }
}

