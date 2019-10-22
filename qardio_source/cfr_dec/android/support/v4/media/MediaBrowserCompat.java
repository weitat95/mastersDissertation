/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaBrowserCompatApi24;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MediaBrowserCompat {
    static final boolean DEBUG = Log.isLoggable((String)"MediaBrowserCompat", (int)3);
    private final MediaBrowserImpl mImpl;

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mImpl = new MediaBrowserImplApi24(context, componentName, connectionCallback, bundle);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserImplApi23(context, componentName, connectionCallback, bundle);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserImplApi21(context, componentName, connectionCallback, bundle);
            return;
        }
        this.mImpl = new MediaBrowserImplBase(context, componentName, connectionCallback, bundle);
    }

    public void connect() {
        this.mImpl.connect();
    }

    public void disconnect() {
        this.mImpl.disconnect();
    }

    public MediaSessionCompat.Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    private static class CallbackHandler
    extends Handler {
        private final WeakReference<MediaBrowserServiceCallbackImpl> mCallbackImplRef;
        private WeakReference<Messenger> mCallbacksMessengerRef;

        CallbackHandler(MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl) {
            this.mCallbackImplRef = new WeakReference<MediaBrowserServiceCallbackImpl>(mediaBrowserServiceCallbackImpl);
        }

        /*
         * Exception decompiling
         */
        public void handleMessage(Message var1_1) {
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

        void setCallbacksMessenger(Messenger messenger) {
            this.mCallbacksMessengerRef = new WeakReference<Messenger>(messenger);
        }
    }

    public static class ConnectionCallback {
        ConnectionCallbackInternal mConnectionCallbackInternal;
        final Object mConnectionCallbackObj;

        public ConnectionCallback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21());
                return;
            }
            this.mConnectionCallbackObj = null;
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }

        void setInternalConnectionCallback(ConnectionCallbackInternal connectionCallbackInternal) {
            this.mConnectionCallbackInternal = connectionCallbackInternal;
        }

        static interface ConnectionCallbackInternal {
            public void onConnected();

            public void onConnectionFailed();

            public void onConnectionSuspended();
        }

        private class StubApi21
        implements MediaBrowserCompatApi21.ConnectionCallback {
            StubApi21() {
            }

            @Override
            public void onConnected() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnected();
                }
                ConnectionCallback.this.onConnected();
            }

            @Override
            public void onConnectionFailed() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed();
                }
                ConnectionCallback.this.onConnectionFailed();
            }

            @Override
            public void onConnectionSuspended() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended();
                }
                ConnectionCallback.this.onConnectionSuspended();
            }
        }

    }

    public static abstract class CustomActionCallback {
        public void onError(String string2, Bundle bundle, Bundle bundle2) {
        }

        public void onProgressUpdate(String string2, Bundle bundle, Bundle bundle2) {
        }

        public void onResult(String string2, Bundle bundle, Bundle bundle2) {
        }
    }

    private static class CustomActionResultReceiver
    extends ResultReceiver {
        private final String mAction;
        private final CustomActionCallback mCallback;
        private final Bundle mExtras;

        @Override
        protected void onReceiveResult(int n, Bundle bundle) {
            if (this.mCallback == null) {
                return;
            }
            switch (n) {
                default: {
                    Log.w((String)"MediaBrowserCompat", (String)("Unknown result code: " + n + " (extras=" + (Object)this.mExtras + ", resultData=" + (Object)bundle + ")"));
                    return;
                }
                case 1: {
                    this.mCallback.onProgressUpdate(this.mAction, this.mExtras, bundle);
                    return;
                }
                case 0: {
                    this.mCallback.onResult(this.mAction, this.mExtras, bundle);
                    return;
                }
                case -1: 
            }
            this.mCallback.onError(this.mAction, this.mExtras, bundle);
        }
    }

    public static abstract class ItemCallback {
        public void onError(String string2) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }
    }

    private static class ItemReceiver
    extends ResultReceiver {
        private final ItemCallback mCallback;
        private final String mMediaId;

        @Override
        protected void onReceiveResult(int n, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (n != 0 || bundle == null || !bundle.containsKey("media_item")) {
                this.mCallback.onError(this.mMediaId);
                return;
            }
            if ((bundle = bundle.getParcelable("media_item")) == null || bundle instanceof MediaItem) {
                this.mCallback.onItemLoaded((MediaItem)bundle);
                return;
            }
            this.mCallback.onError(this.mMediaId);
        }
    }

    static interface MediaBrowserImpl {
        public void connect();

        public void disconnect();

        public MediaSessionCompat.Token getSessionToken();
    }

    static class MediaBrowserImplApi21
    implements ConnectionCallback.ConnectionCallbackInternal,
    MediaBrowserImpl,
    MediaBrowserServiceCallbackImpl {
        protected final Object mBrowserObj;
        protected Messenger mCallbacksMessenger;
        final Context mContext;
        protected final CallbackHandler mHandler = new CallbackHandler(this);
        private MediaSessionCompat.Token mMediaSessionToken;
        protected final Bundle mRootHints;
        protected ServiceBinderWrapper mServiceBinderWrapper;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

        public MediaBrowserImplApi21(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.mContext = context;
            Bundle bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putInt("extra_client_version", 1);
            this.mRootHints = new Bundle(bundle2);
            connectionCallback.setInternalConnectionCallback(this);
            this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(context, componentName, connectionCallback.mConnectionCallbackObj, this.mRootHints);
        }

        @Override
        public void connect() {
            MediaBrowserCompatApi21.connect(this.mBrowserObj);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void disconnect() {
            if (this.mServiceBinderWrapper != null && this.mCallbacksMessenger != null) {
                try {
                    this.mServiceBinderWrapper.unregisterCallbackMessenger(this.mCallbacksMessenger);
                }
                catch (RemoteException remoteException) {
                    Log.i((String)"MediaBrowserCompat", (String)"Remote error unregistering client messenger.");
                }
            }
            MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
        }

        @Override
        public MediaSessionCompat.Token getSessionToken() {
            if (this.mMediaSessionToken == null) {
                this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
            }
            return this.mMediaSessionToken;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onConnected() {
            IMediaSession iMediaSession;
            block7: {
                block6: {
                    Bundle bundle = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
                    if (bundle == null) break block6;
                    IBinder iBinder = BundleCompat.getBinder(bundle, "extra_messenger");
                    if (iBinder != null) {
                        this.mServiceBinderWrapper = new ServiceBinderWrapper(iBinder, this.mRootHints);
                        this.mCallbacksMessenger = new Messenger((Handler)this.mHandler);
                        this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger);
                        try {
                            this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger);
                        }
                        catch (RemoteException remoteException) {
                            Log.i((String)"MediaBrowserCompat", (String)"Remote error registering client messenger.");
                        }
                    }
                    if ((iMediaSession = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "extra_session_binder"))) != null) break block7;
                }
                return;
            }
            this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj), iMediaSession);
        }

        @Override
        public void onConnectionFailed() {
        }

        @Override
        public void onConnectionFailed(Messenger messenger) {
        }

        @Override
        public void onConnectionSuspended() {
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mMediaSessionToken = null;
            this.mHandler.setCallbacksMessenger(null);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onLoadChildren(Messenger object, String string2, List list, Bundle bundle) {
            if (this.mCallbacksMessenger != object) return;
            object = (Subscription)this.mSubscriptions.get(string2);
            if (object == null) {
                if (!DEBUG) return;
                {
                    Log.d((String)"MediaBrowserCompat", (String)("onLoadChildren for id that isn't subscribed id=" + string2));
                    return;
                }
            }
            if ((object = ((Subscription)object).getCallback(this.mContext, bundle)) == null) {
                return;
            }
            if (bundle == null) {
                if (list == null) {
                    ((SubscriptionCallback)object).onError(string2);
                    return;
                }
                ((SubscriptionCallback)object).onChildrenLoaded(string2, list);
                return;
            }
            if (list == null) {
                ((SubscriptionCallback)object).onError(string2, bundle);
                return;
            }
            ((SubscriptionCallback)object).onChildrenLoaded(string2, list, bundle);
        }

        @Override
        public void onServiceConnected(Messenger messenger, String string2, MediaSessionCompat.Token token, Bundle bundle) {
        }
    }

    static class MediaBrowserImplApi23
    extends MediaBrowserImplApi21 {
        public MediaBrowserImplApi23(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }
    }

    static class MediaBrowserImplApi24
    extends MediaBrowserImplApi23 {
        public MediaBrowserImplApi24(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }
    }

    static class MediaBrowserImplBase
    implements MediaBrowserImpl,
    MediaBrowserServiceCallbackImpl {
        final ConnectionCallback mCallback;
        Messenger mCallbacksMessenger;
        final Context mContext;
        private Bundle mExtras;
        final CallbackHandler mHandler = new CallbackHandler(this);
        private MediaSessionCompat.Token mMediaSessionToken;
        final Bundle mRootHints;
        private String mRootId;
        ServiceBinderWrapper mServiceBinderWrapper;
        final ComponentName mServiceComponent;
        MediaServiceConnection mServiceConnection;
        int mState = 1;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

        /*
         * Enabled aggressive block sorting
         */
        public MediaBrowserImplBase(Context object, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            if (object == null) {
                throw new IllegalArgumentException("context must not be null");
            }
            if (componentName == null) {
                throw new IllegalArgumentException("service component must not be null");
            }
            if (connectionCallback == null) {
                throw new IllegalArgumentException("connection callback must not be null");
            }
            this.mContext = object;
            this.mServiceComponent = componentName;
            this.mCallback = connectionCallback;
            object = bundle == null ? null : new Bundle(bundle);
            this.mRootHints = object;
        }

        private static String getStateLabel(int n) {
            switch (n) {
                default: {
                    return "UNKNOWN/" + n;
                }
                case 0: {
                    return "CONNECT_STATE_DISCONNECTING";
                }
                case 1: {
                    return "CONNECT_STATE_DISCONNECTED";
                }
                case 2: {
                    return "CONNECT_STATE_CONNECTING";
                }
                case 3: {
                    return "CONNECT_STATE_CONNECTED";
                }
                case 4: 
            }
            return "CONNECT_STATE_SUSPENDED";
        }

        private boolean isCurrent(Messenger messenger, String string2) {
            boolean bl = true;
            if (this.mCallbacksMessenger != messenger || this.mState == 0 || this.mState == 1) {
                if (this.mState != 0 && this.mState != 1) {
                    Log.i((String)"MediaBrowserCompat", (String)(string2 + " for " + (Object)this.mServiceComponent + " with mCallbacksMessenger=" + (Object)this.mCallbacksMessenger + " this=" + this));
                }
                bl = false;
            }
            return bl;
        }

        @Override
        public void connect() {
            if (this.mState != 0 && this.mState != 1) {
                throw new IllegalStateException("connect() called while neigther disconnecting nor disconnected (state=" + MediaBrowserImplBase.getStateLabel(this.mState) + ")");
            }
            this.mState = 2;
            this.mHandler.post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    block10: {
                        block9: {
                            if (MediaBrowserImplBase.this.mState == 0) break block9;
                            MediaBrowserImplBase.this.mState = 2;
                            if (DEBUG && MediaBrowserImplBase.this.mServiceConnection != null) {
                                throw new RuntimeException("mServiceConnection should be null. Instead it is " + MediaBrowserImplBase.this.mServiceConnection);
                            }
                            if (MediaBrowserImplBase.this.mServiceBinderWrapper != null) {
                                throw new RuntimeException("mServiceBinderWrapper should be null. Instead it is " + MediaBrowserImplBase.this.mServiceBinderWrapper);
                            }
                            if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                                throw new RuntimeException("mCallbacksMessenger should be null. Instead it is " + (Object)MediaBrowserImplBase.this.mCallbacksMessenger);
                            }
                            Intent intent = new Intent("android.media.browse.MediaBrowserService");
                            intent.setComponent(MediaBrowserImplBase.this.mServiceComponent);
                            MediaBrowserImplBase.this.mServiceConnection = new MediaServiceConnection();
                            boolean bl = false;
                            try {
                                boolean bl2;
                                bl = bl2 = MediaBrowserImplBase.this.mContext.bindService(intent, (ServiceConnection)MediaBrowserImplBase.this.mServiceConnection, 1);
                            }
                            catch (Exception exception) {
                                Log.e((String)"MediaBrowserCompat", (String)("Failed binding to service " + (Object)MediaBrowserImplBase.this.mServiceComponent));
                            }
                            if (!bl) {
                                MediaBrowserImplBase.this.forceCloseConnection();
                                MediaBrowserImplBase.this.mCallback.onConnectionFailed();
                            }
                            if (DEBUG) break block10;
                        }
                        return;
                    }
                    Log.d((String)"MediaBrowserCompat", (String)"connect...");
                    MediaBrowserImplBase.this.dump();
                }
            });
        }

        @Override
        public void disconnect() {
            this.mState = 0;
            this.mHandler.post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        try {
                            MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserImplBase.this.mCallbacksMessenger);
                        }
                        catch (RemoteException remoteException) {
                            Log.w((String)"MediaBrowserCompat", (String)("RemoteException during connect for " + (Object)MediaBrowserImplBase.this.mServiceComponent));
                        }
                    }
                    int n = MediaBrowserImplBase.this.mState;
                    MediaBrowserImplBase.this.forceCloseConnection();
                    if (n != 0) {
                        MediaBrowserImplBase.this.mState = n;
                    }
                    if (DEBUG) {
                        Log.d((String)"MediaBrowserCompat", (String)"disconnect...");
                        MediaBrowserImplBase.this.dump();
                    }
                }
            });
        }

        void dump() {
            Log.d((String)"MediaBrowserCompat", (String)"MediaBrowserCompat...");
            Log.d((String)"MediaBrowserCompat", (String)("  mServiceComponent=" + (Object)this.mServiceComponent));
            Log.d((String)"MediaBrowserCompat", (String)("  mCallback=" + this.mCallback));
            Log.d((String)"MediaBrowserCompat", (String)("  mRootHints=" + (Object)this.mRootHints));
            Log.d((String)"MediaBrowserCompat", (String)("  mState=" + MediaBrowserImplBase.getStateLabel(this.mState)));
            Log.d((String)"MediaBrowserCompat", (String)("  mServiceConnection=" + this.mServiceConnection));
            Log.d((String)"MediaBrowserCompat", (String)("  mServiceBinderWrapper=" + this.mServiceBinderWrapper));
            Log.d((String)"MediaBrowserCompat", (String)("  mCallbacksMessenger=" + (Object)this.mCallbacksMessenger));
            Log.d((String)"MediaBrowserCompat", (String)("  mRootId=" + this.mRootId));
            Log.d((String)"MediaBrowserCompat", (String)("  mMediaSessionToken=" + this.mMediaSessionToken));
        }

        void forceCloseConnection() {
            if (this.mServiceConnection != null) {
                this.mContext.unbindService((ServiceConnection)this.mServiceConnection);
            }
            this.mState = 1;
            this.mServiceConnection = null;
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
            this.mRootId = null;
            this.mMediaSessionToken = null;
        }

        @Override
        public MediaSessionCompat.Token getSessionToken() {
            if (!this.isConnected()) {
                throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.mState + ")");
            }
            return this.mMediaSessionToken;
        }

        public boolean isConnected() {
            return this.mState == 3;
        }

        @Override
        public void onConnectionFailed(Messenger messenger) {
            Log.e((String)"MediaBrowserCompat", (String)("onConnectFailed for " + (Object)this.mServiceComponent));
            if (!this.isCurrent(messenger, "onConnectFailed")) {
                return;
            }
            if (this.mState != 2) {
                Log.w((String)"MediaBrowserCompat", (String)("onConnect from service while mState=" + MediaBrowserImplBase.getStateLabel(this.mState) + "... ignoring"));
                return;
            }
            this.forceCloseConnection();
            this.mCallback.onConnectionFailed();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onLoadChildren(Messenger object, String string2, List list, Bundle bundle) {
            if (!this.isCurrent((Messenger)object, "onLoadChildren")) return;
            if (DEBUG) {
                Log.d((String)"MediaBrowserCompat", (String)("onLoadChildren for " + (Object)this.mServiceComponent + " id=" + string2));
            }
            if ((object = (Subscription)this.mSubscriptions.get(string2)) == null) {
                if (!DEBUG) return;
                {
                    Log.d((String)"MediaBrowserCompat", (String)("onLoadChildren for id that isn't subscribed id=" + string2));
                    return;
                }
            }
            if ((object = ((Subscription)object).getCallback(this.mContext, bundle)) == null) {
                return;
            }
            if (bundle == null) {
                if (list == null) {
                    ((SubscriptionCallback)object).onError(string2);
                    return;
                }
                ((SubscriptionCallback)object).onChildrenLoaded(string2, list);
                return;
            }
            if (list == null) {
                ((SubscriptionCallback)object).onError(string2, bundle);
                return;
            }
            ((SubscriptionCallback)object).onChildrenLoaded(string2, list, bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceConnected(Messenger object, String string2, MediaSessionCompat.Token list2, Bundle object2) {
            if (!this.isCurrent((Messenger)object, "onConnect")) return;
            {
                if (this.mState != 2) {
                    Log.w((String)"MediaBrowserCompat", (String)("onConnect from service while mState=" + MediaBrowserImplBase.getStateLabel(this.mState) + "... ignoring"));
                    return;
                }
                this.mRootId = string2;
                this.mMediaSessionToken = list2;
                this.mExtras = object2;
                this.mState = 3;
                if (DEBUG) {
                    Log.d((String)"MediaBrowserCompat", (String)"ServiceCallbacks.onConnect...");
                    this.dump();
                }
                this.mCallback.onConnected();
                try {
                    for (List<SubscriptionCallback> list2 : this.mSubscriptions.entrySet()) {
                        string2 = (String)list2.getKey();
                        object2 = (Subscription)list2.getValue();
                        list2 = ((Subscription)object2).getCallbacks();
                        object2 = ((Subscription)object2).getOptionsList();
                        for (int i = 0; i < list2.size(); ++i) {
                            this.mServiceBinderWrapper.addSubscription(string2, list2.get(i).mToken, (Bundle)object2.get(i), this.mCallbacksMessenger);
                        }
                    }
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.d((String)"MediaBrowserCompat", (String)"addSubscription failed with RemoteException.");
                    return;
                }
            }
        }

        private class MediaServiceConnection
        implements ServiceConnection {
            MediaServiceConnection() {
            }

            private void postOrRun(Runnable runnable) {
                if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
                    runnable.run();
                    return;
                }
                MediaBrowserImplBase.this.mHandler.post(runnable);
            }

            boolean isCurrent(String string2) {
                boolean bl = true;
                if (MediaBrowserImplBase.this.mServiceConnection != this || MediaBrowserImplBase.this.mState == 0 || MediaBrowserImplBase.this.mState == 1) {
                    if (MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
                        Log.i((String)"MediaBrowserCompat", (String)(string2 + " for " + (Object)MediaBrowserImplBase.this.mServiceComponent + " with mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection + " this=" + this));
                    }
                    bl = false;
                }
                return bl;
            }

            public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
                this.postOrRun(new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        block5: {
                            if (DEBUG) {
                                Log.d((String)"MediaBrowserCompat", (String)("MediaServiceConnection.onServiceConnected name=" + (Object)componentName + " binder=" + (Object)iBinder));
                                MediaBrowserImplBase.this.dump();
                            }
                            if (MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                                MediaBrowserImplBase.this.mServiceBinderWrapper = new ServiceBinderWrapper(iBinder, MediaBrowserImplBase.this.mRootHints);
                                MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger((Handler)MediaBrowserImplBase.this.mHandler);
                                MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger);
                                MediaBrowserImplBase.this.mState = 2;
                                try {
                                    if (DEBUG) {
                                        Log.d((String)"MediaBrowserCompat", (String)"ServiceCallbacks.onConnect...");
                                        MediaBrowserImplBase.this.dump();
                                    }
                                    MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserImplBase.this.mContext, MediaBrowserImplBase.this.mCallbacksMessenger);
                                    return;
                                }
                                catch (RemoteException remoteException) {
                                    Log.w((String)"MediaBrowserCompat", (String)("RemoteException during connect for " + (Object)MediaBrowserImplBase.this.mServiceComponent));
                                    if (!DEBUG) break block5;
                                    Log.d((String)"MediaBrowserCompat", (String)"ServiceCallbacks.onConnect...");
                                    MediaBrowserImplBase.this.dump();
                                    return;
                                }
                            }
                        }
                    }
                });
            }

            public void onServiceDisconnected(final ComponentName componentName) {
                this.postOrRun(new Runnable(){

                    @Override
                    public void run() {
                        if (DEBUG) {
                            Log.d((String)"MediaBrowserCompat", (String)("MediaServiceConnection.onServiceDisconnected name=" + (Object)componentName + " this=" + this + " mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection));
                            MediaBrowserImplBase.this.dump();
                        }
                        if (!MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                            return;
                        }
                        MediaBrowserImplBase.this.mServiceBinderWrapper = null;
                        MediaBrowserImplBase.this.mCallbacksMessenger = null;
                        MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
                        MediaBrowserImplBase.this.mState = 4;
                        MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
                    }
                });
            }

        }

    }

    static interface MediaBrowserServiceCallbackImpl {
        public void onConnectionFailed(Messenger var1);

        public void onLoadChildren(Messenger var1, String var2, List var3, Bundle var4);

        public void onServiceConnected(Messenger var1, String var2, MediaSessionCompat.Token var3, Bundle var4);
    }

    public static class MediaItem
    implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new Parcelable.Creator<MediaItem>(){

            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            public MediaItem[] newArray(int n) {
                return new MediaItem[n];
            }
        };
        private final MediaDescriptionCompat mDescription;
        private final int mFlags;

        MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }

        public MediaItem(MediaDescriptionCompat mediaDescriptionCompat, int n) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            }
            if (TextUtils.isEmpty((CharSequence)mediaDescriptionCompat.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
            this.mFlags = n;
            this.mDescription = mediaDescriptionCompat;
        }

        public static MediaItem fromMediaItem(Object object) {
            if (object == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            int n = MediaBrowserCompatApi21.MediaItem.getFlags(object);
            return new MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaBrowserCompatApi21.MediaItem.getDescription(object)), n);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static List<MediaItem> fromMediaItemList(List<?> list) {
            if (list == null) return null;
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList<MediaItem> arrayList = new ArrayList<MediaItem>(list.size());
            Iterator<MediaItem> iterator = list.iterator();
            do {
                list = arrayList;
                if (!iterator.hasNext()) return list;
                arrayList.add(MediaItem.fromMediaItem(iterator.next()));
            } while (true);
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("MediaItem{");
            stringBuilder.append("mFlags=").append(this.mFlags);
            stringBuilder.append(", mDescription=").append(this.mDescription);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, n);
        }

    }

    public static abstract class SearchCallback {
        public void onError(String string2, Bundle bundle) {
        }

        public void onSearchResult(String string2, Bundle bundle, List<MediaItem> list) {
        }
    }

    private static class SearchResultReceiver
    extends ResultReceiver {
        private final SearchCallback mCallback;
        private final Bundle mExtras;
        private final String mQuery;

        @Override
        protected void onReceiveResult(int n, Bundle object) {
            if (object != null) {
                object.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (n != 0 || object == null || !object.containsKey("search_results")) {
                this.mCallback.onError(this.mQuery, this.mExtras);
                return;
            }
            Parcelable[] arrparcelable = object.getParcelableArray("search_results");
            object = null;
            if (arrparcelable != null) {
                ArrayList<MediaItem> arrayList = new ArrayList<MediaItem>();
                int n2 = arrparcelable.length;
                n = 0;
                do {
                    object = arrayList;
                    if (n >= n2) break;
                    arrayList.add((MediaItem)arrparcelable[n]);
                    ++n;
                } while (true);
            }
            this.mCallback.onSearchResult(this.mQuery, this.mExtras, (List<MediaItem>)object);
        }
    }

    private static class ServiceBinderWrapper {
        private Messenger mMessenger;
        private Bundle mRootHints;

        public ServiceBinderWrapper(IBinder iBinder, Bundle bundle) {
            this.mMessenger = new Messenger(iBinder);
            this.mRootHints = bundle;
        }

        private void sendRequest(int n, Bundle bundle, Messenger messenger) throws RemoteException {
            Message message = Message.obtain();
            message.what = n;
            message.arg1 = 1;
            message.setData(bundle);
            message.replyTo = messenger;
            this.mMessenger.send(message);
        }

        void addSubscription(String string2, IBinder iBinder, Bundle bundle, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", string2);
            BundleCompat.putBinder(bundle2, "data_callback_token", iBinder);
            bundle2.putBundle("data_options", bundle);
            this.sendRequest(3, bundle2, messenger);
        }

        void connect(Context context, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString("data_package_name", context.getPackageName());
            bundle.putBundle("data_root_hints", this.mRootHints);
            this.sendRequest(1, bundle, messenger);
        }

        void disconnect(Messenger messenger) throws RemoteException {
            this.sendRequest(2, null, messenger);
        }

        void registerCallbackMessenger(Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putBundle("data_root_hints", this.mRootHints);
            this.sendRequest(6, bundle, messenger);
        }

        void unregisterCallbackMessenger(Messenger messenger) throws RemoteException {
            this.sendRequest(7, null, messenger);
        }
    }

    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks = new ArrayList<SubscriptionCallback>();
        private final List<Bundle> mOptionsList = new ArrayList<Bundle>();

        public SubscriptionCallback getCallback(Context context, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); ++i) {
                if (!MediaBrowserCompatUtils.areSameOptions(this.mOptionsList.get(i), bundle)) continue;
                return this.mCallbacks.get(i);
            }
            return null;
        }

        public List<SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public List<Bundle> getOptionsList() {
            return this.mOptionsList;
        }
    }

    public static abstract class SubscriptionCallback {
        private final Object mSubscriptionCallbackObj;
        WeakReference<Subscription> mSubscriptionRef;
        private final IBinder mToken;

        public SubscriptionCallback() {
            if (Build.VERSION.SDK_INT >= 26) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi24.createSubscriptionCallback(new StubApi24());
                this.mToken = null;
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21());
                this.mToken = new Binder();
                return;
            }
            this.mSubscriptionCallbackObj = null;
            this.mToken = new Binder();
        }

        public void onChildrenLoaded(String string2, List<MediaItem> list) {
        }

        public void onChildrenLoaded(String string2, List<MediaItem> list, Bundle bundle) {
        }

        public void onError(String string2) {
        }

        public void onError(String string2, Bundle bundle) {
        }

        private class StubApi21
        implements MediaBrowserCompatApi21.SubscriptionCallback {
            StubApi21() {
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             */
            List<MediaItem> applyOptions(List<MediaItem> list, Bundle object) {
                void var2_4;
                if (list == null) {
                    return var2_4;
                }
                int n = object.getInt("android.media.browse.extra.PAGE", -1);
                int n2 = object.getInt("android.media.browse.extra.PAGE_SIZE", -1);
                if (n == -1) {
                    List<MediaItem> list2 = list;
                    if (n2 == -1) {
                        return var2_4;
                    }
                }
                int n3 = n2 * n;
                int n4 = n3 + n2;
                if (n < 0 || n2 < 1 || n3 >= list.size()) {
                    return Collections.EMPTY_LIST;
                }
                n = n4;
                if (n4 > list.size()) {
                    n = list.size();
                }
                return list.subList(n3, n);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onChildrenLoaded(String string2, List<?> list) {
                Object object = SubscriptionCallback.this.mSubscriptionRef == null ? null : (Subscription)SubscriptionCallback.this.mSubscriptionRef.get();
                if (object == null) {
                    SubscriptionCallback.this.onChildrenLoaded(string2, MediaItem.fromMediaItemList(list));
                    return;
                }
                list = MediaItem.fromMediaItemList(list);
                List<SubscriptionCallback> list2 = ((Subscription)object).getCallbacks();
                object = ((Subscription)object).getOptionsList();
                int n = 0;
                while (n < list2.size()) {
                    Bundle bundle = (Bundle)object.get(n);
                    if (bundle == null) {
                        SubscriptionCallback.this.onChildrenLoaded(string2, list);
                    } else {
                        SubscriptionCallback.this.onChildrenLoaded(string2, this.applyOptions(list, bundle), bundle);
                    }
                    ++n;
                }
                return;
            }

            @Override
            public void onError(String string2) {
                SubscriptionCallback.this.onError(string2);
            }
        }

        private class StubApi24
        extends StubApi21
        implements MediaBrowserCompatApi24.SubscriptionCallback {
            StubApi24() {
            }

            @Override
            public void onChildrenLoaded(String string2, List<?> list, Bundle bundle) {
                SubscriptionCallback.this.onChildrenLoaded(string2, MediaItem.fromMediaItemList(list), bundle);
            }

            @Override
            public void onError(String string2, Bundle bundle) {
                SubscriptionCallback.this.onError(string2, bundle);
            }
        }

    }

}

