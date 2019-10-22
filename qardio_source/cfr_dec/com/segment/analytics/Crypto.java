/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Crypto {
    public static Crypto none() {
        return new Crypto(){

            @Override
            InputStream decrypt(InputStream inputStream) {
                return inputStream;
            }

            @Override
            OutputStream encrypt(OutputStream outputStream) {
                return outputStream;
            }
        };
    }

    abstract InputStream decrypt(InputStream var1);

    abstract OutputStream encrypt(OutputStream var1);

}

