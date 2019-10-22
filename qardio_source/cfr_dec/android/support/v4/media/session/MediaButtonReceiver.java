/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver
extends BroadcastReceiver {
    private static ComponentName getServiceComponentByAction(Context object, String string2) {
        PackageManager packageManager = object.getPackageManager();
        Intent intent = new Intent(string2);
        intent.setPackage(object.getPackageName());
        object = packageManager.queryIntentServices(intent, 0);
        if (object.size() == 1) {
            object = (ResolveInfo)object.get(0);
            return new ComponentName(object.serviceInfo.packageName, object.serviceInfo.name);
        }
        if (object.isEmpty()) {
            return null;
        }
        throw new IllegalStateException("Expected 1 service that handles " + string2 + ", found " + object.size());
    }

    private static void startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
            return;
        }
        context.startService(intent);
    }

    public void onReceive(Context object, Intent object2) {
        if (object2 == null || !"android.intent.action.MEDIA_BUTTON".equals(object2.getAction()) || !object2.hasExtra("android.intent.extra.KEY_EVENT")) {
            Log.d((String)"MediaButtonReceiver", (String)("Ignore unsupported intent: " + object2));
            return;
        }
        ComponentName componentName = MediaButtonReceiver.getServiceComponentByAction((Context)object, "android.intent.action.MEDIA_BUTTON");
        if (componentName != null) {
            object2.setComponent(componentName);
            MediaButtonReceiver.startForegroundService((Context)object, (Intent)object2);
            return;
        }
        componentName = MediaButtonReceiver.getServiceComponentByAction((Context)object, "android.media.browse.MediaBrowserService");
        if (componentName != null) {
            BroadcastReceiver.PendingResult pendingResult = this.goAsync();
            object = object.getApplicationContext();
            object2 = new MediaButtonConnectionCallback((Context)object, (Intent)object2, pendingResult);
            object = new MediaBrowserCompat((Context)object, componentName, (MediaBrowserCompat.ConnectionCallback)object2, null);
            ((MediaButtonConnectionCallback)object2).setMediaBrowser((MediaBrowserCompat)object);
            ((MediaBrowserCompat)object).connect();
            return;
        }
        throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
    }

    private static class MediaButtonConnectionCallback
    extends MediaBrowserCompat.ConnectionCallback {
        private final Context mContext;
        private final Intent mIntent;
        private MediaBrowserCompat mMediaBrowser;
        private final BroadcastReceiver.PendingResult mPendingResult;

        MediaButtonConnectionCallback(Context context, Intent intent, BroadcastReceiver.PendingResult pendingResult) {
            this.mContext = context;
            this.mIntent = intent;
            this.mPendingResult = pendingResult;
        }

        private void finish() {
            this.mMediaBrowser.disconnect();
            this.mPendingResult.finish();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onConnected() {
            try {
                new MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken()).dispatchMediaButtonEvent((KeyEvent)this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT"));
            }
            catch (RemoteException remoteException) {
                Log.e((String)"MediaButtonReceiver", (String)"Failed to create a media controller", (Throwable)remoteException);
            }
            this.finish();
        }

        @Override
        public void onConnectionFailed() {
            this.finish();
        }

        @Override
        public void onConnectionSuspended() {
            this.finish();
        }

        void setMediaBrowser(MediaBrowserCompat mediaBrowserCompat) {
            this.mMediaBrowser = mediaBrowserCompat;
        }
    }

}

