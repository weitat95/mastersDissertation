/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.media.session.PlaybackState
 *  android.media.session.PlaybackState$CustomAction
 *  android.os.Bundle
 */
package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.os.Bundle;
import java.util.List;

class PlaybackStateCompatApi21 {
    public static long getActions(Object object) {
        return ((PlaybackState)object).getActions();
    }

    public static long getActiveQueueItemId(Object object) {
        return ((PlaybackState)object).getActiveQueueItemId();
    }

    public static long getBufferedPosition(Object object) {
        return ((PlaybackState)object).getBufferedPosition();
    }

    public static List<Object> getCustomActions(Object object) {
        return ((PlaybackState)object).getCustomActions();
    }

    public static CharSequence getErrorMessage(Object object) {
        return ((PlaybackState)object).getErrorMessage();
    }

    public static long getLastPositionUpdateTime(Object object) {
        return ((PlaybackState)object).getLastPositionUpdateTime();
    }

    public static float getPlaybackSpeed(Object object) {
        return ((PlaybackState)object).getPlaybackSpeed();
    }

    public static long getPosition(Object object) {
        return ((PlaybackState)object).getPosition();
    }

    public static int getState(Object object) {
        return ((PlaybackState)object).getState();
    }

    static final class CustomAction {
        public static String getAction(Object object) {
            return ((PlaybackState.CustomAction)object).getAction();
        }

        public static Bundle getExtras(Object object) {
            return ((PlaybackState.CustomAction)object).getExtras();
        }

        public static int getIcon(Object object) {
            return ((PlaybackState.CustomAction)object).getIcon();
        }

        public static CharSequence getName(Object object) {
            return ((PlaybackState.CustomAction)object).getName();
        }
    }

}

