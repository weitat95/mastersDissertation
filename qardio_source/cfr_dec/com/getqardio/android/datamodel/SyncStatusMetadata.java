/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

public class SyncStatusMetadata {
    public long id;
    public int revision;
    public int syncStatus;

    public SyncStatusMetadata(long l, int n, int n2) {
        this.id = l;
        this.syncStatus = n;
        this.revision = n2;
    }
}

