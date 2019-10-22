/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.res.AssetManager
 *  android.util.Log
 */
package com.bumptech.glide.load.data;

import android.content.res.AssetManager;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import java.io.IOException;

public abstract class AssetPathFetcher<T>
implements DataFetcher<T> {
    private final AssetManager assetManager;
    private final String assetPath;
    private T data;

    public AssetPathFetcher(AssetManager assetManager, String string2) {
        this.assetManager = assetManager;
        this.assetPath = string2;
    }

    @Override
    public void cancel() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cleanup() {
        block3: {
            if (this.data != null) {
                try {
                    this.close(this.data);
                    return;
                }
                catch (IOException iOException) {
                    if (!Log.isLoggable((String)"AssetUriFetcher", (int)2)) break block3;
                    Log.v((String)"AssetUriFetcher", (String)"Failed to close data", (Throwable)iOException);
                    return;
                }
            }
        }
    }

    protected abstract void close(T var1) throws IOException;

    @Override
    public String getId() {
        return this.assetPath;
    }

    @Override
    public T loadData(Priority priority) throws Exception {
        this.data = this.loadResource(this.assetManager, this.assetPath);
        return this.data;
    }

    protected abstract T loadResource(AssetManager var1, String var2) throws IOException;
}

