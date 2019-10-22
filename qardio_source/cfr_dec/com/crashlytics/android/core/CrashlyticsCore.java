/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.util.Log;
import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.CrashlyticsBackgroundWorker;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.CrashlyticsFileMarker;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.CrashlyticsPinningInfoProvider;
import com.crashlytics.android.core.ManifestUnityVersionProvider;
import com.crashlytics.android.core.PinningInfoProvider;
import com.crashlytics.android.core.PreferenceManager;
import com.crashlytics.android.core.UnityVersionProvider;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.PriorityTask;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStore;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DependsOn(value={CrashEventDataProvider.class})
public class CrashlyticsCore
extends Kit<Void> {
    private final ConcurrentHashMap<String, String> attributes;
    private CrashlyticsBackgroundWorker backgroundWorker;
    private CrashlyticsController controller;
    private CrashlyticsFileMarker crashMarker;
    private float delay;
    private boolean disabled;
    private CrashEventDataProvider externalCrashEventDataProvider;
    private HttpRequestFactory httpRequestFactory;
    private CrashlyticsFileMarker initializationMarker;
    private CrashlyticsListener listener;
    private final PinningInfoProvider pinningInfo;
    private final long startTime;
    private String userEmail = null;
    private String userId = null;
    private String userName = null;

    public CrashlyticsCore() {
        this(1.0f, null, null, false);
    }

    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean bl) {
        this(f, crashlyticsListener, pinningInfoProvider, bl, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
    }

    /*
     * Enabled aggressive block sorting
     */
    CrashlyticsCore(float f, CrashlyticsListener crashlyticsListener, PinningInfoProvider pinningInfoProvider, boolean bl, ExecutorService executorService) {
        this.delay = f;
        if (crashlyticsListener == null) {
            crashlyticsListener = new NoOpListener();
        }
        this.listener = crashlyticsListener;
        this.pinningInfo = pinningInfoProvider;
        this.disabled = bl;
        this.backgroundWorker = new CrashlyticsBackgroundWorker(executorService);
        this.attributes = new ConcurrentHashMap();
        this.startTime = System.currentTimeMillis();
    }

    private void checkForPreviousCrash() {
        Boolean bl = this.backgroundWorker.submitAndWait(new CrashMarkerCheck(this.crashMarker));
        if (!Boolean.TRUE.equals(bl)) {
            return;
        }
        try {
            this.listener.crashlyticsDidDetectCrashDuringPreviousExecution();
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "Exception thrown by CrashlyticsListener while notifying of previous crash.", exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void doLog(int n, String string2, String string3) {
        if (this.disabled || !CrashlyticsCore.ensureFabricWithCalled("prior to logging messages.")) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = this.startTime;
        this.controller.writeToLog(l - l2, CrashlyticsCore.formatLogMessage(n, string2, string3));
    }

    private static boolean ensureFabricWithCalled(String string2) {
        CrashlyticsCore crashlyticsCore = CrashlyticsCore.getInstance();
        if (crashlyticsCore == null || crashlyticsCore.controller == null) {
            Fabric.getLogger().e("CrashlyticsCore", "Crashlytics must be initialized by calling Fabric.with(Context) " + string2, null);
            return false;
        }
        return true;
    }

    private void finishInitSynchronously() {
        Object object = new PriorityCallable<Void>(){

            @Override
            public Void call() throws Exception {
                return CrashlyticsCore.this.doInBackground();
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        Iterator<Task> iterator = this.getDependencies().iterator();
        while (iterator.hasNext()) {
            ((PriorityTask)object).addDependency(iterator.next());
        }
        object = this.getFabric().getExecutorService().submit(object);
        Fabric.getLogger().d("CrashlyticsCore", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
        try {
            object.get(4L, TimeUnit.SECONDS);
            return;
        }
        catch (InterruptedException interruptedException) {
            Fabric.getLogger().e("CrashlyticsCore", "Crashlytics was interrupted during initialization.", interruptedException);
            return;
        }
        catch (ExecutionException executionException) {
            Fabric.getLogger().e("CrashlyticsCore", "Problem encountered during Crashlytics initialization.", executionException);
            return;
        }
        catch (TimeoutException timeoutException) {
            Fabric.getLogger().e("CrashlyticsCore", "Crashlytics timed out during initialization.", timeoutException);
            return;
        }
    }

    private static String formatLogMessage(int n, String string2, String string3) {
        return CommonUtils.logPriorityToString(n) + "/" + string2 + " " + string3;
    }

    public static CrashlyticsCore getInstance() {
        return Fabric.getKit(CrashlyticsCore.class);
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean isBuildIdValid(String string2, boolean bl) {
        if (!bl) {
            Fabric.getLogger().d("CrashlyticsCore", "Configured not to require a build ID.");
            return true;
        } else {
            if (!CommonUtils.isNullOrEmpty(string2)) return true;
            {
                Log.e((String)"CrashlyticsCore", (String)".");
                Log.e((String)"CrashlyticsCore", (String)".     |  | ");
                Log.e((String)"CrashlyticsCore", (String)".     |  |");
                Log.e((String)"CrashlyticsCore", (String)".     |  |");
                Log.e((String)"CrashlyticsCore", (String)".   \\ |  | /");
                Log.e((String)"CrashlyticsCore", (String)".    \\    /");
                Log.e((String)"CrashlyticsCore", (String)".     \\  /");
                Log.e((String)"CrashlyticsCore", (String)".      \\/");
                Log.e((String)"CrashlyticsCore", (String)".");
                Log.e((String)"CrashlyticsCore", (String)"This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.");
                Log.e((String)"CrashlyticsCore", (String)".");
                Log.e((String)"CrashlyticsCore", (String)".      /\\");
                Log.e((String)"CrashlyticsCore", (String)".     /  \\");
                Log.e((String)"CrashlyticsCore", (String)".    /    \\");
                Log.e((String)"CrashlyticsCore", (String)".   / |  | \\");
                Log.e((String)"CrashlyticsCore", (String)".     |  |");
                Log.e((String)"CrashlyticsCore", (String)".     |  |");
                Log.e((String)"CrashlyticsCore", (String)".     |  |");
                Log.e((String)"CrashlyticsCore", (String)".");
                return false;
            }
        }
    }

    private static String sanitizeAttribute(String string2) {
        String string3 = string2;
        if (string2 != null) {
            string3 = string2 = string2.trim();
            if (string2.length() > 1024) {
                string3 = string2.substring(0, 1024);
            }
        }
        return string3;
    }

    void createCrashMarker() {
        this.crashMarker.create();
    }

    boolean didPreviousInitializationFail() {
        return this.backgroundWorker.submitAndWait(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                return CrashlyticsCore.this.initializationMarker.isPresent();
            }
        });
    }

    @Override
    protected Void doInBackground() {
        this.markInitializationStarted();
        Object object = this.getExternalCrashEventData();
        if (object != null) {
            this.controller.writeExternalCrashEvent((SessionEventData)object);
        }
        this.controller.cleanInvalidTempFiles();
        try {
            object = Settings.getInstance().awaitSettingsData();
            if (object == null) {
                Fabric.getLogger().w("CrashlyticsCore", "Received null settings, skipping report submission!");
                return null;
            }
            if (!object.featuresData.collectReports) {
                Fabric.getLogger().d("CrashlyticsCore", "Collection of crash reports disabled in Crashlytics settings.");
                return null;
            }
            if (!this.controller.finalizeSessions(((SettingsData)object).sessionData)) {
                Fabric.getLogger().d("CrashlyticsCore", "Could not finalize previous sessions.");
            }
            this.controller.submitAllReports(this.delay, (SettingsData)object);
            return null;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "Crashlytics encountered a problem during asynchronous initialization.", exception);
            return null;
        }
        finally {
            this.markInitializationComplete();
        }
    }

    Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    CrashlyticsController getController() {
        return this.controller;
    }

    SessionEventData getExternalCrashEventData() {
        SessionEventData sessionEventData = null;
        if (this.externalCrashEventDataProvider != null) {
            sessionEventData = this.externalCrashEventDataProvider.getCrashEventData();
        }
        return sessionEventData;
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-core";
    }

    String getUserEmail() {
        if (this.getIdManager().canCollectUserIds()) {
            return this.userEmail;
        }
        return null;
    }

    String getUserIdentifier() {
        if (this.getIdManager().canCollectUserIds()) {
            return this.userId;
        }
        return null;
    }

    String getUserName() {
        if (this.getIdManager().canCollectUserIds()) {
            return this.userName;
        }
        return null;
    }

    @Override
    public String getVersion() {
        return "2.3.15.167";
    }

    public void log(int n, String string2, String string3) {
        this.doLog(n, string2, string3);
        Fabric.getLogger().log(n, "" + string2, "" + string3, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void logException(Throwable throwable) {
        if (this.disabled || !CrashlyticsCore.ensureFabricWithCalled("prior to logging exceptions.")) {
            return;
        }
        if (throwable == null) {
            Fabric.getLogger().log(5, "CrashlyticsCore", "Crashlytics is ignoring a request to log a null exception.");
            return;
        }
        this.controller.writeNonFatalException(Thread.currentThread(), throwable);
    }

    void markInitializationComplete() {
        this.backgroundWorker.submit(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                boolean bl;
                try {
                    bl = CrashlyticsCore.this.initializationMarker.remove();
                    Fabric.getLogger().d("CrashlyticsCore", "Initialization marker file removed: " + bl);
                }
                catch (Exception exception) {
                    Fabric.getLogger().e("CrashlyticsCore", "Problem encountered deleting Crashlytics initialization marker.", exception);
                    return false;
                }
                return bl;
            }
        });
    }

    void markInitializationStarted() {
        this.backgroundWorker.submitAndWait(new Callable<Void>(){

            @Override
            public Void call() throws Exception {
                CrashlyticsCore.this.initializationMarker.create();
                Fabric.getLogger().d("CrashlyticsCore", "Initialization marker file created.");
                return null;
            }
        });
    }

    @Override
    protected boolean onPreExecute() {
        return this.onPreExecute(super.getContext());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean onPreExecute(Context context) {
        if (this.disabled) {
            return false;
        }
        Object object = new ApiKey().getValue(context);
        if (object == null) {
            return false;
        }
        Object object2 = CommonUtils.resolveBuildId(context);
        if (!CrashlyticsCore.isBuildIdValid((String)object2, CommonUtils.getBooleanResourceValue(context, "com.crashlytics.RequireBuildId", true))) {
            throw new UnmetDependencyException("This app relies on Crashlytics. Please sign up for access at https://fabric.io/sign_up,\ninstall an Android build tool and ask a team member to invite you to this app's organization.");
        }
        try {
            Fabric.getLogger().i("CrashlyticsCore", "Initializing Crashlytics " + this.getVersion());
            FileStoreImpl fileStoreImpl = new FileStoreImpl(this);
            this.crashMarker = new CrashlyticsFileMarker("crash_marker", fileStoreImpl);
            this.initializationMarker = new CrashlyticsFileMarker("initialization_marker", fileStoreImpl);
            PreferenceManager preferenceManager = PreferenceManager.create(new PreferenceStoreImpl(this.getContext(), "com.crashlytics.android.core.CrashlyticsCore"), this);
            Object object3 = this.pinningInfo != null ? new CrashlyticsPinningInfoProvider(this.pinningInfo) : null;
            this.httpRequestFactory = new DefaultHttpRequestFactory(Fabric.getLogger());
            this.httpRequestFactory.setPinningInfoProvider((io.fabric.sdk.android.services.network.PinningInfoProvider)object3);
            object3 = this.getIdManager();
            object = AppData.create(context, (IdManager)object3, (String)object, (String)object2);
            object2 = new ManifestUnityVersionProvider(context, ((AppData)object).packageName);
            Fabric.getLogger().d("CrashlyticsCore", "Installer package name is: " + ((AppData)object).installerPackageName);
            this.controller = new CrashlyticsController(this, this.backgroundWorker, this.httpRequestFactory, (IdManager)object3, preferenceManager, fileStoreImpl, (AppData)object, (UnityVersionProvider)object2);
            boolean bl = this.didPreviousInitializationFail();
            this.checkForPreviousCrash();
            this.controller.enableExceptionHandling(Thread.getDefaultUncaughtExceptionHandler());
            if (bl && CommonUtils.canTryConnection(context)) {
                Fabric.getLogger().d("CrashlyticsCore", "Crashlytics did not finish previous background initialization. Initializing synchronously.");
                this.finishInitSynchronously();
                return false;
            }
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "Crashlytics was not started due to an exception during initialization", exception);
            this.controller = null;
            return false;
        }
        Fabric.getLogger().d("CrashlyticsCore", "Exception handling initialization successful");
        return true;
    }

    public void setBool(String string2, boolean bl) {
        this.setString(string2, Boolean.toString(bl));
    }

    public void setInt(String string2, int n) {
        this.setString(string2, Integer.toString(n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setString(String string2, String string3) {
        if (this.disabled || !CrashlyticsCore.ensureFabricWithCalled("prior to setting keys.")) {
            return;
        }
        if (string2 == null) {
            string2 = this.getContext();
            if (string2 != null && CommonUtils.isAppDebuggable((Context)string2)) {
                throw new IllegalArgumentException("Custom attribute key must not be null.");
            }
            Fabric.getLogger().e("CrashlyticsCore", "Attempting to set custom attribute with null key, ignoring.", null);
            return;
        }
        String string4 = CrashlyticsCore.sanitizeAttribute(string2);
        if (this.attributes.size() >= 64 && !this.attributes.containsKey(string4)) {
            Fabric.getLogger().d("CrashlyticsCore", "Exceeded maximum number of custom attributes (64)");
            return;
        }
        string2 = string3 == null ? "" : CrashlyticsCore.sanitizeAttribute(string3);
        this.attributes.put(string4, string2);
        this.controller.cacheKeyData(this.attributes);
    }

    public static class Builder {
        private float delay = -1.0f;
        private boolean disabled = false;
        private CrashlyticsListener listener;
        private PinningInfoProvider pinningInfoProvider;

        public CrashlyticsCore build() {
            if (this.delay < 0.0f) {
                this.delay = 1.0f;
            }
            return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
        }

        public Builder disabled(boolean bl) {
            this.disabled = bl;
            return this;
        }
    }

    private static final class CrashMarkerCheck
    implements Callable<Boolean> {
        private final CrashlyticsFileMarker crashMarker;

        public CrashMarkerCheck(CrashlyticsFileMarker crashlyticsFileMarker) {
            this.crashMarker = crashlyticsFileMarker;
        }

        @Override
        public Boolean call() throws Exception {
            if (!this.crashMarker.isPresent()) {
                return Boolean.FALSE;
            }
            Fabric.getLogger().d("CrashlyticsCore", "Found previous crash marker.");
            this.crashMarker.remove();
            return Boolean.TRUE;
        }
    }

    private static final class NoOpListener
    implements CrashlyticsListener {
        private NoOpListener() {
        }

        @Override
        public void crashlyticsDidDetectCrashDuringPreviousExecution() {
        }
    }

}

