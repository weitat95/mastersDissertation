/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Environment
 *  javax.annotation.Nullable
 */
package com.facebook.cache.disk;

import android.os.Environment;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.common.file.FileTree;
import com.facebook.common.file.FileTreeVisitor;
import com.facebook.common.file.FileUtils;
import com.facebook.common.internal.CountingOutputStream;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class DefaultDiskStorage
implements DiskStorage {
    private static final Class<?> TAG = DefaultDiskStorage.class;
    static final long TEMP_FILE_LIFETIME_MS = TimeUnit.MINUTES.toMillis(30L);
    private final CacheErrorLogger mCacheErrorLogger;
    private final Clock mClock;
    private final boolean mIsExternal;
    private final File mRootDirectory;
    private final File mVersionDirectory;

    public DefaultDiskStorage(File file, int n, CacheErrorLogger cacheErrorLogger) {
        Preconditions.checkNotNull(file);
        this.mRootDirectory = file;
        this.mIsExternal = DefaultDiskStorage.isExternal(file, cacheErrorLogger);
        this.mVersionDirectory = new File(this.mRootDirectory, DefaultDiskStorage.getVersionSubdirectoryName(n));
        this.mCacheErrorLogger = cacheErrorLogger;
        this.recreateDirectoryIfVersionChanges();
        this.mClock = SystemClock.get();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private long doRemove(File file) {
        if (!file.exists()) {
            return 0L;
        }
        long l = file.length();
        if (file.delete()) return l;
        return -1L;
    }

    private String getFilename(String object) {
        object = new FileInfo(FileType.CONTENT, (String)object);
        return ((FileInfo)object).toPath(this.getSubdirectoryPath(((FileInfo)object).resourceId));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private FileInfo getShardFileInfo(File object) {
        FileInfo fileInfo = FileInfo.fromFile((File)object);
        if (fileInfo == null) {
            return null;
        }
        if (!this.getSubdirectory(fileInfo.resourceId).equals(((File)object).getParentFile())) return null;
        return fileInfo;
    }

    private File getSubdirectory(String string2) {
        return new File(this.getSubdirectoryPath(string2));
    }

    private String getSubdirectoryPath(String string2) {
        int n = Math.abs(string2.hashCode() % 100);
        return this.mVersionDirectory + File.separator + String.valueOf(n);
    }

    static String getVersionSubdirectoryName(int n) {
        return String.format((Locale)null, "%s.ols%d.%d", "v2", 100, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isExternal(File object, CacheErrorLogger cacheErrorLogger) {
        boolean bl2 = false;
        Object object2 = null;
        Object object3 = Environment.getExternalStorageDirectory();
        boolean bl = bl2;
        if (object3 == null) return bl;
        object3 = ((File)object3).toString();
        try {
            object2 = object = ((File)object).getCanonicalPath();
        }
        catch (IOException iOException) {
            cacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.OTHER, TAG, "failed to read folder to check if external: " + (String)object2, iOException);
            return false;
        }
        boolean bl3 = ((String)object).contains((CharSequence)object3);
        bl = bl2;
        if (!bl3) return bl;
        return true;
    }

    private void mkdirs(File file, String string2) throws IOException {
        try {
            FileUtils.mkdirs(file);
            return;
        }
        catch (FileUtils.CreateDirectoryException createDirectoryException) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, string2, createDirectoryException);
            throw createDirectoryException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void recreateDirectoryIfVersionChanges() {
        boolean bl = false;
        if (!this.mRootDirectory.exists()) {
            bl = true;
        } else if (!this.mVersionDirectory.exists()) {
            bl = true;
            FileTree.deleteRecursively(this.mRootDirectory);
        }
        if (!bl) return;
        try {
            FileUtils.mkdirs(this.mVersionDirectory);
            return;
        }
        catch (FileUtils.CreateDirectoryException createDirectoryException) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_DIR, TAG, "version directory could not be created: " + this.mVersionDirectory, null);
            return;
        }
    }

    File getContentFileFor(String string2) {
        return new File(this.getFilename(string2));
    }

    public List<DiskStorage.Entry> getEntries() throws IOException {
        EntriesCollector entriesCollector = new EntriesCollector();
        FileTree.walkFileTree(this.mVersionDirectory, entriesCollector);
        return entriesCollector.getEntries();
    }

    @Override
    public BinaryResource getResource(String object, Object object2) {
        if (((File)(object = this.getContentFileFor((String)object))).exists()) {
            ((File)object).setLastModified(this.mClock.now());
            return FileBinaryResource.createOrNull((File)object);
        }
        return null;
    }

    @Override
    public String getStorageName() {
        String string2 = this.mRootDirectory.getAbsolutePath();
        return "_" + string2.substring(string2.lastIndexOf(47) + 1, string2.length()) + "_" + string2.hashCode();
    }

    @Override
    public DiskStorage.Inserter insert(String object, Object object2) throws IOException {
        object2 = new FileInfo(FileType.TEMP, (String)object);
        File file = this.getSubdirectory(((FileInfo)object2).resourceId);
        if (!file.exists()) {
            this.mkdirs(file, "insert");
        }
        try {
            object = new InserterImpl((String)object, ((FileInfo)object2).createTempFile(file));
            return object;
        }
        catch (IOException iOException) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_CREATE_TEMPFILE, TAG, "insert", iOException);
            throw iOException;
        }
    }

    @Override
    public boolean isExternal() {
        return this.mIsExternal;
    }

    @Override
    public void purgeUnexpectedResources() {
        FileTree.walkFileTree(this.mRootDirectory, new PurgingVisitor());
    }

    @Override
    public long remove(DiskStorage.Entry entry) {
        return this.doRemove(((EntryImpl)entry).getResource().getFile());
    }

    private class EntriesCollector
    implements FileTreeVisitor {
        private final List<DiskStorage.Entry> result = new ArrayList<DiskStorage.Entry>();

        private EntriesCollector() {
        }

        public List<DiskStorage.Entry> getEntries() {
            return Collections.unmodifiableList(this.result);
        }

        @Override
        public void postVisitDirectory(File file) {
        }

        @Override
        public void preVisitDirectory(File file) {
        }

        @Override
        public void visitFile(File file) {
            FileInfo fileInfo = DefaultDiskStorage.this.getShardFileInfo(file);
            if (fileInfo != null && fileInfo.type == FileType.CONTENT) {
                this.result.add(new EntryImpl(fileInfo.resourceId, file));
            }
        }
    }

    static class EntryImpl
    implements DiskStorage.Entry {
        private final String id;
        private final FileBinaryResource resource;
        private long size;
        private long timestamp;

        private EntryImpl(String string2, File file) {
            Preconditions.checkNotNull(file);
            this.id = Preconditions.checkNotNull(string2);
            this.resource = FileBinaryResource.createOrNull(file);
            this.size = -1L;
            this.timestamp = -1L;
        }

        @Override
        public String getId() {
            return this.id;
        }

        public FileBinaryResource getResource() {
            return this.resource;
        }

        @Override
        public long getSize() {
            if (this.size < 0L) {
                this.size = this.resource.size();
            }
            return this.size;
        }

        @Override
        public long getTimestamp() {
            if (this.timestamp < 0L) {
                this.timestamp = this.resource.getFile().lastModified();
            }
            return this.timestamp;
        }
    }

    private static class FileInfo {
        public final String resourceId;
        public final FileType type;

        private FileInfo(FileType fileType, String string2) {
            this.type = fileType;
            this.resourceId = string2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Nullable
        public static FileInfo fromFile(File object) {
            FileType fileType;
            block3: {
                int n;
                String string2;
                block4: {
                    block2: {
                        n = ((String)(object = ((File)object).getName())).lastIndexOf(46);
                        if (n <= 0 || (fileType = FileType.fromExtension(((String)object).substring(n))) == null) break block2;
                        string2 = ((String)object).substring(0, n);
                        object = string2;
                        if (!fileType.equals((Object)FileType.TEMP)) break block3;
                        n = string2.lastIndexOf(46);
                        if (n > 0) break block4;
                    }
                    return null;
                }
                object = string2.substring(0, n);
            }
            return new FileInfo(fileType, (String)object);
        }

        public File createTempFile(File file) throws IOException {
            return File.createTempFile(this.resourceId + ".", ".tmp", file);
        }

        public String toPath(String string2) {
            return string2 + File.separator + this.resourceId + this.type.extension;
        }

        public String toString() {
            return (Object)((Object)this.type) + "(" + this.resourceId + ")";
        }
    }

    private static enum FileType {
        CONTENT(".cnt"),
        TEMP(".tmp");

        public final String extension;

        private FileType(String string3) {
            this.extension = string3;
        }

        public static FileType fromExtension(String string2) {
            if (".cnt".equals(string2)) {
                return CONTENT;
            }
            if (".tmp".equals(string2)) {
                return TEMP;
            }
            return null;
        }
    }

    private static class IncompleteFileException
    extends IOException {
        public final long actual;
        public final long expected;

        public IncompleteFileException(long l, long l2) {
            super("File was not written completely. Expected: " + l + ", found: " + l2);
            this.expected = l;
            this.actual = l2;
        }
    }

    class InserterImpl
    implements DiskStorage.Inserter {
        private final String mResourceId;
        final File mTemporaryFile;

        public InserterImpl(String string2, File file) {
            this.mResourceId = string2;
            this.mTemporaryFile = file;
        }

        @Override
        public boolean cleanUp() {
            return !this.mTemporaryFile.exists() || this.mTemporaryFile.delete();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public BinaryResource commit(Object object) throws IOException {
            File file;
            block8: {
                file = DefaultDiskStorage.this.getContentFileFor(this.mResourceId);
                try {
                    FileUtils.rename(this.mTemporaryFile, file);
                    if (!file.exists()) break block8;
                    file.setLastModified(DefaultDiskStorage.this.mClock.now());
                }
                catch (FileUtils.RenameException renameException) {
                    void var1_5;
                    Throwable throwable = renameException.getCause();
                    if (throwable == null) {
                        CacheErrorLogger.CacheErrorCategory cacheErrorCategory = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                    } else if (throwable instanceof FileUtils.ParentDirNotFoundException) {
                        CacheErrorLogger.CacheErrorCategory cacheErrorCategory = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND;
                    } else if (throwable instanceof FileNotFoundException) {
                        CacheErrorLogger.CacheErrorCategory cacheErrorCategory = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND;
                    } else {
                        CacheErrorLogger.CacheErrorCategory cacheErrorCategory = CacheErrorLogger.CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                    }
                    DefaultDiskStorage.this.mCacheErrorLogger.logError((CacheErrorLogger.CacheErrorCategory)var1_5, TAG, "commit", renameException);
                    throw renameException;
                }
            }
            return FileBinaryResource.createOrNull(file);
        }

        @Override
        public void writeData(WriterCallback writerCallback, Object object) throws IOException {
            try {
                object = new FileOutputStream(this.mTemporaryFile);
            }
            catch (FileNotFoundException fileNotFoundException) {
                DefaultDiskStorage.this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.WRITE_UPDATE_FILE_NOT_FOUND, TAG, "updateResource", fileNotFoundException);
                throw fileNotFoundException;
            }
            try {
                CountingOutputStream countingOutputStream = new CountingOutputStream((OutputStream)object);
                writerCallback.write(countingOutputStream);
                countingOutputStream.flush();
                long l = countingOutputStream.getCount();
                if (this.mTemporaryFile.length() != l) {
                    throw new IncompleteFileException(l, this.mTemporaryFile.length());
                }
            }
            finally {
                ((FileOutputStream)object).close();
            }
        }
    }

    private class PurgingVisitor
    implements FileTreeVisitor {
        private boolean insideBaseDirectory;

        private PurgingVisitor() {
        }

        private boolean isExpectedFile(File file) {
            boolean bl = false;
            FileInfo fileInfo = DefaultDiskStorage.this.getShardFileInfo(file);
            if (fileInfo == null) {
                return false;
            }
            if (fileInfo.type == FileType.TEMP) {
                return this.isRecentFile(file);
            }
            if (fileInfo.type == FileType.CONTENT) {
                bl = true;
            }
            Preconditions.checkState(bl);
            return true;
        }

        private boolean isRecentFile(File file) {
            return file.lastModified() > DefaultDiskStorage.this.mClock.now() - TEMP_FILE_LIFETIME_MS;
        }

        @Override
        public void postVisitDirectory(File file) {
            if (!DefaultDiskStorage.this.mRootDirectory.equals(file) && !this.insideBaseDirectory) {
                file.delete();
            }
            if (this.insideBaseDirectory && file.equals(DefaultDiskStorage.this.mVersionDirectory)) {
                this.insideBaseDirectory = false;
            }
        }

        @Override
        public void preVisitDirectory(File file) {
            if (!this.insideBaseDirectory && file.equals(DefaultDiskStorage.this.mVersionDirectory)) {
                this.insideBaseDirectory = true;
            }
        }

        @Override
        public void visitFile(File file) {
            if (!this.insideBaseDirectory || !this.isExpectedFile(file)) {
                file.delete();
            }
        }
    }

}

