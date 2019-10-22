/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.hardware.Sensor
 *  android.hardware.SensorEventListener
 *  android.hardware.SensorManager
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.util.DisplayMetrics
 *  android.util.JsonWriter
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.viewcrawler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.JsonWriter;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.OnMixpanelTweaksUpdatedListener;
import com.mixpanel.android.mpmetrics.ResourceIds;
import com.mixpanel.android.mpmetrics.ResourceReader;
import com.mixpanel.android.mpmetrics.SuperPropertyUpdate;
import com.mixpanel.android.mpmetrics.Tweaks;
import com.mixpanel.android.util.ImageStore;
import com.mixpanel.android.util.JSONUtils;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.MPPair;
import com.mixpanel.android.viewcrawler.DynamicEventTracker;
import com.mixpanel.android.viewcrawler.EditProtocol;
import com.mixpanel.android.viewcrawler.EditState;
import com.mixpanel.android.viewcrawler.EditorConnection;
import com.mixpanel.android.viewcrawler.FlipGesture;
import com.mixpanel.android.viewcrawler.TrackingDebug;
import com.mixpanel.android.viewcrawler.UIThreadSet;
import com.mixpanel.android.viewcrawler.UpdatesFromMixpanel;
import com.mixpanel.android.viewcrawler.ViewSnapshot;
import com.mixpanel.android.viewcrawler.ViewVisitor;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(value=16)
public class ViewCrawler
implements TrackingDebug,
UpdatesFromMixpanel,
ViewVisitor.OnLayoutErrorListener {
    private final MPConfig mConfig;
    private final Context mContext;
    private final Map<String, String> mDeviceInfo;
    private final DynamicEventTracker mDynamicEventTracker;
    private final EditState mEditState;
    private final ViewCrawlerHandler mMessageThreadHandler;
    private final MixpanelAPI mMixpanel;
    private final float mScaledDensity;
    private final Tweaks mTweaks;
    private final Set<OnMixpanelTweaksUpdatedListener> mTweaksUpdatedListeners;

    public ViewCrawler(Context context, String string2, MixpanelAPI mixpanelAPI, Tweaks tweaks) {
        this.mConfig = MPConfig.getInstance(context);
        this.mContext = context;
        this.mEditState = new EditState();
        this.mTweaks = tweaks;
        this.mDeviceInfo = mixpanelAPI.getDeviceInfo();
        this.mScaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        this.mTweaksUpdatedListeners = Collections.newSetFromMap(new ConcurrentHashMap());
        tweaks = new HandlerThread(ViewCrawler.class.getCanonicalName());
        tweaks.setPriority(10);
        tweaks.start();
        this.mMessageThreadHandler = new ViewCrawlerHandler(context, string2, tweaks.getLooper(), this);
        this.mDynamicEventTracker = new DynamicEventTracker(mixpanelAPI, this.mMessageThreadHandler);
        this.mMixpanel = mixpanelAPI;
        ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new LifecycleCallbacks());
        this.mTweaks.addOnTweakDeclaredListener(new Tweaks.OnTweakDeclaredListener(){

            @Override
            public void onTweakDeclared() {
                Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(4);
                ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
            }
        });
    }

    static /* synthetic */ float access$600(ViewCrawler viewCrawler) {
        return viewCrawler.mScaledDensity;
    }

    static /* synthetic */ Map access$700(ViewCrawler viewCrawler) {
        return viewCrawler.mDeviceInfo;
    }

    @Override
    public void applyPersistedUpdates() {
        this.mMessageThreadHandler.sendMessage(this.mMessageThreadHandler.obtainMessage(0));
    }

    @Override
    public void onLayoutError(ViewVisitor.LayoutErrorMessage layoutErrorMessage) {
        Message message = this.mMessageThreadHandler.obtainMessage();
        message.what = 12;
        message.obj = layoutErrorMessage;
        this.mMessageThreadHandler.sendMessage(message);
    }

    @Override
    public void reportTrack(String string2) {
        Message message = this.mMessageThreadHandler.obtainMessage();
        message.what = 7;
        message.obj = string2;
        this.mMessageThreadHandler.sendMessage(message);
    }

    @Override
    public void setEventBindings(JSONArray jSONArray) {
        if (jSONArray != null) {
            Message message = this.mMessageThreadHandler.obtainMessage(5);
            message.obj = jSONArray;
            this.mMessageThreadHandler.sendMessage(message);
        }
    }

    @Override
    public void setVariants(JSONArray jSONArray) {
        if (jSONArray != null) {
            Message message = this.mMessageThreadHandler.obtainMessage(9);
            message.obj = jSONArray;
            this.mMessageThreadHandler.sendMessage(message);
        }
    }

    @Override
    public void startUpdates() {
        this.mMessageThreadHandler.start();
        this.applyPersistedUpdates();
    }

    @Override
    public void storeVariants(JSONArray jSONArray) {
        if (jSONArray != null) {
            Message message = this.mMessageThreadHandler.obtainMessage(13);
            message.obj = jSONArray;
            this.mMessageThreadHandler.sendMessage(message);
        }
    }

    private class Editor
    implements EditorConnection.Editor {
        private Editor() {
        }

        @Override
        public void bindEvents(JSONObject jSONObject) {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(6);
            message.obj = jSONObject;
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }

        @Override
        public void cleanup() {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(8);
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }

        @Override
        public void clearEdits(JSONObject jSONObject) {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(10);
            message.obj = jSONObject;
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }

        @Override
        public void performEdit(JSONObject jSONObject) {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(3);
            message.obj = jSONObject;
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }

        @Override
        public void sendDeviceInfo() {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(4);
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }

        @Override
        public void sendSnapshot(JSONObject jSONObject) {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(2);
            message.obj = jSONObject;
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }

        @Override
        public void setTweaks(JSONObject jSONObject) {
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(11);
            message.obj = jSONObject;
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }
    }

    private class EmulatorConnector
    implements Runnable {
        private volatile boolean mStopped = true;

        @Override
        public void run() {
            if (!this.mStopped) {
                Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(1);
                ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
            }
            ViewCrawler.this.mMessageThreadHandler.postDelayed((Runnable)this, 30000L);
        }

        public void start() {
            this.mStopped = false;
            ViewCrawler.this.mMessageThreadHandler.post((Runnable)this);
        }

        public void stop() {
            this.mStopped = true;
            ViewCrawler.this.mMessageThreadHandler.removeCallbacks((Runnable)this);
        }
    }

    private class LifecycleCallbacks
    implements Application.ActivityLifecycleCallbacks,
    FlipGesture.OnFlipGestureListener {
        private final EmulatorConnector mEmulatorConnector;
        private final FlipGesture mFlipGesture = new FlipGesture(this);

        public LifecycleCallbacks() {
            this.mEmulatorConnector = new EmulatorConnector();
        }

        /*
         * Enabled aggressive block sorting
         */
        private void installConnectionSensor(Activity activity) {
            if (this.isInEmulator() && !ViewCrawler.this.mConfig.getDisableEmulatorBindingUI()) {
                this.mEmulatorConnector.start();
                return;
            } else {
                if (ViewCrawler.this.mConfig.getDisableGestureBindingUI()) return;
                {
                    activity = (SensorManager)activity.getSystemService("sensor");
                    Sensor sensor = activity.getDefaultSensor(1);
                    activity.registerListener((SensorEventListener)this.mFlipGesture, sensor, 3);
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean isInEmulator() {
            return !(!Build.HARDWARE.toLowerCase().equals("goldfish") && !Build.HARDWARE.toLowerCase().equals("ranchu") || !Build.BRAND.toLowerCase().startsWith("generic") && !Build.BRAND.toLowerCase().equals("android") && !Build.BRAND.toLowerCase().equals("google") || !Build.DEVICE.toLowerCase().startsWith("generic") || !Build.PRODUCT.toLowerCase().contains("sdk")) && Build.MODEL.toLowerCase(Locale.US).contains("sdk");
        }

        /*
         * Enabled aggressive block sorting
         */
        private void uninstallConnectionSensor(Activity activity) {
            if (this.isInEmulator() && !ViewCrawler.this.mConfig.getDisableEmulatorBindingUI()) {
                this.mEmulatorConnector.stop();
                return;
            } else {
                if (ViewCrawler.this.mConfig.getDisableGestureBindingUI()) return;
                {
                    ((SensorManager)activity.getSystemService("sensor")).unregisterListener((SensorEventListener)this.mFlipGesture);
                    return;
                }
            }
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
            ViewCrawler.this.mEditState.remove(activity);
            this.uninstallConnectionSensor(activity);
        }

        public void onActivityResumed(Activity activity) {
            this.installConnectionSensor(activity);
            ViewCrawler.this.mEditState.add(activity);
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onFlipGesture() {
            ViewCrawler.this.mMixpanel.track("$ab_gesture3");
            Message message = ViewCrawler.this.mMessageThreadHandler.obtainMessage(1);
            ViewCrawler.this.mMessageThreadHandler.sendMessage(message);
        }
    }

    private static class VariantChange {
        public final String activityName;
        public final JSONObject change;
        public final String name;
        public final MPPair<Integer, Integer> variantId;

        public VariantChange(String string2, String string3, JSONObject jSONObject, MPPair<Integer, Integer> mPPair) {
            this.name = string2;
            this.activityName = string3;
            this.change = jSONObject;
            this.variantId = mPPair;
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof VariantChange) {
                bl2 = bl;
                if (object.hashCode() == this.hashCode()) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        public int hashCode() {
            return this.name.hashCode();
        }
    }

    private static class VariantTweak {
        public final JSONObject tweak;
        public final String tweakName;
        public final MPPair<Integer, Integer> variantId;

        public VariantTweak(String string2, JSONObject jSONObject, MPPair<Integer, Integer> mPPair) {
            this.tweakName = string2;
            this.tweak = jSONObject;
            this.variantId = mPPair;
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof VariantTweak) {
                bl2 = bl;
                if (object.hashCode() == this.hashCode()) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        public int hashCode() {
            return this.tweakName.hashCode();
        }
    }

    private class ViewCrawlerHandler
    extends Handler {
        private final Set<VariantTweak> mAppliedTweaks;
        private final Set<VariantChange> mAppliedVisualChanges;
        private final List<String> mEditorAssetUrls;
        private final Map<String, MPPair<String, JSONObject>> mEditorChanges;
        private EditorConnection mEditorConnection;
        private final Map<String, MPPair<String, JSONObject>> mEditorEventBindings;
        private final List<JSONObject> mEditorTweaks;
        private final Set<MPPair<Integer, Integer>> mEmptyExperiments;
        private final ImageStore mImageStore;
        private final Set<MPPair<String, JSONObject>> mOriginalEventBindings;
        private final Set<MPPair<String, JSONObject>> mPersistentEventBindings;
        private final EditProtocol mProtocol;
        private final Set<MPPair<Integer, Integer>> mSeenExperiments;
        private ViewSnapshot mSnapshot;
        private final Lock mStartLock;
        private final String mToken;

        public ViewCrawlerHandler(Context context, String string2, Looper looper, ViewVisitor.OnLayoutErrorListener onLayoutErrorListener) {
            super(looper);
            this.mToken = string2;
            this.mSnapshot = null;
            string2 = ((ViewCrawler)ViewCrawler.this).mConfig.getResourcePackageName();
            ViewCrawler.this = string2;
            if (string2 == null) {
                ViewCrawler.this = context.getPackageName();
            }
            ViewCrawler.this = new ResourceReader.Ids((String)ViewCrawler.this, context);
            this.mImageStore = new ImageStore(context, "ViewCrawler");
            this.mProtocol = new EditProtocol(context, (ResourceIds)ViewCrawler.this, this.mImageStore, onLayoutErrorListener);
            this.mOriginalEventBindings = new HashSet<MPPair<String, JSONObject>>();
            this.mEditorChanges = new HashMap<String, MPPair<String, JSONObject>>();
            this.mEditorTweaks = new ArrayList<JSONObject>();
            this.mEditorAssetUrls = new ArrayList<String>();
            this.mEditorEventBindings = new HashMap<String, MPPair<String, JSONObject>>();
            this.mAppliedVisualChanges = new HashSet<VariantChange>();
            this.mAppliedTweaks = new HashSet<VariantTweak>();
            this.mEmptyExperiments = new HashSet<MPPair<Integer, Integer>>();
            this.mPersistentEventBindings = new HashSet<MPPair<String, JSONObject>>();
            this.mSeenExperiments = new HashSet<MPPair<Integer, Integer>>();
            this.mStartLock = new ReentrantLock();
            this.mStartLock.lock();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void applyVariantsAndEventBindings() {
            int n;
            Object object;
            Object object22;
            Object object32;
            ArrayList<MPPair<Object, Object>> arrayList = new ArrayList<MPPair<Object, Object>>();
            HashSet<MPPair<Integer, Integer>> hashSet = new HashSet<MPPair<Integer, Integer>>();
            HashSet<String> hashSet2 = new HashSet<String>();
            for (Object object22 : this.mAppliedVisualChanges) {
                try {
                    object32 = this.mProtocol.readEdit(((VariantChange)object22).change);
                    arrayList.add(new MPPair<String, ViewVisitor>(((VariantChange)object22).activityName, ((EditProtocol.Edit)object32).visitor));
                    if (this.mSeenExperiments.contains(((VariantChange)object22).variantId)) continue;
                    hashSet.add(((VariantChange)object22).variantId);
                }
                catch (EditProtocol.CantGetEditAssetsException cantGetEditAssetsException) {
                    MPLog.v("MixpanelAPI.ViewCrawler", "Can't load assets for an edit, won't apply the change now", cantGetEditAssetsException);
                }
                catch (EditProtocol.InapplicableInstructionsException inapplicableInstructionsException) {
                    MPLog.i("MixpanelAPI.ViewCrawler", inapplicableInstructionsException.getMessage());
                }
                catch (EditProtocol.BadInstructionsException badInstructionsException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad persistent change request cannot be applied.", badInstructionsException);
                }
            }
            for (Object object32 : this.mAppliedTweaks) {
                try {
                    object22 = this.mProtocol.readTweak(((VariantTweak)object32).tweak);
                    if (!this.mSeenExperiments.contains(((VariantTweak)object32).variantId)) {
                        hashSet.add(((VariantTweak)object32).variantId);
                        hashSet2.add((String)((MPPair)object22).first);
                    } else if (ViewCrawler.this.mTweaks.isNewValue((String)((MPPair)object22).first, ((MPPair)object22).second)) {
                        hashSet2.add((String)((MPPair)object22).first);
                    }
                    if (!ViewCrawler.this.mTweaks.getAllValues().containsKey(((MPPair)object22).first) && (object32 = Tweaks.TweakValue.fromJson(((VariantTweak)object32).tweak)) != null) {
                        ViewCrawler.this.mTweaks.declareTweak((String)((MPPair)object22).first, ((Tweaks.TweakValue)object32).getDefaultValue(), ((Tweaks.TweakValue)object32).getMinimum(), ((Tweaks.TweakValue)object32).getMaximum(), ((Tweaks.TweakValue)object32).type);
                    }
                    ViewCrawler.this.mTweaks.set((String)((MPPair)object22).first, ((MPPair)object22).second);
                }
                catch (EditProtocol.BadInstructionsException badInstructionsException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad editor tweak cannot be applied.", badInstructionsException);
                }
            }
            if (this.mAppliedTweaks.size() == 0) {
                for (Object object32 : ViewCrawler.this.mTweaks.getDefaultValues().entrySet()) {
                    object22 = (Tweaks.TweakValue)object32.getValue();
                    object32 = (String)object32.getKey();
                    if (!ViewCrawler.this.mTweaks.isNewValue((String)object32, ((Tweaks.TweakValue)object22).getValue())) continue;
                    ViewCrawler.this.mTweaks.set((String)object32, ((Tweaks.TweakValue)object22).getValue());
                    hashSet2.add((String)object32);
                }
            }
            for (Object object22 : this.mEditorChanges.values()) {
                try {
                    object32 = this.mProtocol.readEdit((JSONObject)((MPPair)object22).second);
                    arrayList.add(new MPPair<Object, ViewVisitor>(((MPPair)object22).first, ((EditProtocol.Edit)object32).visitor));
                    this.mEditorAssetUrls.addAll(((EditProtocol.Edit)object32).imageUrls);
                }
                catch (EditProtocol.CantGetEditAssetsException cantGetEditAssetsException) {
                    MPLog.v("MixpanelAPI.ViewCrawler", "Can't load assets for an edit, won't apply the change now", cantGetEditAssetsException);
                }
                catch (EditProtocol.InapplicableInstructionsException inapplicableInstructionsException) {
                    MPLog.i("MixpanelAPI.ViewCrawler", inapplicableInstructionsException.getMessage());
                }
                catch (EditProtocol.BadInstructionsException badInstructionsException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad editor change request cannot be applied.", badInstructionsException);
                }
            }
            int n2 = this.mEditorTweaks.size();
            for (n = 0; n < n2; ++n) {
                object = this.mEditorTweaks.get(n);
                try {
                    object = this.mProtocol.readTweak((JSONObject)object);
                    if (ViewCrawler.this.mTweaks.isNewValue((String)((MPPair)object).first, ((MPPair)object).second)) {
                        hashSet2.add((String)((MPPair)object).first);
                    }
                    ViewCrawler.this.mTweaks.set((String)((MPPair)object).first, ((MPPair)object).second);
                    continue;
                }
                catch (EditProtocol.BadInstructionsException badInstructionsException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Strange tweaks received", badInstructionsException);
                }
            }
            if (this.mEditorEventBindings.size() == 0 && this.mOriginalEventBindings.size() == 0) {
                for (Object object22 : this.mPersistentEventBindings) {
                    try {
                        object32 = this.mProtocol.readEventBinding((JSONObject)((MPPair)object22).second, ViewCrawler.this.mDynamicEventTracker);
                        arrayList.add(new MPPair<Object, Object>(((MPPair)object22).first, object32));
                    }
                    catch (EditProtocol.InapplicableInstructionsException inapplicableInstructionsException) {
                        MPLog.i("MixpanelAPI.ViewCrawler", inapplicableInstructionsException.getMessage());
                    }
                    catch (EditProtocol.BadInstructionsException badInstructionsException) {
                        MPLog.e("MixpanelAPI.ViewCrawler", "Bad persistent event binding cannot be applied.", badInstructionsException);
                    }
                }
            }
            for (Object object22 : this.mEditorEventBindings.values()) {
                try {
                    object32 = this.mProtocol.readEventBinding((JSONObject)((MPPair)object22).second, ViewCrawler.this.mDynamicEventTracker);
                    arrayList.add(new MPPair<Object, Object>(((MPPair)object22).first, object32));
                }
                catch (EditProtocol.InapplicableInstructionsException inapplicableInstructionsException) {
                    MPLog.i("MixpanelAPI.ViewCrawler", inapplicableInstructionsException.getMessage());
                }
                catch (EditProtocol.BadInstructionsException badInstructionsException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad editor event binding cannot be applied.", badInstructionsException);
                }
            }
            object22 = new HashMap();
            n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                object32 = (MPPair)((Object)arrayList.get(n));
                if (object22.containsKey(((MPPair)object32).first)) {
                    object = (List)object22.get(((MPPair)object32).first);
                } else {
                    object = new ArrayList();
                    object22.put(((MPPair)object32).first, object);
                }
                object.add(((MPPair)object32).second);
            }
            ViewCrawler.this.mEditState.setEdits((Map<String, List<ViewVisitor>>)object22);
            this.mSeenExperiments.addAll(hashSet);
            hashSet.addAll(this.mEmptyExperiments);
            this.trackSeenExperiments(hashSet);
            this.mEmptyExperiments.clear();
            if (hashSet2.size() > 0) {
                object = ViewCrawler.this.mTweaksUpdatedListeners.iterator();
                while (object.hasNext()) {
                    ((OnMixpanelTweaksUpdatedListener)object.next()).onMixpanelTweakUpdated(hashSet2);
                }
            }
        }

        private void connectToEditor() {
            MPLog.v("MixpanelAPI.ViewCrawler", "connecting to editor");
            if (this.mEditorConnection != null && this.mEditorConnection.isValid()) {
                MPLog.v("MixpanelAPI.ViewCrawler", "There is already a valid connection to an events editor.");
                return;
            }
            Object object = ViewCrawler.this.mConfig.getSSLSocketFactory();
            if (object == null) {
                MPLog.v("MixpanelAPI.ViewCrawler", "SSL is not available on this device, no connection will be attempted to the events editor.");
                return;
            }
            String string2 = MPConfig.getInstance(ViewCrawler.this.mContext).getEditorUrl() + this.mToken;
            try {
                object = ((SocketFactory)object).createSocket();
                this.mEditorConnection = new EditorConnection(new URI(string2), new Editor(), (Socket)object);
                return;
            }
            catch (URISyntaxException uRISyntaxException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Error parsing URI " + string2 + " for editor websocket", uRISyntaxException);
                return;
            }
            catch (EditorConnection.EditorConnectionException editorConnectionException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Error connecting to URI " + string2, editorConnectionException);
                return;
            }
            catch (IOException iOException) {
                MPLog.i("MixpanelAPI.ViewCrawler", "Can't create SSL Socket to connect to editor service", iOException);
                return;
            }
        }

        private SharedPreferences getSharedPreferences() {
            String string2 = "mixpanel.viewcrawler.changes" + this.mToken;
            return ViewCrawler.this.mContext.getSharedPreferences(string2, 0);
        }

        private void handleEditorBindingsCleared(JSONObject jSONObject) {
            int n;
            try {
                jSONObject = jSONObject.getJSONObject("payload").getJSONArray("actions");
                n = 0;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Bad clear request received", jSONException);
            }
            do {
                if (n < jSONObject.length()) {
                    String string2 = jSONObject.getString(n);
                    this.mEditorChanges.remove(string2);
                    ++n;
                    continue;
                }
                break;
            } while (true);
            this.applyVariantsAndEventBindings();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void handleEditorBindingsReceived(JSONObject jSONObject) {
            Object object;
            int n;
            block9: {
                try {
                    jSONObject = jSONObject.getJSONObject("payload").getJSONArray("events");
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad event bindings received", jSONException);
                    return;
                }
                n = jSONObject.length();
                this.mEditorEventBindings.clear();
                if (this.mPersistentEventBindings.isEmpty() || !this.mOriginalEventBindings.isEmpty()) break block9;
                this.mOriginalEventBindings.addAll(this.mPersistentEventBindings);
                object = this.mPersistentEventBindings.iterator();
                while (object.hasNext()) {
                    MPPair mPPair = (MPPair)((Object)object.next());
                    try {
                        this.mEditorEventBindings.put(((JSONObject)mPPair.second).get("path").toString(), mPPair);
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }
                this.mPersistentEventBindings.clear();
            }
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.applyVariantsAndEventBindings();
                    return;
                }
                try {
                    object = jSONObject.getJSONObject(n2);
                    String string2 = JSONUtils.optionalStringKey((JSONObject)object, "target_activity");
                    this.mEditorEventBindings.put(object.get("path").toString(), new MPPair<String, Object>(string2, object));
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad event binding received from editor in " + jSONObject.toString(), jSONException);
                }
                ++n2;
            } while (true);
        }

        private void handleEditorChangeReceived(JSONObject jSONObject) {
            jSONObject = jSONObject.getJSONObject("payload").getJSONArray("actions");
            int n = 0;
            do {
                if (n >= jSONObject.length()) break;
                JSONObject jSONObject2 = jSONObject.getJSONObject(n);
                String string2 = JSONUtils.optionalStringKey(jSONObject2, "target_activity");
                String string3 = jSONObject2.getString("name");
                this.mEditorChanges.put(string3, new MPPair<String, JSONObject>(string2, jSONObject2));
                ++n;
                continue;
                break;
            } while (true);
            try {
                this.applyVariantsAndEventBindings();
                return;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Bad change request received", jSONException);
                return;
            }
        }

        private void handleEditorClosed() {
            this.mEditorChanges.clear();
            this.mEditorEventBindings.clear();
            this.mEditorTweaks.clear();
            this.mPersistentEventBindings.addAll(this.mOriginalEventBindings);
            this.mOriginalEventBindings.clear();
            this.mSnapshot = null;
            MPLog.v("MixpanelAPI.ViewCrawler", "Editor closed- freeing snapshot");
            this.applyVariantsAndEventBindings();
            for (String string2 : this.mEditorAssetUrls) {
                this.mImageStore.deleteStorage(string2);
            }
        }

        private void handleEditorTweaksReceived(JSONObject jSONObject) {
            this.mEditorTweaks.clear();
            jSONObject = jSONObject.getJSONObject("payload").getJSONArray("tweaks");
            int n = jSONObject.length();
            for (int i = 0; i < n; ++i) {
                try {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(i);
                    this.mEditorTweaks.add(jSONObject2);
                    continue;
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Bad tweaks received", jSONException);
                    break;
                }
            }
            this.applyVariantsAndEventBindings();
        }

        private void handleEventBindingsReceived(JSONArray jSONArray) {
            SharedPreferences.Editor editor = this.getSharedPreferences().edit();
            editor.putString("mixpanel.viewcrawler.bindings", jSONArray.toString());
            editor.apply();
            this.loadEventBindings(jSONArray.toString());
            this.applyVariantsAndEventBindings();
        }

        private void handleVariantsReceived(JSONArray jSONArray) {
            this.persistVariants(jSONArray);
            this.loadVariants(jSONArray.toString(), true);
            this.applyVariantsAndEventBindings();
        }

        private void loadEventBindings(String string2) {
            if (string2 != null) {
                int n;
                try {
                    string2 = new JSONArray(string2);
                    this.mPersistentEventBindings.clear();
                    n = 0;
                }
                catch (JSONException jSONException) {
                    MPLog.i("MixpanelAPI.ViewCrawler", "JSON error when loading event bindings, clearing persistent memory", jSONException);
                    SharedPreferences.Editor editor = this.getSharedPreferences().edit();
                    editor.remove("mixpanel.viewcrawler.bindings");
                    editor.apply();
                }
                do {
                    if (n < string2.length()) {
                        JSONObject jSONObject = string2.getJSONObject(n);
                        String string3 = JSONUtils.optionalStringKey(jSONObject, "target_activity");
                        this.mPersistentEventBindings.add(new MPPair<String, JSONObject>(string3, jSONObject));
                        ++n;
                        continue;
                    }
                    break;
                } while (true);
            }
        }

        private void loadKnownChanges() {
            Object object = this.getSharedPreferences();
            String string2 = object.getString("mixpanel.viewcrawler.changes", null);
            object = object.getString("mixpanel.viewcrawler.bindings", null);
            this.mAppliedVisualChanges.clear();
            this.mAppliedTweaks.clear();
            this.mSeenExperiments.clear();
            this.loadVariants(string2, false);
            this.mPersistentEventBindings.clear();
            this.loadEventBindings((String)object);
            this.applyVariantsAndEventBindings();
        }

        private void loadVariants(String string2, boolean bl) {
            if (string2 != null) {
                string2 = new JSONArray(string2);
                int n = string2.length();
                for (int i = 0; i < n; ++i) {
                    JSONObject jSONObject = string2.getJSONObject(i);
                    int n2 = jSONObject.getInt("id");
                    MPPair<Integer, Integer> mPPair = new MPPair<Integer, Integer>(jSONObject.getInt("experiment_id"), n2);
                    Object object = jSONObject.getJSONArray("actions");
                    int n3 = object.length();
                    for (n2 = 0; n2 < n3; ++n2) {
                        Object object2 = object.getJSONObject(n2);
                        String string3 = JSONUtils.optionalStringKey(object2, "target_activity");
                        object2 = new VariantChange(object2.getString("name"), string3, (JSONObject)object2, mPPair);
                        this.mAppliedVisualChanges.add((VariantChange)object2);
                        continue;
                    }
                    jSONObject = jSONObject.getJSONArray("tweaks");
                    int n4 = jSONObject.length();
                    for (n2 = 0; n2 < n4; ++n2) {
                        object = jSONObject.getJSONObject(n2);
                        object = new VariantTweak(object.getString("name"), (JSONObject)object, mPPair);
                        this.mAppliedTweaks.add((VariantTweak)object);
                        continue;
                    }
                    if (!bl) {
                        this.mSeenExperiments.add(mPPair);
                    }
                    if (n4 != 0 || n3 != 0) continue;
                    try {
                        this.mEmptyExperiments.add(mPPair);
                    }
                    catch (JSONException jSONException) {
                        MPLog.i("MixpanelAPI.ViewCrawler", "JSON error when loading ab tests / tweaks, clearing persistent memory", jSONException);
                        SharedPreferences.Editor editor = this.getSharedPreferences().edit();
                        editor.remove("mixpanel.viewcrawler.changes");
                        editor.apply();
                        break;
                    }
                    continue;
                }
            }
        }

        private void persistVariants(JSONArray jSONArray) {
            SharedPreferences.Editor editor = this.getSharedPreferences().edit();
            editor.putString("mixpanel.viewcrawler.changes", jSONArray.toString());
            editor.apply();
        }

        /*
         * Exception decompiling
         */
        private void sendDeviceInfo() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void sendError(String object) {
            if (this.mEditorConnection == null) return;
            if (!this.mEditorConnection.isValid()) return;
            if (!this.mEditorConnection.isConnected()) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("error_message", object);
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Apparently impossible JSONException", jSONException);
            }
            object = new OutputStreamWriter(this.mEditorConnection.getBufferedOutputStream());
            ((Writer)object).write("{\"type\": \"error\", ");
            ((Writer)object).write("\"payload\": ");
            ((Writer)object).write(jSONObject.toString());
            ((Writer)object).write("}");
            try {
                ((OutputStreamWriter)object).close();
                return;
            }
            catch (IOException iOException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Could not close output writer to editor", iOException);
                return;
            }
            catch (IOException iOException) {
                try {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't write error message to editor", iOException);
                }
                catch (Throwable throwable) {
                    try {
                        ((OutputStreamWriter)object).close();
                    }
                    catch (IOException iOException2) {
                        MPLog.e("MixpanelAPI.ViewCrawler", "Could not close output writer to editor", iOException2);
                        throw throwable;
                    }
                    throw throwable;
                }
                try {
                    ((OutputStreamWriter)object).close();
                    return;
                }
                catch (IOException iOException3) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Could not close output writer to editor", iOException3);
                    return;
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void sendLayoutError(ViewVisitor.LayoutErrorMessage layoutErrorMessage) {
            if (this.mEditorConnection == null) return;
            if (!this.mEditorConnection.isValid()) return;
            if (!this.mEditorConnection.isConnected()) {
                return;
            }
            JsonWriter jsonWriter = new JsonWriter((Writer)new OutputStreamWriter(this.mEditorConnection.getBufferedOutputStream()));
            jsonWriter.beginObject();
            jsonWriter.name("type").value("layout_error");
            jsonWriter.name("exception_type").value(layoutErrorMessage.getErrorType());
            jsonWriter.name("cid").value(layoutErrorMessage.getName());
            jsonWriter.endObject();
            try {
                jsonWriter.close();
                return;
            }
            catch (IOException iOException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException);
                return;
            }
            catch (IOException iOException) {
                try {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't write track_message to server", iOException);
                }
                catch (Throwable throwable) {
                    try {
                        jsonWriter.close();
                    }
                    catch (IOException iOException2) {
                        MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException2);
                        throw throwable;
                    }
                    throw throwable;
                }
                try {
                    jsonWriter.close();
                    return;
                }
                catch (IOException iOException3) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException3);
                    return;
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void sendReportTrackToEditor(String string2) {
            if (this.mEditorConnection == null) return;
            if (!this.mEditorConnection.isValid()) return;
            if (!this.mEditorConnection.isConnected()) {
                return;
            }
            JsonWriter jsonWriter = new JsonWriter((Writer)new OutputStreamWriter(this.mEditorConnection.getBufferedOutputStream()));
            jsonWriter.beginObject();
            jsonWriter.name("type").value("track_message");
            jsonWriter.name("payload");
            jsonWriter.beginObject();
            jsonWriter.name("event_name").value(string2);
            jsonWriter.endObject();
            jsonWriter.endObject();
            jsonWriter.flush();
            try {
                jsonWriter.close();
                return;
            }
            catch (IOException iOException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException);
                return;
            }
            catch (IOException iOException) {
                try {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't write track_message to server", iOException);
                }
                catch (Throwable throwable) {
                    try {
                        jsonWriter.close();
                    }
                    catch (IOException iOException2) {
                        MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException2);
                        throw throwable;
                    }
                    throw throwable;
                }
                try {
                    jsonWriter.close();
                    return;
                }
                catch (IOException iOException3) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException3);
                    return;
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void sendSnapshot(JSONObject object) {
            long l;
            block14: {
                l = System.currentTimeMillis();
                try {
                    object = object.getJSONObject("payload");
                    if (object.has("config")) {
                        this.mSnapshot = this.mProtocol.readSnapshotConfig((JSONObject)object);
                        MPLog.v("MixpanelAPI.ViewCrawler", "Initializing snapshot with configuration");
                    }
                    if (this.mSnapshot != null) break block14;
                    this.sendError("No snapshot configuration (or a malformed snapshot configuration) was sent.");
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Payload with snapshot config required with snapshot request", jSONException);
                    this.sendError("Payload with snapshot config required with snapshot request");
                    return;
                }
                catch (EditProtocol.BadInstructionsException badInstructionsException) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Editor sent malformed message with snapshot request", badInstructionsException);
                    this.sendError(badInstructionsException.getMessage());
                    return;
                }
                MPLog.w("MixpanelAPI.ViewCrawler", "Mixpanel editor is misconfigured, sent a snapshot request without a valid configuration.");
                return;
            }
            BufferedOutputStream bufferedOutputStream = this.mEditorConnection.getBufferedOutputStream();
            object = new OutputStreamWriter(bufferedOutputStream);
            ((Writer)object).write("{");
            ((Writer)object).write("\"type\": \"snapshot_response\",");
            ((Writer)object).write("\"payload\": {");
            ((Writer)object).write("\"activities\":");
            ((OutputStreamWriter)object).flush();
            this.mSnapshot.snapshots(ViewCrawler.this.mEditState, bufferedOutputStream);
            long l2 = System.currentTimeMillis();
            ((Writer)object).write(",\"snapshot_time_millis\": ");
            ((Writer)object).write(Long.toString(l2 - l));
            ((Writer)object).write("}");
            ((Writer)object).write("}");
            try {
                ((OutputStreamWriter)object).close();
                return;
            }
            catch (IOException iOException) {
                MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException);
                return;
            }
            catch (IOException iOException) {
                try {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't write snapshot request to server", iOException);
                }
                catch (Throwable throwable) {
                    try {
                        ((OutputStreamWriter)object).close();
                    }
                    catch (IOException iOException2) {
                        MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException2);
                        throw throwable;
                    }
                    throw throwable;
                }
                try {
                    ((OutputStreamWriter)object).close();
                    return;
                }
                catch (IOException iOException3) {
                    MPLog.e("MixpanelAPI.ViewCrawler", "Can't close writer.", iOException3);
                    return;
                }
            }
        }

        private void trackSeenExperiments(Set<MPPair<Integer, Integer>> object) {
            if (object != null && object.size() > 0) {
                final JSONObject jSONObject = new JSONObject();
                try {
                    object = object.iterator();
                    while (object.hasNext()) {
                        MPPair mPPair = (MPPair)((Object)object.next());
                        int n = (Integer)mPPair.first;
                        int n2 = (Integer)mPPair.second;
                        mPPair = new JSONObject();
                        mPPair.put("$experiment_id", n);
                        mPPair.put("$variant_id", n2);
                        jSONObject.put(Integer.toString(n), n2);
                        ViewCrawler.this.mMixpanel.getPeople().merge("$experiments", jSONObject);
                        ViewCrawler.this.mMixpanel.updateSuperProperties(new SuperPropertyUpdate(){

                            @Override
                            public JSONObject update(JSONObject jSONObject2) {
                                try {
                                    jSONObject2.put("$experiments", (Object)jSONObject);
                                    return jSONObject2;
                                }
                                catch (JSONException jSONException) {
                                    MPLog.wtf("MixpanelAPI.ViewCrawler", "Can't write $experiments super property", jSONException);
                                    return jSONObject2;
                                }
                            }
                        });
                        ViewCrawler.this.mMixpanel.track("$experiment_started", (JSONObject)mPPair);
                    }
                }
                catch (JSONException jSONException) {
                    MPLog.wtf("MixpanelAPI.ViewCrawler", "Could not build JSON for reporting experiment start", jSONException);
                }
            }
        }

        /*
         * Exception decompiling
         */
        public void handleMessage(Message var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        public void start() {
            this.mStartLock.unlock();
        }

    }

}

