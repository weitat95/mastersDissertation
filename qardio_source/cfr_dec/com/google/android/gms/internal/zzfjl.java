/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfjl
extends IOException {
    zzfjl(int n, int n2) {
        super(new StringBuilder(108).append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ").append(n).append(" limit ").append(n2).append(").").toString());
    }
}

