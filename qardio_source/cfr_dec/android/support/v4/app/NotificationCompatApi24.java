/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Action$Builder
 *  android.app.Notification$Builder
 *  android.app.Notification$MessagingStyle
 *  android.app.Notification$MessagingStyle$Message
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
import java.util.Iterator;
import java.util.List;

class NotificationCompatApi24 {
    NotificationCompatApi24() {
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
        builder2.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
        builder2.addExtras(bundle);
        builder.addAction(builder2.build());
    }

    public static void addMessagingStyle(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor, CharSequence charSequence, CharSequence charSequence2, List<CharSequence> list, List<Long> list2, List<CharSequence> list3, List<String> list4, List<Uri> list5) {
        charSequence = new Notification.MessagingStyle(charSequence).setConversationTitle(charSequence2);
        for (int i = 0; i < list.size(); ++i) {
            charSequence2 = new Notification.MessagingStyle.Message(list.get(i), list2.get(i).longValue(), list3.get(i));
            if (list4.get(i) != null) {
                charSequence2.setData(list4.get(i), list5.get(i));
            }
            charSequence.addMessage((Notification.MessagingStyle.Message)charSequence2);
        }
        charSequence.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
    }

    public static NotificationCompatBase.Action getAction(Notification notification, int n, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
        return NotificationCompatApi24.getActionCompatFromAction(notification.actions[n], factory, factory2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action action, NotificationCompatBase.Action.Factory factory, RemoteInputCompatBase.RemoteInput.Factory arrremoteInput) {
        boolean bl;
        arrremoteInput = RemoteInputCompatApi20.toCompat(action.getRemoteInputs(), (RemoteInputCompatBase.RemoteInput.Factory)arrremoteInput);
        if (action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies()) {
            bl = true;
            do {
                return factory.build(action.icon, action.title, action.actionIntent, action.getExtras(), arrremoteInput, null, bl);
                break;
            } while (true);
        }
        bl = false;
        return factory.build(action.icon, action.title, action.actionIntent, action.getExtras(), arrremoteInput, null, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Notification.Action getActionFromActionCompat(NotificationCompatBase.Action arrremoteInput) {
        Notification.Action.Builder builder = new Notification.Action.Builder(arrremoteInput.getIcon(), arrremoteInput.getTitle(), arrremoteInput.getActionIntent());
        Bundle bundle = arrremoteInput.getExtras() != null ? new Bundle(arrremoteInput.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", arrremoteInput.getAllowGeneratedReplies());
        builder.setAllowGeneratedReplies(arrremoteInput.getAllowGeneratedReplies());
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
            arraction[n] = NotificationCompatApi24.getActionCompatFromAction((Notification.Action)arrayList.get(n), factory, factory2);
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
            arrayList.add(NotificationCompatApi24.getActionFromActionCompat(arraction[n2]));
            ++n2;
        } while (true);
    }

    public static class Builder
    implements NotificationBuilderWithActions,
    NotificationBuilderWithBuilderAccessor {
        private Notification.Builder b;
        private int mGroupAlertBehavior;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public Builder(Context object, Notification object2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n2, int n3, boolean bl, boolean bl2, boolean bl3, int n4, CharSequence charSequence4, boolean bl4, String string2, ArrayList<String> arrayList, Bundle bundle, int n5, int n6, Notification notification, String string3, boolean bl5, String string4, CharSequence[] arrcharSequence, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, int n7) {
            void var4_6;
            void var11_13;
            void var17_19;
            void var20_22;
            void var25_27;
            void var8_10;
            void var7_9;
            void var16_18;
            void var27_29;
            void var30_32;
            void var12_14;
            void var22_24;
            void var5_7;
            void var29_31;
            void var19_21;
            void var9_11;
            void var10_12;
            void var24_26;
            void var21_23;
            void var15_17;
            void var26_28;
            void var23_25;
            void var18_20;
            void var6_8;
            void var3_5;
            void var13_15;
            boolean bl6;
            void var31_33;
            void var28_30;
            object = new Notification.Builder((Context)object).setWhen(object2.when).setShowWhen(bl6).setSmallIcon(object2.icon, object2.iconLevel).setContent(object2.contentView).setTicker(object2.tickerText, (RemoteViews)var6_8).setSound(object2.sound, object2.audioStreamType).setVibrate(object2.vibrate).setLights(object2.ledARGB, object2.ledOnMS, object2.ledOffMS);
            bl6 = (object2.flags & 2) != 0;
            object = object.setOngoing(bl6);
            bl6 = (object2.flags & 8) != 0;
            object = object.setOnlyAlertOnce(bl6);
            bl6 = (object2.flags & 0x10) != 0;
            object = object.setAutoCancel(bl6).setDefaults(object2.defaults).setContentTitle((CharSequence)var3_5).setContentText((CharSequence)var4_6).setSubText((CharSequence)var17_19).setContentInfo((CharSequence)var5_7).setContentIntent((PendingIntent)var8_10).setDeleteIntent(object2.deleteIntent);
            bl6 = (object2.flags & 0x80) != 0;
            this.b = object.setFullScreenIntent((PendingIntent)var9_11, bl6).setLargeIcon((Bitmap)var10_12).setNumber((int)var7_9).setUsesChronometer((boolean)var15_17).setPriority((int)var16_18).setProgress((int)var11_13, (int)var12_14, (boolean)var13_15).setLocalOnly((boolean)var18_20).setExtras((Bundle)var21_23).setGroup((String)var25_27).setGroupSummary((boolean)var26_28).setSortKey((String)var27_29).setCategory((String)var19_21).setColor((int)var22_24).setVisibility((int)var23_25).setPublicVersion((Notification)var24_26).setRemoteInputHistory((CharSequence[])var28_30);
            if (var29_31 != null) {
                this.b.setCustomContentView((RemoteViews)var29_31);
            }
            if (var30_32 != null) {
                this.b.setCustomBigContentView((RemoteViews)var30_32);
            }
            if (var31_33 != null) {
                this.b.setCustomHeadsUpContentView((RemoteViews)var31_33);
            }
            object = var20_22.iterator();
            do {
                if (!object.hasNext()) {
                    void var32_34;
                    this.mGroupAlertBehavior = var32_34;
                    return;
                }
                String string5 = (String)object.next();
                this.b.addPerson(string5);
            } while (true);
        }

        private void removeSoundAndVibration(Notification notification) {
            notification.sound = null;
            notification.vibrate = null;
            notification.defaults &= 0xFFFFFFFE;
            notification.defaults &= 0xFFFFFFFD;
        }

        @Override
        public void addAction(NotificationCompatBase.Action action) {
            NotificationCompatApi24.addAction(this.b, action);
        }

        @Override
        public Notification build() {
            Notification notification = this.b.build();
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

