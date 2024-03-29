/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.events;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface EventsStorage {
    public void add(byte[] var1) throws IOException;

    public boolean canWorkingFileStore(int var1, int var2);

    public void deleteFilesInRollOverDirectory(List<File> var1);

    public void deleteWorkingFile();

    public List<File> getAllFilesInRollOverDirectory();

    public List<File> getBatchOfFilesToSend(int var1);

    public int getWorkingFileUsedSizeInBytes();

    public boolean isWorkingFileEmpty();

    public void rollOver(String var1) throws IOException;
}

