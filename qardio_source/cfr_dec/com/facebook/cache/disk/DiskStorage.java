/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.WriterCallback;
import java.io.IOException;
import java.util.Collection;

public interface DiskStorage {
    public Collection<Entry> getEntries() throws IOException;

    public BinaryResource getResource(String var1, Object var2) throws IOException;

    public String getStorageName();

    public Inserter insert(String var1, Object var2) throws IOException;

    public boolean isExternal();

    public void purgeUnexpectedResources();

    public long remove(Entry var1) throws IOException;

    public static interface Entry {
        public String getId();

        public long getSize();

        public long getTimestamp();
    }

    public static interface Inserter {
        public boolean cleanUp();

        public BinaryResource commit(Object var1) throws IOException;

        public void writeData(WriterCallback var1, Object var2) throws IOException;
    }

}

