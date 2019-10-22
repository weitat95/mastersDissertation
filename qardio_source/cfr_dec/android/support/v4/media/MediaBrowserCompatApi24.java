/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompatApi21;
import java.util.List;

class MediaBrowserCompatApi24 {
    public static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy<SubscriptionCallback>(subscriptionCallback);
    }

    static interface SubscriptionCallback
    extends MediaBrowserCompatApi21.SubscriptionCallback {
        public void onChildrenLoaded(String var1, List<?> var2, Bundle var3);

        public void onError(String var1, Bundle var2);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback>
    extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
        public SubscriptionCallbackProxy(T t) {
            super(t);
        }

        public void onChildrenLoaded(String string2, List<MediaBrowser.MediaItem> list, Bundle bundle) {
            ((SubscriptionCallback)this.mSubscriptionCallback).onChildrenLoaded(string2, list, bundle);
        }

        public void onError(String string2, Bundle bundle) {
            ((SubscriptionCallback)this.mSubscriptionCallback).onError(string2, bundle);
        }
    }

}

