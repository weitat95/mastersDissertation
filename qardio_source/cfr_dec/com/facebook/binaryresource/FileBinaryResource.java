/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.binaryresource;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.common.internal.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileBinaryResource
implements BinaryResource {
    private final File mFile;

    private FileBinaryResource(File file) {
        this.mFile = Preconditions.checkNotNull(file);
    }

    public static FileBinaryResource createOrNull(File file) {
        if (file != null) {
            return new FileBinaryResource(file);
        }
        return null;
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof FileBinaryResource)) {
            return false;
        }
        object = (FileBinaryResource)object;
        return this.mFile.equals(((FileBinaryResource)object).mFile);
    }

    public File getFile() {
        return this.mFile;
    }

    public int hashCode() {
        return this.mFile.hashCode();
    }

    @Override
    public InputStream openStream() throws IOException {
        return new FileInputStream(this.mFile);
    }

    @Override
    public long size() {
        return this.mFile.length();
    }
}

