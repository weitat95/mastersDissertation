/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.pay;

import com.shopify.buy3.pay.Util;

public final class PaymentToken {
    public final String publicKeyHash;
    public final String token;

    public PaymentToken(String string2, String string3) {
        this.token = Util.checkNotEmpty(string2, "token can't be empty");
        this.publicKeyHash = Util.checkNotEmpty(string3, "publicKeyHash can't be empty");
    }
}

