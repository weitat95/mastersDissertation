/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.events;

import java.io.IOException;

public interface FileRollOverManager {
    public void cancelTimeBasedFileRollOver();

    public boolean rollFileOver() throws IOException;
}

