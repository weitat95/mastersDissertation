/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.pm.PackageManager
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.app.Service;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class MediaBrowserServiceCompat
extends Service {
    static final boolean DEBUG = Log.isLoggable((String)"MBServiceCompat", (int)3);
    final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
    ConnectionRecord mCurConnection;
    final ServiceHandler mHandler = new ServiceHandler();
    MediaSessionCompat.Token mSession;

    void addSubscription(String string2, ConnectionRecord connectionRecord, IBinder iBinder, Bundle bundle) {
        List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(string2);
        List<Pair<IBinder, Bundle>> list2 = list;
        if (list == null) {
            list2 = new ArrayList<Pair<IBinder, Bundle>>();
        }
        for (Pair pair : list2) {
            if (iBinder != pair.first || !MediaBrowserCompatUtils.areSameOptions(bundle, (Bundle)pair.second)) continue;
            return;
        }
        list2.add(new Pair<IBinder, Bundle>(iBinder, bundle));
        connectionRecord.subscriptions.put(string2, list2);
        this.performLoadChildren(string2, connectionRecord, bundle);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, Bundle object) {
        void var2_4;
        if (list == null) {
            return var2_4;
        }
        int n = object.getInt("android.media.browse.extra.PAGE", -1);
        int n2 = object.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        if (n == -1) {
            List<MediaBrowserCompat.MediaItem> list2 = list;
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
    boolean isValidPackage(String string2, int n) {
        if (string2 != null) {
            String[] arrstring = this.getPackageManager().getPackagesForUid(n);
            int n2 = arrstring.length;
            for (n = 0; n < n2; ++n) {
                if (!arrstring[n].equals(string2)) continue;
                return true;
            }
        }
        return false;
    }

    public void onCustomAction(String string2, Bundle bundle, Result<Bundle> result) {
        result.sendError(null);
    }

    public abstract BrowserRoot onGetRoot(String var1, int var2, Bundle var3);

    public abstract void onLoadChildren(String var1, Result<List<MediaBrowserCompat.MediaItem>> var2);

    public void onLoadChildren(String string2, Result<List<MediaBrowserCompat.MediaItem>> result, Bundle bundle) {
        result.setFlags(1);
        this.onLoadChildren(string2, result);
    }

    public void onLoadItem(String string2, Result<MediaBrowserCompat.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void onSearch(String string2, Bundle bundle, Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.setFlags(4);
        result.sendResult(null);
    }

    void performCustomAction(String string2, Bundle bundle, ConnectionRecord connectionRecord, ResultReceiver object) {
        object = new Result<Bundle>((Object)string2, (ResultReceiver)object){
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onErrorSent(Bundle bundle) {
                this.val$receiver.send(-1, bundle);
            }

            @Override
            void onResultSent(Bundle bundle) {
                this.val$receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connectionRecord;
        this.onCustomAction(string2, bundle, (Result<Bundle>)object);
        this.mCurConnection = null;
        if (!((Result)object).isDone()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + string2 + " extras=" + (Object)bundle);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void performLoadChildren(final String string2, final ConnectionRecord connectionRecord, final Bundle bundle) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>((Object)string2){

            @Override
            void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                if (MediaBrowserServiceCompat.this.mConnections.get((Object)connectionRecord.callbacks.asBinder()) != connectionRecord) {
                    if (DEBUG) {
                        Log.d((String)"MBServiceCompat", (String)("Not sending onLoadChildren result for connection that has been disconnected. pkg=" + connectionRecord.pkg + " id=" + string2));
                    }
                    return;
                }
                if ((this.getFlags() & 1) != 0) {
                    list = MediaBrowserServiceCompat.this.applyOptions(list, bundle);
                }
                try {
                    connectionRecord.callbacks.onLoadChildren(string2, list, bundle);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.w((String)"MBServiceCompat", (String)("Calling onLoadChildren() failed for id=" + string2 + " package=" + connectionRecord.pkg));
                    return;
                }
            }
        };
        this.mCurConnection = connectionRecord;
        if (bundle == null) {
            this.onLoadChildren(string2, result);
        } else {
            this.onLoadChildren(string2, result, bundle);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connectionRecord.pkg + " id=" + string2);
        }
    }

    void performLoadItem(String string2, ConnectionRecord connectionRecord, ResultReceiver object) {
        object = new Result<MediaBrowserCompat.MediaItem>((Object)string2, (ResultReceiver)object){
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
                if ((this.getFlags() & 2) != 0) {
                    this.val$receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("media_item", (Parcelable)mediaItem);
                this.val$receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connectionRecord;
        this.onLoadItem(string2, (Result<MediaBrowserCompat.MediaItem>)object);
        this.mCurConnection = null;
        if (!((Result)object).isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + string2);
        }
    }

    void performSearch(String string2, Bundle bundle, ConnectionRecord connectionRecord, ResultReceiver object) {
        object = new Result<List<MediaBrowserCompat.MediaItem>>((Object)string2, (ResultReceiver)object){
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                if ((this.getFlags() & 4) != 0 || list == null) {
                    this.val$receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArray("search_results", (Parcelable[])list.toArray(new MediaBrowserCompat.MediaItem[0]));
                this.val$receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connectionRecord;
        this.onSearch(string2, bundle, (Result<List<MediaBrowserCompat.MediaItem>>)object);
        this.mCurConnection = null;
        if (!((Result)object).isDone()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + string2);
        }
    }

    boolean removeSubscription(String string2, ConnectionRecord connectionRecord, IBinder iBinder) {
        if (iBinder == null) {
            return connectionRecord.subscriptions.remove(string2) != null;
        }
        boolean bl = false;
        boolean bl2 = false;
        List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(string2);
        if (list != null) {
            Iterator<Pair<IBinder, Bundle>> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iBinder != iterator.next().first) continue;
                bl2 = true;
                iterator.remove();
            }
            bl = bl2;
            if (list.size() == 0) {
                connectionRecord.subscriptions.remove(string2);
                bl = bl2;
            }
        }
        return bl;
    }

    public static final class BrowserRoot {
        private final Bundle mExtras;
        private final String mRootId;

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String getRootId() {
            return this.mRootId;
        }
    }

    private static class ConnectionRecord {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap();

        ConnectionRecord() {
        }
    }

    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendResultCalled;

        Result(Object object) {
            this.mDebug = object;
        }

        int getFlags() {
            return this.mFlags;
        }

        boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled;
        }

        void onErrorSent(Bundle bundle) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.mDebug);
        }

        void onResultSent(T t) {
        }

        public void sendError(Bundle bundle) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendErrorCalled = true;
            this.onErrorSent(bundle);
        }

        public void sendResult(T t) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            this.onResultSent(t);
        }

        void setFlags(int n) {
            this.mFlags = n;
        }
    }

    private class ServiceBinderImpl {
        ServiceBinderImpl() {
        }

        public void addSubscription(final String string2, final IBinder iBinder, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        Log.w((String)"MBServiceCompat", (String)("addSubscription for callback that isn't registered id=" + string2));
                        return;
                    }
                    MediaBrowserServiceCompat.this.addSubscription(string2, (ConnectionRecord)object, iBinder, bundle);
                }
            });
        }

        public void connect(final String string2, final int n, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            if (!MediaBrowserServiceCompat.this.isValidPackage(string2, n)) {
                throw new IllegalArgumentException("Package/uid mismatch: uid=" + n + " package=" + string2);
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    IBinder iBinder = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                    ConnectionRecord connectionRecord = new ConnectionRecord();
                    connectionRecord.pkg = string2;
                    connectionRecord.rootHints = bundle;
                    connectionRecord.callbacks = serviceCallbacks;
                    connectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(string2, n, bundle);
                    if (connectionRecord.root == null) {
                        Log.i((String)"MBServiceCompat", (String)("No root for client " + string2 + " from service " + this.getClass().getName()));
                        try {
                            serviceCallbacks.onConnectFailed();
                            return;
                        }
                        catch (RemoteException remoteException) {
                            Log.w((String)"MBServiceCompat", (String)("Calling onConnectFailed() failed. Ignoring. pkg=" + string2));
                            return;
                        }
                    }
                    try {
                        MediaBrowserServiceCompat.this.mConnections.put(iBinder, connectionRecord);
                        if (MediaBrowserServiceCompat.this.mSession == null) return;
                        {
                            serviceCallbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras());
                            return;
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w((String)"MBServiceCompat", (String)("Calling onConnect() failed. Dropping client. pkg=" + string2));
                        MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                        return;
                    }
                }
            });
        }

        public void disconnect(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    IBinder iBinder = serviceCallbacks.asBinder();
                    if ((ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder) != null) {
                        // empty if block
                    }
                }
            });
        }

        public void getMediaItem(final String string2, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty((CharSequence)string2) || resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        Log.w((String)"MBServiceCompat", (String)("getMediaItem for callback that isn't registered id=" + string2));
                        return;
                    }
                    MediaBrowserServiceCompat.this.performLoadItem(string2, (ConnectionRecord)object, resultReceiver);
                }
            });
        }

        public void registerCallbacks(final ServiceCallbacks serviceCallbacks, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    IBinder iBinder = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                    ConnectionRecord connectionRecord = new ConnectionRecord();
                    connectionRecord.callbacks = serviceCallbacks;
                    connectionRecord.rootHints = bundle;
                    MediaBrowserServiceCompat.this.mConnections.put(iBinder, connectionRecord);
                }
            });
        }

        public void removeSubscription(final String string2, final IBinder iBinder, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public void run() {
                    IBinder iBinder2 = serviceCallbacks.asBinder();
                    ConnectionRecord connectionRecord = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get((Object)iBinder2);
                    if (connectionRecord == null) {
                        Log.w((String)"MBServiceCompat", (String)("removeSubscription for callback that isn't registered id=" + string2));
                        return;
                    } else {
                        if (MediaBrowserServiceCompat.this.removeSubscription(string2, connectionRecord, iBinder)) return;
                        {
                            Log.w((String)"MBServiceCompat", (String)("removeSubscription called for " + string2 + " which is not subscribed"));
                            return;
                        }
                    }
                }
            });
        }

        public void search(final String string2, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty((CharSequence)string2) || resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        Log.w((String)"MBServiceCompat", (String)("search for callback that isn't registered query=" + string2));
                        return;
                    }
                    MediaBrowserServiceCompat.this.performSearch(string2, bundle, (ConnectionRecord)object, resultReceiver);
                }
            });
        }

        public void sendCustomAction(final String string2, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (TextUtils.isEmpty((CharSequence)string2) || resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        Log.w((String)"MBServiceCompat", (String)("sendCustomAction for callback that isn't registered action=" + string2 + ", extras=" + (Object)bundle));
                        return;
                    }
                    MediaBrowserServiceCompat.this.performCustomAction(string2, bundle, (ConnectionRecord)object, resultReceiver);
                }
            });
        }

        public void unregisterCallbacks(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    IBinder iBinder = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                }
            });
        }

    }

    private static interface ServiceCallbacks {
        public IBinder asBinder();

        public void onConnect(String var1, MediaSessionCompat.Token var2, Bundle var3) throws RemoteException;

        public void onConnectFailed() throws RemoteException;

        public void onLoadChildren(String var1, List<MediaBrowserCompat.MediaItem> var2, Bundle var3) throws RemoteException;
    }

    private static class ServiceCallbacksCompat
    implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger messenger) {
            this.mCallbacks = messenger;
        }

        private void sendRequest(int n, Bundle bundle) throws RemoteException {
            Message message = Message.obtain();
            message.what = n;
            message.arg1 = 1;
            message.setData(bundle);
            this.mCallbacks.send(message);
        }

        @Override
        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        @Override
        public void onConnect(String string2, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
            Bundle bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putInt("extra_service_version", 1);
            bundle = new Bundle();
            bundle.putString("data_media_item_id", string2);
            bundle.putParcelable("data_media_session_token", (Parcelable)token);
            bundle.putBundle("data_root_hints", bundle2);
            this.sendRequest(1, bundle);
        }

        @Override
        public void onConnectFailed() throws RemoteException {
            this.sendRequest(2, null);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void onLoadChildren(String arrayList, List<MediaBrowserCompat.MediaItem> list, Bundle bundle) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", arrayList);
            bundle2.putBundle("data_options", bundle);
            if (list != null) {
                arrayList = list instanceof ArrayList ? (ArrayList<MediaBrowserCompat.MediaItem>)list : new ArrayList<MediaBrowserCompat.MediaItem>(list);
                bundle2.putParcelableArrayList("data_media_item_list", arrayList);
            }
            this.sendRequest(3, bundle2);
        }
    }

    private final class ServiceHandler
    extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl;

        ServiceHandler() {
            this.mServiceBinderImpl = new ServiceBinderImpl();
        }

        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            switch (message.what) {
                default: {
                    Log.w((String)"MBServiceCompat", (String)("Unhandled message: " + (Object)message + "\n  Service version: " + 1 + "\n  Client version: " + message.arg1));
                    return;
                }
                case 1: {
                    this.mServiceBinderImpl.connect(bundle.getString("data_package_name"), bundle.getInt("data_calling_uid"), bundle.getBundle("data_root_hints"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 2: {
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 3: {
                    this.mServiceBinderImpl.addSubscription(bundle.getString("data_media_item_id"), BundleCompat.getBinder(bundle, "data_callback_token"), bundle.getBundle("data_options"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 4: {
                    this.mServiceBinderImpl.removeSubscription(bundle.getString("data_media_item_id"), BundleCompat.getBinder(bundle, "data_callback_token"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 5: {
                    this.mServiceBinderImpl.getMediaItem(bundle.getString("data_media_item_id"), (ResultReceiver)bundle.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 6: {
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(message.replyTo), bundle.getBundle("data_root_hints"));
                    return;
                }
                case 7: {
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 8: {
                    this.mServiceBinderImpl.search(bundle.getString("data_search_query"), bundle.getBundle("data_search_extras"), (ResultReceiver)bundle.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 9: 
            }
            this.mServiceBinderImpl.sendCustomAction(bundle.getString("data_custom_action"), bundle.getBundle("data_custom_action_extras"), (ResultReceiver)bundle.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
        }

        public void postOrRun(Runnable runnable) {
            if (Thread.currentThread() == this.getLooper().getThread()) {
                runnable.run();
                return;
            }
            this.post(runnable);
        }

        public boolean sendMessageAtTime(Message message, long l) {
            Bundle bundle = message.getData();
            bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            bundle.putInt("data_calling_uid", Binder.getCallingUid());
            return super.sendMessageAtTime(message, l);
        }
    }

}

