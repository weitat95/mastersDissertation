/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.FileLogStore;
import com.crashlytics.android.core.QueueFileLogStore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;
import java.util.Set;

class LogFileManager {
    private static final NoopLogStore NOOP_LOG_STORE = new NoopLogStore();
    private final Context context;
    private FileLogStore currentLog;
    private final FileStore fileStore;

    public LogFileManager(Context context, FileStore fileStore) {
        this(context, fileStore, null);
    }

    public LogFileManager(Context context, FileStore fileStore, String string2) {
        this.context = context;
        this.fileStore = fileStore;
        this.currentLog = NOOP_LOG_STORE;
        this.setCurrentSession(string2);
    }

    private File getLogFileDir() {
        File file = new File(this.fileStore.getFilesDir(), "log-files");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private String getSessionIdForFile(File object) {
        int n = ((String)(object = ((File)object).getName())).lastIndexOf(".temp");
        if (n == -1) {
            return object;
        }
        return ((String)object).substring("crashlytics-userlog-".length(), n);
    }

    private File getWorkingFileForSession(String string2) {
        string2 = "crashlytics-userlog-" + string2 + ".temp";
        return new File(this.getLogFileDir(), string2);
    }

    public void clearLog() {
        this.currentLog.deleteLogFile();
    }

    public void discardOldLogFiles(Set<String> set) {
        File[] arrfile = this.getLogFileDir().listFiles();
        if (arrfile != null) {
            for (File file : arrfile) {
                if (set.contains(this.getSessionIdForFile(file))) continue;
                file.delete();
            }
        }
    }

    public ByteString getByteStringForLog() {
        return this.currentLog.getLogAsByteString();
    }

    public final void setCurrentSession(String string2) {
        this.currentLog.closeLogFile();
        this.currentLog = NOOP_LOG_STORE;
        if (string2 == null) {
            return;
        }
        if (!CommonUtils.getBooleanResourceValue(this.context, "com.crashlytics.CollectCustomLogs", true)) {
            Fabric.getLogger().d("CrashlyticsCore", "Preferences requested no custom logs. Aborting log file creation.");
            return;
        }
        this.setLogFile(this.getWorkingFileForSession(string2), 65536);
    }

    void setLogFile(File file, int n) {
        this.currentLog = new QueueFileLogStore(file, n);
    }

    public void writeToLog(long l, String string2) {
        this.currentLog.writeToLog(l, string2);
    }

    private static final class NoopLogStore
    implements FileLogStore {
        private NoopLogStore() {
        }

        @Override
        public void closeLogFile() {
        }

        @Override
        public void deleteLogFile() {
        }

        @Override
        public ByteString getLogAsByteString() {
            return null;
        }

        @Override
        public void writeToLog(long l, String string2) {
        }
    }

}

