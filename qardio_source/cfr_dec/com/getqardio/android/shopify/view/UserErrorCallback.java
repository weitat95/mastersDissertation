/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import com.getqardio.android.shopify.view.LifeCycleBoundCallback;

public final class UserErrorCallback
extends LifeCycleBoundCallback<Error> {
    public void notify(int n, Throwable throwable, String string2) {
        this.notify(new Error(n, throwable, string2));
    }

    public static final class Error {
        public final String message;
        public final int requestId;
        public final Throwable t;

        private Error(int n, Throwable throwable, String string2) {
            this.requestId = n;
            this.t = throwable;
            this.message = string2;
        }
    }

}

