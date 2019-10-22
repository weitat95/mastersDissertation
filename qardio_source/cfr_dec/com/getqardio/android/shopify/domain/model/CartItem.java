/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.util.Util;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public final class CartItem {
    public final String image;
    public final List<Option> options;
    public final BigDecimal price;
    public final String productId;
    public final String productTitle;
    public final String productVariantId;
    public final int quantity;
    public final String variantTitle;

    public CartItem(String string2, String string3, String string4, String string5, BigDecimal bigDecimal, List<Option> list, String string6) {
        this(string2, string3, string4, string5, bigDecimal, list, string6, 1);
    }

    private CartItem(String string2, String string3, String string4, String string5, BigDecimal bigDecimal, List<Option> list, String string6, int n) {
        this.productId = Util.checkNotNull(string2, "productId == null");
        this.productVariantId = Util.checkNotNull(string3, "productVariantId == null");
        this.productTitle = Util.checkNotNull(string4, "productTitle == null");
        this.variantTitle = Util.checkNotNull(string5, "variantTitle == null");
        this.price = Util.checkNotNull(bigDecimal, "price == null");
        this.options = Collections.unmodifiableList(Util.checkNotEmpty(list, "options is empty"));
        this.image = string6;
        this.quantity = Math.max(0, n);
    }

    public CartItem decrementQuantity(int n) {
        return new CartItem(this.productId, this.productVariantId, this.productTitle, this.variantTitle, this.price, this.options, this.image, Math.max(0, this.quantity - n));
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
        if (!(object instanceof CartItem)) return bl2;
        object = (CartItem)object;
        bl2 = bl;
        if (!this.productId.equals(((CartItem)object).productId)) return bl2;
        return this.productVariantId.equals(((CartItem)object).productVariantId);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equalsByContent(CartItem cartItem) {
        return this.quantity == cartItem.quantity && this.productTitle.equals(cartItem.productTitle) && this.variantTitle.equals(cartItem.variantTitle) && this.price.equals(cartItem.price) && this.options.equals(cartItem.options) && (this.image != null ? this.image.equals(cartItem.image) : cartItem.image == null);
    }

    public boolean equalsById(CartItem cartItem) {
        return this.equals(cartItem);
    }

    public int hashCode() {
        return this.productId.hashCode() * 31 + this.productVariantId.hashCode();
    }

    public CartItem incrementQuantity(int n) {
        return new CartItem(this.productId, this.productVariantId, this.productTitle, this.variantTitle, this.price, this.options, this.image, Math.max(0, this.quantity + n));
    }

    public String toString() {
        return "CartItem{productId='" + this.productId + '\'' + ", productVariantId='" + this.productVariantId + '\'' + ", productTitle='" + this.productTitle + '\'' + ", variantTitle='" + this.variantTitle + '\'' + ", price=" + this.price + ", options=" + this.options + ", image='" + this.image + '\'' + ", quantity=" + this.quantity + '}';
    }

    public static final class Option {
        public final String name;
        public final String value;

        public Option(String string2, String string3) {
            this.name = Util.checkNotNull(string2, "name == null");
            this.value = Util.checkNotNull(string3, "value == null");
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
            if (!(object instanceof Option)) return bl2;
            object = (Option)object;
            bl2 = bl;
            if (!this.name.equals(((Option)object).name)) return bl2;
            return this.value.equals(((Option)object).value);
        }

        public int hashCode() {
            return this.name.hashCode() * 31 + this.value.hashCode();
        }

        public String toString() {
            return "SelectedOption{name='" + this.name + '\'' + ", value='" + this.value + '\'' + '}';
        }
    }

}

