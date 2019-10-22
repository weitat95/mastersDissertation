/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Checkout;
import io.reactivex.Single;
import java.util.List;

public interface CheckoutCreateInteractor {
    public Single<Checkout> execute(List<Checkout.LineItem> var1);
}

