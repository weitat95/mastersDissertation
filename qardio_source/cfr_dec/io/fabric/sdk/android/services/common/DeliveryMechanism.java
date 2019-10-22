/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.common;

public enum DeliveryMechanism {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);

    private final int id;

    private DeliveryMechanism(int n2) {
        this.id = n2;
    }

    public static DeliveryMechanism determineFrom(String string2) {
        if ("io.crash.air".equals(string2)) {
            return TEST_DISTRIBUTION;
        }
        if (string2 != null) {
            return APP_STORE;
        }
        return DEVELOPER;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return Integer.toString(this.id);
    }
}

