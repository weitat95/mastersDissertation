/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.common;

import javax.annotation.Nullable;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;


    /*
     * Enabled aggressive block sorting
     */
    public static Priority getHigherPriority(@Nullable Priority priority, @Nullable Priority priority2) {
        block5: {
            block4: {
                if (priority == null) break block4;
                if (priority2 == null) {
                    return priority;
                }
                if (priority.ordinal() > priority2.ordinal()) break block5;
            }
            return priority2;
        }
        return priority;
    }
}

