/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.PriorityProvider;

public enum Priority {
    LOW,
    NORMAL,
    HIGH,
    IMMEDIATE;


    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static <Y> int compareTo(PriorityProvider priorityProvider, Y object) {
        if (object instanceof PriorityProvider) {
            object = ((PriorityProvider)object).getPriority();
            do {
                return ((Enum)object).ordinal() - priorityProvider.getPriority().ordinal();
                break;
            } while (true);
        }
        object = NORMAL;
        return ((Enum)object).ordinal() - priorityProvider.getPriority().ordinal();
    }
}

