/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android;

public interface InitializationCallback<T> {
    public static final InitializationCallback EMPTY = new Empty();

    public void failure(Exception var1);

    public void success(T var1);

    public static class Empty
    implements InitializationCallback<Object> {
        private Empty() {
        }

        @Override
        public void failure(Exception exception) {
        }

        @Override
        public void success(Object object) {
        }
    }

}

