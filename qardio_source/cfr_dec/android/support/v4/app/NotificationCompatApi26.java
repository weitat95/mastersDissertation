/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Builder
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Bundle
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationBuilderWithActions;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatApi24;
import android.support.v4.app.NotificationCompatBase;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

class NotificationCompatApi26 {
    NotificationCompatApi26() {
    }

    public static class Builder
    implements NotificationBuilderWithActions,
    NotificationBuilderWithBuilderAccessor {
        private Notification.Builder mB;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        Builder(Context object, Notification object2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n2, int n3, boolean bl, boolean bl2, boolean bl3, int n4, CharSequence charSequence4, boolean bl4, String string2, ArrayList<String> arrayList, Bundle bundle, int n5, int n6, Notification notification, String string3, boolean bl5, String string4, CharSequence[] arrcharSequence, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, String string5, int n7, String string6, long l, boolean bl6, boolean bl7, int n8) {
            void var4_6;
            void var11_13;
            void var17_19;
            void var20_22;
            void var34_36;
            void var25_27;
            void var8_10;
            void var7_9;
            void var39_40;
            void var16_18;
            void var27_29;
            void var30_32;
            void var12_14;
            void var22_24;
            void var5_7;
            void var29_31;
            void var32_34;
            void var19_21;
            void var9_11;
            void var10_12;
            void var24_26;
            void var21_23;
            void var15_17;
            void var35_37;
            void var26_28;
            void var23_25;
            void var33_35;
            void var18_20;
            void var6_8;
            void var3_5;
            void var13_15;
            boolean bl8;
            void var31_33;
            void var38_39;
            void var28_30;
            object = new Notification.Builder((Context)object, (String)var32_34).setWhen(object2.when).setShowWhen(bl8).setSmallIcon(object2.icon, object2.iconLevel).setContent(object2.contentView).setTicker(object2.tickerText, (RemoteViews)var6_8).setSound(object2.sound, object2.audioStreamType).setVibrate(object2.vibrate).setLights(object2.ledARGB, object2.ledOnMS, object2.ledOffMS);
            bl8 = (object2.flags & 2) != 0;
            object = object.setOngoing(bl8);
            bl8 = (object2.flags & 8) != 0;
            object = object.setOnlyAlertOnce(bl8);
            bl8 = (object2.flags & 0x10) != 0;
            object = object.setAutoCancel(bl8).setDefaults(object2.defaults).setContentTitle((CharSequence)var3_5).setContentText((CharSequence)var4_6).setSubText((CharSequence)var17_19).setContentInfo((CharSequence)var5_7).setContentIntent((PendingIntent)var8_10).setDeleteIntent(object2.deleteIntent);
            bl8 = (object2.flags & 0x80) != 0;
            this.mB = object.setFullScreenIntent((PendingIntent)var9_11, bl8).setLargeIcon((Bitmap)var10_12).setNumber((int)var7_9).setUsesChronometer((boolean)var15_17).setPriority((int)var16_18).setProgress((int)var11_13, (int)var12_14, (boolean)var13_15).setLocalOnly((boolean)var18_20).setExtras((Bundle)var21_23).setGroup((String)var25_27).setGroupSummary((boolean)var26_28).setSortKey((String)var27_29).setCategory((String)var19_21).setColor((int)var22_24).setVisibility((int)var23_25).setPublicVersion((Notification)var24_26).setRemoteInputHistory((CharSequence[])var28_30).setChannelId((String)var32_34).setBadgeIconType((int)var33_35).setShortcutId((String)var34_36).setTimeoutAfter((long)var35_37).setGroupAlertBehavior((int)var39_40);
            if (var38_39 != false) {
                void var37_38;
                this.mB.setColorized((boolean)var37_38);
            }
            if (var29_31 != null) {
                this.mB.setCustomContentView((RemoteViews)var29_31);
            }
            if (var30_32 != null) {
                this.mB.setCustomBigContentView((RemoteViews)var30_32);
            }
            if (var31_33 != null) {
                this.mB.setCustomHeadsUpContentView((RemoteViews)var31_33);
            }
            object = var20_22.iterator();
            while (object.hasNext()) {
                String string7 = (String)object.next();
                this.mB.addPerson(string7);
            }
            return;
        }

        @Override
        public void addAction(NotificationCompatBase.Action action) {
            NotificationCompatApi24.addAction(this.mB, action);
        }

        @Override
        public Notification build() {
            return this.mB.build();
        }

        @Override
        public Notification.Builder getBuilder() {
            return this.mB;
        }
    }

}

