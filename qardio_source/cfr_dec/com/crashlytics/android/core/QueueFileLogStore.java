/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.FileLogStore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore
implements FileLogStore {
    private QueueFile logFile;
    private final int maxLogSize;
    private final File workingFile;

    public QueueFileLogStore(File file, int n) {
        this.workingFile = file;
        this.maxLogSize = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doWriteToLog(long l, String object) {
        if (this.logFile == null) return;
        {
            Object object2 = object;
            if (object == null) {
                object2 = "null";
            }
            try {
                int n = this.maxLogSize / 4;
                object = object2;
                if (((String)object2).length() > n) {
                    object = "..." + ((String)object2).substring(((String)object2).length() - n);
                }
                object = ((String)object).replaceAll("\r", " ").replaceAll("\n", " ");
                object = String.format(Locale.US, "%d %s%n", l, object).getBytes("UTF-8");
                this.logFile.add((byte[])object);
                while (!this.logFile.isEmpty() && this.logFile.usedBytes() > this.maxLogSize) {
                    this.logFile.remove();
                }
                return;
            }
            catch (IOException iOException) {
                Fabric.getLogger().e("CrashlyticsCore", "There was a problem writing to the Crashlytics log.", iOException);
                return;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void openLogFile() {
        if (this.logFile != null) return;
        try {
            this.logFile = new QueueFile(this.workingFile);
            return;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("CrashlyticsCore", "Could not open log file: " + this.workingFile, iOException);
            return;
        }
    }

    @Override
    public void closeLogFile() {
        CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
    }

    @Override
    public void deleteLogFile() {
        this.closeLogFile();
        this.workingFile.delete();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ByteString getLogAsByteString() {
        if (!this.workingFile.exists()) {
            return null;
        }
        this.openLogFile();
        if (this.logFile == null) return null;
        final int[] arrn = new int[]{0};
        final byte[] arrby = new byte[this.logFile.usedBytes()];
        try {
            this.logFile.forEach(new QueueFile.ElementReader(){

                @Override
                public void read(InputStream inputStream, int n) throws IOException {
                    int[] arrn2;
                    try {
                        inputStream.read(arrby, arrn[0], n);
                        arrn2 = arrn;
                    }
                    catch (Throwable throwable) {
                        inputStream.close();
                        throw throwable;
                    }
                    arrn2[0] = arrn2[0] + n;
                    inputStream.close();
                }
            });
            return ByteString.copyFrom(arrby, 0, arrn[0]);
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("CrashlyticsCore", "A problem occurred while reading the Crashlytics log file.", iOException);
            return ByteString.copyFrom(arrby, 0, arrn[0]);
        }
    }

    @Override
    public void writeToLog(long l, String string2) {
        this.openLogFile();
        this.doWriteToLog(l, string2);
    }

}

