/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CartFetchInteractor;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.getqardio.android.shopify.domain.repository.RealCartRepository;

public class RealCartFetchInteractor
implements CartFetchInteractor {
    private final CartRepository cartRepository = new RealCartRepository();

    @Override
    public Cart execute() {
        return this.cartRepository.cart();
    }
}

