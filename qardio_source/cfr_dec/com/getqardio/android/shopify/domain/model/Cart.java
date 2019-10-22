/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.domain.model.CartItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cart {
    private final Map<String, CartItem> cartItems = new LinkedHashMap<String, CartItem>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    protected Cart() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void add(CartItem cartItem) {
        this.lock.writeLock().lock();
        try {
            CartItem cartItem2 = this.cartItems.get(cartItem.productVariantId);
            if (cartItem2 == null) {
                this.cartItems.put(cartItem.productVariantId, cartItem);
                do {
                    return;
                    break;
                } while (true);
            }
            this.cartItems.put(cartItem.productVariantId, cartItem2.incrementQuantity(1));
            return;
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    public List<CartItem> cartItems() {
        this.lock.readLock().lock();
        try {
            ArrayList<CartItem> arrayList = new ArrayList<CartItem>(this.cartItems.values());
            return arrayList;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    protected void clear() {
        this.lock.writeLock().lock();
        try {
            this.cartItems.clear();
            return;
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    public CartItem findByProductVariantId(String string2) {
        return this.cartItems.get(string2);
    }

    protected void remove(CartItem cartItem) {
        block4: {
            this.lock.writeLock().lock();
            CartItem cartItem2 = this.cartItems.get(cartItem.productVariantId);
            if (cartItem2 == null) break block4;
            Map<String, CartItem> map = this.cartItems;
            String string2 = cartItem2.productVariantId;
            cartItem2 = cartItem2.decrementQuantity(1);
            map.put(string2, cartItem2);
            if (cartItem2.quantity != 0) break block4;
            this.cartItems.remove(cartItem.productVariantId);
        }
        return;
        finally {
            this.lock.writeLock().unlock();
        }
    }

    public BigDecimal totalPrice() {
        this.lock.readLock().lock();
        double d = 0.0;
        try {
            for (CartItem cartItem : this.cartItems.values()) {
                d += cartItem.price.doubleValue() * (double)cartItem.quantity;
            }
            BigDecimal bigDecimal = BigDecimal.valueOf(d);
            return bigDecimal;
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    public int totalQuantity() {
        this.lock.readLock().lock();
        int n = 0;
        try {
            Iterator<CartItem> iterator = this.cartItems.values().iterator();
            while (iterator.hasNext()) {
                int n2 = iterator.next().quantity;
                n += n2;
            }
            this.lock.readLock().unlock();
            return n;
        }
        catch (Throwable throwable) {
            this.lock.readLock().unlock();
            throw throwable;
        }
    }
}

