/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.pay;

import com.shopify.buy3.pay.Util;

public enum CardNetworkType {
    VISA,
    MASTERCARD,
    DISCOVER,
    AMERICAN_EXPRESS,
    JCB;


    public static CardNetworkType findByName(String string2) {
        Util.checkNotNull(string2, "name is null");
        for (CardNetworkType cardNetworkType : CardNetworkType.values()) {
            if (!cardNetworkType.name().equalsIgnoreCase(string2.trim())) continue;
            return cardNetworkType;
        }
        if ("AMEX".equalsIgnoreCase(string2.trim())) {
            return AMERICAN_EXPRESS;
        }
        return null;
    }
}

