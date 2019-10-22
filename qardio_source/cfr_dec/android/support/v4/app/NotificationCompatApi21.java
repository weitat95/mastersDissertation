/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.app.RemoteInput
 *  android.app.RemoteInput$Builder
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
import android.support.v4.app.NotificationCompatApi20;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.RemoteInputCompatBase;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

class NotificationCompatApi21 {
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_MESSAGES = "messages";
    private static final String KEY_ON_READ = "on_read";
    private static final String KEY_ON_REPLY = "on_reply";
    private static final String KEY_PARTICIPANTS = "participants";
    private static final String KEY_REMOTE_INPUT = "remote_input";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TIMESTAMP = "timestamp";

    NotificationCompatApi21() {
    }

    private static RemoteInput fromCompatRemoteInput(RemoteInputCompatBase.RemoteInput remoteInput) {
        return new RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build();
    }

    static Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation unreadConversation) {
        Parcelable[] arrparcelable;
        if (unreadConversation == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        Object object = arrparcelable = null;
        if (unreadConversation.getParticipants() != null) {
            object = arrparcelable;
            if (unreadConversation.getParticipants().length > 1) {
                object = unreadConversation.getParticipants()[0];
            }
        }
        arrparcelable = new Parcelable[unreadConversation.getMessages().length];
        for (int i = 0; i < arrparcelable.length; ++i) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(KEY_TEXT, unreadConversation.getMessages()[i]);
            bundle2.putString(KEY_AUTHOR, (String)object);
            arrparcelable[i] = bundle2;
        }
        bundle.putParcelableArray(KEY_MESSAGES, arrparcelable);
        object = unreadConversation.getRemoteInput();
        if (object != null) {
            bundle.putParcelable(KEY_REMOTE_INPUT, (Parcelable)NotificationCompatApi21.fromCompatRemoteInput((RemoteInputCompatBase.RemoteInput)object));
        }
        bundle.putParcelable(KEY_ON_REPLY, (Parcelable)unreadConversation.getReplyPendingIntent());
        bundle.putParcelable(KEY_ON_READ, (Parcelable)unreadConversation.getReadPendingIntent());
        bundle.putStringArray(KEY_PARTICIPANTS, unreadConversation.getParticipants());
        bundle.putLong(KEY_TIMESTAMP, unreadConversation.getLatestTimestamp());
        return bundle;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle var0, NotificationCompatBase.UnreadConversation.Factory var1_1, RemoteInputCompatBase.RemoteInput.Factory var2_2) {
        var7_3 = null;
        if (var0 == null) {
            return null;
        }
        var8_4 = var0.getParcelableArray("messages");
        var6_5 = null;
        if (var8_4 == null) ** GOTO lbl22
        var6_5 = new String[((Parcelable[])var8_4).length];
        var5_6 = true;
        var4_7 = 0;
        do {
            block7: {
                block5: {
                    block6: {
                        var3_8 = var5_6;
                        if (var4_7 >= var6_5.length) break block5;
                        if (var8_4[var4_7] instanceof Bundle) break block6;
                        var3_8 = false;
                        break block5;
                    }
                    var6_5[var4_7] = ((Bundle)var8_4[var4_7]).getString("text");
                    if (var6_5[var4_7] != null) break block7;
                    var3_8 = false;
                }
                if (var3_8 == false) return null;
lbl22:
                // 2 sources
                var8_4 = (PendingIntent)var0.getParcelable("on_read");
                var9_9 = (PendingIntent)var0.getParcelable("on_reply");
                var11_10 = (RemoteInput)var0.getParcelable("remote_input");
                var10_11 = var0.getStringArray("participants");
                if (var10_11 == null) return null;
                if (var10_11.length != 1) return null;
                if (var11_10 == null) return var1_1.build(var6_5, var7_3, var9_9, var8_4, var10_11, var0.getLong("timestamp"));
                var7_3 = NotificationCompatApi21.toCompatRemoteInput(var11_10, var2_2);
                return var1_1.build(var6_5, var7_3, var9_9, var8_4, var10_11, var0.getLong("timestamp"));
            }
            ++var4_7;
        } while (true);
    }

    private static RemoteInputCompatBase.RemoteInput toCompatRemoteInput(RemoteInput remoteInput, RemoteInputCompatBase.RemoteInput.Factory factory) {
        return factory.build(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), null);
    }

    public static class Builder
    implements NotificationBuilderWithActions,
    NotificationBuilderWithBuilderAccessor {
        private Notification.Builder b;
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private Bundle mExtras;
        private int mGroupAlertBehavior;
        private RemoteViews mHeadsUpContentView;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public Builder(Context object, Notification object2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n2, int n3, boolean bl, boolean bl2, boolean bl3, int n4, CharSequence charSequence4, boolean bl4, String string2, ArrayList<String> arrayList, Bundle bundle, int n5, int n6, Notification notification, String string3, boolean bl5, String string4, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, int n7) {
            void var4_6;
            void var11_13;
            void var17_19;
            void var20_22;
            void var25_27;
            void var8_10;
            void var7_9;
            void var16_18;
            void var27_29;
            void var12_14;
            void var22_24;
            void var5_7;
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
            object = new Notification.Builder((Context)object).setWhen(object2.when).setShowWhen(bl6).setSmallIcon(object2.icon, object2.iconLevel).setContent(object2.contentView).setTicker(object2.tickerText, (RemoteViews)var6_8).setSound(object2.sound, object2.audioStreamType).setVibrate(object2.vibrate).setLights(object2.ledARGB, object2.ledOnMS, object2.ledOffMS);
            bl6 = (object2.flags & 2) != 0;
            object = object.setOngoing(bl6);
            bl6 = (object2.flags & 8) != 0;
            object = object.setOnlyAlertOnce(bl6);
            bl6 = (object2.flags & 0x10) != 0;
            object = object.setAutoCancel(bl6).setDefaults(object2.defaults).setContentTitle((CharSequence)var3_5).setContentText((CharSequence)var4_6).setSubText((CharSequence)var17_19).setContentInfo((CharSequence)var5_7).setContentIntent((PendingIntent)var8_10).setDeleteIntent(object2.deleteIntent);
            bl6 = (object2.flags & 0x80) != 0;
            this.b = object.setFullScreenIntent((PendingIntent)var9_11, bl6).setLargeIcon((Bitmap)var10_12).setNumber((int)var7_9).setUsesChronometer((boolean)var15_17).setPriority((int)var16_18).setProgress((int)var11_13, (int)var12_14, (boolean)var13_15).setLocalOnly((boolean)var18_20).setGroup((String)var25_27).setGroupSummary((boolean)var26_28).setSortKey((String)var27_29).setCategory((String)var19_21).setColor((int)var22_24).setVisibility((int)var23_25).setPublicVersion((Notification)var24_26);
            this.mExtras = new Bundle();
            if (var21_23 != null) {
                this.mExtras.putAll((Bundle)var21_23);
            }
            object = var20_22.iterator();
            do {
                if (!object.hasNext()) {
                    void var30_32;
                    void var29_31;
                    void var31_33;
                    void var28_30;
                    this.mContentView = var28_30;
                    this.mBigContentView = var29_31;
                    this.mHeadsUpContentView = var30_32;
                    this.mGroupAlertBehavior = var31_33;
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
            if (this.mHeadsUpContentView != null) {
                notification.headsUpContentView = this.mHeadsUpContentView;
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

