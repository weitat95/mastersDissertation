/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CartRemoveItemInteractor;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.getqardio.android.shopify.domain.repository.RealCartRepository;

public class RealCartRemoveItemInteractor
implements CartRemoveItemInteractor {
    private final CartRepository cartRepository = new RealCartRepository();

    @Override
    public void execute(CartItem cartItem) {
        this.cartRepository.removeCartItem(cartItem);
    }
}

