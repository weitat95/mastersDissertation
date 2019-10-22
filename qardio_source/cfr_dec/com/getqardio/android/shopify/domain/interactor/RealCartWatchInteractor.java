/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CartWatchInteractor;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.getqardio.android.shopify.domain.repository.RealCartRepository;
import io.reactivex.Observable;

public final class RealCartWatchInteractor
implements CartWatchInteractor {
    private final CartRepository cartRepository = new RealCartRepository();

    @Override
    public Observable<Cart> execute() {
        return this.cartRepository.watch();
    }
}

