/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CartAddItemInteractor;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.getqardio.android.shopify.domain.repository.RealCartRepository;

public class RealCartAddItemInteractor
implements CartAddItemInteractor {
    private final CartRepository cartRepository = new RealCartRepository();

    @Override
    public void execute(CartItem cartItem) {
        this.cartRepository.addCartItem(cartItem);
    }
}

