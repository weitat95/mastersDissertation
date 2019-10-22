/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.media.MediaDescription
 *  android.media.session.MediaSession
 *  android.media.session.MediaSession$QueueItem
 *  android.media.session.MediaSession$Token
 */
package android.support.v4.media.session;

import android.media.MediaDescription;
import android.media.session.MediaSession;

class MediaSessionCompatApi21 {
    public static Object verifyToken(Object object) {
        if (object instanceof MediaSession.Token) {
            return object;
        }
        throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
    }

    static class QueueItem {
        public static Object getDescription(Object object) {
            return ((MediaSession.QueueItem)object).getDescription();
        }

        public static long getQueueId(Object object) {
            return ((MediaSession.QueueItem)object).getQueueId();
        }
    }

}

