/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.util.Util;
import java.math.BigDecimal;
import java.util.List;

public final class Checkout {
    public final String currency;
    public final String id;
    public final List<LineItem> lineItems;
    public final boolean requiresShipping;
    public final ShippingRate shippingLine;
    public final ShippingRates shippingRates;
    public final BigDecimal subtotalPrice;
    public final BigDecimal taxPrice;
    public final BigDecimal totalPrice;
    public final String webUrl;

    public Checkout(String string2, String string3, String string4, boolean bl, List<LineItem> list, ShippingRates shippingRates, ShippingRate shippingRate, BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3) {
        this.id = Util.checkNotNull(string2, "id == null");
        this.webUrl = Util.checkNotNull(string3, "webUrl == null");
        this.currency = Util.checkNotNull(string4, "currency == null");
        this.requiresShipping = bl;
        this.lineItems = Util.checkNotEmpty(list, "lineItems can't be empty");
        this.shippingRates = Util.checkNotNull(shippingRates, "shippingRates == null");
        this.shippingLine = shippingRate;
        this.taxPrice = Util.checkNotNull(bigDecimal, "taxPrice == null");
        this.subtotalPrice = Util.checkNotNull(bigDecimal2, "subtotalPrice == null");
        this.totalPrice = Util.checkNotNull(bigDecimal3, "totalPrice == null");
    }

    public String toString() {
        return "Checkout{id='" + this.id + '\'' + ", webUrl='" + this.webUrl + '\'' + ", currency='" + this.currency + '\'' + ", requiresShipping=" + this.requiresShipping + ", lineItems=" + this.lineItems + ", shippingRates=" + this.shippingRates + ", shippingLine=" + this.shippingLine + ", taxPrice=" + this.taxPrice + ", subtotalPrice=" + this.subtotalPrice + ", totalPrice=" + this.totalPrice + '}';
    }

    public static final class LineItem {
        public final BigDecimal price;
        public final int quantity;
        public final String title;
        public final String variantId;

        public LineItem(String string2, String string3, int n, BigDecimal bigDecimal) {
            this.variantId = Util.checkNotNull(string2, "variantId == null");
            this.title = Util.checkNotNull(string3, "title == null");
            this.quantity = n;
            this.price = Util.checkNotNull(bigDecimal, "price == null");
        }

        public String toString() {
            return "LineItem{variantId='" + this.variantId + '\'' + ", title='" + this.title + '\'' + ", quantity=" + this.quantity + ", price=" + this.price + '}';
        }
    }

    public static final class ShippingRate {
        public final String handle;
        public final BigDecimal price;
        public final String title;

        public ShippingRate(String string2, BigDecimal bigDecimal, String string3) {
            this.handle = Util.checkNotNull(string2, "handle == null");
            this.price = Util.checkNotNull(bigDecimal, "price == null");
            this.title = Util.checkNotNull(string3, "title == null");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl = false;
            if (this == object) {
                return true;
            }
            boolean bl2 = bl;
            if (!(object instanceof ShippingRate)) return bl2;
            object = (ShippingRate)object;
            bl2 = bl;
            if (!this.handle.equals(((ShippingRate)object).handle)) return bl2;
            bl2 = bl;
            if (!this.price.equals(((ShippingRate)object).price)) return bl2;
            return this.title.equals(((ShippingRate)object).title);
        }

        public int hashCode() {
            return (this.handle.hashCode() * 31 + this.price.hashCode()) * 31 + this.title.hashCode();
        }

        public String toString() {
            return "ShippingRate{handle='" + this.handle + '\'' + ", price=" + this.price + ", title='" + this.title + '\'' + '}';
        }
    }

    public static final class ShippingRates {
        public final boolean ready;
        public final List<ShippingRate> shippingRates;

        public ShippingRates(boolean bl, List<ShippingRate> list) {
            this.ready = bl;
            this.shippingRates = Util.checkNotNull(list, "shippingRates == null");
        }

        public String toString() {
            return "ShippingRates{ready=" + this.ready + ", shippingRates=" + this.shippingRates + '}';
        }
    }

}

