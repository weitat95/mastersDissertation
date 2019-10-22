/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Environment
 *  android.os.StatFs
 */
package com.crashlytics.android.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.CLSUUID;
import com.crashlytics.android.core.ClsFileOutputStream;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CrashPromptDialog;
import com.crashlytics.android.core.CrashlyticsBackgroundWorker;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.DefaultCreateReportSpiCall;
import com.crashlytics.android.core.DevicePowerStateListener;
import com.crashlytics.android.core.LogFileManager;
import com.crashlytics.android.core.MetaDataStore;
import com.crashlytics.android.core.MiddleOutFallbackStrategy;
import com.crashlytics.android.core.NativeCrashWriter;
import com.crashlytics.android.core.PreferenceManager;
import com.crashlytics.android.core.RemoveRepeatsStrategy;
import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.ReportUploader;
import com.crashlytics.android.core.SessionProtobufHelper;
import com.crashlytics.android.core.SessionReport;
import com.crashlytics.android.core.StackTraceTrimmingStrategy;
import com.crashlytics.android.core.TrimmedThrowableData;
import com.crashlytics.android.core.UnityVersionProvider;
import com.crashlytics.android.core.UserMetaData;
import com.crashlytics.android.core.Utils;
import com.crashlytics.android.core.internal.models.BinaryImageData;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.crashlytics.android.core.internal.models.SignalData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrashlyticsController {
    static final FilenameFilter ANY_SESSION_FILENAME_FILTER;
    private static final String[] INITIAL_SESSION_PART_TAGS;
    static final Comparator<File> LARGEST_FILE_NAME_FIRST;
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER;
    static final FilenameFilter SESSION_FILE_FILTER;
    private static final Pattern SESSION_FILE_PATTERN;
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST;
    private final AppData appData;
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsUncaughtExceptionHandler crashHandler;
    private final CrashlyticsCore crashlyticsCore;
    private final DevicePowerStateListener devicePowerStateListener;
    private final AtomicInteger eventCounter = new AtomicInteger(0);
    private final FileStore fileStore;
    private final HttpRequestFactory httpRequestFactory;
    private final IdManager idManager;
    private final LogFileManager logFileManager;
    private final PreferenceManager preferenceManager;
    private final StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
    private final String unityVersion;

    static {
        SESSION_FILE_FILTER = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string2) {
                return string2.length() == ".cls".length() + 35 && string2.endsWith(".cls");
            }
        };
        LARGEST_FILE_NAME_FIRST = new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                return file2.getName().compareTo(file.getName());
            }
        };
        SMALLEST_FILE_NAME_FIRST = new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                return file.getName().compareTo(file2.getName());
            }
        };
        ANY_SESSION_FILENAME_FILTER = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string2) {
                return SESSION_FILE_PATTERN.matcher(string2).matches();
            }
        };
        SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
        SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
        INITIAL_SESSION_PART_TAGS = new String[]{"SessionUser", "SessionApp", "SessionOS", "SessionDevice"};
    }

    CrashlyticsController(CrashlyticsCore crashlyticsCore, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker, HttpRequestFactory httpRequestFactory, IdManager idManager, PreferenceManager preferenceManager, FileStore fileStore, AppData appData, UnityVersionProvider unityVersionProvider) {
        this.crashlyticsCore = crashlyticsCore;
        this.backgroundWorker = crashlyticsBackgroundWorker;
        this.httpRequestFactory = httpRequestFactory;
        this.idManager = idManager;
        this.preferenceManager = preferenceManager;
        this.fileStore = fileStore;
        this.appData = appData;
        this.unityVersion = unityVersionProvider.getUnityVersion();
        crashlyticsCore = crashlyticsCore.getContext();
        this.logFileManager = new LogFileManager((Context)crashlyticsCore, fileStore);
        this.devicePowerStateListener = new DevicePowerStateListener((Context)crashlyticsCore);
        this.stackTraceTrimmingStrategy = new MiddleOutFallbackStrategy(1024, new RemoveRepeatsStrategy(10));
    }

    private void closeOpenSessions(File[] arrfile, int n, int n2) {
        Fabric.getLogger().d("CrashlyticsCore", "Closing open sessions.");
        while (n < arrfile.length) {
            File file = arrfile[n];
            String string2 = CrashlyticsController.getSessionIdFromSessionFile(file);
            Fabric.getLogger().d("CrashlyticsCore", "Closing session: " + string2);
            this.writeSessionPartsToSessionFile(file, string2, n2);
            ++n;
        }
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream clsFileOutputStream) {
        if (clsFileOutputStream == null) {
            return;
        }
        try {
            clsFileOutputStream.closeInProgressStream();
            return;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("CrashlyticsCore", "Error closing session file stream in the presence of an exception", iOException);
            return;
        }
    }

    private static void copyToCodedOutputStream(InputStream inputStream, CodedOutputStream codedOutputStream, int n) throws IOException {
        int n2;
        byte[] arrby = new byte[n];
        for (n = 0; n < arrby.length && (n2 = inputStream.read(arrby, n, arrby.length - n)) >= 0; n += n2) {
        }
        codedOutputStream.writeRawBytes(arrby);
    }

    private void deleteSessionPartFilesFor(String arrfile) {
        arrfile = this.listSessionPartFilesFor((String)arrfile);
        int n = arrfile.length;
        for (int i = 0; i < n; ++i) {
            arrfile[i].delete();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void doCloseSessions(SessionSettingsData sessionSettingsData, boolean bl) throws Exception {
        int n = bl ? 1 : 0;
        this.trimOpenSessions(n + 8);
        File[] arrfile = this.listSortedSessionBeginFiles();
        if (arrfile.length <= n) {
            Fabric.getLogger().d("CrashlyticsCore", "No open sessions to be closed.");
            return;
        }
        this.writeSessionUser(CrashlyticsController.getSessionIdFromSessionFile(arrfile[n]));
        if (sessionSettingsData == null) {
            Fabric.getLogger().d("CrashlyticsCore", "Unable to close session. Settings are not loaded.");
            return;
        }
        this.closeOpenSessions(arrfile, n, sessionSettingsData.maxCustomExceptionEvents);
    }

    private void doOpenSession() throws Exception {
        Date date = new Date();
        String string2 = new CLSUUID(this.idManager).toString();
        Fabric.getLogger().d("CrashlyticsCore", "Opening a new session with ID " + string2);
        this.writeBeginSession(string2, date);
        this.writeSessionApp(string2);
        this.writeSessionOS(string2);
        this.writeSessionDevice(string2);
        this.logFileManager.setCurrentSession(string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void doWriteExternalCrashEvent(SessionEventData var1_1) throws IOException {
        block10: {
            var10_6 = null;
            var6_7 = null;
            var11_9 = null;
            var9_10 = null;
            var8_11 = null;
            var7_12 = null;
            var3_13 = var11_9;
            var4_14 = var10_6;
            var12_15 = this.getPreviousSessionId();
            if (var12_15 != null) break block10;
            var3_13 = var11_9;
            var4_14 = var10_6;
            Fabric.getLogger().e("CrashlyticsCore", "Tried to write a native crash while no session was open.", null);
            CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
            return;
        }
        var3_13 = var11_9;
        var4_14 = var10_6;
        try {
            CrashlyticsController.recordFatalExceptionAnswersEvent(var12_15, String.format(Locale.US, "<native-crash [%s (%s)]>", new Object[]{var1_1.signal.code, var1_1.signal.name}));
            var3_13 = var11_9;
            var4_14 = var10_6;
            if (var1_1.binaryImages == null) ** GOTO lbl-1000
            var3_13 = var11_9;
            var4_14 = var10_6;
            if (var1_1.binaryImages.length > 0) {
                var2_16 = true;
            } else lbl-1000:
            // 2 sources
            {
                var2_16 = false;
            }
            ** GOTO lbl38
        }
        catch (Exception var1_2) {
            block11: {
                var5_17 = var6_7;
                var6_7 = var1_2;
                var1_1 = var7_12;
                break block11;
lbl38:
                // 2 sources
                var5_17 = var2_16 != false ? "SessionCrash" : "SessionMissingBinaryImages";
                var3_13 = var11_9;
                var4_14 = var10_6;
                var5_17 = new ClsFileOutputStream(this.getFilesDir(), var12_15 + (String)var5_17);
                var3_13 = var9_10;
                var4_14 = var8_11;
                try {
                    var3_13 = var6_7 = CodedOutputStream.newInstance((OutputStream)var5_17);
                    var4_14 = var6_7;
                    var7_12 = new MetaDataStore(this.getFilesDir()).readKeyData(var12_15);
                    var3_13 = var6_7;
                    var4_14 = var6_7;
                    NativeCrashWriter.writeNativeCrash((SessionEventData)var1_1, new LogFileManager(this.crashlyticsCore.getContext(), this.fileStore, var12_15), var7_12, (CodedOutputStream)var6_7);
                }
                catch (Throwable var1_5) {
                    var4_14 = var5_17;
                    ** GOTO lbl-1000
                }
                catch (Exception var6_8) {
                    var1_1 = var4_14;
                }
                CommonUtils.flushOrLog((Flushable)var6_7, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog((Closeable)var5_17, "Failed to close fatal exception file output stream.");
                return;
            }
            var3_13 = var1_1;
            var4_14 = var5_17;
            try {
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the native crash logger", (Throwable)var6_7);
            }
            catch (Throwable var1_3) lbl-1000:
            // 2 sources
            {
                CommonUtils.flushOrLog(var3_13, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(var4_14, "Failed to close fatal exception file output stream.");
                throw var1_4;
            }
            CommonUtils.flushOrLog((Flushable)var1_1, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog((Closeable)var5_17, "Failed to close fatal exception file output stream.");
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void doWriteNonFatal(Date var1_1, Thread var2_6, Throwable var3_7) {
        var12_11 = this.getCurrentSessionId();
        if (var12_11 == null) {
            Fabric.getLogger().e("CrashlyticsCore", "Tried to write a non-fatal exception while no session was open.", null);
            return;
        }
        CrashlyticsController.recordLoggedExceptionAnswersEvent(var12_11, var3_7.getClass().getName());
        var6_12 = null;
        var8_13 = null;
        var11_14 = null;
        var10_15 = null;
        var9_16 = null;
        var7_17 = null;
        var4_18 = var11_14;
        var5_19 = var6_12;
        Fabric.getLogger().d("CrashlyticsCore", "Crashlytics is logging non-fatal exception \"" + var3_7 + "\" from thread " + var2_6.getName());
        var4_18 = var11_14;
        var5_19 = var6_12;
        var13_20 = CommonUtils.padWithZerosToMaxIntWidth(this.eventCounter.getAndIncrement());
        var4_18 = var11_14;
        var5_19 = var6_12;
        var13_20 = var12_11 + "SessionEvent" + var13_20;
        var4_18 = var11_14;
        var5_19 = var6_12;
        var6_12 = new ClsFileOutputStream(this.getFilesDir(), var13_20);
        var4_18 = var10_15;
        var5_19 = var9_16;
        var7_17 = CodedOutputStream.newInstance((OutputStream)var6_12);
        var4_18 = var7_17;
        var5_19 = var7_17;
        this.writeSessionEvent(var7_17, (Date)var1_1, (Thread)var2_6, var3_7, "error", false);
        CommonUtils.flushOrLog(var7_17, "Failed to flush to non-fatal file.");
        CommonUtils.closeOrLog((Closeable)var6_12, "Failed to close non-fatal file output stream.");
        ** GOTO lbl56
        catch (Exception var3_8) {
            block10: {
                var2_6 = var8_13;
                var1_1 = var7_17;
                break block10;
                catch (Throwable var1_5) {
                    var5_19 = var6_12;
                    ** GOTO lbl-1000
                }
                catch (Exception var3_10) {
                    var2_6 = var6_12;
                    var1_1 = var5_19;
                }
            }
            var4_18 = var1_1;
            var5_19 = var2_6;
            try {
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the non-fatal exception logger", (Throwable)var3_9);
            }
            catch (Throwable var1_3) lbl-1000:
            // 2 sources
            {
                CommonUtils.flushOrLog(var4_18, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(var5_19, "Failed to close non-fatal file output stream.");
                throw var1_4;
            }
            CommonUtils.flushOrLog((Flushable)var1_1, "Failed to flush to non-fatal file.");
            CommonUtils.closeOrLog((Closeable)var2_6, "Failed to close non-fatal file output stream.");
lbl56:
            // 2 sources
            try {
                this.trimSessionEventFiles(var12_11, 64);
                return;
            }
            catch (Exception var1_2) {
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred when trimming non-fatal files.", var1_2);
                return;
            }
        }
    }

    private File[] ensureFileArrayNotNull(File[] arrfile) {
        File[] arrfile2 = arrfile;
        if (arrfile == null) {
            arrfile2 = new File[]{};
        }
        return arrfile2;
    }

    private CreateReportSpiCall getCreateReportSpiCall(String string2) {
        String string3 = CommonUtils.getStringsFileValue(this.crashlyticsCore.getContext(), "com.crashlytics.ApiEndpoint");
        return new DefaultCreateReportSpiCall(this.crashlyticsCore, string3, string2, this.httpRequestFactory);
    }

    private String getCurrentSessionId() {
        File[] arrfile = this.listSortedSessionBeginFiles();
        if (arrfile.length > 0) {
            return CrashlyticsController.getSessionIdFromSessionFile(arrfile[0]);
        }
        return null;
    }

    private String getPreviousSessionId() {
        File[] arrfile = this.listSortedSessionBeginFiles();
        if (arrfile.length > 1) {
            return CrashlyticsController.getSessionIdFromSessionFile(arrfile[1]);
        }
        return null;
    }

    static String getSessionIdFromSessionFile(File file) {
        return file.getName().substring(0, 35);
    }

    private File[] getTrimmedNonFatalFiles(String string2, File[] arrfile, int n) {
        File[] arrfile2 = arrfile;
        if (arrfile.length > n) {
            Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Trimming down to %d logged exceptions.", n));
            this.trimSessionEventFiles(string2, n);
            arrfile2 = this.listFilesMatching(new FileNameContainsFilter(string2 + "SessionEvent"));
        }
        return arrfile2;
    }

    private UserMetaData getUserMetaData(String string2) {
        if (this.isHandlingException()) {
            return new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail());
        }
        return new MetaDataStore(this.getFilesDir()).readUserData(string2);
    }

    private File[] listCompleteSessionFiles() {
        return this.listFilesMatching(SESSION_FILE_FILTER);
    }

    private File[] listFiles(File file) {
        return this.ensureFileArrayNotNull(file.listFiles());
    }

    private File[] listFilesMatching(File file, FilenameFilter filenameFilter) {
        return this.ensureFileArrayNotNull(file.listFiles(filenameFilter));
    }

    private File[] listFilesMatching(FilenameFilter filenameFilter) {
        return this.listFilesMatching(this.getFilesDir(), filenameFilter);
    }

    private File[] listSessionPartFilesFor(String string2) {
        return this.listFilesMatching(new SessionPartFileFilter(string2));
    }

    private File[] listSortedSessionBeginFiles() {
        File[] arrfile = this.listSessionBeginFiles();
        Arrays.sort(arrfile, LARGEST_FILE_NAME_FIRST);
        return arrfile;
    }

    private static void recordFatalExceptionAnswersEvent(String string2, String string3) {
        Answers answers = Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d("CrashlyticsCore", "Answers is not available");
            return;
        }
        answers.onException(new Crash.FatalException(string2, string3));
    }

    private static void recordLoggedExceptionAnswersEvent(String string2, String string3) {
        Answers answers = Fabric.getKit(Answers.class);
        if (answers == null) {
            Fabric.getLogger().d("CrashlyticsCore", "Answers is not available");
            return;
        }
        answers.onException(new Crash.LoggedException(string2, string3));
    }

    private void retainSessions(File[] arrfile, Set<String> set) {
        int n = arrfile.length;
        int n2 = 0;
        do {
            String string2;
            File file;
            Matcher matcher;
            block6: {
                block5: {
                    if (n2 >= n) break block5;
                    file = arrfile[n2];
                    string2 = file.getName();
                    matcher = SESSION_FILE_PATTERN.matcher(string2);
                    if (matcher.matches()) break block6;
                    Fabric.getLogger().d("CrashlyticsCore", "Deleting unknown file: " + string2);
                    file.delete();
                }
                return;
            }
            if (!set.contains(matcher.group(1))) {
                Fabric.getLogger().d("CrashlyticsCore", "Trimming session file: " + string2);
                file.delete();
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void sendSessionReports(SettingsData object) {
        if (object == null) {
            Fabric.getLogger().w("CrashlyticsCore", "Cannot send reports. Settings are unavailable.");
            return;
        } else {
            Context context = this.crashlyticsCore.getContext();
            object = this.getCreateReportSpiCall(object.appData.reportsUrl);
            object = new ReportUploader(this.appData.apiKey, (CreateReportSpiCall)object);
            File[] arrfile = this.listCompleteSessionFiles();
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                SessionReport sessionReport = new SessionReport(arrfile[i], SEND_AT_CRASHTIME_HEADER);
                this.backgroundWorker.submit(new SendReportRunnable(context, sessionReport, (ReportUploader)object));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldPromptUserBeforeSendingCrashReports(SettingsData settingsData) {
        return settingsData != null && settingsData.featuresData.promptEnabled && !this.preferenceManager.shouldAlwaysSendReports();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void synthesizeSessionFile(File var1_1, String var2_5, File[] var3_6, File var4_7) {
        block10: {
            var5_15 = var4_11 != null;
            var7_16 = null;
            var10_32 = null;
            var6_33 = null;
            var12_34 = null;
            var11_35 = null;
            var9_36 = null;
            var8_37 = new ClsFileOutputStream(this.getFilesDir(), (String)var2_9);
            var6_33 = var12_34;
            var7_17 = var11_35;
            var6_33 = var9_36 = CodedOutputStream.newInstance(var8_37);
            var7_18 = var9_36;
            Fabric.getLogger().d("CrashlyticsCore", "Collecting SessionStart data for session ID " + (String)var2_9);
            var6_33 = var9_36;
            var7_19 = var9_36;
            CrashlyticsController.writeToCosFromFile(var9_36, (File)var1_1 /* !! */ );
            var6_33 = var9_36;
            var7_20 = var9_36;
            var9_36.writeUInt64(4, new Date().getTime() / 1000L);
            var6_33 = var9_36;
            var7_21 = var9_36;
            var9_36.writeBool(5, var5_15);
            var6_33 = var9_36;
            var7_22 = var9_36;
            var9_36.writeUInt32(11, 1);
            var6_33 = var9_36;
            var7_23 = var9_36;
            var9_36.writeEnum(12, 3);
            var6_33 = var9_36;
            var7_24 = var9_36;
            this.writeInitialPartsTo(var9_36, (String)var2_9);
            var6_33 = var9_36;
            var7_25 = var9_36;
            CrashlyticsController.writeNonFatalEventsTo(var9_36, (File[])var3_10, (String)var2_9);
            if (!var5_15) break block10;
            var6_33 = var9_36;
            var7_26 = var9_36;
            CrashlyticsController.writeToCosFromFile(var9_36, (File)var4_11);
        }
        CommonUtils.flushOrLog(var9_36, "Error flushing session file stream");
        if (false) {
            this.closeWithoutRenamingOrLog(var8_37);
            return;
        }
        CommonUtils.closeOrLog(var8_37, "Failed to close CLS file");
        return;
        catch (Exception var4_12) {
            block11: {
                var3_10 = var10_32;
                var1_2 = var9_36;
                break block11;
                catch (Throwable var1_6) {
                    var7_31 = var8_37;
                    ** GOTO lbl-1000
                }
                catch (Exception var4_14) {
                    var3_10 = var8_37;
                    var1_7 = var7_17;
                }
            }
            var6_33 = var1_3;
            var7_28 = var3_10;
            try {
                Fabric.getLogger().e("CrashlyticsCore", "Failed to write session file for session ID: " + (String)var2_9, (Throwable)var4_13);
            }
            catch (Throwable var1_4) lbl-1000:
            // 2 sources
            {
                CommonUtils.flushOrLog(var6_33, "Error flushing session file stream");
                if (false) {
                    this.closeWithoutRenamingOrLog((ClsFileOutputStream)var7_30);
                    throw var1_5;
                }
                CommonUtils.closeOrLog((Closeable)var7_30, "Failed to close CLS file");
                throw var1_5;
            }
            CommonUtils.flushOrLog((Flushable)var1_3, "Error flushing session file stream");
            if (true) {
                this.closeWithoutRenamingOrLog(var3_10);
                return;
            }
            CommonUtils.closeOrLog(var3_10, "Failed to close CLS file");
            return;
        }
    }

    private void trimInvalidSessionFiles() {
        File file = this.getInvalidFilesDir();
        if (!file.exists()) {
            return;
        }
        File[] arrfile = this.listFilesMatching(file, new InvalidPartFileFilter());
        Arrays.sort(arrfile, Collections.reverseOrder());
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < arrfile.length && hashSet.size() < 4; ++i) {
            hashSet.add(CrashlyticsController.getSessionIdFromSessionFile(arrfile[i]));
        }
        this.retainSessions(this.listFiles(file), hashSet);
    }

    private void trimOpenSessions(int n) {
        HashSet<String> hashSet = new HashSet<String>();
        File[] arrfile = this.listSortedSessionBeginFiles();
        int n2 = Math.min(n, arrfile.length);
        for (n = 0; n < n2; ++n) {
            hashSet.add(CrashlyticsController.getSessionIdFromSessionFile(arrfile[n]));
        }
        this.logFileManager.discardOldLogFiles(hashSet);
        this.retainSessions(this.listFilesMatching(new AnySessionPartFileFilter()), hashSet);
    }

    private void trimSessionEventFiles(String string2, int n) {
        Utils.capFileCount(this.getFilesDir(), new FileNameContainsFilter(string2 + "SessionEvent"), n, SMALLEST_FILE_NAME_FIRST);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeBeginSession(String object, Date date) throws Exception {
        CodedOutputStream codedOutputStream = null;
        CodedOutputStream codedOutputStream2 = null;
        CodedOutputStream codedOutputStream3 = null;
        ClsFileOutputStream clsFileOutputStream = new ClsFileOutputStream(this.getFilesDir(), (String)object + "BeginSession");
        codedOutputStream3 = codedOutputStream2;
        codedOutputStream3 = codedOutputStream = CodedOutputStream.newInstance(clsFileOutputStream);
        SessionProtobufHelper.writeBeginSession(codedOutputStream, (String)object, String.format(Locale.US, "Crashlytics Android SDK/%s", this.crashlyticsCore.getVersion()), date.getTime() / 1000L);
        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session begin file.");
        CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close begin session file.");
        return;
        catch (Throwable throwable) {
            void var2_4;
            block4: {
                object = codedOutputStream;
                break block4;
                catch (Throwable throwable2) {
                    object = clsFileOutputStream;
                }
            }
            CommonUtils.flushOrLog(codedOutputStream3, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog((Closeable)object, "Failed to close begin session file.");
            throw var2_4;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeFatal(Date var1_1, Thread var2_5, Throwable var3_6) {
        block8: {
            var6_10 = null;
            var8_11 = null;
            var11_12 = null;
            var10_13 = null;
            var9_14 = null;
            var7_15 = null;
            var4_16 = var11_12;
            var5_17 = var6_10;
            var12_18 = this.getCurrentSessionId();
            if (var12_18 != null) break block8;
            var4_16 = var11_12;
            var5_17 = var6_10;
            Fabric.getLogger().e("CrashlyticsCore", "Tried to write a fatal exception while no session was open.", null);
            CommonUtils.flushOrLog(null, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(null, "Failed to close fatal exception file output stream.");
            return;
        }
        var4_16 = var11_12;
        var5_17 = var6_10;
        CrashlyticsController.recordFatalExceptionAnswersEvent(var12_18, var3_6.getClass().getName());
        var4_16 = var11_12;
        var5_17 = var6_10;
        var6_10 = new ClsFileOutputStream(this.getFilesDir(), var12_18 + "SessionCrash");
        var4_16 = var10_13;
        var5_17 = var9_14;
        var7_15 = CodedOutputStream.newInstance((OutputStream)var6_10);
        var4_16 = var7_15;
        var5_17 = var7_15;
        this.writeSessionEvent(var7_15, (Date)var1_1, (Thread)var2_5, var3_6, "crash", true);
        CommonUtils.flushOrLog(var7_15, "Failed to flush to session begin file.");
        CommonUtils.closeOrLog((Closeable)var6_10, "Failed to close fatal exception file output stream.");
        return;
        catch (Exception var3_7) {
            block9: {
                var2_5 = var8_11;
                var1_1 = var7_15;
                break block9;
                catch (Throwable var1_4) {
                    var5_17 = var6_10;
                    ** GOTO lbl-1000
                }
                catch (Exception var3_9) {
                    var2_5 = var6_10;
                    var1_1 = var5_17;
                }
            }
            var4_16 = var1_1;
            var5_17 = var2_5;
            try {
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the fatal exception logger", (Throwable)var3_8);
            }
            catch (Throwable var1_2) lbl-1000:
            // 2 sources
            {
                CommonUtils.flushOrLog(var4_16, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(var5_17, "Failed to close fatal exception file output stream.");
                throw var1_3;
            }
            CommonUtils.flushOrLog((Flushable)var1_1, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog((Closeable)var2_5, "Failed to close fatal exception file output stream.");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeInitialPartsTo(CodedOutputStream codedOutputStream, String string2) throws IOException {
        String[] arrstring = INITIAL_SESSION_PART_TAGS;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string3 = arrstring[n2];
            File[] arrfile = this.listFilesMatching(new FileNameContainsFilter(string2 + string3));
            if (arrfile.length == 0) {
                Fabric.getLogger().e("CrashlyticsCore", "Can't find " + string3 + " data for session ID " + string2, null);
            } else {
                Fabric.getLogger().d("CrashlyticsCore", "Collecting " + string3 + " data for session ID " + string2);
                CrashlyticsController.writeToCosFromFile(codedOutputStream, arrfile[0]);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void writeNonFatalEventsTo(CodedOutputStream codedOutputStream, File[] arrfile, String string2) {
        Arrays.sort(arrfile, CommonUtils.FILE_MODIFIED_COMPARATOR);
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            File file = arrfile[n2];
            try {
                Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", string2, file.getName()));
                CrashlyticsController.writeToCosFromFile(codedOutputStream, file);
            }
            catch (Exception exception) {
                Fabric.getLogger().e("CrashlyticsCore", "Error writting non-fatal to session.", exception);
            }
            ++n2;
        }
        return;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeSessionApp(String object) throws Exception {
        Object object2;
        Object object3 = null;
        String string2 = null;
        String string3 = null;
        try {
            object2 = new ClsFileOutputStream(this.getFilesDir(), (String)object + "SessionApp");
            object = string2;
        }
        catch (Throwable throwable) {
            object2 = object3;
            object3 = throwable;
            object = string3;
            CommonUtils.flushOrLog((Flushable)object, "Failed to flush to session app file.");
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close session app file.");
            throw object3;
        }
        object = object3 = CodedOutputStream.newInstance((OutputStream)object2);
        string3 = this.idManager.getAppIdentifier();
        object = object3;
        string2 = this.appData.versionCode;
        object = object3;
        String string4 = this.appData.versionName;
        object = object3;
        String string5 = this.idManager.getAppInstallIdentifier();
        object = object3;
        int n = DeliveryMechanism.determineFrom(this.appData.installerPackageName).getId();
        object = object3;
        SessionProtobufHelper.writeSessionApp((CodedOutputStream)object3, string3, this.appData.apiKey, string2, string4, string5, n, this.unityVersion);
        {
            catch (Throwable throwable) {}
        }
        CommonUtils.flushOrLog((Flushable)object3, "Failed to flush to session app file.");
        CommonUtils.closeOrLog((Closeable)object2, "Failed to close session app file.");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeSessionDevice(String object) throws Exception {
        Object object2;
        Object object3 = null;
        String string2 = null;
        Context context = null;
        try {
            object2 = new ClsFileOutputStream(this.getFilesDir(), (String)object + "SessionDevice");
            object = string2;
        }
        catch (Throwable throwable) {
            object2 = object3;
            object3 = throwable;
            object = context;
            CommonUtils.flushOrLog((Flushable)object, "Failed to flush session device info.");
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close session device file.");
            throw object3;
        }
        object = object3 = CodedOutputStream.newInstance((OutputStream)object2);
        context = this.crashlyticsCore.getContext();
        object = object3;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        object = object3;
        string2 = this.idManager.getDeviceUUID();
        object = object3;
        int n = CommonUtils.getCpuArchitectureInt();
        object = object3;
        int n2 = Runtime.getRuntime().availableProcessors();
        object = object3;
        long l = CommonUtils.getTotalRamInBytes();
        object = object3;
        long l2 = statFs.getBlockCount();
        object = object3;
        long l3 = statFs.getBlockSize();
        object = object3;
        boolean bl = CommonUtils.isEmulator(context);
        object = object3;
        Map<IdManager.DeviceIdentifierType, String> map = this.idManager.getDeviceIdentifiers();
        object = object3;
        int n3 = CommonUtils.getDeviceState(context);
        object = object3;
        SessionProtobufHelper.writeSessionDevice((CodedOutputStream)object3, string2, n, Build.MODEL, n2, l, l2 * l3, bl, map, n3, Build.MANUFACTURER, Build.PRODUCT);
        {
            catch (Throwable throwable) {}
        }
        CommonUtils.flushOrLog((Flushable)object3, "Failed to flush session device info.");
        CommonUtils.closeOrLog((Closeable)object2, "Failed to close session device file.");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeSessionEvent(CodedOutputStream codedOutputStream, Date object, Thread thread, Throwable object2, String string2, boolean bl) throws Exception {
        TrimmedThrowableData trimmedThrowableData = new TrimmedThrowableData((Throwable)object2, this.stackTraceTrimmingStrategy);
        Object object3 = this.crashlyticsCore.getContext();
        long l = ((Date)object).getTime() / 1000L;
        Float f = CommonUtils.getBatteryLevel((Context)object3);
        int n = CommonUtils.getBatteryVelocity((Context)object3, this.devicePowerStateListener.isPowerConnected());
        boolean bl2 = CommonUtils.getProximitySensorEnabled((Context)object3);
        int n2 = object3.getResources().getConfiguration().orientation;
        long l2 = CommonUtils.getTotalRamInBytes();
        long l3 = CommonUtils.calculateFreeRamInBytes((Context)object3);
        long l4 = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = CommonUtils.getAppProcessInfo(object3.getPackageName(), (Context)object3);
        LinkedList<StackTraceElement[]> linkedList = new LinkedList<StackTraceElement[]>();
        StackTraceElement[] arrstackTraceElement = trimmedThrowableData.stacktrace;
        String string3 = this.appData.buildId;
        String string4 = this.idManager.getAppIdentifier();
        if (bl) {
            object2 = Thread.getAllStackTraces();
            object = new Thread[object2.size()];
            int n3 = 0;
            Iterator iterator = object2.entrySet().iterator();
            do {
                object2 = object;
                if (iterator.hasNext()) {
                    object2 = iterator.next();
                    object[n3] = (Thread)object2.getKey();
                    linkedList.add(this.stackTraceTrimmingStrategy.getTrimmedStackTrace((StackTraceElement[])object2.getValue()));
                    ++n3;
                    continue;
                }
                break;
            } while (true);
        } else {
            object2 = new Thread[]{};
        }
        if (!CommonUtils.getBooleanResourceValue((Context)object3, "com.crashlytics.CollectCustomKeys", true)) {
            object = new TreeMap();
        } else {
            object = object3 = this.crashlyticsCore.getAttributes();
            if (object3 != null) {
                object = object3;
                if (object3.size() > 1) {
                    object = new TreeMap(object3);
                }
            }
        }
        SessionProtobufHelper.writeSessionEvent(codedOutputStream, l, string2, trimmedThrowableData, thread, arrstackTraceElement, (Thread[])object2, linkedList, (Map<String, String>)object, this.logFileManager, runningAppProcessInfo, n2, string4, string3, f, n, bl2, l2 - l3, l4);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeSessionOS(String object) throws Exception {
        Object var2_2 = null;
        Object var5_6 = null;
        Object var4_7 = null;
        Object object2 = new ClsFileOutputStream(this.getFilesDir(), (String)object + "SessionOS");
        object = var5_6;
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance((OutputStream)object2);
        object = codedOutputStream;
        SessionProtobufHelper.writeSessionOS(codedOutputStream, CommonUtils.isRooted(this.crashlyticsCore.getContext()));
        CommonUtils.flushOrLog(codedOutputStream, "Failed to flush to session OS file.");
        CommonUtils.closeOrLog((Closeable)object2, "Failed to close session OS file.");
        return;
        catch (Throwable throwable) {
            void var2_4;
            block4: {
                object = var4_7;
                break block4;
                catch (Throwable throwable2) {
                    ClsFileOutputStream clsFileOutputStream = object2;
                    object2 = throwable2;
                }
            }
            CommonUtils.flushOrLog((Flushable)object, "Failed to flush to session OS file.");
            CommonUtils.closeOrLog((Closeable)var2_4, "Failed to close session OS file.");
            throw object2;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void writeSessionPartsToSessionFile(File file, String string2, int n) {
        Fabric.getLogger().d("CrashlyticsCore", "Collecting session parts for ID " + string2);
        File[] arrfile = this.listFilesMatching(new FileNameContainsFilter(string2 + "SessionCrash"));
        boolean bl = arrfile != null && arrfile.length > 0;
        Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Session %s has fatal exception: %s", string2, bl));
        File[] arrfile2 = this.listFilesMatching(new FileNameContainsFilter(string2 + "SessionEvent"));
        boolean bl2 = arrfile2 != null && arrfile2.length > 0;
        Fabric.getLogger().d("CrashlyticsCore", String.format(Locale.US, "Session %s has non-fatal exceptions: %s", string2, bl2));
        if (bl || bl2) {
            void var6_6;
            arrfile2 = this.getTrimmedNonFatalFiles(string2, arrfile2, n);
            if (bl) {
                File file2 = arrfile[0];
            } else {
                Object var6_7 = null;
            }
            this.synthesizeSessionFile(file, string2, arrfile2, (File)var6_6);
        } else {
            Fabric.getLogger().d("CrashlyticsCore", "No events present for session ID " + string2);
        }
        Fabric.getLogger().d("CrashlyticsCore", "Removing session part files for ID " + string2);
        this.deleteSessionPartFilesFor(string2);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void writeSessionUser(String object) throws Exception {
        Object object2;
        CodedOutputStream codedOutputStream;
        CodedOutputStream codedOutputStream2;
        block5: {
            codedOutputStream2 = null;
            CodedOutputStream codedOutputStream3 = null;
            codedOutputStream = null;
            object2 = new ClsFileOutputStream(this.getFilesDir(), (String)object + "SessionUser");
            codedOutputStream = codedOutputStream3;
            codedOutputStream = codedOutputStream2 = CodedOutputStream.newInstance((OutputStream)object2);
            object = this.getUserMetaData((String)object);
            codedOutputStream = codedOutputStream2;
            boolean bl = ((UserMetaData)object).isEmpty();
            if (!bl) break block5;
            CommonUtils.flushOrLog(codedOutputStream2, "Failed to flush session user file.");
            CommonUtils.closeOrLog((Closeable)object2, "Failed to close session user file.");
            return;
        }
        codedOutputStream = codedOutputStream2;
        SessionProtobufHelper.writeSessionUser(codedOutputStream2, ((UserMetaData)object).id, ((UserMetaData)object).name, ((UserMetaData)object).email);
        CommonUtils.flushOrLog(codedOutputStream2, "Failed to flush session user file.");
        CommonUtils.closeOrLog((Closeable)object2, "Failed to close session user file.");
        return;
        catch (Throwable throwable) {
            block6: {
                object = codedOutputStream2;
                break block6;
                catch (Throwable throwable2) {
                    object = object2;
                    object2 = throwable2;
                }
            }
            CommonUtils.flushOrLog(codedOutputStream, "Failed to flush session user file.");
            CommonUtils.closeOrLog((Closeable)object, "Failed to close session user file.");
            throw object2;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void writeToCosFromFile(CodedOutputStream object, File file) throws IOException {
        if (!file.exists()) {
            Fabric.getLogger().e("CrashlyticsCore", "Tried to include a file that doesn't exist: " + file.getName(), null);
            return;
        }
        Object var2_5 = null;
        FileInputStream fileInputStream = new FileInputStream(file);
        CrashlyticsController.copyToCodedOutputStream(fileInputStream, (CodedOutputStream)object, (int)file.length());
        CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream.");
        return;
        catch (Throwable throwable) {
            void var1_3;
            block5: {
                object = var2_5;
                break block5;
                catch (Throwable throwable2) {
                    object = fileInputStream;
                }
            }
            CommonUtils.closeOrLog((Closeable)object, "Failed to close file input stream.");
            throw var1_3;
        }
    }

    void cacheKeyData(final Map<String, String> map) {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                String string2 = CrashlyticsController.this.getCurrentSessionId();
                new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(string2, map);
                return null;
            }
        });
    }

    void cleanInvalidTempFiles() {
        this.backgroundWorker.submit(new Runnable(){

            @Override
            public void run() {
                CrashlyticsController.this.doCleanInvalidTempFiles(CrashlyticsController.this.listFilesMatching(new InvalidPartFileFilter()));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] object) {
        File[] arrfile = new HashSet();
        for (File file : object) {
            Fabric.getLogger().d("CrashlyticsCore", "Found invalid session part file: " + file);
            arrfile.add(CrashlyticsController.getSessionIdFromSessionFile(file));
        }
        if (arrfile.isEmpty()) {
            return;
        }
        object = this.getInvalidFilesDir();
        if (!((File)object).exists()) {
            ((File)object).mkdir();
        }
        for (File file : this.listFilesMatching(new FilenameFilter((Set)arrfile){
            final /* synthetic */ Set val$invalidSessionIds;
            {
                this.val$invalidSessionIds = set;
            }

            @Override
            public boolean accept(File file, String string2) {
                if (string2.length() < 35) {
                    return false;
                }
                return this.val$invalidSessionIds.contains(string2.substring(0, 35));
            }
        })) {
            Fabric.getLogger().d("CrashlyticsCore", "Moving session file: " + file);
            if (file.renameTo(new File((File)object, file.getName()))) continue;
            Fabric.getLogger().d("CrashlyticsCore", "Could not move session file. Deleting " + file);
            file.delete();
        }
        this.trimInvalidSessionFiles();
    }

    void doCloseSessions(SessionSettingsData sessionSettingsData) throws Exception {
        this.doCloseSessions(sessionSettingsData, false);
    }

    void enableExceptionHandling(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.openSession();
        this.crashHandler = new CrashlyticsUncaughtExceptionHandler(new CrashlyticsUncaughtExceptionHandler.CrashListener(){

            @Override
            public void onUncaughtException(Thread thread, Throwable throwable) {
                CrashlyticsController.this.handleUncaughtException(thread, throwable);
            }
        }, uncaughtExceptionHandler);
        Thread.setDefaultUncaughtExceptionHandler(this.crashHandler);
    }

    boolean finalizeSessions(final SessionSettingsData sessionSettingsData) {
        return this.backgroundWorker.submitAndWait(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                if (CrashlyticsController.this.isHandlingException()) {
                    Fabric.getLogger().d("CrashlyticsCore", "Skipping session finalization because a crash has already occurred.");
                    return Boolean.FALSE;
                }
                Fabric.getLogger().d("CrashlyticsCore", "Finalizing previously open sessions.");
                CrashlyticsController.this.doCloseSessions(sessionSettingsData, true);
                Fabric.getLogger().d("CrashlyticsCore", "Closed all previously open sessions");
                return Boolean.TRUE;
            }
        });
    }

    File getFilesDir() {
        return this.fileStore.getFilesDir();
    }

    File getInvalidFilesDir() {
        return new File(this.getFilesDir(), "invalidClsFiles");
    }

    void handleUncaughtException(final Thread thread, final Throwable throwable) {
        synchronized (this) {
            Fabric.getLogger().d("CrashlyticsCore", "Crashlytics is handling uncaught exception \"" + throwable + "\" from thread " + thread.getName());
            this.devicePowerStateListener.dispose();
            final Date date = new Date();
            this.backgroundWorker.submitAndWait(new Callable<Void>(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public Void call() throws Exception {
                    CrashlyticsController.this.crashlyticsCore.createCrashMarker();
                    CrashlyticsController.this.writeFatal(date, thread, throwable);
                    SettingsData settingsData = Settings.getInstance().awaitSettingsData();
                    SessionSettingsData sessionSettingsData = settingsData != null ? settingsData.sessionData : null;
                    CrashlyticsController.this.doCloseSessions(sessionSettingsData);
                    CrashlyticsController.this.doOpenSession();
                    CrashlyticsController.this.trimSessionFiles();
                    if (!CrashlyticsController.this.shouldPromptUserBeforeSendingCrashReports(settingsData)) {
                        CrashlyticsController.this.sendSessionReports(settingsData);
                    }
                    return null;
                }
            });
            return;
        }
    }

    boolean isHandlingException() {
        return this.crashHandler != null && this.crashHandler.isHandlingException();
    }

    File[] listSessionBeginFiles() {
        return this.listFilesMatching(new FileNameContainsFilter("BeginSession"));
    }

    void openSession() {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                CrashlyticsController.this.doOpenSession();
                return null;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    void submitAllReports(float f, SettingsData object) {
        if (object == null) {
            Fabric.getLogger().w("CrashlyticsCore", "Could not send reports. Settings are not available.");
            return;
        }
        CreateReportSpiCall createReportSpiCall = this.getCreateReportSpiCall(object.appData.reportsUrl);
        object = this.shouldPromptUserBeforeSendingCrashReports((SettingsData)object) ? new PrivacyDialogCheck(this.crashlyticsCore, this.preferenceManager, ((SettingsData)object).promptData) : new ReportUploader.AlwaysSendCheck();
        new ReportUploader(this.appData.apiKey, createReportSpiCall).uploadReports(f, (ReportUploader.SendCheck)object);
    }

    void trimSessionFiles() {
        Utils.capFileCount(this.getFilesDir(), SESSION_FILE_FILTER, 4, SMALLEST_FILE_NAME_FIRST);
    }

    void writeExternalCrashEvent(final SessionEventData sessionEventData) {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteExternalCrashEvent(sessionEventData);
                }
                return null;
            }
        });
    }

    void writeNonFatalException(final Thread thread, final Throwable throwable) {
        final Date date = new Date();
        this.backgroundWorker.submit(new Runnable(){

            @Override
            public void run() {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.doWriteNonFatal(date, thread, throwable);
                }
            }
        });
    }

    void writeToLog(final long l, final String string2) {
        this.backgroundWorker.submit(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                if (!CrashlyticsController.this.isHandlingException()) {
                    CrashlyticsController.this.logFileManager.writeToLog(l, string2);
                }
                return null;
            }
        });
    }

    private static class AnySessionPartFileFilter
    implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        @Override
        public boolean accept(File file, String string2) {
            return !SESSION_FILE_FILTER.accept(file, string2) && SESSION_FILE_PATTERN.matcher(string2).matches();
        }
    }

    static class FileNameContainsFilter
    implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String string2) {
            this.string = string2;
        }

        @Override
        public boolean accept(File file, String string2) {
            return string2.contains(this.string) && !string2.endsWith(".cls_temp");
        }
    }

    static class InvalidPartFileFilter
    implements FilenameFilter {
        InvalidPartFileFilter() {
        }

        @Override
        public boolean accept(File file, String string2) {
            return ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, string2) || string2.contains("SessionMissingBinaryImages");
        }
    }

    private static final class PrivacyDialogCheck
    implements ReportUploader.SendCheck {
        private final Kit kit;
        private final PreferenceManager preferenceManager;
        private final PromptSettingsData promptData;

        public PrivacyDialogCheck(Kit kit, PreferenceManager preferenceManager, PromptSettingsData promptSettingsData) {
            this.kit = kit;
            this.preferenceManager = preferenceManager;
            this.promptData = promptSettingsData;
        }

        @Override
        public boolean canSendReports() {
            Activity activity = this.kit.getFabric().getCurrentActivity();
            if (activity == null || activity.isFinishing()) {
                return true;
            }
            Object object = new CrashPromptDialog.AlwaysSendCallback(){

                @Override
                public void sendUserReportsWithoutPrompting(boolean bl) {
                    PrivacyDialogCheck.this.preferenceManager.setShouldAlwaysSendReports(bl);
                }
            };
            object = CrashPromptDialog.create(activity, this.promptData, (CrashPromptDialog.AlwaysSendCallback)object);
            activity.runOnUiThread(new Runnable((CrashPromptDialog)object){
                final /* synthetic */ CrashPromptDialog val$dialog;
                {
                    this.val$dialog = crashPromptDialog;
                }

                @Override
                public void run() {
                    this.val$dialog.show();
                }
            });
            Fabric.getLogger().d("CrashlyticsCore", "Waiting for user opt-in.");
            ((CrashPromptDialog)object).await();
            return ((CrashPromptDialog)object).getOptIn();
        }

    }

    private static final class SendReportRunnable
    implements Runnable {
        private final Context context;
        private final Report report;
        private final ReportUploader reportUploader;

        public SendReportRunnable(Context context, Report report, ReportUploader reportUploader) {
            this.context = context;
            this.report = report;
            this.reportUploader = reportUploader;
        }

        @Override
        public void run() {
            if (!CommonUtils.canTryConnection(this.context)) {
                return;
            }
            Fabric.getLogger().d("CrashlyticsCore", "Attempting to send crash report at time of crash...");
            this.reportUploader.forceUpload(this.report);
        }
    }

    static class SessionPartFileFilter
    implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String string2) {
            this.sessionId = string2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean accept(File file, String string2) {
            return !string2.equals(this.sessionId + ".cls") && string2.contains(this.sessionId) && !string2.endsWith(".cls_temp");
        }
    }

}

