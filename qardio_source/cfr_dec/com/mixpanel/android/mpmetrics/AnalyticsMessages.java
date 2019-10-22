/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.os.SystemClock
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.iid.InstanceID;
import com.mixpanel.android.mpmetrics.DecideChecker;
import com.mixpanel.android.mpmetrics.DecideMessages;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MPDbAdapter;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.SystemInformation;
import com.mixpanel.android.util.HttpService;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.OfflineMode;
import com.mixpanel.android.util.RemoteService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class AnalyticsMessages {
    private static final Map<Context, AnalyticsMessages> sInstances = new HashMap<Context, AnalyticsMessages>();
    protected final MPConfig mConfig;
    protected final Context mContext;
    private final Worker mWorker;

    AnalyticsMessages(Context context) {
        this.mContext = context;
        this.mConfig = this.getConfig(context);
        this.mWorker = this.createWorker();
        this.getPoster().checkIsMixpanelBlocked();
    }

    static /* synthetic */ void access$500(AnalyticsMessages analyticsMessages, String string2, Throwable throwable) {
        analyticsMessages.logAboutMessageToMixpanel(string2, throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AnalyticsMessages getInstance(Context object) {
        Map<Context, AnalyticsMessages> map = sInstances;
        synchronized (map) {
            Context context = object.getApplicationContext();
            if (sInstances.containsKey((Object)context)) return sInstances.get((Object)context);
            object = new AnalyticsMessages(context);
            sInstances.put(context, (AnalyticsMessages)object);
            return object;
        }
    }

    private void logAboutMessageToMixpanel(String string2) {
        MPLog.v("MixpanelAPI.Messages", string2 + " (Thread " + Thread.currentThread().getId() + ")");
    }

    private void logAboutMessageToMixpanel(String string2, Throwable throwable) {
        MPLog.v("MixpanelAPI.Messages", string2 + " (Thread " + Thread.currentThread().getId() + ")", throwable);
    }

    protected Worker createWorker() {
        return new Worker();
    }

    public void eventsMessage(EventDescription eventDescription) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = eventDescription;
        this.mWorker.runMessage(message);
    }

    protected MPConfig getConfig(Context context) {
        return MPConfig.getInstance(context);
    }

    protected RemoteService getPoster() {
        return new HttpService();
    }

    public void installDecideCheck(DecideMessages decideMessages) {
        Message message = Message.obtain();
        message.what = 12;
        message.obj = decideMessages;
        this.mWorker.runMessage(message);
    }

    protected MPDbAdapter makeDbAdapter(Context context) {
        return MPDbAdapter.getInstance(context);
    }

    public void peopleMessage(PeopleDescription peopleDescription) {
        Message message = Message.obtain();
        message.what = 0;
        message.obj = peopleDescription;
        this.mWorker.runMessage(message);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void postToServer(FlushDescription flushDescription) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = flushDescription.getToken();
        int n = flushDescription.shouldCheckDecide() ? 1 : 0;
        message.arg1 = n;
        this.mWorker.runMessage(message);
    }

    public void registerForGCM(String string2) {
        Message message = Message.obtain();
        message.what = 13;
        message.obj = string2;
        this.mWorker.runMessage(message);
    }

    static class EventDescription
    extends MixpanelDescription {
        private final String mEventName;
        private final boolean mIsAutomatic;
        private final JSONObject mProperties;

        public EventDescription(String string2, JSONObject jSONObject, String string3, boolean bl) {
            super(string3);
            this.mEventName = string2;
            this.mProperties = jSONObject;
            this.mIsAutomatic = bl;
        }

        public String getEventName() {
            return this.mEventName;
        }

        public JSONObject getProperties() {
            return this.mProperties;
        }

        public boolean isAutomatic() {
            return this.mIsAutomatic;
        }
    }

    static class FlushDescription
    extends MixpanelDescription {
        private final boolean checkDecide;

        public FlushDescription(String string2) {
            this(string2, true);
        }

        protected FlushDescription(String string2, boolean bl) {
            super(string2);
            this.checkDecide = bl;
        }

        public boolean shouldCheckDecide() {
            return this.checkDecide;
        }
    }

    static class MixpanelDescription {
        private final String mToken;

        public MixpanelDescription(String string2) {
            this.mToken = string2;
        }

        public String getToken() {
            return this.mToken;
        }
    }

    static class PeopleDescription
    extends MixpanelDescription {
        private final JSONObject message;

        public PeopleDescription(JSONObject jSONObject, String string2) {
            super(string2);
            this.message = jSONObject;
        }

        public JSONObject getMessage() {
            return this.message;
        }

        public String toString() {
            return this.message.toString();
        }
    }

    class Worker {
        private long mAveFlushFrequency = 0L;
        private long mFlushCount = 0L;
        private Handler mHandler;
        private final Object mHandlerLock = new Object();
        private long mLastFlushTime = -1L;
        private SystemInformation mSystemInformation;

        public Worker() {
            this.mHandler = this.restartWorkerThread();
        }

        static /* synthetic */ SystemInformation access$100(Worker worker) {
            return worker.mSystemInformation;
        }

        static /* synthetic */ void access$200(Worker worker) {
            worker.updateFlushFrequency();
        }

        static /* synthetic */ Object access$300(Worker worker) {
            return worker.mHandlerLock;
        }

        static /* synthetic */ Handler access$402(Worker worker, Handler handler) {
            worker.mHandler = handler;
            return handler;
        }

        private void updateFlushFrequency() {
            long l = System.currentTimeMillis();
            long l2 = this.mFlushCount + 1L;
            if (this.mLastFlushTime > 0L) {
                this.mAveFlushFrequency = (l - this.mLastFlushTime + this.mAveFlushFrequency * this.mFlushCount) / l2;
                long l3 = this.mAveFlushFrequency / 1000L;
                AnalyticsMessages.this.logAboutMessageToMixpanel("Average send frequency approximately " + l3 + " seconds.");
            }
            this.mLastFlushTime = l;
            this.mFlushCount = l2;
        }

        protected Handler restartWorkerThread() {
            HandlerThread handlerThread = new HandlerThread("com.mixpanel.android.AnalyticsWorker", 10);
            handlerThread.start();
            return new AnalyticsMessageHandler(handlerThread.getLooper());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void runMessage(Message message) {
            Object object = this.mHandlerLock;
            synchronized (object) {
                if (this.mHandler == null) {
                    AnalyticsMessages.this.logAboutMessageToMixpanel("Dead mixpanel worker dropping a message: " + message.what);
                } else {
                    this.mHandler.sendMessage(message);
                }
                return;
            }
        }

        class AnalyticsMessageHandler
        extends Handler {
            private MPDbAdapter mDbAdapter;
            private final DecideChecker mDecideChecker;
            private long mDecideRetryAfter;
            private int mFailedRetries;
            private final long mFlushInterval;
            private long mTrackEngageRetryAfter;

            public AnalyticsMessageHandler(Looper looper) {
                super(looper);
                this.mDbAdapter = null;
                Worker.this.mSystemInformation = SystemInformation.getInstance(AnalyticsMessages.this.mContext);
                this.mDecideChecker = this.createDecideChecker();
                this.mFlushInterval = AnalyticsMessages.this.mConfig.getFlushInterval();
            }

            /*
             * Exception decompiling
             */
            private JSONObject getDefaultEventProperties() throws JSONException {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CASE]], but top level block is 1[TRYBLOCK]
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                throw new IllegalStateException("Decompilation failed");
            }

            private JSONObject prepareEventObject(EventDescription eventDescription) throws JSONException {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = eventDescription.getProperties();
                JSONObject jSONObject3 = this.getDefaultEventProperties();
                jSONObject3.put("token", (Object)eventDescription.getToken());
                if (jSONObject2 != null) {
                    Iterator iterator = jSONObject2.keys();
                    while (iterator.hasNext()) {
                        String string2 = (String)iterator.next();
                        jSONObject3.put(string2, jSONObject2.get(string2));
                    }
                }
                jSONObject.put("event", (Object)eventDescription.getEventName());
                jSONObject.put("properties", (Object)jSONObject3);
                return jSONObject;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void runGCMRegistration(final String string2) {
                try {
                    try {
                        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AnalyticsMessages.this.mContext) != 0) {
                            MPLog.i("MixpanelAPI.Messages", "Can't register for push notifications, Google Play Services are not installed.");
                            return;
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        MPLog.i("MixpanelAPI.Messages", "Can't register for push notifications, Google Play services are not configured.");
                        return;
                    }
                }
                catch (IOException iOException) {
                    MPLog.i("MixpanelAPI.Messages", "Exception when trying to register for GCM", iOException);
                    return;
                }
                catch (NoClassDefFoundError noClassDefFoundError) {
                    MPLog.w("MixpanelAPI.Messages", "Google play services were not part of this build, push notifications cannot be registered or delivered");
                    return;
                }
                {
                    string2 = InstanceID.getInstance(AnalyticsMessages.this.mContext).getToken(string2, "GCM", null);
                }
                MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(){

                    @Override
                    public void process(MixpanelAPI mixpanelAPI) {
                        MPLog.v("MixpanelAPI.Messages", "Using existing pushId " + string2);
                        mixpanelAPI.getPeople().setPushRegistrationId(string2);
                    }
                });
            }

            private void sendAllData(MPDbAdapter mPDbAdapter, String string2) {
                if (!AnalyticsMessages.this.getPoster().isOnline(AnalyticsMessages.this.mContext, AnalyticsMessages.this.mConfig.getOfflineMode())) {
                    AnalyticsMessages.this.logAboutMessageToMixpanel("Not flushing data to Mixpanel because the device is not connected to the internet.");
                    return;
                }
                this.sendData(mPDbAdapter, string2, MPDbAdapter.Table.EVENTS, AnalyticsMessages.this.mConfig.getEventsEndpoint());
                this.sendData(mPDbAdapter, string2, MPDbAdapter.Table.PEOPLE, AnalyticsMessages.this.mConfig.getPeopleEndpoint());
            }

            /*
             * Exception decompiling
             */
            private void sendData(MPDbAdapter var1_1, String var2_2, MPDbAdapter.Table var3_3, String var4_4) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                throw new IllegalStateException("Decompilation failed");
            }

            protected DecideChecker createDecideChecker() {
                return new DecideChecker(AnalyticsMessages.this.mContext, AnalyticsMessages.this.mConfig);
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            public void handleMessage(Message var1_1) {
                if (this.mDbAdapter == null) {
                    this.mDbAdapter = Worker.this.AnalyticsMessages.this.makeDbAdapter(Worker.this.AnalyticsMessages.this.mContext);
                    this.mDbAdapter.cleanupEvents(System.currentTimeMillis() - (long)Worker.this.AnalyticsMessages.this.mConfig.getDataExpiration(), MPDbAdapter.Table.EVENTS);
                    this.mDbAdapter.cleanupEvents(System.currentTimeMillis() - (long)Worker.this.AnalyticsMessages.this.mConfig.getDataExpiration(), MPDbAdapter.Table.PEOPLE);
                }
                var4_5 = -3;
                var10_6 = null;
                var9_7 = null;
                try {
                    block34: {
                        block33: {
                            if (var1_1 /* !! */ .what != 0) break block33;
                            var9_7 = (PeopleDescription)var1_1 /* !! */ .obj;
                            AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "Queuing people record for sending later");
                            AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "    " + var9_7.toString());
                            var1_1 /* !! */  = var9_7.getToken();
                            var2_11 = this.mDbAdapter.addJSON(var9_7.getMessage(), (String)var1_1 /* !! */ , MPDbAdapter.Table.PEOPLE, false);
                            ** GOTO lbl122
                        }
                        if (var1_1 /* !! */ .what != 1) break block34;
                        var11_14 = (EventDescription)var1_1 /* !! */ .obj;
                        var1_1 /* !! */  = var10_6;
                        try {
                            var12_15 = this.prepareEventObject(var11_14);
                            var1_1 /* !! */  = var10_6;
                            AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "Queuing event for sending later");
                            var1_1 /* !! */  = var10_6;
                            AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "    " + var12_15.toString());
                            var1_1 /* !! */  = var10_6;
                            var9_7 = var11_14.getToken();
                            var1_1 /* !! */  = var9_7;
                            var10_6 = this.mDecideChecker.getDecideMessages((String)var9_7);
                            if (var10_6 != null) {
                                var1_1 /* !! */  = var9_7;
                                if (var11_14.isAutomatic()) {
                                    var1_1 /* !! */  = var9_7;
                                    if (var10_6.shouldTrackAutomaticEvent() == false) return;
                                }
                            }
                            var1_1 /* !! */  = var9_7;
                            var2_11 = this.mDbAdapter.addJSON(var12_15, (String)var9_7, MPDbAdapter.Table.EVENTS, var11_14.isAutomatic());
                            var1_1 /* !! */  = var9_7;
                        }
                        catch (JSONException var9_8) {
                            MPLog.e("MixpanelAPI.Messages", "Exception tracking event " + var11_14.getEventName(), var9_8);
                            var2_11 = var4_5;
                        }
                        ** GOTO lbl122
                    }
                    if (var1_1 /* !! */ .what == 2) {
                        AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "Flushing queue due to scheduled or forced flush");
                        Worker.access$200(Worker.this);
                        var9_7 = (String)var1_1 /* !! */ .obj;
                        var3_16 = var1_1 /* !! */ .arg1 == 1;
                    }
                    ** GOTO lbl83
                }
                catch (RuntimeException var9_9) {
                    block35: {
                        MPLog.e("MixpanelAPI.Messages", "Worker threw an unhandled exception", var9_9);
                        var1_1 /* !! */  = Worker.access$300(Worker.this);
                        // MONITORENTER : var1_1 /* !! */ 
                        Worker.access$402(Worker.this, null);
                        try {
                            Looper.myLooper().quit();
                            MPLog.e("MixpanelAPI.Messages", "Mixpanel will not process any more analytics messages", var9_9);
                            // MONITOREXIT : var1_1 /* !! */ 
                            return;
                        }
                        catch (Exception var9_10) {
                            MPLog.e("MixpanelAPI.Messages", "Could not halt looper", var9_10);
                            return;
                        }
                        this.sendAllData(this.mDbAdapter, (String)var9_7);
                        var2_11 = var4_5;
                        var1_1 /* !! */  = var9_7;
                        if (var3_16) {
                            var5_12 = SystemClock.elapsedRealtime();
                            var7_13 = this.mDecideRetryAfter;
                            var2_11 = var4_5;
                            var1_1 /* !! */  = var9_7;
                            if (var5_12 >= var7_13) {
                                try {
                                    this.mDecideChecker.runDecideCheck((String)var9_7, Worker.this.AnalyticsMessages.this.getPoster());
                                    var2_11 = var4_5;
                                    var1_1 /* !! */  = var9_7;
                                }
                                catch (RemoteService.ServiceUnavailableException var1_2) {
                                    this.mDecideRetryAfter = SystemClock.elapsedRealtime() + (long)(var1_2.getRetryAfter() * 1000);
                                    var2_11 = var4_5;
                                    var1_1 /* !! */  = var9_7;
                                }
                            }
                        }
                        break block35;
lbl83:
                        // 1 sources
                        if (var1_1 /* !! */ .what == 12) {
                            AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "Installing a check for in-app notifications");
                            var10_6 = (DecideMessages)var1_1 /* !! */ .obj;
                            this.mDecideChecker.addDecideCheck(var10_6);
                            var5_12 = SystemClock.elapsedRealtime();
                            var7_13 = this.mDecideRetryAfter;
                            var2_11 = var4_5;
                            var1_1 /* !! */  = var9_7;
                            if (var5_12 >= var7_13) {
                                try {
                                    this.mDecideChecker.runDecideCheck(var10_6.getToken(), Worker.this.AnalyticsMessages.this.getPoster());
                                    var2_11 = var4_5;
                                    var1_1 /* !! */  = var9_7;
                                }
                                catch (RemoteService.ServiceUnavailableException var1_3) {
                                    this.mDecideRetryAfter = SystemClock.elapsedRealtime() + (long)(var1_3.getRetryAfter() * 1000);
                                    var2_11 = var4_5;
                                    var1_1 /* !! */  = var9_7;
                                }
                            }
                        } else if (var1_1 /* !! */ .what == 13) {
                            this.runGCMRegistration((String)var1_1 /* !! */ .obj);
                            var2_11 = var4_5;
                            var1_1 /* !! */  = var9_7;
                        } else if (var1_1 /* !! */ .what == 5) {
                            MPLog.w("MixpanelAPI.Messages", "Worker received a hard kill. Dumping all events and force-killing. Thread id " + Thread.currentThread().getId());
                            var1_1 /* !! */  = Worker.access$300(Worker.this);
                            // MONITORENTER : var1_1 /* !! */ 
                            this.mDbAdapter.deleteDB();
                            Worker.access$402(Worker.this, null);
                            Looper.myLooper().quit();
                            // MONITOREXIT : var1_1 /* !! */ 
                            var2_11 = var4_5;
                            var1_1 /* !! */  = var9_7;
                        } else {
                            MPLog.e("MixpanelAPI.Messages", "Unexpected message received by Mixpanel worker: " + (Object)var1_1 /* !! */ );
                            var2_11 = var4_5;
                            var1_1 /* !! */  = var9_7;
                        }
                    }
                    if ((var2_11 >= Worker.this.AnalyticsMessages.this.mConfig.getBulkUploadLimit() || var2_11 == -2) && this.mFailedRetries <= 0 && var1_1 /* !! */  != null) {
                        AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "Flushing queue due to bulk upload limit (" + var2_11 + ") for project " + (String)var1_1 /* !! */ );
                        Worker.access$200(Worker.this);
                        this.sendAllData(this.mDbAdapter, (String)var1_1 /* !! */ );
                        var5_12 = SystemClock.elapsedRealtime();
                        var7_13 = this.mDecideRetryAfter;
                        if (var5_12 < var7_13) return;
                        try {
                            this.mDecideChecker.runDecideCheck((String)var1_1 /* !! */ , Worker.this.AnalyticsMessages.this.getPoster());
                            return;
                        }
                        catch (RemoteService.ServiceUnavailableException var1_4) {
                            this.mDecideRetryAfter = SystemClock.elapsedRealtime() + (long)(var1_4.getRetryAfter() * 1000);
                            return;
                        }
                    }
                    if (var2_11 <= 0) return;
                    if (this.hasMessages(2, (Object)var1_1 /* !! */ ) != false) return;
                    AnalyticsMessages.access$000(Worker.this.AnalyticsMessages.this, "Queue depth " + var2_11 + " - Adding flush in " + this.mFlushInterval);
                    if (this.mFlushInterval < 0L) return;
                    var9_7 = Message.obtain();
                    var9_7.what = 2;
                    var9_7.obj = var1_1 /* !! */ ;
                    var9_7.arg1 = 1;
                    this.sendMessageDelayed((Message)var9_7, this.mFlushInterval);
                    return;
                }
            }

        }

    }

}

