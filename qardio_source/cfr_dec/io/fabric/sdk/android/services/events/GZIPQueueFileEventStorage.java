/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.events.QueueFileEventStorage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPQueueFileEventStorage
extends QueueFileEventStorage {
    public GZIPQueueFileEventStorage(Context context, File file, String string2, String string3) throws IOException {
        super(context, file, string2, string3);
    }

    @Override
    public OutputStream getMoveOutputStream(File file) throws IOException {
        return new GZIPOutputStream(new FileOutputStream(file));
    }
}

