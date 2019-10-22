/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.disk.DefaultDiskStorage;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.common.file.FileTree;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.Nullable;

public class DynamicDefaultDiskStorage
implements DiskStorage {
    private static final Class<?> TAG = DynamicDefaultDiskStorage.class;
    private final String mBaseDirectoryName;
    private final Supplier<File> mBaseDirectoryPathSupplier;
    private final CacheErrorLogger mCacheErrorLogger;
    volatile State mCurrentState;
    private final int mVersion;

    public DynamicDefaultDiskStorage(int n, Supplier<File> supplier, String string2, CacheErrorLogger cacheErrorLogger) {
        this.mVersion = n;
        this.mCacheErrorLogger = cacheErrorLogger;
        this.mBaseDirectoryPathSupplier = supplier;
        this.mBaseDirectoryName = string2;
        this.mCurrentState = new State(null, null);
    }

    private void createStorage() throws IOException {
        File file = new File(this.mBaseDirectoryPathSupplier.get(), this.mBaseDirectoryName);
        this.createRootDirectoryIfNecessary(file);
        this.mCurrentState = new State(file, new DefaultDiskStorage(file, this.mVersion, this.mCacheErrorLogger));
    }

    private boolean shouldCreateNewStorage() {
        State state = this.mCurrentState;
        return state.delegate == null || state.rootDirectory == null || !state.rootDirectory.exists();
    }

    void createRootDirectoryIfNecessary(File file) throws IOException {
        try {
            FileUtils.mkdirs(file);
        }
        catch (FileUtils.CreateDirectoryException createDirectoryException) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, "createRootDirectoryIfNecessary", createDirectoryException);
            throw createDirectoryException;
        }
        FLog.d(TAG, "Created cache directory %s", file.getAbsolutePath());
    }

    void deleteOldStorageIfNecessary() {
        if (this.mCurrentState.delegate != null && this.mCurrentState.rootDirectory != null) {
            FileTree.deleteRecursively(this.mCurrentState.rootDirectory);
        }
    }

    DiskStorage get() throws IOException {
        synchronized (this) {
            if (this.shouldCreateNewStorage()) {
                this.deleteOldStorageIfNecessary();
                this.createStorage();
            }
            DiskStorage diskStorage = Preconditions.checkNotNull(this.mCurrentState.delegate);
            return diskStorage;
        }
    }

    @Override
    public Collection<DiskStorage.Entry> getEntries() throws IOException {
        return this.get().getEntries();
    }

    @Override
    public BinaryResource getResource(String string2, Object object) throws IOException {
        return this.get().getResource(string2, object);
    }

    @Override
    public String getStorageName() {
        try {
            String string2 = this.get().getStorageName();
            return string2;
        }
        catch (IOException iOException) {
            return "";
        }
    }

    @Override
    public DiskStorage.Inserter insert(String string2, Object object) throws IOException {
        return this.get().insert(string2, object);
    }

    @Override
    public boolean isExternal() {
        try {
            boolean bl = this.get().isExternal();
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public void purgeUnexpectedResources() {
        try {
            this.get().purgeUnexpectedResources();
            return;
        }
        catch (IOException iOException) {
            FLog.e(TAG, "purgeUnexpectedResources", (Throwable)iOException);
            return;
        }
    }

    @Override
    public long remove(DiskStorage.Entry entry) throws IOException {
        return this.get().remove(entry);
    }

    static class State {
        @Nullable
        public final DiskStorage delegate;
        @Nullable
        public final File rootDirectory;

        State(@Nullable File file, @Nullable DiskStorage diskStorage) {
            this.delegate = diskStorage;
            this.rootDirectory = file;
        }
    }

}

