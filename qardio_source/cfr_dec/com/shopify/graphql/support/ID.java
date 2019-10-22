/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

import java.io.Serializable;

public class ID
implements Serializable {
    protected final String id;

    public ID(String string2) {
        this.id = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        return super.equals(object) || object instanceof ID && this.id.equals(((ID)object).id);
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return this.id;
    }
}

