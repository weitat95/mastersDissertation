/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.media.MediaDescription
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$ConnectionCallback
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.media.browse.MediaBrowser$SubscriptionCallback
 *  android.media.session.MediaSession
 *  android.media.session.MediaSession$Token
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;
import java.util.List;

class MediaBrowserCompatApi21 {
    public static void connect(Object object) {
        ((MediaBrowser)object).connect();
    }

    public static Object createBrowser(Context context, ComponentName componentName, Object object, Bundle bundle) {
        return new MediaBrowser(context, componentName, (MediaBrowser.ConnectionCallback)object, bundle);
    }

    public static Object createConnectionCallback(ConnectionCallback connectionCallback) {
        return new ConnectionCallbackProxy<ConnectionCallback>(connectionCallback);
    }

    public static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy<SubscriptionCallback>(subscriptionCallback);
    }

    public static void disconnect(Object object) {
        ((MediaBrowser)object).disconnect();
    }

    public static Bundle getExtras(Object object) {
        return ((MediaBrowser)object).getExtras();
    }

    public static Object getSessionToken(Object object) {
        return ((MediaBrowser)object).getSessionToken();
    }

    static interface ConnectionCallback {
        public void onConnected();

        public void onConnectionFailed();

        public void onConnectionSuspended();
    }

    static class ConnectionCallbackProxy<T extends ConnectionCallback>
    extends MediaBrowser.ConnectionCallback {
        protected final T mConnectionCallback;

        public ConnectionCallbackProxy(T t) {
            this.mConnectionCallback = t;
        }

        public void onConnected() {
            this.mConnectionCallback.onConnected();
        }

        public void onConnectionFailed() {
            this.mConnectionCallback.onConnectionFailed();
        }

        public void onConnectionSuspended() {
            this.mConnectionCallback.onConnectionSuspended();
        }
    }

    static class MediaItem {
        public static Object getDescription(Object object) {
            return ((MediaBrowser.MediaItem)object).getDescription();
        }

        public static int getFlags(Object object) {
            return ((MediaBrowser.MediaItem)object).getFlags();
        }
    }

    static interface SubscriptionCallback {
        public void onChildrenLoaded(String var1, List<?> var2);

        public void onError(String var1);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback>
    extends MediaBrowser.SubscriptionCallback {
        protected final T mSubscriptionCallback;

        public SubscriptionCallbackProxy(T t) {
            this.mSubscriptionCallback = t;
        }

        public void onChildrenLoaded(String string2, List<MediaBrowser.MediaItem> list) {
            this.mSubscriptionCallback.onChildrenLoaded(string2, list);
        }

        public void onError(String string2) {
            this.mSubscriptionCallback.onError(string2);
        }
    }

}

