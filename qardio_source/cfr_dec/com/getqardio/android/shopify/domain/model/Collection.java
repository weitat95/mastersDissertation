/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.util.Util;
import java.util.Collections;
import java.util.List;

public final class Collection {
    public final String cursor;
    public final String description;
    public final String id;
    public final String image;
    public final List<Product> products;
    public final String title;

    public Collection(String string2, String string3, String string4, String string5, String string6, List<Product> list) {
        this.id = Util.checkNotNull(string2, "id == null");
        this.title = Util.checkNotNull(string3, "title == null");
        this.description = Util.checkNotNull(string4, "description == null");
        this.image = string5;
        this.cursor = Util.checkNotNull(string6, "cursor == null");
        this.products = Collections.unmodifiableList(Util.checkNotNull(list, "products == null"));
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
        if (!(object instanceof Collection)) return bl2;
        object = (Collection)object;
        bl2 = bl;
        if (!this.id.equals(((Collection)object).id)) return bl2;
        bl2 = bl;
        if (!this.title.equals(((Collection)object).title)) return bl2;
        bl2 = bl;
        if (!this.description.equals(((Collection)object).description)) return bl2;
        if (this.image != null) {
            bl2 = bl;
            if (!this.image.equals(((Collection)object).image)) return bl2;
        } else if (((Collection)object).image != null) {
            return false;
        }
        bl2 = bl;
        if (!this.cursor.equals(((Collection)object).cursor)) return bl2;
        return this.products.equals(((Collection)object).products);
    }

    public boolean equalsById(Collection collection) {
        return this.id.equals(collection.id);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        int n2 = this.id.hashCode();
        int n3 = this.title.hashCode();
        int n4 = this.description.hashCode();
        if (this.image != null) {
            n = this.image.hashCode();
            do {
                return ((((n2 * 31 + n3) * 31 + n4) * 31 + n) * 31 + this.cursor.hashCode()) * 31 + this.products.hashCode();
                break;
            } while (true);
        }
        n = 0;
        return ((((n2 * 31 + n3) * 31 + n4) * 31 + n) * 31 + this.cursor.hashCode()) * 31 + this.products.hashCode();
    }

    public String toString() {
        return "Collection{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", image='" + this.image + '\'' + ", cursor='" + this.cursor + '\'' + ", products=" + this.products + '}';
    }
}

