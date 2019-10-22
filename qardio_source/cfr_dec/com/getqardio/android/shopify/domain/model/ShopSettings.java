/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.pay.CardNetworkType;
import java.util.Collections;
import java.util.Set;

public final class ShopSettings {
    public final Set<CardNetworkType> acceptedCardBrands;
    public final String countryCode;
    public final String name;

    public ShopSettings(String string2, Set<CardNetworkType> set, String string3) {
        this.name = Util.checkNotNull(string2, "name can't be null");
        this.acceptedCardBrands = Collections.unmodifiableSet(Util.checkNotNull(set, "acceptedCardBrands can't be null)"));
        this.countryCode = Util.checkNotNull(string3, "countryCode can't be null");
    }

    public String toString() {
        return "ShopSettings{name='" + this.name + '\'' + ", acceptedCardBrands=" + this.acceptedCardBrands + ", countryCode='" + this.countryCode + '\'' + '}';
    }
}

