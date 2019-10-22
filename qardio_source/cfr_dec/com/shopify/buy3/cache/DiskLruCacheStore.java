/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.cache.ResponseCacheRecord;
import com.shopify.buy3.cache.ResponseCacheRecordEditor;
import com.shopify.buy3.cache.ResponseCacheStore;
import java.io.File;
import java.io.IOException;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import okio.Sink;
import okio.Source;

final class DiskLruCacheStore
implements ResponseCacheStore {
    private final DiskLruCache cache;

    DiskLruCacheStore(File file, long l) {
        this.cache = DiskLruCache.create(FileSystem.SYSTEM, file, 99991, 2, l);
    }

    @Override
    public ResponseCacheRecord cacheRecord(String object) throws IOException {
        if ((object = this.cache.get((String)object)) == null) {
            return null;
        }
        return new ResponseCacheRecord((DiskLruCache.Snapshot)object){
            final /* synthetic */ DiskLruCache.Snapshot val$snapshot;
            {
                this.val$snapshot = snapshot;
            }

            @Override
            public Source bodySource() {
                return this.val$snapshot.getSource(1);
            }

            @Override
            public void close() {
                this.val$snapshot.close();
            }

            @Override
            public Source headerSource() {
                return this.val$snapshot.getSource(0);
            }
        };
    }

    @Override
    public ResponseCacheRecordEditor cacheRecordEditor(String object) throws IOException {
        if ((object = this.cache.edit((String)object)) == null) {
            return null;
        }
        return new ResponseCacheRecordEditor((DiskLruCache.Editor)object){
            final /* synthetic */ DiskLruCache.Editor val$editor;
            {
                this.val$editor = editor;
            }

            @Override
            public void abort() throws IOException {
                this.val$editor.abort();
            }

            @Override
            public Sink bodySink() {
                return this.val$editor.newSink(1);
            }

            @Override
            public void commit() throws IOException {
                this.val$editor.commit();
            }

            @Override
            public Sink headerSink() {
                return this.val$editor.newSink(0);
            }
        };
    }

    @Override
    public void remove(String string2) throws IOException {
        this.cache.remove(string2);
    }

}

