/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$BigPictureStyle
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$InboxStyle
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.SparseArray
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.BundleUtil;
import android.support.v4.app.NotificationBuilderWithActions;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.RemoteInputCompatBase;
import android.support.v4.app.RemoteInputCompatJellybean;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class NotificationCompatJellybean {
    static final String EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies";
    static final String EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock;
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock;

    static {
        sExtrasLock = new Object();
        sActionsLock = new Object();
    }

    NotificationCompatJellybean() {
    }

    public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean bl, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean bl2) {
        notificationBuilderWithBuilderAccessor = new Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (bl2) {
            notificationBuilderWithBuilderAccessor.bigLargeIcon(bitmap2);
        }
        if (bl) {
            notificationBuilderWithBuilderAccessor.setSummaryText(charSequence2);
        }
    }

    public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, boolean bl, CharSequence charSequence2, CharSequence charSequence3) {
        notificationBuilderWithBuilderAccessor = new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(charSequence).bigText(charSequence3);
        if (bl) {
            notificationBuilderWithBuilderAccessor.setSummaryText(charSequence2);
        }
    }

    public static void addInboxStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence object, boolean bl, CharSequence charSequence, ArrayList<CharSequence> arrayList) {
        notificationBuilderWithBuilderAccessor = new Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle((CharSequence)object);
        if (bl) {
            notificationBuilderWithBuilderAccessor.setSummaryText(charSequence);
        }
        object = arrayList.iterator();
        while (object.hasNext()) {
            notificationBuilderWithBuilderAccessor.addLine((CharSequence)object.next());
        }
    }

    public static SparseArray<Bundle> buildActionExtrasMap(List<Bundle> list) {
        SparseArray sparseArray = null;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Bundle bundle = list.get(i);
            SparseArray sparseArray2 = sparseArray;
            if (bundle != null) {
                sparseArray2 = sparseArray;
                if (sparseArray == null) {
                    sparseArray2 = new SparseArray();
                }
                sparseArray2.put(i, (Object)bundle);
            }
            sparseArray = sparseArray2;
        }
        return sparseArray;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean ensureActionReflectionReadyLocked() {
        boolean bl = true;
        if (sActionsAccessFailed) {
            return false;
        }
        try {
            if (sActionsField == null) {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField(KEY_TITLE);
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)classNotFoundException);
            sActionsAccessFailed = true;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)noSuchFieldException);
            sActionsAccessFailed = true;
        }
        if (sActionsAccessFailed) return false;
        return bl;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static NotificationCompatBase.Action getAction(Notification object, int n, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        Object object2 = sActionsLock;
        synchronized (object2) {
            try {
                Bundle bundle = NotificationCompatJellybean.getActionObjectsLocked((Notification)object);
                if (bundle == null) return null;
                Object object3 = bundle[n];
                bundle = null;
                Bundle bundle2 = NotificationCompatJellybean.getExtras((Notification)object);
                object = bundle;
                if (bundle2 == null) return NotificationCompatJellybean.readAction(factory, factory2, sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), object);
                bundle2 = bundle2.getSparseParcelableArray("android.support.actionExtras");
                object = bundle;
                if (bundle2 == null) return NotificationCompatJellybean.readAction(factory, factory2, sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), object);
                object = (Bundle)bundle2.get(n);
                return NotificationCompatJellybean.readAction(factory, factory2, sActionIconField.getInt(object3), (CharSequence)sActionTitleField.get(object3), (PendingIntent)sActionIntentField.get(object3), object);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)illegalAccessException);
                sActionsAccessFailed = true;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getActionCount(Notification arrobject) {
        Object object = sActionsLock;
        synchronized (object) {
            arrobject = NotificationCompatJellybean.getActionObjectsLocked((Notification)arrobject);
            if (arrobject == null) return 0;
            return arrobject.length;
        }
    }

    private static NotificationCompatBase.Action getActionFromBundle(Bundle bundle, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        Bundle bundle2 = bundle.getBundle(KEY_EXTRAS);
        boolean bl = false;
        if (bundle2 != null) {
            bl = bundle2.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES, false);
        }
        return factory.build(bundle.getInt(KEY_ICON), bundle.getCharSequence(KEY_TITLE), (PendingIntent)bundle.getParcelable(KEY_ACTION_INTENT), bundle.getBundle(KEY_EXTRAS), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, KEY_REMOTE_INPUTS), factory2), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, KEY_DATA_ONLY_REMOTE_INPUTS), factory2), bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Object[] getActionObjectsLocked(Notification arrobject) {
        Object object = sActionsLock;
        synchronized (object) {
            if (!NotificationCompatJellybean.ensureActionReflectionReadyLocked()) {
                return null;
            }
            try {
                return (Object[])sActionsField.get(arrobject);
            }
            catch (IllegalAccessException illegalAccessException) {
                Log.e((String)TAG, (String)"Unable to access notification actions", (Throwable)illegalAccessException);
                sActionsAccessFailed = true;
                return null;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        if (arrayList == null) {
            return null;
        }
        NotificationCompatBase.Action[] arraction = factory.newArray(arrayList.size());
        int n = 0;
        do {
            NotificationCompatBase.Action[] arraction2 = arraction;
            if (n >= arraction.length) return arraction2;
            arraction[n] = NotificationCompatJellybean.getActionFromBundle((Bundle)arrayList.get(n), factory, factory2);
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Bundle getBundleForAction(NotificationCompatBase.Action action) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ICON, action.getIcon());
        bundle.putCharSequence(KEY_TITLE, action.getTitle());
        bundle.putParcelable(KEY_ACTION_INTENT, (Parcelable)action.getActionIntent());
        Bundle bundle2 = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle2.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        bundle.putBundle(KEY_EXTRAS, bundle2);
        bundle.putParcelableArray(KEY_REMOTE_INPUTS, (Parcelable[])RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
        return bundle;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static Bundle getExtras(Notification notification) {
        Object object = sExtrasLock;
        // MONITORENTER : object
        if (sExtrasFieldAccessFailed) {
            // MONITOREXIT : object
            return null;
        }
        try {
            Field field;
            if (sExtrasField == null) {
                field = Notification.class.getDeclaredField(KEY_EXTRAS);
                if (!Bundle.class.isAssignableFrom(field.getType())) {
                    Log.e((String)TAG, (String)"Notification.extras field is not of type Bundle");
                    sExtrasFieldAccessFailed = true;
                    // MONITOREXIT : object
                    return null;
                }
                field.setAccessible(true);
                sExtrasField = field;
            }
            Bundle bundle = (Bundle)sExtrasField.get((Object)notification);
            field = bundle;
            if (bundle == null) {
                field = new Bundle();
                sExtrasField.set((Object)notification, field);
            }
            // MONITOREXIT : object
            return field;
        }
        catch (IllegalAccessException illegalAccessException) {
            block10: {
                Log.e((String)TAG, (String)"Unable to access notification extras", (Throwable)illegalAccessException);
                break block10;
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.e((String)TAG, (String)"Unable to access notification extras", (Throwable)noSuchFieldException);
                }
            }
            sExtrasFieldAccessFailed = true;
            // MONITOREXIT : object
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] arraction) {
        if (arraction == null) {
            return null;
        }
        ArrayList<Bundle> arrayList = new ArrayList<Bundle>(arraction.length);
        int n = arraction.length;
        int n2 = 0;
        do {
            ArrayList<Bundle> arrayList2 = arrayList;
            if (n2 >= n) return arrayList2;
            arrayList.add(NotificationCompatJellybean.getBundleForAction(arraction[n2]));
            ++n2;
        } while (true);
    }

    public static NotificationCompatBase.Action readAction(NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2, int n, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        RemoteInputCompatBase.RemoteInput[] arrremoteInput = null;
        RemoteInputCompatBase.RemoteInput[] arrremoteInput2 = null;
        boolean bl = false;
        if (bundle != null) {
            arrremoteInput = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, "android.support.remoteInputs"), factory2);
            arrremoteInput2 = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle(bundle, EXTRA_DATA_ONLY_REMOTE_INPUTS), factory2);
            bl = bundle.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES);
        }
        return factory.build(n, charSequence, pendingIntent, bundle, arrremoteInput, arrremoteInput2, bl);
    }

    public static Bundle writeActionAndGetExtras(Notification.Builder builder, NotificationCompatBase.Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent());
        builder = new Bundle(action.getExtras());
        if (action.getRemoteInputs() != null) {
            builder.putParcelableArray("android.support.remoteInputs", (Parcelable[])RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            builder.putParcelableArray(EXTRA_DATA_ONLY_REMOTE_INPUTS, (Parcelable[])RemoteInputCompatJellybean.toBundleArray(action.getDataOnlyRemoteInputs()));
        }
        builder.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies());
        return builder;
    }

    public static class Builder
    implements NotificationBuilderWithActions,
    NotificationBuilderWithBuilderAccessor {
        private Notification.Builder b;
        private List<Bundle> mActionExtrasList = new ArrayList<Bundle>();
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private final Bundle mExtras;

        /*
         * Enabled aggressive block sorting
         */
        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n2, int n3, boolean bl, boolean bl2, int n4, CharSequence charSequence4, boolean bl3, Bundle bundle, String string2, boolean bl4, String string3, RemoteViews remoteViews2, RemoteViews remoteViews3) {
            context = new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
            boolean bl5 = (notification.flags & 2) != 0;
            context = context.setOngoing(bl5);
            bl5 = (notification.flags & 8) != 0;
            context = context.setOnlyAlertOnce(bl5);
            bl5 = (notification.flags & 0x10) != 0;
            context = context.setAutoCancel(bl5).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
            bl5 = (notification.flags & 0x80) != 0;
            this.b = context.setFullScreenIntent(pendingIntent2, bl5).setLargeIcon(bitmap).setNumber(n).setUsesChronometer(bl2).setPriority(n4).setProgress(n2, n3, bl);
            this.mExtras = new Bundle();
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            if (bl3) {
                this.mExtras.putBoolean("android.support.localOnly", true);
            }
            if (string2 != null) {
                this.mExtras.putString("android.support.groupKey", string2);
                if (bl4) {
                    this.mExtras.putBoolean("android.support.isGroupSummary", true);
                } else {
                    this.mExtras.putBoolean("android.support.useSideChannel", true);
                }
            }
            if (string3 != null) {
                this.mExtras.putString("android.support.sortKey", string3);
            }
            this.mContentView = remoteViews2;
            this.mBigContentView = remoteViews3;
        }

        @Override
        public void addAction(NotificationCompatBase.Action action) {
            this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.b, action));
        }

        @Override
        public Notification build() {
            Notification notification = this.b.build();
            SparseArray<Bundle> sparseArray = NotificationCompatJellybean.getExtras(notification);
            Bundle bundle = new Bundle(this.mExtras);
            for (String string2 : this.mExtras.keySet()) {
                if (!sparseArray.containsKey(string2)) continue;
                bundle.remove(string2);
            }
            sparseArray.putAll(bundle);
            sparseArray = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (sparseArray != null) {
                NotificationCompatJellybean.getExtras(notification).putSparseParcelableArray("android.support.actionExtras", sparseArray);
            }
            if (this.mContentView != null) {
                notification.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification.bigContentView = this.mBigContentView;
            }
            return notification;
        }

        @Override
        public Notification.Builder getBuilder() {
            return this.b;
        }
    }

}

