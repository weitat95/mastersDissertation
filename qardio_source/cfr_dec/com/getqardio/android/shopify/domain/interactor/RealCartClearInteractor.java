/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.CartClearInteractor;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.getqardio.android.shopify.domain.repository.RealCartRepository;

public class RealCartClearInteractor
implements CartClearInteractor {
    private final CartRepository cartRepository = new RealCartRepository();

    @Override
    public void execute() {
        this.cartRepository.clear();
    }
}

