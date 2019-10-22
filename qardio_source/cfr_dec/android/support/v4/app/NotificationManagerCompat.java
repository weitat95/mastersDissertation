/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.app.Notification
 *  android.app.NotificationManager
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Log
 */
package android.support.v4.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    private static Set<String> sEnabledNotificationListenerPackages;
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock;
    private static final Object sLock;
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager;

    static {
        sEnabledNotificationListenersLock = new Object();
        sEnabledNotificationListenerPackages = new HashSet<String>();
        sLock = new Object();
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    }

    public static NotificationManagerCompat from(Context context) {
        return new NotificationManagerCompat(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Set<String> getEnabledListenerPackages(Context object) {
        String string2 = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)SETTING_ENABLED_NOTIFICATION_LISTENERS);
        object = sEnabledNotificationListenersLock;
        synchronized (object) {
            if (string2 == null) return sEnabledNotificationListenerPackages;
            if (string2.equals(sEnabledNotificationListeners)) return sEnabledNotificationListenerPackages;
            String[] arrstring = string2.split(":");
            HashSet<String> hashSet = new HashSet<String>(arrstring.length);
            int n = arrstring.length;
            int n2 = 0;
            do {
                if (n2 < n) {
                    ComponentName componentName = ComponentName.unflattenFromString((String)arrstring[n2]);
                    if (componentName != null) {
                        hashSet.add(componentName.getPackageName());
                    }
                } else {
                    sEnabledNotificationListenerPackages = hashSet;
                    sEnabledNotificationListeners = string2;
                    return sEnabledNotificationListenerPackages;
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void pushSideChannelQueue(Task task) {
        Object object = sLock;
        synchronized (object) {
            if (sSideChannelManager == null) {
                sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
            sSideChannelManager.queueTask(task);
            return;
        }
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        return (notification = NotificationCompat.getExtras(notification)) != null && notification.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean areNotificationsEnabled() {
        boolean bl = true;
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.areNotificationsEnabled();
        }
        if (Build.VERSION.SDK_INT < 19) return bl;
        AppOpsManager appOpsManager = (AppOpsManager)this.mContext.getSystemService("appops");
        Object object = this.mContext.getApplicationInfo();
        String string2 = this.mContext.getApplicationContext().getPackageName();
        int n = ((ApplicationInfo)object).uid;
        try {
            object = Class.forName(AppOpsManager.class.getName());
            n = (Integer)((Class)object).getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class).invoke((Object)appOpsManager, (int)((Integer)((Class)object).getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)), n, string2);
            if (n != 0) return false;
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return true;
        }
        catch (RuntimeException runtimeException) {
            return true;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return true;
        }
        catch (IllegalAccessException illegalAccessException) {
            return true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return true;
        }
        catch (InvocationTargetException invocationTargetException) {
            return true;
        }
    }

    public void cancel(int n) {
        this.cancel(null, n);
    }

    public void cancel(String string2, int n) {
        this.mNotificationManager.cancel(string2, n);
        if (Build.VERSION.SDK_INT <= 19) {
            this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), n, string2));
        }
    }

    public void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (Build.VERSION.SDK_INT <= 19) {
            this.pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public int getImportance() {
        if (Build.VERSION.SDK_INT >= 24) {
            return this.mNotificationManager.getImportance();
        }
        return -1000;
    }

    public void notify(int n, Notification notification) {
        this.notify(null, n, notification);
    }

    public void notify(String string2, int n, Notification notification) {
        if (NotificationManagerCompat.useSideChannelForNotification(notification)) {
            this.pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), n, string2, notification));
            this.mNotificationManager.cancel(string2, n);
            return;
        }
        this.mNotificationManager.notify(string2, n, notification);
    }

    private static class CancelTask
    implements Task {
        final boolean all;
        final int id;
        final String packageName;
        final String tag;

        CancelTask(String string2) {
            this.packageName = string2;
            this.id = 0;
            this.tag = null;
            this.all = true;
        }

        CancelTask(String string2, int n, String string3) {
            this.packageName = string2;
            this.id = n;
            this.tag = string3;
            this.all = false;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            if (this.all) {
                iNotificationSideChannel.cancelAll(this.packageName);
                return;
            }
            iNotificationSideChannel.cancel(this.packageName, this.id, this.tag);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CancelTask[");
            stringBuilder.append("packageName:").append(this.packageName);
            stringBuilder.append(", id:").append(this.id);
            stringBuilder.append(", tag:").append(this.tag);
            stringBuilder.append(", all:").append(this.all);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class NotifyTask
    implements Task {
        final int id;
        final Notification notif;
        final String packageName;
        final String tag;

        NotifyTask(String string2, int n, String string3, Notification notification) {
            this.packageName = string2;
            this.id = n;
            this.tag = string3;
            this.notif = notification;
        }

        @Override
        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
            stringBuilder.append("packageName:").append(this.packageName);
            stringBuilder.append(", id:").append(this.id);
            stringBuilder.append(", tag:").append(this.tag);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    private static class SideChannelManager
    implements ServiceConnection,
    Handler.Callback {
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages;
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap<ComponentName, ListenerRecord>();

        public SideChannelManager(Context context) {
            this.mCachedEnabledPackages = new HashSet<String>();
            this.mContext = context;
            this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), (Handler.Callback)this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean ensureServiceBound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                return true;
            }
            Intent intent = new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.componentName);
            listenerRecord.bound = this.mContext.bindService(intent, (ServiceConnection)this, 33);
            if (listenerRecord.bound) {
                listenerRecord.retryCount = 0;
                do {
                    return listenerRecord.bound;
                    break;
                } while (true);
            }
            Log.w((String)NotificationManagerCompat.TAG, (String)("Unable to bind to listener " + (Object)listenerRecord.componentName));
            this.mContext.unbindService((ServiceConnection)this);
            return listenerRecord.bound;
        }

        private void ensureServiceUnbound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                this.mContext.unbindService((ServiceConnection)this);
                listenerRecord.bound = false;
            }
            listenerRecord.service = null;
        }

        private void handleQueueTask(Task task) {
            this.updateListenerMap();
            for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
                listenerRecord.taskQueue.add(task);
                this.processListenerQueue(listenerRecord);
            }
        }

        private void handleRetryListenerQueue(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) != null) {
                this.processListenerQueue((ListenerRecord)object);
            }
        }

        private void handleServiceConnected(ComponentName object, IBinder iBinder) {
            if ((object = this.mRecordMap.get(object)) != null) {
                object.service = INotificationSideChannel.Stub.asInterface(iBinder);
                object.retryCount = 0;
                this.processListenerQueue((ListenerRecord)object);
            }
        }

        private void handleServiceDisconnected(ComponentName object) {
            if ((object = this.mRecordMap.get(object)) != null) {
                this.ensureServiceUnbound((ListenerRecord)object);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void processListenerQueue(ListenerRecord var1_1) {
            block8: {
                if (Log.isLoggable((String)"NotifManCompat", (int)3)) {
                    Log.d((String)"NotifManCompat", (String)("Processing component " + (Object)var1_1.componentName + ", " + var1_1.taskQueue.size() + " queued tasks"));
                }
                if (var1_1.taskQueue.isEmpty()) {
                    return;
                }
                if (this.ensureServiceBound(var1_1) && var1_1.service != null) ** GOTO lbl16
                this.scheduleListenerRetry(var1_1);
                return;
lbl-1000:
                // 1 sources
                {
                    if (Log.isLoggable((String)"NotifManCompat", (int)3)) {
                        Log.d((String)"NotifManCompat", (String)("Sending task " + var2_2));
                    }
                    var2_2.send(var1_1.service);
                    var1_1.taskQueue.remove();
lbl16:
                    // 2 sources
                    ** while ((var2_2 = var1_1.taskQueue.peek()) != null)
                }
lbl17:
                // 1 sources
                break block8;
                catch (DeadObjectException var2_3) {
                    if (Log.isLoggable((String)"NotifManCompat", (int)3)) {
                        Log.d((String)"NotifManCompat", (String)("Remote service has died: " + (Object)var1_1.componentName));
                    }
                }
                catch (RemoteException var2_4) {
                    Log.w((String)"NotifManCompat", (String)("RemoteException communicating with " + (Object)var1_1.componentName), (Throwable)var2_4);
                }
            }
            if (var1_1.taskQueue.isEmpty() != false) return;
            this.scheduleListenerRetry(var1_1);
        }

        private void scheduleListenerRetry(ListenerRecord listenerRecord) {
            if (this.mHandler.hasMessages(3, (Object)listenerRecord.componentName)) {
                return;
            }
            ++listenerRecord.retryCount;
            if (listenerRecord.retryCount > 6) {
                Log.w((String)NotificationManagerCompat.TAG, (String)("Giving up on delivering " + listenerRecord.taskQueue.size() + " tasks to " + (Object)listenerRecord.componentName + " after " + listenerRecord.retryCount + " retries"));
                listenerRecord.taskQueue.clear();
                return;
            }
            int n = (1 << listenerRecord.retryCount - 1) * 1000;
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                Log.d((String)NotificationManagerCompat.TAG, (String)("Scheduling retry for " + n + " ms"));
            }
            listenerRecord = this.mHandler.obtainMessage(3, (Object)listenerRecord.componentName);
            this.mHandler.sendMessageDelayed((Message)listenerRecord, (long)n);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void updateListenerMap() {
            Object object = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            if (!object.equals(this.mCachedEnabledPackages)) {
                this.mCachedEnabledPackages = object;
                ComponentName componentName3 = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
                HashSet<ComponentName> hashSet = new HashSet<ComponentName>();
                for (ResolveInfo resolveInfo : componentName3) {
                    if (!object.contains(resolveInfo.serviceInfo.packageName)) continue;
                    ComponentName componentName2 = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                    if (resolveInfo.serviceInfo.permission != null) {
                        Log.w((String)NotificationManagerCompat.TAG, (String)("Permission present on component " + (Object)componentName2 + ", not adding listener record."));
                        continue;
                    }
                    hashSet.add(componentName2);
                }
                for (ComponentName componentName3 : hashSet) {
                    if (this.mRecordMap.containsKey((Object)componentName3)) continue;
                    if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                        Log.d((String)NotificationManagerCompat.TAG, (String)("Adding listener record for " + componentName3));
                    }
                    this.mRecordMap.put(componentName3, new ListenerRecord(componentName3));
                }
                object = this.mRecordMap.entrySet().iterator();
                while (object.hasNext()) {
                    componentName3 = object.next();
                    if (hashSet.contains((Object)componentName3.getKey())) continue;
                    if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                        Log.d((String)NotificationManagerCompat.TAG, (String)("Removing listener record for " + (Object)componentName3.getKey()));
                    }
                    this.ensureServiceUnbound(componentName3.getValue());
                    object.remove();
                }
            }
        }

        public boolean handleMessage(Message object) {
            switch (object.what) {
                default: {
                    return false;
                }
                case 0: {
                    this.handleQueueTask((Task)object.obj);
                    return true;
                }
                case 1: {
                    object = (ServiceConnectedEvent)object.obj;
                    this.handleServiceConnected(object.componentName, object.iBinder);
                    return true;
                }
                case 2: {
                    this.handleServiceDisconnected((ComponentName)object.obj);
                    return true;
                }
                case 3: 
            }
            this.handleRetryListenerQueue((ComponentName)object.obj);
            return true;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                Log.d((String)NotificationManagerCompat.TAG, (String)("Connected to service " + (Object)componentName));
            }
            this.mHandler.obtainMessage(1, (Object)new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (Log.isLoggable((String)NotificationManagerCompat.TAG, (int)3)) {
                Log.d((String)NotificationManagerCompat.TAG, (String)("Disconnected from service " + (Object)componentName));
            }
            this.mHandler.obtainMessage(2, (Object)componentName).sendToTarget();
        }

        public void queueTask(Task task) {
            this.mHandler.obtainMessage(0, (Object)task).sendToTarget();
        }

        private static class ListenerRecord {
            public boolean bound = false;
            public final ComponentName componentName;
            public int retryCount = 0;
            public INotificationSideChannel service;
            public LinkedList<Task> taskQueue = new LinkedList();

            public ListenerRecord(ComponentName componentName) {
                this.componentName = componentName;
            }
        }

    }

    private static interface Task {
        public void send(INotificationSideChannel var1) throws RemoteException;
    }

}

