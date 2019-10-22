/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Notification
 *  android.app.Notification$BigTextStyle
 *  android.app.Notification$Builder
 *  android.app.Notification$Style
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Color
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.ResourceIds;
import com.mixpanel.android.mpmetrics.ResourceReader;
import com.mixpanel.android.util.MPLog;
import org.json.JSONException;
import org.json.JSONObject;

public class GCMReceiver
extends BroadcastReceiver {
    private Notification buildNotification(Context context, Intent object, ResourceIds resourceIds) {
        if ((object = this.readInboundIntent(context, (Intent)object, resourceIds)) == null) {
            return null;
        }
        MPLog.d("MixpanelAPI.GCMReceiver", "MP GCM notification received: " + object.message);
        resourceIds = PendingIntent.getActivity((Context)context, (int)0, (Intent)object.intent, (int)134217728);
        if (Build.VERSION.SDK_INT >= 26) {
            return this.makeNotificationSDK26OrHigher(context, (PendingIntent)resourceIds, (NotificationData)object);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return this.makeNotificationSDK21OrHigher(context, (PendingIntent)resourceIds, (NotificationData)object);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return this.makeNotificationSDK16OrHigher(context, (PendingIntent)resourceIds, (NotificationData)object);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            return this.makeNotificationSDK11OrHigher(context, (PendingIntent)resourceIds, (NotificationData)object);
        }
        return this.makeNotificationSDKLessThan11(context, (PendingIntent)resourceIds, (NotificationData)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Intent buildNotificationIntent(Context context, String string2, String string3, String string4, String string5) {
        Uri uri = null;
        if (string2 != null) {
            uri = Uri.parse((String)string2);
        }
        context = uri == null ? this.getDefaultIntent(context) : new Intent("android.intent.action.VIEW", uri);
        if (string3 != null) {
            context.putExtra("mp_campaign_id", string3);
        }
        if (string4 != null) {
            context.putExtra("mp_message_id", string4);
        }
        if (string5 != null) {
            context.putExtra("mp", string5);
        }
        return context;
    }

    private void handleNotificationIntent(Context context, Intent intent) {
        String string2 = MPConfig.getInstance(context).getResourcePackageName();
        Object object = string2;
        if (string2 == null) {
            object = context.getPackageName();
        }
        object = new ResourceReader.Drawables((String)object, context);
        intent = this.buildNotification(context.getApplicationContext(), intent, (ResourceIds)object);
        if (intent != null) {
            ((NotificationManager)context.getSystemService("notification")).notify(0, (Notification)intent);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleRegistrationIntent(Intent intent) {
        final String string2 = intent.getStringExtra("registration_id");
        if (intent.getStringExtra("error") != null) {
            MPLog.e("MixpanelAPI.GCMReceiver", "Error when registering for GCM: " + intent.getStringExtra("error"));
            return;
        } else {
            if (string2 != null) {
                MPLog.d("MixpanelAPI.GCMReceiver", "Registering GCM ID: " + string2);
                MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(){

                    @Override
                    public void process(MixpanelAPI mixpanelAPI) {
                        mixpanelAPI.getPeople().setPushRegistrationId(string2);
                    }
                });
                return;
            }
            if (intent.getStringExtra("unregistered") == null) return;
            {
                MPLog.d("MixpanelAPI.GCMReceiver", "Unregistering from GCM");
                MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(){

                    @Override
                    public void process(MixpanelAPI mixpanelAPI) {
                        mixpanelAPI.getPeople().clearPushRegistrationId();
                    }
                });
                return;
            }
        }
    }

    private void trackCampaignReceived(final String string2, final String string3, final String string4) {
        if (string2 != null && string3 != null) {
            MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                @Override
                public void process(MixpanelAPI mixpanelAPI) {
                    JSONObject jSONObject;
                    if (!mixpanelAPI.isAppInForeground()) return;
                    JSONObject jSONObject2 = jSONObject = new JSONObject();
                    try {
                        if (string4 != null) {
                            jSONObject2 = new JSONObject(string4);
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONObject2 = jSONObject;
                    }
                    try {
                        jSONObject2.put("campaign_id", Integer.valueOf(string2).intValue());
                        jSONObject2.put("message_id", Integer.valueOf(string3).intValue());
                        jSONObject2.put("message_type", (Object)"push");
                        mixpanelAPI.track("$campaign_received", jSONObject2);
                        return;
                    }
                    catch (JSONException jSONException) {
                        return;
                    }
                }
            });
        }
    }

    Intent getDefaultIntent(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    }

    @TargetApi(value=11)
    protected Notification makeNotificationSDK11OrHigher(Context context, PendingIntent pendingIntent, NotificationData notificationData) {
        pendingIntent = new Notification.Builder(context).setSmallIcon(notificationData.icon).setTicker((CharSequence)notificationData.message).setWhen(System.currentTimeMillis()).setContentTitle(notificationData.title).setContentText((CharSequence)notificationData.message).setContentIntent(pendingIntent).setDefaults(MPConfig.getInstance(context).getNotificationDefaults());
        if (notificationData.largeIcon != -1) {
            pendingIntent.setLargeIcon(BitmapFactory.decodeResource((Resources)context.getResources(), (int)notificationData.largeIcon));
        }
        context = pendingIntent.getNotification();
        context.flags |= 0x10;
        return context;
    }

    @SuppressLint(value={"NewApi"})
    @TargetApi(value=16)
    protected Notification makeNotificationSDK16OrHigher(Context context, PendingIntent pendingIntent, NotificationData notificationData) {
        pendingIntent = new Notification.Builder(context).setSmallIcon(notificationData.icon).setTicker((CharSequence)notificationData.message).setWhen(System.currentTimeMillis()).setContentTitle(notificationData.title).setContentText((CharSequence)notificationData.message).setContentIntent(pendingIntent).setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)notificationData.message)).setDefaults(MPConfig.getInstance(context).getNotificationDefaults());
        if (notificationData.largeIcon != -1) {
            pendingIntent.setLargeIcon(BitmapFactory.decodeResource((Resources)context.getResources(), (int)notificationData.largeIcon));
        }
        context = pendingIntent.build();
        context.flags |= 0x10;
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    @TargetApi(value=21)
    protected Notification makeNotificationSDK21OrHigher(Context context, PendingIntent pendingIntent, NotificationData notificationData) {
        pendingIntent = new Notification.Builder(context).setTicker((CharSequence)notificationData.message).setWhen(System.currentTimeMillis()).setContentTitle(notificationData.title).setContentText((CharSequence)notificationData.message).setContentIntent(pendingIntent).setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)notificationData.message)).setDefaults(MPConfig.getInstance(context).getNotificationDefaults());
        if (notificationData.whiteIcon != -1) {
            pendingIntent.setSmallIcon(notificationData.whiteIcon);
        } else {
            pendingIntent.setSmallIcon(notificationData.icon);
        }
        if (notificationData.largeIcon != -1) {
            pendingIntent.setLargeIcon(BitmapFactory.decodeResource((Resources)context.getResources(), (int)notificationData.largeIcon));
        }
        if (notificationData.color != -1) {
            pendingIntent.setColor(notificationData.color);
        }
        context = pendingIntent.build();
        context.flags |= 0x10;
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"NewApi"})
    @TargetApi(value=26)
    protected Notification makeNotificationSDK26OrHigher(Context context, PendingIntent pendingIntent, NotificationData notificationData) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService("notification");
        String string2 = MPConfig.getInstance(context).getNotificationChannelId();
        NotificationChannel notificationChannel = new NotificationChannel(string2, (CharSequence)MPConfig.getInstance(context).getNotificationChannelName(), MPConfig.getInstance(context).getNotificationChannelImportance());
        int n = MPConfig.getInstance(context).getNotificationDefaults();
        if (n == 2 || n == -1) {
            notificationChannel.enableVibration(true);
        }
        if (n == 4 || n == -1) {
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-1);
        }
        notificationManager.createNotificationChannel(notificationChannel);
        pendingIntent = new Notification.Builder(context).setTicker((CharSequence)notificationData.message).setWhen(System.currentTimeMillis()).setContentTitle(notificationData.title).setContentText((CharSequence)notificationData.message).setContentIntent(pendingIntent).setStyle((Notification.Style)new Notification.BigTextStyle().bigText((CharSequence)notificationData.message)).setChannelId(string2);
        if (notificationData.whiteIcon != -1) {
            pendingIntent.setSmallIcon(notificationData.whiteIcon);
        } else {
            pendingIntent.setSmallIcon(notificationData.icon);
        }
        if (notificationData.largeIcon != -1) {
            pendingIntent.setLargeIcon(BitmapFactory.decodeResource((Resources)context.getResources(), (int)notificationData.largeIcon));
        }
        if (notificationData.color != -1) {
            pendingIntent.setColor(notificationData.color);
        }
        context = pendingIntent.build();
        context.flags |= 0x10;
        return context;
    }

    @TargetApi(value=9)
    protected Notification makeNotificationSDKLessThan11(Context context, PendingIntent object, NotificationData notificationData) {
        object = new NotificationCompat.Builder(context).setSmallIcon(notificationData.icon).setTicker(notificationData.message).setWhen(System.currentTimeMillis()).setContentTitle(notificationData.title).setContentText(notificationData.message).setContentIntent((PendingIntent)object).setDefaults(MPConfig.getInstance(context).getNotificationDefaults());
        if (notificationData.largeIcon != -1) {
            ((NotificationCompat.Builder)object).setLargeIcon(BitmapFactory.decodeResource((Resources)context.getResources(), (int)notificationData.largeIcon));
        }
        context = ((NotificationCompat.Builder)object).getNotification();
        context.flags |= 0x10;
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onReceive(Context context, Intent intent) {
        String string2 = intent.getAction();
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(string2)) {
            this.handleRegistrationIntent(intent);
            return;
        } else {
            if (!"com.google.android.c2dm.intent.RECEIVE".equals(string2)) return;
            {
                this.handleNotificationIntent(context, intent);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    NotificationData readInboundIntent(Context context, Intent object, ResourceIds object2) {
        void var2_8;
        int n;
        Object object3;
        PackageManager packageManager = context.getPackageManager();
        String string2 = object.getStringExtra("mp_message");
        String string3 = object.getStringExtra("mp_icnm");
        String string4 = object.getStringExtra("mp_icnm_l");
        String string5 = object.getStringExtra("mp_icnm_w");
        String string6 = object.getStringExtra("mp_cta");
        String string7 = object.getStringExtra("mp_title");
        String string8 = object.getStringExtra("mp_color");
        String string9 = object.getStringExtra("mp_campaign_id");
        String string10 = object.getStringExtra("mp_message_id");
        String string11 = object.getStringExtra("mp");
        int n2 = -1;
        this.trackCampaignReceived(string9, string10, string11);
        int n3 = n2;
        if (string8 != null) {
            try {
                n3 = Color.parseColor((String)string8);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                n3 = n2;
            }
        }
        if (string2 == null) {
            return null;
        }
        n2 = n = -1;
        if (string3 != null) {
            n2 = n;
            if (object3.knownIdName(string3)) {
                n2 = object3.idFromName(string3);
            }
        }
        int n4 = n = -1;
        if (string4 != null) {
            n4 = n;
            if (object3.knownIdName(string4)) {
                n4 = object3.idFromName(string4);
            }
        }
        int n5 = n = -1;
        if (string5 != null) {
            n5 = n;
            if (object3.knownIdName(string5)) {
                n5 = object3.idFromName(string5);
            }
        }
        try {
            object3 = packageManager.getApplicationInfo(context.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            object3 = null;
        }
        n = n2;
        if (n2 == -1) {
            n = n2;
            if (object3 != null) {
                n = object3.icon;
            }
        }
        n2 = n;
        if (n == -1) {
            n2 = 17301651;
        }
        String string12 = string7;
        if (string7 == null) {
            String string13 = string7;
            if (object3 != null) {
                CharSequence charSequence = packageManager.getApplicationLabel(object3);
            }
        }
        object3 = var2_8;
        if (var2_8 == null) {
            object3 = "A message for you";
        }
        return new NotificationData(n2, n4, n5, (CharSequence)object3, string2, this.buildNotificationIntent(context, string6, string9, string10, string11), n3);
    }

    protected static class NotificationData {
        public final int color;
        public final int icon;
        public final Intent intent;
        public final int largeIcon;
        public final String message;
        public final CharSequence title;
        public final int whiteIcon;

        private NotificationData(int n, int n2, int n3, CharSequence charSequence, String string2, Intent intent, int n4) {
            this.icon = n;
            this.largeIcon = n2;
            this.whiteIcon = n3;
            this.title = charSequence;
            this.message = string2;
            this.intent = intent;
            this.color = n4;
        }
    }

}

