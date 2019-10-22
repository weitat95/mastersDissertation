/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.util.Util;

public final class Payment {
    public final String errorMessage;
    public final String id;
    public final boolean ready;

    public Payment(String string2, String string3, boolean bl) {
        this.id = Util.checkNotBlank(string2, "id == null");
        this.errorMessage = string3;
        this.ready = bl;
    }

    public String toString() {
        return "Payment{id='" + this.id + '\'' + ", errorMessage='" + this.errorMessage + '\'' + ", ready=" + this.ready + '}';
    }
}

