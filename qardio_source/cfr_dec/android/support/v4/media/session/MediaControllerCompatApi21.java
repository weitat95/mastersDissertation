/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.AudioAttributes
 *  android.media.MediaMetadata
 *  android.media.session.MediaController
 *  android.media.session.MediaController$Callback
 *  android.media.session.MediaController$PlaybackInfo
 *  android.media.session.MediaSession
 *  android.media.session.MediaSession$QueueItem
 *  android.media.session.MediaSession$Token
 *  android.media.session.PlaybackState
 *  android.os.Bundle
 *  android.os.ResultReceiver
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import java.util.List;

class MediaControllerCompatApi21 {
    public static Object createCallback(Callback callback) {
        return new CallbackProxy<Callback>(callback);
    }

    public static boolean dispatchMediaButtonEvent(Object object, KeyEvent keyEvent) {
        return ((MediaController)object).dispatchMediaButtonEvent(keyEvent);
    }

    public static Object fromToken(Context context, Object object) {
        return new MediaController(context, (MediaSession.Token)object);
    }

    public static void sendCommand(Object object, String string2, Bundle bundle, ResultReceiver resultReceiver) {
        ((MediaController)object).sendCommand(string2, bundle, resultReceiver);
    }

    public static interface Callback {
        public void onAudioInfoChanged(int var1, int var2, int var3, int var4, int var5);

        public void onExtrasChanged(Bundle var1);

        public void onMetadataChanged(Object var1);

        public void onPlaybackStateChanged(Object var1);

        public void onQueueChanged(List<?> var1);

        public void onQueueTitleChanged(CharSequence var1);

        public void onSessionDestroyed();

        public void onSessionEvent(String var1, Bundle var2);
    }

    static class CallbackProxy<T extends Callback>
    extends MediaController.Callback {
        protected final T mCallback;

        public CallbackProxy(T t) {
            this.mCallback = t;
        }

        public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            this.mCallback.onAudioInfoChanged(playbackInfo.getPlaybackType(), PlaybackInfo.getLegacyAudioStream((Object)playbackInfo), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
        }

        public void onExtrasChanged(Bundle bundle) {
            this.mCallback.onExtrasChanged(bundle);
        }

        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            this.mCallback.onMetadataChanged((Object)mediaMetadata);
        }

        public void onPlaybackStateChanged(PlaybackState playbackState) {
            this.mCallback.onPlaybackStateChanged((Object)playbackState);
        }

        public void onQueueChanged(List<MediaSession.QueueItem> list) {
            this.mCallback.onQueueChanged(list);
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
            this.mCallback.onQueueTitleChanged(charSequence);
        }

        public void onSessionDestroyed() {
            this.mCallback.onSessionDestroyed();
        }

        public void onSessionEvent(String string2, Bundle bundle) {
            this.mCallback.onSessionEvent(string2, bundle);
        }
    }

    public static class PlaybackInfo {
        public static AudioAttributes getAudioAttributes(Object object) {
            return ((MediaController.PlaybackInfo)object).getAudioAttributes();
        }

        public static int getLegacyAudioStream(Object object) {
            return PlaybackInfo.toLegacyStreamType(PlaybackInfo.getAudioAttributes(object));
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static int toLegacyStreamType(AudioAttributes audioAttributes) {
            int n = 3;
            if ((audioAttributes.getFlags() & 1) == 1) {
                return 7;
            }
            if ((audioAttributes.getFlags() & 4) == 4) {
                return 6;
            }
            switch (audioAttributes.getUsage()) {
                case 1: 
                case 11: 
                case 12: 
                case 14: {
                    return n;
                }
                default: {
                    return 3;
                }
                case 2: {
                    return 0;
                }
                case 13: {
                    return 1;
                }
                case 3: {
                    return 8;
                }
                case 4: {
                    return 4;
                }
                case 6: {
                    return 2;
                }
                case 5: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
            }
            return 5;
        }
    }

}

