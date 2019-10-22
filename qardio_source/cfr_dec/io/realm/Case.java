/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

public enum Case {
    SENSITIVE(true),
    INSENSITIVE(false);

    private final boolean value;

    private Case(boolean bl) {
        this.value = bl;
    }

    public boolean getValue() {
        return this.value;
    }
}

