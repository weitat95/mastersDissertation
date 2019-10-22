/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.internal;

import com.facebook.common.internal.Supplier;

public class Suppliers {
    public static <T> Supplier<T> of(final T t) {
        return new Supplier<T>(){

            @Override
            public T get() {
                return (T)t;
            }
        };
    }

}

