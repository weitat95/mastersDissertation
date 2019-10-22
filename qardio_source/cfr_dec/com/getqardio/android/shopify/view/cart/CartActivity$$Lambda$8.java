/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.getqardio.android.shopify.view.cart;

import android.content.DialogInterface;
import com.getqardio.android.shopify.view.cart.CartActivity;
import java.lang.invoke.LambdaForm;

final class CartActivity$$Lambda$8
implements DialogInterface.OnClickListener {
    private static final CartActivity$$Lambda$8 instance = new CartActivity$$Lambda$8();

    private CartActivity$$Lambda$8() {
    }

    public static DialogInterface.OnClickListener lambdaFactory$() {
        return instance;
    }

    @LambdaForm.Hidden
    public void onClick(DialogInterface dialogInterface, int n) {
        CartActivity.lambda$showAlertErrorMessage$7(dialogInterface, n);
    }
}

