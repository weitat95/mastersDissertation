/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

final class ByteString {
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private final byte[] bytes;
    private volatile int hash = 0;

    private ByteString(byte[] arrby) {
        this.bytes = arrby;
    }

    public static ByteString copyFrom(byte[] arrby, int n, int n2) {
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return new ByteString(arrby2);
    }

    public static ByteString copyFromUtf8(String object) {
        try {
            object = new ByteString(((String)object).getBytes("UTF-8"));
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("UTF-8 not supported.", unsupportedEncodingException);
        }
    }

    public void copyTo(byte[] arrby, int n, int n2, int n3) {
        System.arraycopy(this.bytes, n, arrby, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object arrby) {
        if (arrby != this) {
            if (!(arrby instanceof ByteString)) {
                return false;
            }
            byte[] arrby2 = arrby;
            int n = this.bytes.length;
            if (n != arrby2.bytes.length) {
                return false;
            }
            arrby = this.bytes;
            arrby2 = arrby2.bytes;
            for (int i = 0; i < n; ++i) {
                if (arrby[i] == arrby2[i]) continue;
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hash;
        if (n == 0) {
            int n3;
            byte[] arrby = this.bytes;
            n = n3 = this.bytes.length;
            for (n2 = 0; n2 < n3; ++n2) {
                n = n * 31 + arrby[n2];
            }
            n2 = n;
            if (n == 0) {
                n2 = 1;
            }
            this.hash = n2;
        }
        return n2;
    }

    public InputStream newInput() {
        return new ByteArrayInputStream(this.bytes);
    }

    public int size() {
        return this.bytes.length;
    }
}

