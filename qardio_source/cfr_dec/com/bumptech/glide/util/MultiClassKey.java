/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.util;

public class MultiClassKey {
    private Class<?> first;
    private Class<?> second;

    public MultiClassKey() {
    }

    public MultiClassKey(Class<?> class_, Class<?> class_2) {
        this.set(class_, class_2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6: {
            block5: {
                if (this == object) break block5;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (MultiClassKey)object;
                if (!this.first.equals(((MultiClassKey)object).first)) {
                    return false;
                }
                if (!this.second.equals(((MultiClassKey)object).second)) break block6;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.first.hashCode() * 31 + this.second.hashCode();
    }

    public void set(Class<?> class_, Class<?> class_2) {
        this.first = class_;
        this.second = class_2;
    }

    public String toString() {
        return "MultiClassKey{first=" + this.first + ", second=" + this.second + '}';
    }
}

