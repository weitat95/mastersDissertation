/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.model.CartItem;
import io.reactivex.Observable;

public interface CartRepository {
    public void addCartItem(CartItem var1);

    public Cart cart();

    public void clear();

    public void removeCartItem(CartItem var1);

    public Observable<Cart> watch();
}

