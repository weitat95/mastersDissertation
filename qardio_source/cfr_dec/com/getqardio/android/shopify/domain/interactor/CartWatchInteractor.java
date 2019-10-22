/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.model.Cart;
import io.reactivex.Observable;

public interface CartWatchInteractor {
    public Observable<Cart> execute();
}

