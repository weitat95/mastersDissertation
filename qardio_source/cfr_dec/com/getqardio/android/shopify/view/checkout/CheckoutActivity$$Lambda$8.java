/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.shopify.view.checkout;

import android.content.DialogInterface;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity;
import java.lang.invoke.LambdaForm;

final class CheckoutActivity$$Lambda$8
implements DialogInterface.OnClickListener {
    private final CheckoutActivity arg$1;

    private CheckoutActivity$$Lambda$8(CheckoutActivity checkoutActivity) {
        this.arg$1 = checkoutActivity;
    }

    public static DialogInterface.OnClickListener lambdaFactory$(CheckoutActivity checkoutActivity) {
        return new CheckoutActivity$$Lambda$8(checkoutActivity);
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        this.arg$1.lambda$showCheckoutSuccessMessage$6(dialogInterface, n);
    }
}

