/*
 * Decompiled with CFR 0.147.
 */
package android.support.v7.view.menu;

class BaseWrapper<T> {
    final T mWrappedObject;

    BaseWrapper(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.mWrappedObject = t;
    }
}

