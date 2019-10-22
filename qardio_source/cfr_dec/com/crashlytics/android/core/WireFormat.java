/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

final class WireFormat {
    static final int MESSAGE_SET_ITEM_END_TAG;
    static final int MESSAGE_SET_ITEM_TAG;
    static final int MESSAGE_SET_MESSAGE_TAG;
    static final int MESSAGE_SET_TYPE_ID_TAG;

    static {
        MESSAGE_SET_ITEM_TAG = WireFormat.makeTag(1, 3);
        MESSAGE_SET_ITEM_END_TAG = WireFormat.makeTag(1, 4);
        MESSAGE_SET_TYPE_ID_TAG = WireFormat.makeTag(2, 0);
        MESSAGE_SET_MESSAGE_TAG = WireFormat.makeTag(3, 2);
    }

    static int makeTag(int n, int n2) {
        return n << 3 | n2;
    }
}

