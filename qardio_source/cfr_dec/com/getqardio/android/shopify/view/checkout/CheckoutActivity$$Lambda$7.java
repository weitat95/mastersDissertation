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

final class CheckoutActivity$$Lambda$7
implements DialogInterface.OnClickListener {
    private static final CheckoutActivity$$Lambda$7 instance = new CheckoutActivity$$Lambda$7();

    private CheckoutActivity$$Lambda$7() {
    }

    public static DialogInterface.OnClickListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        CheckoutActivity.lambda$showAlertErrorMessage$5(dialogInterface, n);
    }
}

