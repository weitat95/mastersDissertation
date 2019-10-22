/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.util.Util;
import java.math.BigDecimal;

public final class Product {
    public final BigDecimal compareAtPrice;
    public final String cursor;
    public final String id;
    public final String image;
    public final BigDecimal price;
    public final String sku;
    public final String title;

    public Product(String string2, String string3, String string4, BigDecimal bigDecimal, String string5, String string6, BigDecimal bigDecimal2) {
        this.id = Util.checkNotNull(string2, "id == null");
        this.title = Util.checkNotNull(string3, "title == null");
        this.image = string4;
        this.price = bigDecimal;
        this.cursor = Util.checkNotNull(string5, "cursor == null");
        this.sku = string6;
        this.compareAtPrice = bigDecimal2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) {
            return true;
        }
        boolean bl2 = bl;
        if (!(object instanceof Product)) return bl2;
        object = (Product)object;
        bl2 = bl;
        if (!this.id.equals(((Product)object).id)) return bl2;
        bl2 = bl;
        if (!this.title.equals(((Product)object).title)) return bl2;
        if (this.image != null) {
            bl2 = bl;
            if (!this.image.equals(((Product)object).image)) return bl2;
        } else if (((Product)object).image != null) {
            return false;
        }
        bl2 = bl;
        if (!this.price.equals(((Product)object).price)) return bl2;
        return this.cursor.equals(((Product)object).cursor);
    }

    public boolean equalsById(Product product) {
        return this.id.equals(product.id);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        int n2 = this.id.hashCode();
        int n3 = this.title.hashCode();
        if (this.image != null) {
            n = this.image.hashCode();
            do {
                return (((n2 * 31 + n3) * 31 + n) * 31 + this.price.hashCode()) * 31 + this.cursor.hashCode();
                break;
            } while (true);
        }
        n = 0;
        return (((n2 * 31 + n3) * 31 + n) * 31 + this.price.hashCode()) * 31 + this.cursor.hashCode();
    }

    public String toString() {
        return "Product{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", sku='" + this.sku + '\'' + ", compareAtPrice='" + this.compareAtPrice + '\'' + ", image='" + this.image + '\'' + ", price='" + this.price + '\'' + ", cursor='" + this.cursor + '\'' + '}';
    }
}

