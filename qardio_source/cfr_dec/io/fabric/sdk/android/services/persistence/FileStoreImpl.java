/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.persistence;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;

public class FileStoreImpl
implements FileStore {
    private final String contentPath;
    private final Context context;
    private final String legacySupport;

    public FileStoreImpl(Kit kit) {
        if (kit.getContext() == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.context = kit.getContext();
        this.contentPath = kit.getPath();
        this.legacySupport = "Android/" + this.context.getPackageName();
    }

    @Override
    public File getFilesDir() {
        return this.prepare(this.context.getFilesDir());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    File prepare(File file) {
        if (file != null) {
            if (file.exists() || file.mkdirs()) {
                return file;
            }
            Fabric.getLogger().w("Fabric", "Couldn't create file");
            do {
                return null;
                break;
            } while (true);
        }
        Fabric.getLogger().d("Fabric", "Null File");
        return null;
    }
}

