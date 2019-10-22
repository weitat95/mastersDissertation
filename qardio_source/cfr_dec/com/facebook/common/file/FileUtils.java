/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.file;

import com.facebook.common.internal.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {
    /*
     * Enabled aggressive block sorting
     */
    public static void mkdirs(File file) throws CreateDirectoryException {
        if (file.exists()) {
            if (file.isDirectory()) return;
            {
                if (!file.delete()) {
                    throw new CreateDirectoryException(file.getAbsolutePath(), new FileDeleteException(file.getAbsolutePath()));
                }
            }
        }
        if (!file.mkdirs() && !file.isDirectory()) throw new CreateDirectoryException(file.getAbsolutePath());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static void rename(File file, File file2) throws RenameException {
        void var2_4;
        Preconditions.checkNotNull(file);
        Preconditions.checkNotNull(file2);
        file2.delete();
        if (file.renameTo(file2)) {
            return;
        }
        Object var2_2 = null;
        if (file2.exists()) {
            FileDeleteException fileDeleteException = new FileDeleteException(file2.getAbsolutePath());
            throw new RenameException("Unknown error renaming " + file.getAbsolutePath() + " to " + file2.getAbsolutePath(), (Throwable)var2_4);
        }
        if (!file.getParentFile().exists()) {
            ParentDirNotFoundException parentDirNotFoundException = new ParentDirNotFoundException(file.getAbsolutePath());
            throw new RenameException("Unknown error renaming " + file.getAbsolutePath() + " to " + file2.getAbsolutePath(), (Throwable)var2_4);
        }
        if (file.exists()) throw new RenameException("Unknown error renaming " + file.getAbsolutePath() + " to " + file2.getAbsolutePath(), (Throwable)var2_4);
        FileNotFoundException fileNotFoundException = new FileNotFoundException(file.getAbsolutePath());
        throw new RenameException("Unknown error renaming " + file.getAbsolutePath() + " to " + file2.getAbsolutePath(), (Throwable)var2_4);
    }

    public static class CreateDirectoryException
    extends IOException {
        public CreateDirectoryException(String string2) {
            super(string2);
        }

        public CreateDirectoryException(String string2, Throwable throwable) {
            super(string2);
            this.initCause(throwable);
        }
    }

    public static class FileDeleteException
    extends IOException {
        public FileDeleteException(String string2) {
            super(string2);
        }
    }

    public static class ParentDirNotFoundException
    extends FileNotFoundException {
        public ParentDirNotFoundException(String string2) {
            super(string2);
        }
    }

    public static class RenameException
    extends IOException {
        public RenameException(String string2, Throwable throwable) {
            super(string2);
            this.initCause(throwable);
        }
    }

}

