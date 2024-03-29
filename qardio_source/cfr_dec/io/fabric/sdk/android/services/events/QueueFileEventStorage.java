/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import io.fabric.sdk.android.services.events.EventsStorage;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class QueueFileEventStorage
implements EventsStorage {
    private final Context context;
    private QueueFile queueFile;
    private File targetDirectory;
    private final String targetDirectoryName;
    private final File workingDirectory;
    private final File workingFile;

    public QueueFileEventStorage(Context context, File file, String string2, String string3) throws IOException {
        this.context = context;
        this.workingDirectory = file;
        this.targetDirectoryName = string3;
        this.workingFile = new File(this.workingDirectory, string2);
        this.queueFile = new QueueFile(this.workingFile);
        this.createTargetDirectory();
    }

    private void createTargetDirectory() {
        this.targetDirectory = new File(this.workingDirectory, this.targetDirectoryName);
        if (!this.targetDirectory.exists()) {
            this.targetDirectory.mkdirs();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void move(File file, File object) throws IOException {
        Object object2 = null;
        Object object3 = null;
        Object var5_5 = null;
        Object object4 = new FileInputStream(file);
        object3 = object2;
        object3 = object = this.getMoveOutputStream((File)object);
        CommonUtils.copyStream((InputStream)object4, (OutputStream)object, new byte[1024]);
        CommonUtils.closeOrLog((Closeable)object4, "Failed to close file input stream");
        CommonUtils.closeOrLog((Closeable)object, "Failed to close output stream");
        file.delete();
        return;
        catch (Throwable throwable) {
            block4: {
                object = var5_5;
                break block4;
                catch (Throwable throwable2) {
                    object = object4;
                    object4 = throwable2;
                }
            }
            CommonUtils.closeOrLog((Closeable)object, "Failed to close file input stream");
            CommonUtils.closeOrLog(object3, "Failed to close output stream");
            file.delete();
            throw object4;
        }
    }

    @Override
    public void add(byte[] arrby) throws IOException {
        this.queueFile.add(arrby);
    }

    @Override
    public boolean canWorkingFileStore(int n, int n2) {
        return this.queueFile.hasSpaceFor(n, n2);
    }

    @Override
    public void deleteFilesInRollOverDirectory(List<File> object) {
        object = object.iterator();
        while (object.hasNext()) {
            File file = (File)object.next();
            CommonUtils.logControlled(this.context, String.format("deleting sent analytics file %s", file.getName()));
            file.delete();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void deleteWorkingFile() {
        try {
            this.queueFile.close();
        }
        catch (IOException iOException) {}
        this.workingFile.delete();
    }

    @Override
    public List<File> getAllFilesInRollOverDirectory() {
        return Arrays.asList(this.targetDirectory.listFiles());
    }

    @Override
    public List<File> getBatchOfFilesToSend(int n) {
        ArrayList<File> arrayList = new ArrayList<File>();
        File[] arrfile = this.targetDirectory.listFiles();
        int n2 = arrfile.length;
        int n3 = 0;
        do {
            block4: {
                block3: {
                    if (n3 >= n2) break block3;
                    arrayList.add(arrfile[n3]);
                    if (arrayList.size() < n) break block4;
                }
                return arrayList;
            }
            ++n3;
        } while (true);
    }

    public OutputStream getMoveOutputStream(File file) throws IOException {
        return new FileOutputStream(file);
    }

    @Override
    public int getWorkingFileUsedSizeInBytes() {
        return this.queueFile.usedBytes();
    }

    @Override
    public boolean isWorkingFileEmpty() {
        return this.queueFile.isEmpty();
    }

    @Override
    public void rollOver(String string2) throws IOException {
        this.queueFile.close();
        this.move(this.workingFile, new File(this.targetDirectory, string2));
        this.queueFile = new QueueFile(this.workingFile);
    }
}

