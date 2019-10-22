/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Payment;
import com.shopify.buy3.pay.PayAddress;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PaymentToken;
import io.reactivex.Single;

public interface CheckoutCompleteInteractor {
    public Single<Payment> execute(String var1, PayCart var2, PaymentToken var3, String var4, PayAddress var5);
}

