/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationBuilderWithActions;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.RemoteInputCompatApi20;
import android.support.v4.app.RemoteInputCompatBase;
import android.widget.RemoteViews;
import java.util.ArrayList;

class NotificationCompatApi20 {
    NotificationCompatApi20() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void addAction(Notification.Builder builder, NotificationCompatBase.Action action) {
        Bundle bundle;
        Notification.Action.Builder builder2 = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            bundle = RemoteInputCompatApi20.fromCompat(action.getRemoteInputs());
            int n = ((RemoteInput[])bundle).length;
            for (int i = 0; i < n; ++i) {
                builder2.addRemoteInput((RemoteInput)bundle[i]);
            }
        }
        bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        builder2.addExtras(bundle);
        builder.addAction(builder2.build());
    }

    public static NotificationCompatBase.Action getAction(Notification notification, int n, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        return NotificationCompatApi20.getActionCompatFromAction(notification.actions[n], factory, factory2);
    }

    private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action action, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory arrremoteInput) {
        arrremoteInput = RemoteInputCompatApi20.toCompat(action.getRemoteInputs(), (RemoteInputCompatBase.RemoteInput.Factory)arrremoteInput);
        boolean bl = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        return factory.build(action.icon, action.title, action.actionIntent, action.getExtras(), arrremoteInput, null, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Notification.Action getActionFromActionCompat(NotificationCompatBase.Action arrremoteInput) {
        Notification.Action.Builder builder = new Notification.Action.Builder(arrremoteInput.getIcon(), arrremoteInput.getTitle(), arrremoteInput.getActionIntent());
        Bundle bundle = arrremoteInput.getExtras() != null ? new Bundle(arrremoteInput.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", arrremoteInput.getAllowGeneratedReplies());
        builder.addExtras(bundle);
        arrremoteInput = arrremoteInput.getRemoteInputs();
        if (arrremoteInput != null) {
            arrremoteInput = RemoteInputCompatApi20.fromCompat(arrremoteInput);
            int n = arrremoteInput.length;
            for (int i = 0; i < n; ++i) {
                builder.addRemoteInput((RemoteInput)arrremoteInput[i]);
            }
        }
        return builder.build();
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
            arraction[n] = NotificationCompatApi20.getActionCompatFromAction((Notification.Action)arrayList.get(n), factory, factory2);
            ++n;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] arraction) {
        if (arraction == null) {
            return null;
        }
        ArrayList<Notification.Action> arrayList = new ArrayList<Notification.Action>(arraction.length);
        int n = arraction.length;
        int n2 = 0;
        do {
            ArrayList<Notification.Action> arrayList2 = arrayList;
            if (n2 >= n) return arrayList2;
            arrayList.add(NotificationCompatApi20.getActionFromActionCompat(arraction[n2]));
            ++n2;
        } while (true);
    }

    public static class Builder
    implements NotificationBuilderWithActions,
    NotificationBuilderWithBuilderAccessor {
        private Notification.Builder b;
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private Bundle mExtras;
        private int mGroupAlertBehavior;

        /*
         * Enabled aggressive block sorting
         */
        public Builder(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n2, int n3, boolean bl, boolean bl2, boolean bl3, int n4, CharSequence charSequence4, boolean bl4, ArrayList<String> arrayList, Bundle bundle, String string2, boolean bl5, String string3, RemoteViews remoteViews2, RemoteViews remoteViews3, int n5) {
            context = new Notification.Builder(context).setWhen(notification.when).setShowWhen(bl2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
            bl2 = (notification.flags & 2) != 0;
            context = context.setOngoing(bl2);
            bl2 = (notification.flags & 8) != 0;
            context = context.setOnlyAlertOnce(bl2);
            bl2 = (notification.flags & 0x10) != 0;
            context = context.setAutoCancel(bl2).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
            bl2 = (notification.flags & 0x80) != 0;
            this.b = context.setFullScreenIntent(pendingIntent2, bl2).setLargeIcon(bitmap).setNumber(n).setUsesChronometer(bl3).setPriority(n4).setProgress(n2, n3, bl).setLocalOnly(bl4).setGroup(string2).setGroupSummary(bl5).setSortKey(string3);
            this.mExtras = new Bundle();
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            if (arrayList != null && !arrayList.isEmpty()) {
                this.mExtras.putStringArray("android.people", arrayList.toArray(new String[arrayList.size()]));
            }
            this.mContentView = remoteViews2;
            this.mBigContentView = remoteViews3;
            this.mGroupAlertBehavior = n5;
        }

        private void removeSoundAndVibration(Notification notification) {
            notification.sound = null;
            notification.vibrate = null;
            notification.defaults &= 0xFFFFFFFE;
            notification.defaults &= 0xFFFFFFFD;
        }

        @Override
        public void addAction(NotificationCompatBase.Action action) {
            NotificationCompatApi20.addAction(this.b, action);
        }

        @Override
        public Notification build() {
            this.b.setExtras(this.mExtras);
            Notification notification = this.b.build();
            if (this.mContentView != null) {
                notification.contentView = this.mContentView;
            }
            if (this.mBigContentView != null) {
                notification.bigContentView = this.mBigContentView;
            }
            if (this.mGroupAlertBehavior != 0) {
                if (notification.getGroup() != null && (notification.flags & 0x200) != 0 && this.mGroupAlertBehavior == 2) {
                    this.removeSoundAndVibration(notification);
                }
                if (notification.getGroup() != null && (notification.flags & 0x200) == 0 && this.mGroupAlertBehavior == 1) {
                    this.removeSoundAndVibration(notification);
                }
            }
            return notification;
        }

        @Override
        public Notification.Builder getBuilder() {
            return this.b;
        }
    }

}

