/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.model;

import com.getqardio.android.shopify.util.Util;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public final class ProductDetails {
    public final String description;
    public final String id;
    public List<String> images;
    public List<Option> options;
    public List<String> tags;
    public final String title;
    public List<Variant> variants;

    public ProductDetails(String string2, String string3, String string4, List<String> list, List<String> list2, List<Option> list3, List<Variant> list4) {
        this.id = Util.checkNotNull(string2, "id == null");
        this.title = Util.checkNotNull(string3, "title == null");
        this.description = Util.checkNotNull(string4, "description == null");
        this.tags = Util.checkNotNull(list, "id == null");
        this.images = Collections.unmodifiableList(Util.checkNotNull(list2, "images == null"));
        this.options = Collections.unmodifiableList(Util.checkNotNull(list3, "options == null"));
        this.variants = Collections.unmodifiableList(Util.checkNotNull(list4, "variants == null"));
    }

    public String toString() {
        return "Product{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + ", tags=" + this.tags + ", images=" + this.images + ", options=" + this.options + ", variants=" + this.variants + '}';
    }

    public static final class Option {
        public final String id;
        public final String name;
        public final List<String> values;

        public Option(String string2, String string3, List<String> list) {
            this.id = Util.checkNotNull(string2, "id == null");
            this.name = Util.checkNotNull(string3, "name == null");
            this.values = Collections.unmodifiableList(Util.checkNotNull(list, "values == null"));
        }

        public String toString() {
            return "Option{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", values=" + this.values + '}';
        }
    }

    public static final class SelectedOption {
        public final String name;
        public final String value;

        public SelectedOption(String string2, String string3) {
            this.name = Util.checkNotNull(string2, "name == null");
            this.value = Util.checkNotNull(string3, "value == null");
        }

        public String toString() {
            return "SelectedOption{name='" + this.name + '\'' + ", value='" + this.value + '\'' + '}';
        }
    }

    public static final class Variant {
        public final boolean available;
        public final BigDecimal compareAtPrice;
        public final String id;
        public final BigDecimal price;
        public final List<SelectedOption> selectedOptions;
        public final String title;

        public Variant(String string2, String string3, boolean bl, List<SelectedOption> list, BigDecimal bigDecimal, BigDecimal bigDecimal2) {
            this.id = Util.checkNotNull(string2, "name == null");
            this.title = Util.checkNotNull(string3, "title == null");
            this.available = bl;
            this.selectedOptions = Collections.unmodifiableList(Util.checkNotNull(list, "selectedOptions == null"));
            this.price = Util.checkNotNull(bigDecimal, "price == null");
            this.compareAtPrice = bigDecimal2;
        }

        public String toString() {
            return "Variant{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", available=" + this.available + ", selectedOptions=" + this.selectedOptions + ", price=" + this.price + '}';
        }
    }

}

