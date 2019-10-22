/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.Error;
import java.util.Collections;
import java.util.List;

public final class GraphResponse<T extends AbstractResponse<T>> {
    private final T data;
    private final List<Error> errors;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    GraphResponse(T t, List<Error> list) {
        void var2_3;
        this.data = t;
        if (list == null) {
            List list2 = Collections.emptyList();
        }
        this.errors = var2_3;
    }

    public T data() {
        return this.data;
    }

    public List<Error> errors() {
        return this.errors;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }
}

