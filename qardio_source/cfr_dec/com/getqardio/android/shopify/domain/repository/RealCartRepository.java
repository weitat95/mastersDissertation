/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.repository;

import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

public final class RealCartRepository
implements CartRepository {
    @Override
    public void addCartItem(CartItem cartItem) {
        CartManager.INSTANCE.addCartItem(cartItem);
    }

    @Override
    public Cart cart() {
        return CartManager.INSTANCE.cart();
    }

    @Override
    public void clear() {
        CartManager.INSTANCE.clear();
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        CartManager.INSTANCE.removeCartItem(cartItem);
    }

    @Override
    public Observable<Cart> watch() {
        return CartManager.INSTANCE.cartObservable();
    }

    private static final class CartManager {
        static final CartManager INSTANCE = new CartManager();
        final RealCart cart = new RealCart();
        final BehaviorRelay<Cart> updateCartSubject = BehaviorRelay.create();

        private CartManager() {
        }

        void addCartItem(CartItem cartItem) {
            this.cart.add(cartItem);
            this.updateCartSubject.accept(this.cart);
        }

        Cart cart() {
            return this.cart;
        }

        Observable<Cart> cartObservable() {
            return this.updateCartSubject;
        }

        void clear() {
            this.cart.clear();
            this.updateCartSubject.accept(this.cart);
        }

        void removeCartItem(CartItem cartItem) {
            this.cart.remove(cartItem);
            this.updateCartSubject.accept(this.cart);
        }
    }

    private static final class RealCart
    extends Cart {
        private RealCart() {
        }

        @Override
        protected void add(CartItem cartItem) {
            super.add(cartItem);
        }

        @Override
        protected void clear() {
            super.clear();
        }

        @Override
        protected void remove(CartItem cartItem) {
            super.remove(cartItem);
        }
    }

}

