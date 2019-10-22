/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CreateReportRequest;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.InvalidSessionReport;
import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.SessionReport;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ReportUploader {
    static final Map<String, String> HEADER_INVALID_CLS_FILE;
    private static final short[] RETRY_INTERVALS;
    private static final FilenameFilter crashFileFilter;
    private final String apiKey;
    private final CreateReportSpiCall createReportCall;
    private final Object fileAccessLock = new Object();
    private Thread uploadThread;

    static {
        crashFileFilter = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string2) {
                return string2.endsWith(".cls") && !string2.contains("Session");
            }
        };
        HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
        RETRY_INTERVALS = new short[]{10, 20, 30, 60, 120, 300};
    }

    public ReportUploader(String string2, CreateReportSpiCall createReportSpiCall) {
        if (createReportSpiCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.createReportCall = createReportSpiCall;
        this.apiKey = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<Report> findReports() {
        Object object;
        Object object2;
        Fabric.getLogger().d("CrashlyticsCore", "Checking for crash reports...");
        Object object3 = CrashlyticsCore.getInstance().getController();
        LinkedList<Report> linkedList = this.fileAccessLock;
        synchronized (linkedList) {
            object2 = ((CrashlyticsController)object3).getFilesDir().listFiles(crashFileFilter);
            object3 = ((CrashlyticsController)object3).getInvalidFilesDir().listFiles();
        }
        linkedList = new LinkedList<Report>();
        for (Object object4 : object2) {
            Fabric.getLogger().d("CrashlyticsCore", "Found crash report " + ((File)object4).getPath());
            linkedList.add(new SessionReport((File)object4));
        }
        object2 = new HashMap();
        if (object3 != null) {
            for (Object object4 : object3) {
                object = CrashlyticsController.getSessionIdFromSessionFile((File)object4);
                if (!object2.containsKey(object)) {
                    object2.put(object, new LinkedList());
                }
                ((List)object2.get(object)).add(object4);
            }
        }
        for (Object object4 : object2.keySet()) {
            Fabric.getLogger().d("CrashlyticsCore", "Found invalid session: " + (String)object4);
            object = (List)object2.get(object4);
            linkedList.add(new InvalidSessionReport((String)object4, object.toArray(new File[object.size()])));
        }
        if (linkedList.isEmpty()) {
            Fabric.getLogger().d("CrashlyticsCore", "No reports found.");
        }
        return linkedList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean forceUpload(Report report) {
        boolean bl = false;
        Object object = this.fileAccessLock;
        synchronized (object) {
            try {
                Object object2 = new CreateReportRequest(this.apiKey, report);
                boolean bl2 = this.createReportCall.invoke((CreateReportRequest)object2);
                Logger logger = Fabric.getLogger();
                StringBuilder stringBuilder = new StringBuilder().append("Crashlytics report upload ");
                object2 = bl2 ? "complete: " : "FAILED: ";
                logger.i("CrashlyticsCore", stringBuilder.append((String)object2).append(report.getIdentifier()).toString());
                boolean bl3 = bl;
                if (!bl2) return bl3;
                report.remove();
                return true;
            }
            catch (Exception exception) {
                Fabric.getLogger().e("CrashlyticsCore", "Error occurred sending report " + report, exception);
                return bl;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void uploadReports(float f, SendCheck sendCheck) {
        synchronized (this) {
            if (this.uploadThread != null) {
                Fabric.getLogger().d("CrashlyticsCore", "Report upload has already been started.");
            } else {
                this.uploadThread = new Thread((Runnable)new Worker(f, sendCheck), "Crashlytics Report Uploader");
                this.uploadThread.start();
            }
            return;
        }
    }

    static final class AlwaysSendCheck
    implements SendCheck {
        AlwaysSendCheck() {
        }

        @Override
        public boolean canSendReports() {
            return true;
        }
    }

    static interface SendCheck {
        public boolean canSendReports();
    }

    private class Worker
    extends BackgroundPriorityRunnable {
        private final float delay;
        private final SendCheck sendCheck;

        Worker(float f, SendCheck sendCheck) {
            this.delay = f;
            this.sendCheck = sendCheck;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void attemptUploadWithRetry() {
            Fabric.getLogger().d("CrashlyticsCore", "Starting report processing in " + this.delay + " second(s)...");
            if (this.delay > 0.0f) {
                try {
                    Thread.sleep((long)(this.delay * 1000.0f));
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            CrashlyticsController crashlyticsController = CrashlyticsCore.getInstance().getController();
            Object object = ReportUploader.this.findReports();
            if (crashlyticsController.isHandlingException()) return;
            if (!object.isEmpty() && !this.sendCheck.canSendReports()) {
                Fabric.getLogger().d("CrashlyticsCore", "User declined to send. Removing " + object.size() + " Report(s).");
                object = object.iterator();
                while (object.hasNext()) {
                    ((Report)object.next()).remove();
                }
                return;
            } else {
                int n = 0;
                while (!object.isEmpty() && !crashlyticsController.isHandlingException()) {
                    Object object2;
                    Fabric.getLogger().d("CrashlyticsCore", "Attempting to send " + object.size() + " report(s)");
                    object = object.iterator();
                    while (object.hasNext()) {
                        object2 = (Report)object.next();
                        ReportUploader.this.forceUpload((Report)object2);
                    }
                    object = object2 = ReportUploader.this.findReports();
                    if (object2.isEmpty()) continue;
                    long l = RETRY_INTERVALS[Math.min(n, RETRY_INTERVALS.length - 1)];
                    Fabric.getLogger().d("CrashlyticsCore", "Report submisson: scheduling delayed retry in " + l + " seconds");
                    try {
                        Thread.sleep(1000L * l);
                        ++n;
                        object = object2;
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRun() {
            try {
                this.attemptUploadWithRetry();
            }
            catch (Exception exception) {
                Fabric.getLogger().e("CrashlyticsCore", "An unexpected error occurred while attempting to upload crash reports.", exception);
            }
            ReportUploader.this.uploadThread = null;
        }
    }

}

