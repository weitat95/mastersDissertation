/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.util;

public enum TriState {
    YES,
    NO,
    UNSET;


    public static TriState valueOf(boolean bl) {
        if (bl) {
            return YES;
        }
        return NO;
    }
}

